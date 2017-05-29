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
package com.ibm.jp.awag.rest.common.resource;

/**
 * HTTPステータスコードを示す定数クラス。
 *
 */
public class HTTPStatusConstants {
	
	/** 
	 * 唯一のコンストラクタ。
	 */
	private HTTPStatusConstants() {}
	
	/** 200 OK */
	public static final int OK = 200;
	/** 201 Created */
	public static final int CREATED = 201;
	/** 204 No Content */
	public static final int NO_CONTENT = 204;
	/** 400 Bad Request */
	public static final int BAD_REQUEST = 400;
	/** 404 Not Found */
	public static final int NOT_FOUND = 404;
	/** 409 Conflict */
	public static final int CONFLICT = 409;
	/** 410 Gone */
	public static final int GONE = 410;
	/** 422 Unprocessable Entity */
	public static final int UNPROCESSABLE_ENTITY = 422;
	/** 500 Internal Server Error */
	public static final int INTERNAL_SERVER_ERROR = 500;
}
