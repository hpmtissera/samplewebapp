package com.example;

import java.util.List;

public interface UserDao
{
    void add(User user);

    List<User> getAll();
}
