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
package com.ibm.jp.awag2.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列を処理するユーティリティ・クラス
 *
 */
public class TextUtility {

	/**
	 * 文字列中のSQLワイルドカード文字「%」「 ％」「 _」「 ＿」を「\\」文字でエスケープする。
	 * @param string エスケープする文字列
	 * @return エスケープされた文字列
	 */
	public static String replaceWildcard(String string) {
		return string.replaceAll("%","\\\\%").replaceAll("％","\\\\％").replaceAll("_","\\\\_").replaceAll("＿","\\\\＿");
	}
	
	/**
	 * Timestampオブジェクトの文字列表現（ミリ秒以下の0埋め有り）を取得する。
	 * @param timestamp タイムスタンプ
	 * @return タイムスタンプの文字列表現
	 */
	public static String formatTimestamp(Timestamp timestamp) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
		return format.format(timestamp) + String.format("%09d", timestamp.getNanos()).substring(0, 6);
		
	}

	/**
	 * Timestampオブジェクトの文字列表現（ミリ秒以下の0埋め有り）を取得する。
	 * @param timestamp タイムスタンプ
	 * @return タイムスタンプの文字列表現
	 */
	public static String formatTimestamp(String timestampString) {
		
		if(timestampString == null){
			return null;
		}
		
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d*?";
		Matcher m = Pattern.compile(regex).matcher(timestampString);
		if (m.matches()) {
			StringBuilder sb = new StringBuilder(timestampString);
			for (int i = 0; i < 26-timestampString.length(); i++) {
				sb.append("0");
			}
			return sb.toString();
		}
		return timestampString;	
	}
	
	/**
	 * 空文字をnullに変換する。
	 * @param string 文字列
	 * @return 入力が空文字の場合null
	 */
	public static String emptyStringToNull(String string) {
		if (string != null && string.trim().isEmpty()) {
			return null;
		} else {
			return string;
		}
	}
}
