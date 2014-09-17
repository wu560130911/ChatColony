/**
 * 
 */
package com.wms.studio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wms.studio.model.User;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:59:18
 */
public interface UserService {

	public void save(User user);
	
	public void save(List<User> users);
	
	public User findById(String id);
	
	public void delete(User user);
	
	public void delete(List<User> users);
	
	public void update(User user);
	
	public List<User> findAll();
	
	public Page<User> findAllByPage(Pageable pageable);
}
