package com.github.dieselniu.wxshop.dao;

import com.github.dieselniu.wxshop.generate.User;
import com.github.dieselniu.wxshop.generate.UserExample;
import com.github.dieselniu.wxshop.generate.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
	private final UserMapper userMapper;

	public UserDao(UserMapper userMapper) {
		this.userMapper = userMapper;
	}


	public void insertUser(User user) {
		userMapper.insert(user);
	}


	public User getUserByTel(String tel) {
		UserExample example = new UserExample();
		example.createCriteria().andTelEqualTo(tel);
		return userMapper.selectByExample(example).get(0);
	}
}
