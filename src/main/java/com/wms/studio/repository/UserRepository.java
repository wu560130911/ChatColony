/**
 * 
 */
package com.wms.studio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wms.studio.model.User;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:38:33
 */
public interface UserRepository extends JpaRepository<User, String> {

}
