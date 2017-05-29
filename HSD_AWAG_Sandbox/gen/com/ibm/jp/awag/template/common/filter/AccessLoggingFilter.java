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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;

/**
 * REST I/Fのアクセスログを出力するフィルタ
 *
 */
@WebFilter(filterName="accessLoggingFilter", urlPatterns={"/rest/*"})
public class AccessLoggingFilter implements Filter {

	/**
	 * 唯一のコンストラクタ。
	 */
    public AccessLoggingFilter() {
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
	 * コンテナから呼び出され、REST I/Fのアクセスログを出力する。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		LocalDateTime requestTime = LocalDateTime.now();

        Logger logger = LoggerFactory.getLogger(this);

        HttpServletRequest req = (HttpServletRequest)request;

        logger.trace("====== REQUEST HEADER ======");
        String uri = req.getRequestURI();
        String method = req.getMethod();
        Optional.ofNullable(req.getHeaderNames()).ifPresent(headers -> Collections.list(headers).forEach(name -> logger.trace(name + " = " + req.getHeader(name))));

        logger.trace("====== REQUEST PARAMETER ======");
        Optional.ofNullable(req.getParameterNames()).ifPresent(parameters -> Collections.list(parameters).forEach(name -> logger.trace(name + " = " + req.getParameter(name))));

        logger.trace("====== REQUEST ATTRIBUTE ======");
        Optional.ofNullable(req.getAttributeNames()).ifPresent(attributes -> Collections.list(attributes).forEach(name -> logger.trace(name + " = " + req.getAttribute(name))));

		try {
			chain.doFilter(request, response);
		} finally {
			LocalDateTime responseTime = LocalDateTime.now();
			logger.info(method+ "\t" + uri + "\t" + (Duration.between(requestTime,responseTime).toString()));
		}
	}
}
