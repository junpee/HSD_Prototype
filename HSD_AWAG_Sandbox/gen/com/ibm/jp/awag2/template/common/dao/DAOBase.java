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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ibm.jp.awag2.common.entity.EntityBase;
import com.ibm.jp.awag2.common.entity.JoinEntity;
import com.ibm.jp.awag2.common.entity.PKBase;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.logic.ServiceDBException.DBErrorType;
import com.ibm.jp.awag2.common.resource.RequestContext;
import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;

/**
 * DAOクラスの基底クラス。
 * JPAを使用した基本的なCRUD処理を提供します。
 *
 * @param <T> Entity型パラメータ
 * @param <S> PK型パラメータ
 * @param <U> Version型パラメータ
 */
public class DAOBase<T extends EntityBase<S, U>, S extends PKBase, U> {

	/**
	 * SELECT時のWHERE句のオペレータを示すEnumクラス。
	 *
	 */
	public enum SelectWhereOperator {
		EQUALS,
	    /**
	     * @deprecated Use NOT_EQUALS instead.
	     */
		@Deprecated
		NOTEQUALS,
	    /**
	     * @deprecated Use GREATER_THAN instead.
	     */
		@Deprecated
		GREATERTHAN,
	    /**
	     * @deprecated Use GREATER_EQUAL instead.
	     */
		@Deprecated
		GREATEREQUAL,
	    /**
	     * @deprecated Use LESS_THAN instead.
	     */
		@Deprecated
		LESSTHAN,
	    /**
	     * @deprecated Use LESS_EQUAL instead.
	     */
		@Deprecated
		LESSEQUAL,
	    /**
	     * @deprecated Use STARTS_WITH instead.
	     */
		@Deprecated
		STARTSWITH,
	    /**
	     * @deprecated Use ENDS_WITH instead.
	     */
		@Deprecated
		ENDSWITH,
		CONTAINS,
		//TRANSITION: V1-V2 以降対応（snake_caseへの変更）
		NOT_EQUALS,
		GREATER_THAN,
		GREATER_EQUAL,
		LESS_THAN,
		LESS_EQUAL,
		STARTS_WITH,
		ENDS_WITH
		;
	}
	
	/** EntityManager */
	@PersistenceContext
	private EntityManager em;

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public DAOBase() {
		super();
	}

	/**
	 * EntityManagerを指定してインスタンスを初期化する。
	 * @param entityManager 初期化に使用するEntityManager
	 */
	public DAOBase(EntityManager entityManager) {
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
		return LoggerFactory.getInstance().getLogger(this);
	}
	
	/**
	 * RequestContextオブジェクトを取得する。
	 * @return RequestContextオブジェクト
	 */
	protected RequestContext getRequestContext() {
		return requestContext;
	}

	/**
	 * RequestContextオブジェクトを設定する。
	 * @param requestContext RequestContextオブジェクト
	 */
	protected void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}
	
	/**
	 * 主キーを指定してEntityを検索する。
	 * @param tClass 検索対象のEntityクラス
	 * @param pk 主キーを格納したPKクラス・オブジェクト
	 * @return 検索結果 検索結果が無い場合{@code null}を返す
	 * @throws ServiceDBException DB例外が発生した場合、ServiceDBExceptionにはDBErrorType.OTHERを設定する。
	 */
	protected Optional<T> findEntity(Class<T> tClass, S pk) throws ServiceDBException {

		try {
			return Optional.ofNullable(em.find(tClass, pk));
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		} catch (RuntimeException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		}
	}
	
	/**
	 * Entityを登録する。
	 * @param bean 登録内容を格納したEntityクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合。ServiceDBExceptionには、重複エラーの場合DBErrorType.DUPPLICATEを、それ以外のエラーの場合DBErrorType.OTHERを設定する。
	 */
	protected void persistEntity(T bean) throws ServiceDBException {

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
	 * 複数のEntityを一括登録する。
	 * @param beanList 登録対象のEntityオブジェクトを含むリスト。beanListが{@code null}の場合何も処理しない。
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	protected void persistList(List<T> beanList) throws ServiceDBException {
		if (beanList != null) {
			for (T bean : beanList) {
				this.persistEntity(bean);
			}
		}	
	}

	/**
	 * Entityを更新する。
	 * @param bean 更新内容を格納したEntityクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合。ServiceDBExceptionには、楽観ロックエラーの場合DBErrorType.OPTIMISTIC_LOCKを、それ以外のエラーの場合DBErrorType.OTHERを設定する。
	 */
	protected void mergeEntity(T bean) throws ServiceDBException {

		try {
			em.merge(bean);
			em.flush();
		} catch (OptimisticLockException | NoResultException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTIC_LOCK, "_EDB00004", e);
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00005", e);
		}
	}

	/**
	 * 複数のEntityを一括更新する。（UPDATEのみ対応）
	 * @param beanList 更新対象のEntityオブジェクトを含むリスト。beanListが{@code null}の場合何も処理しない。
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	protected void mergeList(List<T> beanList) throws ServiceDBException {

		if (beanList != null) {
			for (T bean : beanList) {
				this.mergeEntity(bean);
			}
		}
	}
	
	/**
	 * 主キーを指定してEntityを削除する。
	 * @param tClass 削除対象のEntityクラス
	 * @param pk 主キーを格納したPKクラス・オブジェクト
	 * @throws ServiceDBException DB例外が発生した場合。ServiceDBExceptionには、楽観ロックエラーの場合DBErrorType.OPTIMISTIC_LOCKを、それ以外のエラーの場合DBErrorType.OTHERを設定する。
	 */
	protected void removeEntity(Class<T> tClass, S pk) throws ServiceDBException {
		T targetBean = em.find(tClass, pk);

		if (targetBean != null) {

			try {
				em.remove(targetBean);
				em.flush();
			} catch (OptimisticLockException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTIC_LOCK, "_EDB00004", e);
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
	 * @throws ServiceDBException DB例外が発生した場合。ServiceDBExceptionには、楽観ロックエラーの場合DBErrorType.OPTIMISTIC_LOCKを、それ以外のエラーの場合DBErrorType.OTHERを設定する。
	 */
	protected void removeEntity(Class<T> tClass, S pk, U version) throws ServiceDBException {
		T targetBean = em.find(tClass, pk);

		if (targetBean != null) {
			try {
				if (version == null) {
					throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTIC_LOCK, "_EDB00004", null);
				}

				targetBean.set_resourceVersion(version);
				em.remove(targetBean);
				em.flush();
			} catch (OptimisticLockException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OPTIMISTIC_LOCK, "_EDB00004", e);
			} catch (PersistenceException e) {
				throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00006", e);
			}
		} else {
			throw createServiceDBException(ServiceDBException.DBErrorType.DELETED, "_EDB00007", null);
		}
	}
	
	/**
	 * 複数のEntityを一括登録する。
	 * @param beanList 登録対象のEntityオブジェクトを含むリスト
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	protected void removeList(List<T> beanList) throws ServiceDBException {
		if (beanList != null) {
			for (T bean : beanList) {
				this.removeEntity((Class<T>) bean.getClass(),  bean.getPk(), bean.get_resourceVersion());
			}
		}	
	}
	
	/**
	 * 複数のEntityのCRUDを一括実行する。
	 * @deprecated AWAG1使用のメソッド。将来的に削除される可能性がある。
	 * 
	 * @param beanList 更新対象のEntityオブジェクトを含むリスト
	 * @throws ServiceDBException DB例外が発生した場合
	 */
	protected void crudList(List<T> beanList) throws ServiceDBException {
		
		if (beanList != null) {
			for (T bean : beanList) {
				
				if (bean.get_Action() == null) {
					continue;
				}
				switch (bean.get_Action()) {
				case "a" :
					this.persistEntity(bean);
					break;
				case "u" :
					this.mergeEntity(bean);
					break;
				case "d" :
					this.removeEntity((Class<T>) bean.getClass(), bean.getPk(), bean.get_resourceVersion());
					break;
				default :
					break;
				}
			}
		}
	}
	
	/**
	 * 指定されたJOIN対象のエンティティの階層に従い再帰的にCriteria QueryのJoin Fetchを実行する。
	 * @param root JOIN対象rootオブジェクト。最上位の親から呼び出す場合のみ設定する。それ以外は{@code null}を設定する。
	 * @param fetch 再帰呼び出し時のJOIN対象Fetchオブジェクト
	 * @param joinEntity JOIN対象のエンティティ名称を階層的に保持したJoinEntity
	 */
	protected void recursiveJoin(Root<?> root, Fetch<?,?> fetch, JoinEntity joinEntity) {
		
		if (joinEntity != null) {
			
			// Rootからの呼び出し時、再帰呼び出し時で場合分け
			Fetch<?,?> fetchTmp = null;
			if (root != null) {
				fetchTmp = root.fetch(joinEntity.getEntityName(), JoinType.LEFT);
			} else {
				fetchTmp = fetch.fetch(joinEntity.getEntityName(), JoinType.LEFT);
			}
			
			// 従属JOIN先がある場合、再帰呼び出し
			for (Entry<String, JoinEntity> eachJoinEntity : joinEntity.getJoinEntyMap().entrySet()) {
				recursiveJoin(null, fetchTmp, eachJoinEntity.getValue());
			}
		}
	}

	/**
	 * 指定された検索条件に応じたWHERE句オペレータをCriteriaQueryに設定する。
	 * 
	 * @param criteriaBuilder WHERE句オペレータを設定するCriteriaBuilder
	 * @param path 検索対象を格納したPathオブジェクト
	 * @param daoCondition 検索条件を格納したDAOConditionオブジェクト
	 * @return 指定したオペレータを表すPredicateオブジェクト
	 */
	protected Predicate createPredicate(CriteriaBuilder criteriaBuilder, Path<?> path, DAOCondition daoCondition) {

		Predicate retPred = null;
		
		switch (daoCondition.getSelectWhereParameter()) {
		case EQUALS:
			retPred =  criteriaBuilder.equal(path, daoCondition.getValue());
			break;
		case NOTEQUALS :
		case NOT_EQUALS :
			retPred =  criteriaBuilder.notEqual(path, daoCondition.getValue());
			break;			
		case GREATEREQUAL:
		case GREATER_EQUAL:
			retPred =  criteriaBuilder.ge(path.as(BigDecimal.class), new BigDecimal(daoCondition.getValue()));
			break;
		case GREATERTHAN:
		case GREATER_THAN:
			retPred =  criteriaBuilder.gt(path.as(BigDecimal.class), new BigDecimal(daoCondition.getValue()));
			break;
		case LESSEQUAL:
		case LESS_EQUAL:
			retPred =  criteriaBuilder.le(path.as(BigDecimal.class), new BigDecimal(daoCondition.getValue()));
		case LESSTHAN:
		case LESS_THAN:
			retPred =  criteriaBuilder.lt(path.as(BigDecimal.class), new BigDecimal(daoCondition.getValue()));
		case STARTSWITH:
		case STARTS_WITH:
			retPred =  criteriaBuilder.like(path.as(String.class), daoCondition.getValue() + "%");
			break;
		case ENDSWITH:
		case ENDS_WITH:
			retPred =  criteriaBuilder.like(path.as(String.class), "%" + daoCondition.getValue());
			break;
		case CONTAINS:
			retPred =  criteriaBuilder.like(path.as(String.class), "%" + daoCondition.getValue() + "%");
			break;
		default:
			break;
		}
		
		return retPred;
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
