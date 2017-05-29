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
package com.ibm.jp.awag.generator.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import com.ibm.jp.awag.generator.common.GenerateException;

public class GeneratorUtil {

	/**
	 * コンソールにメッセージを出力する。
	 * @param string メッセージ
	 */
	public static void print(String string) {
		System.out.println(string);
	}
	
	/**
	 * コンソールにエラー・メッセージを出力する。
	 * @param string エラー・メッセージ
	 */
	public static void printError(String string) {
		print("\n******************  エラー詳細  ******************\n" + string + "\n************************************************\n \n");
	}

	/**
	 * 指定した例外に含まれる例外メッセージをコンソール出力用に整形する。
	 * @param e 整形対象の例外
	 * @return 整形された例外メッセージ
	 */
	public static String getStackErrorMessage(Throwable tr) {

		StringBuilder sb = new StringBuilder();
		Throwable t = tr;

		sb.append("(CAUSE = ");
		while (t != null) {
			sb.append(" >> ");
			sb.append(t.getMessage());
			t = t.getCause();
		}
		sb.append(")");
		
		tr.printStackTrace();
		
		return sb.toString();
	}
	
	/**
	 * 一階層のJSON文字列をMapに変換する。
	 * @param keyValueStr 一階層のJSON文字列
	 * @return Mapオブジェクト
	 */
	public static Map<String, String> flatJsonStringToMap(String keyValueStr) {
		
		if (keyValueStr == null || keyValueStr.isEmpty()) {
			return null;
		}
		
		keyValueStr = keyValueStr.replaceAll("^\\{", "").replaceAll("\\}$", "");
		
		HashMap<String, String> retMap = new HashMap<>();
		String[] eachKVs = keyValueStr.split("[\\s]*,[\\s]*");
        
        for (int i = 0; i < eachKVs.length; i++) {
        	String[] kv = eachKVs[i].split("[\\s]*:[\\s]*");
        	retMap.put(kv[0].replaceAll("^\"", "").replaceAll("\"$", ""), kv[1].replaceAll("^\"", "").replaceAll("\"$", ""));
        }
        
        return retMap;
	}
	
	public static <T> T mapXmlToObject(String xmlFilePath, Class<T> clazz) throws IOException, DataBindingException {
		Path path = new File(xmlFilePath).toPath();
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);) {
			return JAXB.unmarshal(br, clazz);
		} catch (IOException ex1) {
			throw ex1;
		} catch (DataBindingException ex2) {
			throw ex2;
		}
	}
	
	public static <T> T loadConfig(String configFilePath, Class<T> configModelClass) {
		Path path = new File(configFilePath).toPath();
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);) {
			return JAXB.unmarshal(br, configModelClass);
		} catch (IOException e1) {
			throw new GenerateException("Generator設定ファイルのOpenに失敗。[設定ファイル]" + configFilePath, e1);
		} catch (DataBindingException e2) {
			throw new GenerateException("Generator設定ファイルのParseに失敗。[設定ファイル]"+ configFilePath, e2);
		}
	}
	
	/**
	 * null値をDouble型の0に変換する。
	 * @param value 変換対象のDoubleオブジェクト
	 * @return 変換後のDoubleオブジェクト
	 */
	public static Double replaceNullwithZero(Double value) {
		return Optional.ofNullable(value).orElse(new Double(0));
	}
	
	/**
	 * 空文字列をnullに変換する。
	 * @param value 変換対象の文字列
	 * @return 引数が空文字列の場合null、それ以外の場合引数の文字列
	 */
	public static String replaceNullStringwithNull(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return value;
	}
	
	/**
	 * 文字列をCapitalize変換する。
	 * @param value 変換対象の文字列
	 * @return 引数がnullまたは空文字列の場合入力された文字列、それ以外の場合引数の文字列
	 */
	public static String capitalizeString(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		return  value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
	}
	
	public static String capitalizeFirstString(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		return  value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	
	public static String getExtensionString(String fileName) {
		
		if (fileName == null || fileName.isEmpty()) {
			return fileName;
		}
		
		int dotIndex = fileName.lastIndexOf(".");
		
		if (dotIndex > 0) {
			return fileName.substring(dotIndex + 1);
		} else {
			return "";
		}	
	}

	public static String getSimpleFileNameString(String fileName) {
		
		if (fileName == null || fileName.isEmpty()) {
			return fileName;
		}
		
		int dotIndex = fileName.lastIndexOf(".");
		
		if (dotIndex > 0) {
			return fileName.substring(0, dotIndex);
		} else {
			return "";
		}	
	}
	
	public static int comparePriority(Integer pri1, Integer pri2) {
		if (pri1 == null) {
			pri1 = Integer.MAX_VALUE;
		}
		if (pri2 == null) {
			pri2 = Integer.MAX_VALUE;
		}

		if (pri1 > pri2) {
			return 1;
		} else if (pri1 == pri2) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public static String removeBlankFromString(String value) {
		
		if (value == null || value.length() == 0) {
			return value;
		}
		
		StringBuilder blankRemovedStringBuilder = new StringBuilder(value.length());
		
		char[] valueCharArray = value.toCharArray();
		for (char target : valueCharArray) {
			if (target != ' ' && target != '　') {
				blankRemovedStringBuilder.append(target);
			}
		}
		
		return blankRemovedStringBuilder.toString();
		
	}
}
