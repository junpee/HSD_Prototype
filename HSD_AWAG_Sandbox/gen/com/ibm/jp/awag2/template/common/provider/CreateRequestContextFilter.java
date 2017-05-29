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
package com.ibm.jp.awag2.common.provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import com.ibm.jp.awag2.common.resource.RequestContext;

/**
 * REST I/Fのリクエストから必要なヘッダを取得しRequestContextに設定するフィルタークラス。
 *
 */
@Provider
public class CreateRequestContextFilter implements ContainerRequestFilter {
	
    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;

	/* (非 Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		
		// Locale設定
		List<Locale> acceptableLangs = context.getAcceptableLanguages();
		if (acceptableLangs != null && !acceptableLangs.isEmpty()) {
			requestContext.setLocale(acceptableLangs.get(0));
		}
		
		MultivaluedMap<String, String> headerMapOrg = context.getHeaders();
		Map<String, String> headerMap = new HashMap<>();
		
		headerMapOrg.forEach((s, v) -> headerMap.put(s, context.getHeaderString(s)));
		requestContext.setHeaderMap(headerMap);
		
// デフォルトではRequestContextにCookieを保持しない。
// 必要な場合、コメント解除する。
//			// Cookieの設定
//  			Optional.ofNullable(request.getCookies()).ifPresent(cookies -> requestContext.setCookies(Arrays.asList(cookies)));
// 

	}

}
