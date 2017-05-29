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
package com.ibm.jp.awag2.common.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * PKクラスの基底クラス。
 *
 */
@MappedSuperclass
public abstract class PKBase implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * PKBaseオブジェクトと他のオブジェクトが等しいかどうかを示す。 
	 * @param obj 他のオブジェクト
	 * @return PKBaseオブジェクトが obj 引数と同じである場合は true、それ以外の場合は false。
	 */		
	abstract public boolean equals(Object obj);
	
	/**
	 * オブジェクトのハッシュコード値を返す。
	 * @return PKBaseオブジェクトのハッシュコード値
	 */		
	abstract public int hashCode();

	/**
	 * 表示用の文字列表現を取得する。
	 * @return 表示用の文字列表現
	 */	
	abstract public String toDisplayString();

}
