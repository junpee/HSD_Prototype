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
package com.ibm.jp.awag.rest.common.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entityクラスの基底クラス。
 *
 */
@Access(AccessType.PROPERTY)
@XmlAccessorType(XmlAccessType.PROPERTY)
@MappedSuperclass
public abstract class EntityBase <TPK extends PKBase, VERSIONTYPE> implements Serializable {

	/** AngularJSで使用するID */
	private String id;

	/** 主従リソース一括更新時のリソースに対するアクション */
	private String _action;

	/** リソースのVersion */
	protected ResourceVersion<VERSIONTYPE> _resourceVersion;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public EntityBase() {
	}
	
	/**
	 * 主キーオブジェクトを指定してインスタンスを初期化する。
	 * @param pk 主キーオブジェクト
	 */
	public EntityBase(TPK pk) {
		this.setPk(pk);
	}

	/**
	 * 主キーオブジェクトを取得する。
	 * @return 主キーオブジェクト
	 */
	@Transient
	public abstract TPK getPk();

	/**
	 * 主キーオブジェクトを設定する。
	 * @param pk 主キーオブジェクト
	 */
	public abstract void setPk(TPK pk);

	/**
	 * AngularJS用のIDを取得する。
	 * 
	 * @return AngularJS用ID
	 */
	@Transient
	public String getId() {
		return id;
	}

	/**
	 * AngularJS用のIDを設定する。
	 * 
	 * @param id
	 *            AngularJS用ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * リソース・アクションを取得する。
	 * @return リソース・アクション
	 */
	@Transient
	@XmlElement(name = "_action")
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
	@XmlTransient
	@Transient
	public VERSIONTYPE get_resourceVersion() {
		return this._resourceVersion.getVersion();
	}

	/**
	 * リソースのVersionを設定する。
	 * @param _resourceVersion ソースのVersion
	 */
	public void set_resourceVersion(VERSIONTYPE _resourceVersion) {
		this._resourceVersion.setVersion(_resourceVersion);
	}
	
	/**
	 * リソースのVersionを文字列形式で取得する。
	 * @return リソースのVersionの文字列形式
	 */
	@XmlTransient
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
	@XmlTransient
	@Transient
	public String get_keyString() {
		return this.getPk().toString();
	}

}
