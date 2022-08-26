package com.Assignment.notification.service;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.repositories.MessageESRepo;
import com.Assignment.notification.services.esService.ESServiceImpl;
import com.Assignment.notification.services.otherServices.HelperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public  class ESServiceTest {

    @Mock
    private MessageESRepo theESRepo;

    @Mock
    ElasticsearchOperations elasticsearchOps;

    @Mock
    private HelperService theHelperService;

    @InjectMocks
    private ESServiceImpl underTest;

    @Test
    void getAllTest() {
        String msg ="hello";
        MessageModelES detail1 = new MessageModelES("0","+919191919191","Welcome To Meesho!");
        MessageModelES detail2 = new MessageModelES("1","+919191919191","Welcome To Meesho!");
        List<MessageModelES> list2 = new ArrayList<>() ;
        list2.add(detail1);
        list2.add(detail2);
        Page<MessageModelES> page = new PageImpl<>(list2);
        Mockito.when(theESRepo.findAll()).thenReturn(page);
        Assertions.assertEquals(page.getSize(),underTest.getAll().getSize());
    }


}
