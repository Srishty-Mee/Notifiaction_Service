package com.Assignment.notification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
@Primary
public class BlackListServiceImpl implements BlackListService{

    private EntityManager theEntityManager;

    @Autowired
    public BlackListServiceImpl(EntityManager entityManager){
        theEntityManager=entityManager;
    }
    @Override
    public HashMap< String, List<String>> findAll() {

        Query theQuery = theEntityManager.createQuery("select phoneNumber from BlackListModel");
        List<String> blackList= theQuery.getResultList();

        HashMap<String, List<String>>res = new HashMap<>();
        res.put("phoneNumbers", blackList);
        return res;
    }

    @Override
    public void save(HashMap<String, List<String>> theList) {

    }


}
