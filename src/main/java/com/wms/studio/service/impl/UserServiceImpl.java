package com.wms.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wms.studio.model.User;
import com.wms.studio.repository.UserRepository;
import com.wms.studio.service.UserService;

/**
 * 
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午4:35:09
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void save(User user) {
		
		this.userRepository.save(user);
	}

	public void save(List<User> users) {
		
		this.userRepository.save(users);
	}

	public User findById(String id) {
		
		return this.userRepository.findOne(id);
	}

	public void delete(User user) {
		
		this.userRepository.delete(user);
	}

	public void delete(List<User> users) {
		
		this.userRepository.delete(users);
	}

	public void update(User user) {
		
		this.userRepository.save(user);
	}

	public List<User> findAll() {
		
		return this.userRepository.findAll();
	}

	public Page<User> findAllByPage(Pageable pageable) {
		
		return this.userRepository.findAll(pageable);
	}

}
