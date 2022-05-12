package com.github.dieselniu.wxshop.service;

import com.github.dieselniu.wxshop.dao.UserDao;
import com.github.dieselniu.wxshop.generate.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

	private final UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User createUseIfNotExist(String tel) {
		User user = new User();
		user.setTel(tel);
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		try {
			userDao.insertUser(user);
		} catch (PersistenceException exception) {
			return userDao.getUserByTel(tel);
		}
		return user;

	}
}
