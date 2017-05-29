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
package com.ibm.jp.awag2.common.dao;

import java.util.HashMap;
import java.util.Map;

import com.ibm.jp.awag2.common.dao.DAOBase.SelectWhereOperator;

/**
 * DAOの検索条件を示すクラス。
 *
 */
public class DAOParameter {
	
	/** 検索条件リスト */
	private Map<String, DAOCondition> parameterMap = new HashMap<>();
	
	/**
	 * 検索条件を追加する。
	 * 
	 * @param parameterName 検索項目
	 * @param daoCondition 検索条件
	 */
	public void set(String parameterName, DAOCondition daoCondition) {
		parameterMap.put(parameterName, daoCondition);
	}
	
	/**
	 * 検索条件を追加する。（WHERE句オペレータは"="となる）
	 * 
	 * @param parameterName 検索項目
	 * @param value 検索値
	 */
	public void set(String parameterName, String value) {
		parameterMap.put(parameterName, new DAOCondition(value));
	}
	
	/**
	 * 検索条件を追加する。
	 * 
	 * @param parameterName 検索項目
	 * @param selectWhereParameter WHERE句オペレータ
	 * @param value 検索値
	 */
	public void set(String parameterName, SelectWhereOperator selectWhereParameter, String value) {
		parameterMap.put(parameterName, new DAOCondition(selectWhereParameter, value));
	}
	
	/**
	 * 検索条件を取得する。
	 * 
	 * @param parameterName 検索項目
	 * @return 検索条件
	 */
	public DAOCondition get(String parameterName) {
		return parameterMap.get(parameterName);
	}
	
	/**
	 * 検索条件の数を取得する。
	 * 
	 * @return 検索条件の数
	 */
	public int size() {
		return parameterMap.size();
	}
	
	/**
	 * 検索条件を取得する。
	 * 
	 * @return 検索条件のMap
	 */
	public Map<String, DAOCondition> getParameterMap() {
		return this.parameterMap;
	}
}
