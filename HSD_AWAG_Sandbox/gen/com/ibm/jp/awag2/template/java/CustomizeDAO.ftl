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

package ${tableCategory.javaPackage}.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.ibm.jp.awag2.common.dao.DAOBase;
import com.ibm.jp.awag2.common.dao.DAOParameter;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;

/**
 * ${tableDefinition.nameLocal}（${tableDefinition.name}）Entityのカスタマイズ用DAOクラス。
 *
 */
public class ${tableDefinition.name?cap_first}CustomizeDAO extends ${tableDefinition.name?cap_first}DAO {


}
