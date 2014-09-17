/**
 * 
 */
package com.wms.studio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wms.studio.model.ServerHistory;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午4:32:31
 */
public interface ServerHistoryService {

	public void save(ServerHistory sh);
	
	public void save(List<ServerHistory> shs);
	
	public void delete(ServerHistory sh);
	
	public void delete(List<ServerHistory> shs);
	
	public void update(ServerHistory sh);
	
	public void update(List<ServerHistory> shs);
	
	public ServerHistory findById(long id);
	
	public List<ServerHistory> findAll();
	
	public Page<ServerHistory> findAllByPage(Pageable pageable);
}
