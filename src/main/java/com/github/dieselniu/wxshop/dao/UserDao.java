package com.github.dieselniu.wxshop.dao;

import com.github.dieselniu.wxshop.generate.User;
import com.github.dieselniu.wxshop.generate.UserExample;
import com.github.dieselniu.wxshop.generate.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

	private final SqlSessionFactory sqlSessionFactory;

	public UserDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	public void insertUser(User user) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			mapper.insert(user);
		}
	}

	public User getUserByTel(String tel) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			UserExample example = new UserExample();
			example.createCriteria().andTelEqualTo(tel);
			return mapper.selectByExample(example).get(0);
		}
	}
}
