package com.wms.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wms.studio.model.ServerHistory;
import com.wms.studio.repository.ServerHistoryRepository;
import com.wms.studio.service.ServerHistoryService;

/**
 * 
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午4:38:24
 */
@Service("serverHistoryService")
public class ServerHistoryServiceImpl implements ServerHistoryService {

	@Autowired
	private ServerHistoryRepository serverHistoryRepository;
	
	public void setServerHistoryRepository(
			ServerHistoryRepository serverHistoryRepository) {
		this.serverHistoryRepository = serverHistoryRepository;
	}
	
	public void save(ServerHistory sh) {

		this.serverHistoryRepository.save(sh);
	}

	public void save(List<ServerHistory> shs) {

		this.serverHistoryRepository.save(shs);
	}

	public void delete(ServerHistory sh) {

		this.serverHistoryRepository.delete(sh);
	}

	public void delete(List<ServerHistory> shs) {

		this.serverHistoryRepository.delete(shs);
	}

	public void update(ServerHistory sh) {

		this.serverHistoryRepository.save(sh);
	}

	public void update(List<ServerHistory> shs) {

		this.serverHistoryRepository.save(shs);
	}

	public ServerHistory findById(long id) {

		return this.serverHistoryRepository.findOne(id);
	}

	public List<ServerHistory> findAll() {

		return this.serverHistoryRepository.findAll();
	}

	public Page<ServerHistory> findAllByPage(Pageable pageable) {

		return this.serverHistoryRepository.findAll(pageable);
	}

}
