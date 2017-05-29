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
package com.ibm.jp.awag.rest.common.logic;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.ibm.jp.awag.rest.common.logic.ServiceDBException.DBErrorType;
import com.ibm.jp.awag.rest.common.model.EntityBase;
import com.ibm.jp.awag.rest.common.model.PKBase;
import com.ibm.jp.awag.rest.common.resource.RequestContext;
import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;

/**
 * サービスクラスの基底クラス。
 *
 */
public abstract class ServiceBase {

	/** EntityManager */
	@PersistenceContext
	private EntityManager em;

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public ServiceBase() {
		super();
	}

	/**
	 * EntityManagerを指定してインスタンスを初期化する。
	 * @param entityManager
	 */
	public ServiceBase(EntityManager entityManager) {
		this.em = entityManager;
	}

	/**
	 * EntityManagerを取得する。
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager() {
		return em;
	}
	
	/**
	 * Loggerを取得する。
	 * @return Logger
	 */
	protected Logger getLogger() {	
		return LoggerFactory.getLogger(this);
	}
	
	/**
	 * RequestContextオブジェクトを取得する。
	 * @return RequestCOntextオブジェクト
	 */
	protected RequestContext getRequestContext() {
		return requestContext;
	}

	/**
	 * RequestCntextオブジェクトを設定する。
	 * @param requestContext RequestCOntextオブジェクト
	 */
	protected void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}
	
	/**
	 * 主キーを指定してEntityを検索する。
	 * @param tClass 検索対象のEntityクラス
	 * @param pk 主キーを格納したPKクラス・オブジェクト
	 * @return 検索結果
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> T getByPK(Class<T> tClass, TPK pk) throws ServiceDBException {

		try {
			return em.find(tClass, pk);
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		}
	}

	/**
	 * Entityを登録する。
	 * @param bean 登録内容を格納したEntityクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> void create(T bean) throws ServiceDBException {

		try {
			em.persist(bean);
			em.flush();
		} catch (EntityExistsException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.DUPPLICATE, "_EDB00002", e);
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00003", e);
		}
	}

	/**
	 * Entityを更新する。
	 * @param bean 更新内容を格納したEntityクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合
	 * @throws ServiceAppException 
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> void updateByPK(T bean) throws ServiceDBException {

		try {
			em.merge(bean);
			em.flush();
		} catch (OptimisticLockException | NoResultException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTICLOCK, "_EDB00004", e);
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00005", e);
		}
	}
	
	/**
	 * 主キーを指定してEntityを削除する。
	 * @param tClass 削除対象のEntityクラス
	 * @param pk 主キーを格納したPKクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> void deleteByPK(Class<T> tClass, TPK pk) throws ServiceDBException {
		T targetBean = em.find(tClass, pk);

		if (targetBean != null) {

			try {
				em.remove(targetBean);
				em.flush();
			} catch (OptimisticLockException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTICLOCK, "_EDB00004", e);
			} catch (PersistenceException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00006", e);
			}
		} else {
			throw createServiceDBException(ServiceDBException.DBErrorType.DELETED, "_EDB00007", null);
		}
	}
	
	/**
	 * 主キーを指定してEntityを削除する。
	 * @param tClass 削除対象のEntityクラス
	 * @param pk 主キーを格納したPKクラス・オブジェクト
	 * @param bean 削除対象のversionを格納したEntityクラス
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> void deleteByPK(Class<T> tClass, TPK pk, VERSIONTYPE version) throws ServiceDBException {
		T targetBean = em.find(tClass, pk);

		if (targetBean != null) {
			try {
				if (version == null) {
					throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTICLOCK, "_EDB00004", null);
				}

				targetBean.set_resourceVersion(version);
				em.remove(targetBean);
				em.flush();
			} catch (OptimisticLockException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTICLOCK, "_EDB00004", e);
			} catch (PersistenceException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00006", e);
			}
		} else {
			throw createServiceDBException(ServiceDBException.DBErrorType.DELETED, "_EDB00007", null);
		}
	}
	
	/**
	 * 複数のEntityのCRUDを一括実行する。
	 * @param beanList 更新対象のEntityオブジェクトを含むリスト
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	public <T extends EntityBase<TPK, VERSIONTYPE>, TPK extends PKBase, VERSIONTYPE> void updateList(List<T> beanList) throws ServiceDBException {
		
		if (beanList != null) {
			for (T bean : beanList) {
				
				if (bean.get_Action() == null) {
					continue;
				}
				switch (bean.get_Action()) {
				case "a" :
					this.create(bean);
					break;
				case "u" :
					this.updateByPK(bean);
					break;
				case "d" :
					this.deleteByPK(bean.getClass(), bean.getPk(), bean.get_resourceVersion());
					break;
				default :
					break;
				}
			}
		}
	}
	
	/**
	 * カスタム検索の最大取得件数を取得する。
	 * コード生成時に取得件数を指定しなかったリソースで使用する。
	 * @return　カスタム検索の取得件数
	 */
	protected int getResultMaxNum() {
		return 500;
	}	
	
	/**
	 * ServiceDBExceptionを生成する。
	 * @param dbErrorType DBエラータイプ
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 * @return ServiceDBException
	 */
	protected ServiceDBException createServiceDBException(DBErrorType dbErrorType, String errorCode, Exception cause) {
		
		ServiceDBException ex = null;
		
		if (requestContext.getLocale() != null) {
			ex = new ServiceDBException(dbErrorType, errorCode, cause, requestContext.getLocale());
		} else {
			ex = new ServiceDBException(dbErrorType, errorCode, cause);
		}
		
		return ex;
	}

}
