package com.Assignment.notification.services.ESService;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;
import com.Assignment.notification.repositories.MessageESRepo;

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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ESServiceImpl implements  ESService{

    private static final String SMS_INDEX = "sms_services";
    @Autowired
    private MessageESRepo theESRepo;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    ElasticsearchOperations elasticsearchOps;

    public static final int DEFAULT_PAGE_SIZE= 5;

    Logger LOOGER = LoggerFactory.getLogger(ESServiceImpl.class);


    @Override
    public Optional<MessageModelES> getById(String id) {
        return theESRepo.findById(id);
    }

    @Override
    public Page<MessageModelES> getAll() {
        return (Page<MessageModelES>) theESRepo.findAll() ;
    }


    @Override
    public List<MessageModelES> getByPhoneNumber(String number) {
        return theESRepo.findAllByPhoneNumber(number);

    }

    @Override
    public List<MessageModelES> getByPhoneNumberAndBetweenDates(SearchByDateAndNumberModel theRequest) throws Exception {
        Date theStartDate= new SimpleDateFormat("dd/MM/yyyy").parse(theRequest.getStartDate());
        Date theEndDate= new SimpleDateFormat("dd/MM/yyyy").parse(theRequest.getEndDate());

        int pageNo= theRequest.getPageNo()-1;

        if (theEndDate.compareTo(theStartDate) < 0 ){
            throw new Exception("Start Date is after End Date");
        }

        LOOGER.info(String.valueOf(theStartDate));
        LOOGER.info(String.valueOf(theEndDate));

        Criteria theCriteria1 = new Criteria ("phoneNumber").matches(theRequest.getPhoneNumber());
        Criteria theCriteria2 = new Criteria("createdAt").between(theStartDate, theEndDate).and(theCriteria1);

        Query searchQuery = new CriteriaQuery(theCriteria2)
                .setPageable(PageRequest.of(pageNo, DEFAULT_PAGE_SIZE));

        SearchHits<MessageModelES> smsRecordSearchHits = elasticsearchOps
                .search(searchQuery, MessageModelES.class, IndexCoordinates.of(SMS_INDEX));

        if (smsRecordSearchHits.getTotalHits() <= pageNo * DEFAULT_PAGE_SIZE) {
            throw new Exception("No more results to show");
        }
        return smsRecordSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }



    @Override
    public List<MessageModelES> findSmsContainsText(SearchByTextModel theRequest) throws Exception {

        String theSearchText= theRequest.getText();
        int pageNo= theRequest.getPageNo()-1;
        String search= "*" + theSearchText.toLowerCase()+"*";

        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("message", search);

        Query searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder)
                .withPageable(PageRequest.of(pageNo, DEFAULT_PAGE_SIZE))
                .build();

        SearchHits<MessageModelES> smsRecordSearchHits = elasticsearchOps
                .search(searchQuery, MessageModelES.class, IndexCoordinates.of(SMS_INDEX));

        LOOGER.info("The text '" + theSearchText + "' found in "+
                String.valueOf(smsRecordSearchHits.getTotalHits())+ " records.");

        LOOGER.info("Displaying page number " + String.valueOf(pageNo+1));

        if (smsRecordSearchHits.getTotalHits() <= pageNo * DEFAULT_PAGE_SIZE) {
            throw new Exception("No more results to show");
        }
        return smsRecordSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }

}
