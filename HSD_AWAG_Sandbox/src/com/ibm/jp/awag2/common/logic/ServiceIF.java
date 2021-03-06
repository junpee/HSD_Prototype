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
package com.ibm.jp.awag2.common.logic;

import java.util.Map;

import com.ibm.jp.awag2.common.dto.DTOBase;

/**
 * Serviceクラスのインターフェース。
 *
 * @param <T> 入力DTO型パラメータ
 * @param <S> 出力DTO型パラメータ
 */
public interface ServiceIF <T extends DTOBase, S extends DTOBase> {
	
	/**
	 * ビジネス・ロジックを実行する。
	 * @param input 入力DTO
	 * @return 出力DTO
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public S execute(T input) throws ServiceDBException, ServiceAppException;
	
}
