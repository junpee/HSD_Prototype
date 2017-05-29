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

import com.ibm.jp.awag2.common.dao.DAOBase.SelectWhereOperator;

/**
 * 単一項目の検索条件を表すクラス。
 *
 */
public class DAOCondition {
	
	/** SELECT文のWHERE句オペレータ */
	private SelectWhereOperator selectWhereParameter;
	
	/** 検索パラメータ */
	private String value;
	
	/**
	 * デフォルト・コンストラクタ。
	 */
	public DAOCondition() {
		
	}
	
	/**
	 * WHERE句オペレータと検索パラメータを指定してオブジェクトを初期化する。
	 * 
	 * @param selectWhereParameter
	 * @param value
	 */
	public DAOCondition(SelectWhereOperator selectWhereParameter, String value) {
		this.selectWhereParameter = selectWhereParameter;
		this.value = value;
	}
	
	/**
	 * 検索パラメータを指定してオブジェクトを初期化する。WHERE句オペレータは"="イコールとなる。
	 * 
	 * @param value
	 */
	public DAOCondition(String value) {
		this.selectWhereParameter = SelectWhereOperator.EQUALS;
		this.value = value;
	}
	
	/**
	 * SELECT文のWHERE句オペレータを取得する。
	 * @return SELECT文のWHERE句オペレータ
	 */
	public SelectWhereOperator getSelectWhereParameter() {
		return this.selectWhereParameter;
	}
	
	/**
	 * SELECT文のWHERE句オペレータを設定する。
	 * @param selectWhereParameter 設定するSELECT文のWHERE句オペレータ
	 */
	public void setSelectWhereParameter(SelectWhereOperator selectWhereParameter) {
		this.selectWhereParameter = selectWhereParameter;
	}
	
	/**
	 * 検索パラメータを取得する。
	 * @return 検索パラメータ
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * 検索パラメータを設定する。
	 * @param value 設定する検索パラメータ
	 */
	public void setValue(String value) {
		this.value = value;
	}	
}
