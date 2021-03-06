/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.entity;

import static com.ibm.jp.awag2.common.util.TextUtility.emptyStringToNull;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ibm.jp.awag2.common.entity.EntityBase;
import com.ibm.jp.awag2.common.entity.ResourceVersionNotlockImpl;
import com.ibm.jp.awag2.common.entity.ResourceVersionLongImpl;
import com.ibm.jp.awag2.common.entity.ResourceVersionTimestampImpl;

/**
 * 社員のEntityクラス。
 *
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "staff")
public class STAFF extends EntityBase <STAFFPK, Timestamp> {

	/** 主キー */
	private STAFFPK pk = new STAFFPK();
	
	/** 部門CD(DEPTCD) */
	private String deptcd;
	
	/** 名前(NAME) */
	private String name;
	
	/** 名前英字(NAMEEN) */
	private String nameen;
	
	/** 電話番号(TEL) */
	private String tel;
	
	/** 更新日付(VERSION) */
	private String _version;
	

	/**
	 * デフォルト・コンストラクタ。
	 */
	public STAFF() {
		super();
		super._resourceVersion = new ResourceVersionTimestampImpl();
	}

	/**
	 * Entityのフィールド値を指定してインスタンスを初期化する。
	 * @param employeeno 従業員番号
	 * @param deptcd 部門CD
	 * @param name 名前
	 * @param nameen 名前英字
	 * @param tel 電話番号
	 * @param version 更新日付
	 */
	public STAFF (String employeeno, String deptcd, String name, String nameen, String tel, String version) {
		super._resourceVersion = new ResourceVersionTimestampImpl();
		
		this.setEMPLOYEENO(employeeno);
		this.setDEPTCD(deptcd);
		this.setNAME(name);
		this.setNAMEEN(nameen);
		this.setTEL(tel);
		this.setVERSION(version);
	}

	/**
	 * 主キーを取得する。
	 * @return 主キーオブジェクト
	 */
	@EmbeddedId
	@Override
	public STAFFPK getPk() {
		return this.pk;
	}

	/**
	 * 主キーを設定する。
	 * @param 主キーオブジェクト
	 */
	@Override
	public void setPk(STAFFPK pk) {
		this.pk = (STAFFPK) pk;
	}

	/**
	 * EMPLOYEENOを取得する。
	 * @return EMPLOYEENO
	 */
	@Transient	
	public String getEMPLOYEENO() {
		return this.pk.getEMPLOYEENO();
	}

	/**
	 * 従業員番号を設定する。
	 * @param EMPLOYEENO
	 */
	public void setEMPLOYEENO(String employeeno) {
		this.pk.setEMPLOYEENO(emptyStringToNull(employeeno));
	}

	/**
	 * DEPTCDを取得する。
	 * @return DEPTCD
	 */
	@Column(name = "deptcd")
	public String getDEPTCD() {
		return this.deptcd;
	}

	/**
	 * 部門CDを設定する。
	 * @param DEPTCD
	 */
	public void setDEPTCD(String deptcd) {
		this.deptcd = emptyStringToNull(deptcd);
	}

	/**
	 * NAMEを取得する。
	 * @return NAME
	 */
	@Column(name = "name")
	public String getNAME() {
		return this.name;
	}

	/**
	 * 名前を設定する。
	 * @param NAME
	 */
	public void setNAME(String name) {
		this.name = emptyStringToNull(name);
	}

	/**
	 * NAMEENを取得する。
	 * @return NAMEEN
	 */
	@Column(name = "nameen")
	public String getNAMEEN() {
		return this.nameen;
	}

	/**
	 * 名前英字を設定する。
	 * @param NAMEEN
	 */
	public void setNAMEEN(String nameen) {
		this.nameen = emptyStringToNull(nameen);
	}

	/**
	 * TELを取得する。
	 * @return TEL
	 */
	@Column(name = "tel")
	public String getTEL() {
		return this.tel;
	}

	/**
	 * 電話番号を設定する。
	 * @param TEL
	 */
	public void setTEL(String tel) {
		this.tel = emptyStringToNull(tel);
	}

	/**
	 * VERSIONのDB値を取得する。
	 * @return VERSIONのDB値
	 */
	@Version
	@Column(name = "version")
	public Timestamp getVERSION_db() {	
		return super.get_resourceVersion();
	}

	/**
	 * 更新日付の文字列表記を取得する。
	 * @return VERSIONの文字列表記
	 */
	@Transient	
	public String getVERSION() {
		return super.get_resourceVersionString();
	}

	/**
	 * 更新日付のDB値を設定する。
	 * @param VERSIONのDB値
	 */
	public void setVERSION_db(Timestamp version) {
		super.set_resourceVersion(version);
	}

	/**
	 * 更新日付の文字列表記を設定する。
	 * @param VERSIONの文字列表記
	 */
	public void setVERSION(String version) {
		super.set_resourceVersionString(emptyStringToNull(version));
	}


}