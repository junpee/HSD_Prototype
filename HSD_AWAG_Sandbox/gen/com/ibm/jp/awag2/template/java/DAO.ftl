<#--
Automated web application generator

Licensed Materials - Property of IBM
"Restricted Materials of IBM"
IPSC : 6949-63S
(C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
(C) Copyright IBM Corp. 2016 All Rights Reserved.
US Government Users Restricted Rights -
Use, duplication or disclosure restricted
by GSA ADP Schedule Contract with IBM Corp.
 -->
 <#assign hasDependent = tableDefinition.getRelationDefinitions()?? && tableDefinition.getRelationDefinitions()?size gt 0>
<#include "includeHeader.ftl">
<#include "includeCommonMacro.ftl">

package ${tableCategory.javaPackage}.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.interceptor.Interceptors;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ibm.jp.awag2.common.dao.DAOBase;
import com.ibm.jp.awag2.common.dao.DAOParameter;
import com.ibm.jp.awag2.common.entity.JoinEntity;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.dao.DAOIF;
import com.ibm.jp.awag2.common.util.MethodTraceLoggingInterceptor;
import ${tableCategory.javaPackage}.entity.${tableDefinition.name?cap_first};
import ${tableCategory.javaPackage}.entity.${tableDefinition.name?cap_first}PK;
<#if hasDependent>
<#list tableDefinition.relationDefinitions as relationDefinition>
import ${tableCategory.javaPackage}.entity.${relationDefinition.name?cap_first};
</#list>
</#if>

/**
 * ${tableDefinition.nameLocal}（${tableDefinition.name}）EntityのDAOクラス。
 *
 */
 @Interceptors(MethodTraceLoggingInterceptor.class)
 @Dependent
public class ${tableDefinition.name?cap_first}DAO extends DAOBase<${tableDefinition.name?cap_first}, ${tableDefinition.name?cap_first}PK, ${tableDefinition.versionType.javaType}> implements DAOIF<${tableDefinition.name?cap_first}, ${tableDefinition.name?cap_first}PK> {

	/** デフォルト最大取得件数 */
	private static int DEFAULT_MAX_RESULT = <#if tableDefinition.maxResult??>${tableDefinition.maxResult?c}<#else>0</#if>;

	@Override
	public Optional<${tableDefinition.name?cap_first}> find(${tableDefinition.name?cap_first}PK pk)  throws ServiceDBException, ServiceAppException {

		return super.findEntity(${tableDefinition.name?cap_first}.class, pk);

	}

	@Override
	public Optional<${tableDefinition.name?cap_first}> find(${tableDefinition.name?cap_first}PK pk, List<JoinEntity> joinEntityList)  throws ServiceDBException, ServiceAppException {

		EntityManager em = super.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<${tableDefinition.name?cap_first}> cQuery = cb.createQuery(${tableDefinition.name?cap_first}.class);

		// FROM
		Root<${tableDefinition.name?cap_first}> ${tableDefinition.name?lower_case} = cQuery.from(${tableDefinition.name?cap_first}.class);

		// JOIN
		if (joinEntityList != null) {
			for (JoinEntity joinEntry :  joinEntityList) {
				recursiveJoin(${tableDefinition.name?lower_case}, null, joinEntry);
			}
		}

		// WHERE
		List<Predicate> preds = new ArrayList<>();
<#list tableDefinition.pkFields as column><#t>

		if (pk.get${column.name?cap_first}() != null) {
		    preds.add(cb.equal(${tableDefinition.name?lower_case}<#if column.pk>.get("pk")</#if>.get("<@propertyName orgName=column.name />"), pk.get${column.name?cap_first}()));
		}
</#list>
		cQuery.where(cb.and(preds.toArray(new Predicate[]{})));

	    TypedQuery<${tableDefinition.name?cap_first}> tQuery = em.createQuery(cQuery);

		try {
			List<${tableDefinition.name?cap_first}> ${tableDefinition.name?lower_case}List = tQuery.getResultList();
			if (${tableDefinition.name?lower_case}List == null || ${tableDefinition.name?lower_case}List.isEmpty()) {
				return Optional.ofNullable(null);
			} else {
				return Optional.ofNullable(tQuery.getResultList().get(0));
			}

		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		} catch (RuntimeException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		}
	}

	@Override
	public  List<${tableDefinition.name?cap_first}> findList(DAOParameter param, List<JoinEntity> joinEntityList, boolean isInclusiveOr, Integer maxResults)  throws ServiceDBException, ServiceAppException {

		EntityManager em = super.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<${tableDefinition.name?cap_first}> cQuery = cb.createQuery(${tableDefinition.name?cap_first}.class);

		// FROM
		Root<${tableDefinition.name?cap_first}> ${tableDefinition.name?lower_case} = cQuery.from(${tableDefinition.name?cap_first}.class);

		// JOIN
		if (joinEntityList != null) {
			for (JoinEntity joinEntry :  joinEntityList) {
				recursiveJoin(${tableDefinition.name?lower_case}, null, joinEntry);
			}
		}

		// WHERE
		List<Predicate> preds = new ArrayList<>();
<#list tableDefinition.columnDefinitions as column><#t>

		if (param.get("${column.name?lower_case}") != null) {
<#--		    preds.add(cb.equal(${tableDefinition.name?lower_case}<#if column.pk>.get("pk")</#if>.get("${column.name}"), param.get("${column.name?lower_case}")));-->
		    preds.add(createPredicate(cb, ${tableDefinition.name?lower_case}<#if column.pk>.get("pk")</#if>.get("<@propertyName orgName=column.name />"), param.get("${column.name?lower_case}")));
		}
</#list>
		if (!preds.isEmpty()) {
			if (isInclusiveOr) {
				cQuery.where(cb.or(preds.toArray(new Predicate[]{})));
			} else {
				cQuery.where(cb.and(preds.toArray(new Predicate[]{})));
			}
		}

	    // ORDER BY
<#if tableDefinition.pkFields?size gt 0>
	    cQuery.orderBy(
<#list tableDefinition.pkFields as pkField><#t>
		cb.asc(${tableDefinition.name?lower_case}.get("pk").get("<@propertyName orgName=pkField.name />"))<#sep>, </#sep>
</#list>
		);
</#if>

	    TypedQuery<${tableDefinition.name?cap_first}> tQuery = em.createQuery(cQuery);
	    if (maxResults != null && maxResults > 0) {
		    tQuery.setMaxResults(maxResults);
	    } else {
		    tQuery.setMaxResults(DEFAULT_MAX_RESULT);
	    }

		try {
			List<${tableDefinition.name?cap_first}> resultList = tQuery.getResultList();
			if (resultList == null) {
				return new ArrayList<${tableDefinition.name?cap_first}>();
			} else {
				return resultList;
			}
		} catch (PersistenceException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		} catch (RuntimeException e) {
			throw createServiceDBException(ServiceDBException.DBErrorType.OTHER, "_EDB00001", e);
		}
	}

	@Override
	public void insert(${tableDefinition.name?cap_first} entity) throws ServiceDBException, ServiceAppException {

		super.persistEntity(entity);

	}

	@Override
	public void insertList(List<${tableDefinition.name?cap_first}> entityList) throws ServiceDBException, ServiceAppException {

		super.persistList(entityList);

	}

	@Override
	public void update(${tableDefinition.name?cap_first} entity) throws ServiceDBException, ServiceAppException {

		super.mergeEntity(entity);

	}

	@Override
	public void updateList(List<${tableDefinition.name?cap_first}> entityList) throws ServiceDBException, ServiceAppException {

		super.mergeList(entityList);

	}

	@Override
	public void delete(${tableDefinition.name?cap_first} entity) throws ServiceDBException, ServiceAppException {

		super.removeEntity(${tableDefinition.name?cap_first}.class, entity.getPk(), entity.get_resourceVersion());

	}

	@Override
	public void deleteList(List<${tableDefinition.name?cap_first}> entityList) throws ServiceDBException, ServiceAppException {

		super.removeList(entityList);

	}

}
