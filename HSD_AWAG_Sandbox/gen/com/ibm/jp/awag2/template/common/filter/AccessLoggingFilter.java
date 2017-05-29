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
import javax.servlet.http.HttpServletRequest;

import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;

/**
 * REST I/Fのアクセスログを出力するフィルタ
 *
 */
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

        Logger logger = LoggerFactory.getInstance().getLogger(this);

        HttpServletRequest req = (HttpServletRequest)request;

        String uri = req.getRequestURI();
        String method = req.getMethod();
        
        // リクエストデータのダンプ
        if (logger.isTraceEnabled()) {
            logger.trace("====== REQUEST HEADER ======");
            Optional.ofNullable(req.getHeaderNames()).ifPresent(headers -> Collections.list(headers).forEach(name -> logger.trace(new StringBuffer(name).append(" = ").append(req.getHeader(name)).toString())));

            logger.trace("====== REQUEST PARAMETER ======");
            Optional.ofNullable(req.getParameterNames()).ifPresent(parameters -> Collections.list(parameters).forEach(name -> logger.trace(new StringBuffer(name).append(" = ").append(req.getParameter(name)).toString())));

            logger.trace("====== REQUEST ATTRIBUTE ======");
            Optional.ofNullable(req.getAttributeNames()).ifPresent(attributes -> Collections.list(attributes).forEach(name -> logger.trace(new StringBuffer(name).append(" = ").append(req.getAttribute(name)).toString())));
        }
        
		try {
			chain.doFilter(request, response);
		} finally {
			LocalDateTime responseTime = LocalDateTime.now();
			logger.info(new StringBuilder(method).append("\t").append(uri).append("\t").append(Duration.between(requestTime,responseTime).toString()).toString());
		}
	}
}
