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
package com.ibm.jp.awag2.generator.model.common;

import javax.xml.bind.annotation.XmlEnum;

/** 
 * HTTP METHODを表すenumクラス。
 * 
 */
@XmlEnum
public enum HTTPMethodType {
	GET,
	POST,
	PUT,
	DELETE
}