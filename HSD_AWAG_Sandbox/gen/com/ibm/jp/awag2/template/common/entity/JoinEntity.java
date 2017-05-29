/*
 * Automated web application generator
 *
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 * IPSC : 6949-63S
 * (C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
 * (C) Copyright IBM Corp. 2016 All Rights Reserved.
 * US Government Users Restricted Rights -
 * Use, duplication or disclosure restricted
 * by GSA ADP Schedule Contract with IBM Corp.
 *
 */
package com.ibm.jp.awag2.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Join対象のエンティティを示すクラス。
 *
 */
public class JoinEntity {

	/** エンティティ名 */
	private String entityName;
	
	/** Join対象エンティティのMap */
	private Map<String, JoinEntity> joinEntyMap = new HashMap<>();
	
	/**
	 * 唯一のコンストラクタ。
	 * @param entityName エンンティティ名
	 */	
	public JoinEntity(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Join対象のエンティティを追加する。
	 * @param entityName エンンティティ名
	 */	
	public void addJoinEntity(String entityName) {
		this.joinEntyMap.put(entityName, new JoinEntity(entityName));
	}

	/**
	 * Join対象のエンティティを取得する。
	 * @param entityName エンンティティ名
	 * @return Join対象のエンティティ
	 */	
	public JoinEntity getJoinEntity(String entityName) {
		return this.joinEntyMap.get(entityName);
	}

	/**
	 * エンティティ名を取得する。
	 * @return エンティティ名
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * エンティティ名を設定する。
	 * @param entityName エンティティ名
	 */	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Join対象エンティティのMapを取得する。
	 * @return Join対象エンティティのMap
	 */
	public Map<String, JoinEntity> getJoinEntyMap() {
		return joinEntyMap;
	}

	/**
	 * Join対象エンティティのMapを設定する。
	 * @param joinEntyMap Join対象エンティティのMap
	 */
	public void setJoinEntyMap(Map<String, JoinEntity> joinEntyMap) {
		this.joinEntyMap = joinEntyMap;
	}
	
}
