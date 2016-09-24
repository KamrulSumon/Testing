package com.sumon.api.repository;

import java.util.List;

import com.sumon.api.entity.User;

public interface IUserRepository {

	public List<User> findAll();

	public User findOne(String id);

	public User findByEmail(String email);

	public User create(User user);

	public User update(User user);

	public void delete(User user);

}
