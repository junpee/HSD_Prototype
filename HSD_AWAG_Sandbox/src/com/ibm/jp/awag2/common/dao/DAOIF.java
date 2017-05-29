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

import java.util.List;
import java.util.Optional;

import com.ibm.jp.awag2.common.dao.DAOParameter;
import com.ibm.jp.awag2.common.entity.EntityBase;
import com.ibm.jp.awag2.common.entity.JoinEntity;
import com.ibm.jp.awag2.common.entity.PKBase;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;

/**
 * DAOクラスのインターフェース。
 * 
 * @param <T> Entity型パラメータ
 * @param <S> PK型パラメータ
 */
public interface DAOIF <T extends EntityBase<?, ?>, S extends PKBase>{
	
	/**
	 * 主キーを指定してEntityを取得する。（Join対象指定）
	 * @param pk 主キーを格納したPKオブジェクト
	 * @param joinEntityList Join対象の関連Entity
	 * @return 取得結果。0件の場合null
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public Optional<T> find(S pk, List<JoinEntity> joinEntityList) throws ServiceDBException, ServiceAppException ;
	
	/**
	 * 主キーを指定してEntityを取得する。
	 * @param param 主キーを格納したPKオブジェクト
	 * @return 取得結果。0件の場合null
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public Optional<T> find(S param) throws ServiceDBException, ServiceAppException ;

	/**
	 * 検索条件を指定してEntityを取得する。
	 * @param daoParam 検索条件を格納したDAOParameterオブジェクト
	 * @param joinEntityList joinEntityList Join対象の関連Entity
	 * @param isInclusiveOr 検索条件をORで結ぶ場合true、ANDで結ぶ場合false
	 * @param maxResults 取得件数
	 * @return 取得結果。0件の場合、空のリスト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public List<T> findList(DAOParameter daoParam, List<JoinEntity> joinEntityList, boolean isInclusiveOr, Integer maxResults) throws ServiceDBException, ServiceAppException;
	
	/**
	 * 単一のEntityを登録する。
	 * @param entity 登録するEntityオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public  void insert(T entity) throws ServiceDBException, ServiceAppException ;

	/**
	 * 複数のEntityを更新する。
	 * @param entityList 更新内容を格納したEntityオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public void insertList(List<T> entityList)  throws ServiceDBException, ServiceAppException ;
	
	/**
	 * 単一のEntityを更新する。
	 * @param entity 更新するEntityオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public void update(T entity) throws ServiceDBException, ServiceAppException ;

	/**
	 * 複数のEntityを更新する。
	 * @param entityList 更新するEntityのリストオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public void updateList(List<T> entityList)  throws ServiceDBException, ServiceAppException ;
	
	/**
	 * 単一のEntityを削除する。
	 * @param entity 削除するEntityオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public void delete(T entity) throws ServiceDBException, ServiceAppException ;

	/**
	 * 複数のEntityを削除する。
	 * @param entityList 削除内容を格納したEntityオブジェクト
	 * @throws ServiceDBException
	 * @throws ServiceAppException
	 */
	public void deleteList(List<T> entityList)  throws ServiceDBException, ServiceAppException ;
}
