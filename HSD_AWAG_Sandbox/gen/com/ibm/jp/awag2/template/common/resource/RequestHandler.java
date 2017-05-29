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

import java.util.Enumeration;
import java.util.HashMap;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;

/**
 * リクエスト受信時の共通処理を提供するInterceptorクラス。
 *
 */
public class RequestHandler {

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;

	/**
	 * REST I/Fのリクエストから必要なヘッダを取得しRequestContextに設定する。
	 * @param ic InvocationContext
	 * @return InvocationContext.proceedの結果
	 * @throws Exception
	 */
	@AroundInvoke
	protected Object interceptorAroundInvoke(InvocationContext ic) throws Exception {

		Object targetObj = ic.getTarget();

		HttpServletRequest request = null;
		if (targetObj instanceof ResourceManagerBase) {
			request = ((ResourceManagerBase) targetObj).getRequest();
		}

		if (request != null) {
			requestContext.setLocale(request.getLocale());

			HashMap<String, String> headerMap = new HashMap<>();

			if (request.getHeaderNames() != null) {
				for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
					String headerName = e.nextElement();
					headerMap.put(headerName, request.getHeader(headerName));
				}
			}
			requestContext.setHeaderMap(headerMap);

// デフォルトではRequestContextにCookieを保持しない。
// 必要な場合、コメント解除する。
//			// Cookieの設定
//  			Optional.ofNullable(request.getCookies()).ifPresent(cookies -> requestContext.setCookies(Arrays.asList(cookies)));
// 

		}

		return ic.proceed();

	}
}
