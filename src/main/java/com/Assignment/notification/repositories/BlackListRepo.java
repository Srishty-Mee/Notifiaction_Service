package com.Assignment.notification.repositories;

import com.Assignment.notification.model.BlackListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlackListRepo {

    public static final String HASH_KEY = "Black_List";

    @Autowired
    private RedisTemplate template;


    public List<String> save(List<String> theList){
        for(int i=0; i<theList.size() ; i++) {
            String theNumber = theList.get(i);
            BlackListModel temp = new BlackListModel(theNumber);
            template.opsForHash().put(HASH_KEY, theNumber, temp);
        }
        return theList;
    }


    public List<BlackListModel> findAll(){

        return template.opsForHash().values(HASH_KEY);
    }


    public String deleteNumber(List<String> theList){
        for(int i=0; i<theList.size(); i++) {
            String theNumber= theList.get(i);
            System.out.println("removed " + theNumber);
            template.opsForHash().delete(HASH_KEY, theNumber);
        }
        return "number removed !!";
    }


    public BlackListModel check(String theNumber) {
        System.out.println("called from DB");
        return  (BlackListModel) template.opsForHash().get(theNumber, HASH_KEY);
    }

}
