/**
 * 
 */
package com.wms.studio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wms.studio.model.ServerHistory;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:40:00
 */
public interface ServerHistoryRepository extends JpaRepository<ServerHistory, Long> {

}
