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
package com.ibm.jp.awag.rest.common.model;

import javax.persistence.MappedSuperclass;

/**
 * PKクラスの基底クラス。
 *
 */
@MappedSuperclass
public abstract class PKBase {

	abstract public boolean equals(Object obj);

	abstract public int hashCode();
	
	abstract public String toDisplayString();

}
