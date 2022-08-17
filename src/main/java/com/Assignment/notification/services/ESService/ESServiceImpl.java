package com.Assignment.notification.services.ESService;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.repositories.MessageESRepo;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ESServiceImpl implements  ESService{

    @Autowired
    private MessageESRepo theESRepo;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    ElasticsearchOperations elasticsearchOps;

    @Override
    public Optional<MessageModelES> getById(String id) {
        return theESRepo.findById(id);
    }

    @Override
    public Page<MessageModelES> getAll() {
        return (Page<MessageModelES>) theESRepo.findAll() ;
    }

    @Override
    public SearchPage<MessageModelES> getByMessage(String someText) {
        return  null;
    }

    @Override
    public List<MessageModelES> getByPhoneNumber(String number) {
        return theESRepo.findAllByPhoneNumber(number);

    }

    @Override
    public List<MessageModelES> getByPhoneNumberAndBetweenDates(String theNumber, Date date1, Date date2) {
//        BoolQueryBuilder criteria = QueryBuilders.boolQuery();
//        criteria.must().addAll(List.of(QueryBuilders.matchQuery("phoneNumber", theNumber), QueryBuilders.matchQuery("createdAt", date1)));
//        return elasticsearchTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(criteria).build(), MessageModelES.class);
        return null;
    }


}
