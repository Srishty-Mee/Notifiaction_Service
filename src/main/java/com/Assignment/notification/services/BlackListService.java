package com.Assignment.notification.services;

import com.Assignment.notification.model.BlackListModel;

import java.util.HashMap;
import java.util.List;

public interface BlackListService {
    HashMap<String, List<String> >findAll();


    void save( HashMap<String, List<String>> theList);
}


