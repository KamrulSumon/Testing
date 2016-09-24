package com.sumon.api.service;

import java.util.List;
import com.sumon.api.entity.User;

public interface IUserService {

	List<User> findAll();

	User findOne(String id);

	User create(User user);

	User update(String id, User user);

    void delete(String id);

}
