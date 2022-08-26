package com.Assignment.notification.services.esService;

import com.Assignment.notification.customExceptions.BadRequestException;
import com.Assignment.notification.customExceptions.NotFoundException;
import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;
import com.Assignment.notification.repositories.MessageESRepo;

import com.Assignment.notification.services.otherServices.HelperService;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ESServiceImpl implements  ESService{

    private static final String ES_INDEX = "sms_services";
    @Autowired
    private MessageESRepo theESRepo;

    @Autowired
    ElasticsearchOperations elasticsearchOps;

    @Autowired
    private HelperService theHelperService;

    public static final int PAGE_SIZE= 3;

    Logger LOGGER = LoggerFactory.getLogger(ESServiceImpl.class);

    @Override
    public Optional<MessageModelES> getById(String id) {
        Optional<MessageModelES> res;
        try {
            res= theESRepo.findById(id);
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.ES_ERROR, "Something went wrong in ElasticSearch");
        }
        if (res.isEmpty())
            throw new NotFoundException(CustomErrorCodes.INVALID_ID, "No details for the given id found");
        return res;
    }

    @Override
    public Page<MessageModelES> getAll() {
        Page<MessageModelES> res;
        try {
            res= (Page<MessageModelES>) theESRepo.findAll();
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.ES_ERROR, "Something went wrong in ElasticSearch");
        }
        if (res.isEmpty())
            throw new NotFoundException(CustomErrorCodes.INVALID_ID, "No details for the given id found");
        return res;

    }

    @Override
    public List<MessageModelES> getByPhoneNumberAndBetweenDates(SearchByDateAndNumberModel theRequest) throws ParseException {

        long theEndDate, theStartDate;
        try {
            theStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(theRequest.getStartDate()).getTime();
            theEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(theRequest.getEndDate()).getTime();
        } catch (Exception ex) {
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Enter proper start and end date");
        }

        int pageNo = theRequest.getPageNo() - 1;

        if (theEndDate < theStartDate) {
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Start date is after End date");
        }

        boolean isNumberValid = theHelperService.validNumber(theRequest.getPhoneNumber());
        if (!isNumberValid)
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Invalid Phone Number");


        try {
            Criteria theCriteria1 = new Criteria("phoneNumber").matches(theRequest.getPhoneNumber());
            Criteria theCriteria2 = new Criteria("createdAt").
                    greaterThanEqual(theStartDate).lessThanEqual(theEndDate).and(theCriteria1);

            Query searchQuery = new CriteriaQuery(theCriteria2)
                    .setPageable(PageRequest.of(pageNo, PAGE_SIZE));

            SearchHits<MessageModelES> smsRecordSearchHits = elasticsearchOps
                    .search(searchQuery, MessageModelES.class, IndexCoordinates.of(ES_INDEX));
            LOGGER.info(String.valueOf(smsRecordSearchHits.getTotalHits()) + " match found.");

            if (smsRecordSearchHits.getTotalHits() <= pageNo * PAGE_SIZE) {
                throw new NotFoundException(CustomErrorCodes.NO_DATA_FOUND, "No more results to show");
            }
            return smsRecordSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        } catch (ServiceException ex) {throw ex;
        } catch (NotFoundException ex) {throw ex;
        } catch (Exception ex) {
            throw new ServiceException(CustomErrorCodes.ES_ERROR, "Something went wrong in ElasticSearch");
        }

    }



    @Override
    public List<MessageModelES> getByText(SearchByTextModel theRequest) {

        String theSearchText= theRequest.getText();

        if(theSearchText.length()==0)
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "text field can not be empty");

        int pageNo= theRequest.getPageNo()-1;
        String search= "*" + theSearchText.toLowerCase()+"*";
        try {

            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("message", search);
            Query searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder)
                    .withPageable(PageRequest.of(pageNo, PAGE_SIZE)).build();

            SearchHits<MessageModelES> smsRecordSearchHits = elasticsearchOps
                    .search(searchQuery, MessageModelES.class, IndexCoordinates.of(ES_INDEX));

            LOGGER.info("The text '" + theSearchText + "' found in " +
                    String.valueOf(smsRecordSearchHits.getTotalHits()) + " records.");

            LOGGER.info("Displaying page number " + String.valueOf(pageNo ));
            LOGGER.info("Displaying page number " + String.valueOf(pageNo + 1));

            if (smsRecordSearchHits.getTotalHits() <= pageNo * PAGE_SIZE) {
                throw new NotFoundException(CustomErrorCodes.NO_DATA_FOUND, "No more results to show");
            }
            return smsRecordSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        }
        catch (ServiceException ex) {throw ex;}
        catch (NotFoundException ex) {throw ex;}
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.ES_ERROR, "Something went wrong in ElasticSearch");
        }
    }

}
