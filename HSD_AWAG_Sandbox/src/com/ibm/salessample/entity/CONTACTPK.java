/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.entity.PKBase;
import static com.ibm.jp.awag2.common.util.TextUtility.*;

/**
 * コンタクト履歴のPKクラス。
 *
 */
@Embeddable
public class CONTACTPK extends PKBase implements Serializable{

	/** お客様番号(CUSTOMERID) */
	@DisplayName(name = "お客様番号")
	private String customerid;

	/** 履歴番号(CONTACTID) */
	@DisplayName(name = "履歴番号")
	private String contactid;

	/**
	 * デフォルト・コンストラクタ。
	 */
	public CONTACTPK() {}

	/**
	 * PKのフィールド値を指定してインスタンスを初期化する。
	 * @param CUSTOMERID お客様番号
	 * @param CONTACTID 履歴番号
	 */
	public CONTACTPK (String customerid, String contactid) {
		super();

		this.setCUSTOMERID(customerid);
		this.setCONTACTID(contactid);
	}

	/**
	 * お客様番号を取得する。
	 * @return CUSTOMERID
	 */
	@Column(name = "customerid")
	public String getCUSTOMERID() {
		return this.customerid;
	}

	/**
	 * お客様番号を設定する。
	 * @param customerid
	 */
	public void setCUSTOMERID(String customerid) {
		this.customerid = emptyStringToNull(customerid);
	}
	/**
	 * 履歴番号を取得する。
	 * @return CONTACTID
	 */
	@Column(name = "contactid")
	public String getCONTACTID() {
		return this.contactid;
	}

	/**
	 * 履歴番号を設定する。
	 * @param contactid
	 */
	public void setCONTACTID(String contactid) {
		this.contactid = emptyStringToNull(contactid);
	}

	/**
	 * このPKオブジェクトが指定したオブジェクトと一致するか確認する。
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CONTACTPK)) {
			return false;
		}
		CONTACTPK castOther = (CONTACTPK) other;
		return this.customerid.equals(castOther.customerid) && this.contactid.equals(castOther.contactid);
	}

	/**
	 * PKオブジェクトのハッシュコードを取得する。
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((this.customerid != null) ? this.customerid : "").hashCode();
		hash = hash * prime + ((this.contactid != null) ? this.contactid : "").hashCode();

		return hash;
	}

	/**
	 * PKオブジェクトの文字列表現を取得する。
	 */
	@Override
	public String toString() {
		String ret = this.customerid + "," + this.contactid;		
		return ret;
	}

	/**
	 * PKオブジェクトの表示表文字列表現を取得する。
	 */
	@Override
	public String toDisplayString() {
		String ret = "お客様番号:" + this.customerid + "," + "履歴番号:" + this.contactid;		
		return ret;
	}
}