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
package com.ibm.jp.awag2.generator.model.web;

import com.ibm.jp.awag2.generator.model.common.HTTPMethodType;

/**
 * 画面項目定義のイベント仕様を表すクラス。
 *
 */
public class EventDefinition {

	/** イベント名 （Excel:繰り返し項目 - パラメータ名）*/
	private String eventName;

	/** 表示用イベント名 （Excel:繰り返し項目 - 項目名）*/
	private String eventDisplayName;

	/** 遷移先画面物理名 */
	private String nextScreen;

	/** 遷移先画面名 */
	private String nextDisplayScreen;

	/** API Path */
	private String apiPath;

	/** METHOD */
	private HTTPMethodType apiType;

	/** イベント発火条件 */
	private EventFireType eventFireType;

	/** イベント発火種別のenum */
	public enum EventFireType {
		FORMVALID,
		ANYTIME,
		TABLE_SELECTED
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDisplayName() {
		return eventDisplayName;
	}

	public void setEventDisplayName(String eventDisplayName) {
		this.eventDisplayName = eventDisplayName;
	}

	public String getNextScreen() {
		return nextScreen;
	}

	public void setNextScreen(String nextScreen) {
		this.nextScreen = nextScreen;
	}

	public String getNextDisplayScreen() {
		return nextDisplayScreen;
	}

	public void setNextDisplayScreen(String nextDisplayScreen) {
		this.nextDisplayScreen = nextDisplayScreen;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public HTTPMethodType getApiType() {
		return apiType;
	}

	public void setApiType(HTTPMethodType apiType) {
		this.apiType = apiType;
	}

	public EventFireType getEventFireType() {
		return eventFireType;
	}

	public void setEventFireType(EventFireType eventFireType) {
		this.eventFireType = eventFireType;
	}

	public String getReplacedApiPath() {
		String str = "'" + apiPath;
		str = str.replaceAll("\\{", "'+encodeURIComponent(param.");
		str = str.replaceAll("\\}", ")+'");
		if(str.endsWith("+'")){
			str = str.substring(0, str.length()-2);
		} else {
			str = str + "'";
		}
		return str;
	}

}
