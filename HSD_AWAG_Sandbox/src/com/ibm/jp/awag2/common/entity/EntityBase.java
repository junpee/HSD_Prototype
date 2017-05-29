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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Entityクラスの基底クラス。
 *
 * @param <T> Entity型パラメータ
 * @param <S> Version型パラメータ
 */
@Access(AccessType.PROPERTY)
@MappedSuperclass
public abstract class EntityBase <T extends PKBase, S> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 主従リソース一括更新時のリソースに対するアクション */
	private String _action;

	/** リソースのVersion */
	protected ResourceVersion<S> _resourceVersion;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public EntityBase() {
	}
	
	/**
	 * 主キーオブジェクトを指定してインスタンスを初期化する。
	 * @param pk 主キーオブジェクト
	 */
	public EntityBase(T pk) {
		this.setPk(pk);
	}

	/**
	 * 主キーオブジェクトを取得する。
	 * @return 主キーオブジェクト
	 */
	public abstract T getPk();

	/**
	 * 主キーオブジェクトを設定する。
	 * @param pk 主キーオブジェクト
	 */
	public abstract void setPk(T pk);

	/**
	 * リソース・アクションを取得する。
	 * @return リソース・アクション
	 */
	@Transient
	public String get_Action() {
		return _action;
	}

	/**
	 * リソース・アクションを設定する。
	 * @param action リソース・アクション
	 */
	public void set_Action(String action) {
		this._action = action;
	}

	/**
	 * リソースのVersionを取得する。
	 * @return リソースのVersion
	 */
	@Transient
	public S get_resourceVersion() {
		return this._resourceVersion.getVersion();
	}

	/**
	 * リソースのVersionを設定する。
	 * @param _resourceVersion ソースのVersion
	 */
	public void set_resourceVersion(S _resourceVersion) {
		this._resourceVersion.setVersion(_resourceVersion);
	}
	
	/**
	 * リソースのVersionを文字列形式で取得する。
	 * @return リソースのVersionの文字列形式
	 */
	@Transient
	public String get_resourceVersionString() {
		return this._resourceVersion.getVersionString();
	}

	/**
	 * リソースのVersionを文字列形式で設定する。
	 * @param _resourceVersionString リソースのVersionの文字列形式
	 */
	public void set_resourceVersionString(String _resourceVersionString) {
		this._resourceVersion.setVersionString(_resourceVersionString);
	}

	/**
	 * 主キーの文字列表現を取得する。
	 * @return 主キーの文字列表現
	 */
	@Transient
	public String get_keyString() {
		return this.getPk().toString();
	}

}
