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
package com.ibm.jp.awag.rest.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * 全てのREST I/Fに適用するデフォルト・フィルタ
 *
 */
@WebFilter(filterName="defaultServletFilter", urlPatterns={"/rest/*"})
public class DefaultServletFilter implements Filter {

	/**
	 * 唯一のコンストラクタ。
	 */
    public DefaultServletFilter() {
    }
    
	/**
	 * フィルタのinit処理を実行する。
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {		
	}
	
    /**
     * フィルタのDestroy処理を実行する。
     */
	public void destroy() {
	}
	
	/**
	 * コンテナから呼び出され、全Servletに共通の処理を行う。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		DefaultResponseWrapper defResWrapper = new DefaultResponseWrapper((HttpServletResponse)response);
		
		chain.doFilter(request, defResWrapper);
	}
}
