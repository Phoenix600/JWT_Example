package com.pranay.myJWTExample.services;

import com.pranay.myJWTExample.model.User;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {
    private List<User> store = new LinkedList<>();

    public UserService()
    {
        store.add(new User(UUID.randomUUID().toString(),"pranay","thisispranayramteke@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"divya","divyamalhotra@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"rajesh","rajeshshiyal@gmail.com"));
    }

    public List<User> getUsers()
    {
        return store;
    }
}
