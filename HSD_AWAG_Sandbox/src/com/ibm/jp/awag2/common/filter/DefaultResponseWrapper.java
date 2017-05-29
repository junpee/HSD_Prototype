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
package com.ibm.jp.awag2.common.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 全てのServletResponseに適用するデフォルト・ラッパー
 *
 */
public class DefaultResponseWrapper extends HttpServletResponseWrapper {

	public DefaultResponseWrapper(HttpServletResponse response) {
		super(response);
		
		// Cache control
		response.setHeader("Cache-Control","no-cache,no-store");
		response.setHeader("Pragma","no-cache");

		//CORS
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setHeader("Access-Control-Max-Age", "86400");

	}

}
