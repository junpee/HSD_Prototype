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
package com.ibm.jp.awag2.common.resource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

/**
 * リクエスト・スコープの共有情報を保持するクラス。
 *
 */
@RequestScoped
public class RequestContext {

	/** HTTP Header情報格納用Map */
	private Map<String, String> headerMap;
	
//	デフォルトではRequestContextにCookieを保持しない。
//	必要な場合、コメント解除する。
//	/** HTTP Cookie情報格納用Map */
//	private List<Cookie> cookies;

	
	/** Accept-Language */
	private Locale locale;

	/** 汎用データ格納用Map */
	private Map<String, Object> genericDataMap = new HashMap<>();

	/**
	 * デフォルト・コンストラクタ。
	 */
	public RequestContext() {
		super();
	}

	/**
	 * HTTP Header情報格納用Mapを取得する。
	 * @return HTTP Header情報格納用Map
	 */
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	/**
	 * HTTP Header情報格納用Mapを設定する。
	 * @param headerMap HTTP Header情報格納用Map
	 */
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	

// デフォルトではRequestContextにCookieを保持しない。
// 必要な場合、コメント解除する。
//	/**
//	 * HTTP Cookie情報格納用Mapを取得する。
//	 * @return HTTP Cookie情報格納用Map
//	 */
//	public List<Cookie> getCookies() {
//		return cookies;
//	}
//
//	/**
//	 * HTTP Cookie情報格納用Mapを設定する。
//	 * @param cookies HTTP Cookie情報格納用Map
//	 */
//	public void setCookies(List<Cookie> cookies) {
//		this.cookies = cookies;
//	}
	
	/**
	 * リクエスト単位のLocale（Accept-Language）を取得する。
	 * @return リクエスト単位のLocale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * リクエスト単位のLocale（Accept-Language）を設定する。
	 * @param locale リクエスト単位のLocale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * 汎用データ格納用Mapを取得する。
	 * @return 汎用データ格納用Map
	 */
	public Map<String, Object> getGenericDataMap() {
		return genericDataMap;
	}

	/**
	 * 汎用データ格納用Mapを設定する。
	 * @param genericDataMap 汎用データ格納用Map
	 */
	public void setGenericDataMap(Map<String, Object> genericDataMap) {
		this.genericDataMap = genericDataMap;
	}

}
