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
import com.ibm.salessample.entity.CONTACT;
import com.ibm.salessample.entity.APPROVELOG;
import com.ibm.salessample.entity.ADDRESS;
import com.ibm.salessample.entity.STAFF;

/**
 * お客様のEntityクラス。
 *
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "customer")
public class CUSTOMER extends EntityBase <CUSTOMERPK, Timestamp> {

	/** 主キー */
	private CUSTOMERPK pk = new CUSTOMERPK();
	
	/** お客様名（英字）(NAMEEN) */
	private String nameen;
	
	/** お客様名（全角）(NAME) */
	private String name;
	
	/** 担当ID(INCHARGEID) */
	private String inchargeid;
	
	/** 登録日(REGISTEREDDATE) */
	private String registereddate;
	
	/** お客様名（かな）(HIRAGANA) */
	private String hiragana;
	
	/** お客様名（カナ）(KATAKANA) */
	private String katakana;
	
	/** 概要(DESCRIPTION) */
	private String description;
	
	/** ISINコード(ISIN) */
	private String isin;
	
	/** 法人番号(HOUJINNO) */
	private String houjinno;
	
	/** お客様ランク(RANK) */
	private String rank;
	
	/** お客様区分1(CUSTCLASS1) */
	private String custclass1;
	
	/** お客様区分2(CUSTCLASS2) */
	private String custclass2;
	
	/** 顧客承認フラグ(CHECKBOX) */
	private String checkbox;
	
	/** タイムスタンプ(TIME) */
	private String _time;
	
	/** CONTACT(CONTACT) */
	private List<CONTACT> contact;
	/** APPROVELOG(APPROVELOG) */
	private APPROVELOG approvelog;
	/** ADDRESS(ADDRESS) */
	private List<ADDRESS> address;
	/** STAFF(STAFF) */
	private STAFF staff;

	/**
	 * デフォルト・コンストラクタ。
	 */
	public CUSTOMER() {
		super();
		super._resourceVersion = new ResourceVersionTimestampImpl();
	}

	/**
	 * Entityのフィールド値を指定してインスタンスを初期化する。
	 * @param customerid お客様番号
	 * @param nameen お客様名（英字）
	 * @param name お客様名（全角）
	 * @param inchargeid 担当ID
	 * @param registereddate 登録日
	 * @param hiragana お客様名（かな）
	 * @param katakana お客様名（カナ）
	 * @param description 概要
	 * @param isin ISINコード
	 * @param houjinno 法人番号
	 * @param rank お客様ランク
	 * @param custclass1 お客様区分1
	 * @param custclass2 お客様区分2
	 * @param checkbox 顧客承認フラグ
	 * @param time タイムスタンプ
	 */
	public CUSTOMER (String customerid, String nameen, String name, String inchargeid, String registereddate, String hiragana, String katakana, String description, String isin, String houjinno, String rank, String custclass1, String custclass2, String checkbox, String time) {
		super._resourceVersion = new ResourceVersionTimestampImpl();
		
		this.setCUSTOMERID(customerid);
		this.setNAMEEN(nameen);
		this.setNAME(name);
		this.setINCHARGEID(inchargeid);
		this.setREGISTEREDDATE(registereddate);
		this.setHIRAGANA(hiragana);
		this.setKATAKANA(katakana);
		this.setDESCRIPTION(description);
		this.setISIN(isin);
		this.setHOUJINNO(houjinno);
		this.setRANK(rank);
		this.setCUSTCLASS1(custclass1);
		this.setCUSTCLASS2(custclass2);
		this.setCHECKBOX(checkbox);
		this.setTIME(time);
	}

	/**
	 * 主キーを取得する。
	 * @return 主キーオブジェクト
	 */
	@EmbeddedId
	@Override
	public CUSTOMERPK getPk() {
		return this.pk;
	}

	/**
	 * 主キーを設定する。
	 * @param 主キーオブジェクト
	 */
	@Override
	public void setPk(CUSTOMERPK pk) {
		this.pk = (CUSTOMERPK) pk;
	}

	/**
	 * CUSTOMERIDを取得する。
	 * @return CUSTOMERID
	 */
	@Transient	
	public String getCUSTOMERID() {
		return this.pk.getCUSTOMERID();
	}

	/**
	 * お客様番号を設定する。
	 * @param CUSTOMERID
	 */
	public void setCUSTOMERID(String customerid) {
		this.pk.setCUSTOMERID(emptyStringToNull(customerid));
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
	 * お客様名（英字）を設定する。
	 * @param NAMEEN
	 */
	public void setNAMEEN(String nameen) {
		this.nameen = emptyStringToNull(nameen);
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
	 * お客様名（全角）を設定する。
	 * @param NAME
	 */
	public void setNAME(String name) {
		this.name = emptyStringToNull(name);
	}

	/**
	 * INCHARGEIDを取得する。
	 * @return INCHARGEID
	 */
	@Column(name = "inchargeid")
	public String getINCHARGEID() {
		return this.inchargeid;
	}

	/**
	 * 担当IDを設定する。
	 * @param INCHARGEID
	 */
	public void setINCHARGEID(String inchargeid) {
		this.inchargeid = emptyStringToNull(inchargeid);
	}

	/**
	 * REGISTEREDDATEを取得する。
	 * @return REGISTEREDDATE
	 */
	@Column(name = "registereddate")
	public String getREGISTEREDDATE() {
		return this.registereddate;
	}

	/**
	 * 登録日を設定する。
	 * @param REGISTEREDDATE
	 */
	public void setREGISTEREDDATE(String registereddate) {
		this.registereddate = emptyStringToNull(registereddate);
	}

	/**
	 * HIRAGANAを取得する。
	 * @return HIRAGANA
	 */
	@Column(name = "hiragana")
	public String getHIRAGANA() {
		return this.hiragana;
	}

	/**
	 * お客様名（かな）を設定する。
	 * @param HIRAGANA
	 */
	public void setHIRAGANA(String hiragana) {
		this.hiragana = emptyStringToNull(hiragana);
	}

	/**
	 * KATAKANAを取得する。
	 * @return KATAKANA
	 */
	@Column(name = "katakana")
	public String getKATAKANA() {
		return this.katakana;
	}

	/**
	 * お客様名（カナ）を設定する。
	 * @param KATAKANA
	 */
	public void setKATAKANA(String katakana) {
		this.katakana = emptyStringToNull(katakana);
	}

	/**
	 * DESCRIPTIONを取得する。
	 * @return DESCRIPTION
	 */
	@Column(name = "description")
	public String getDESCRIPTION() {
		return this.description;
	}

	/**
	 * 概要を設定する。
	 * @param DESCRIPTION
	 */
	public void setDESCRIPTION(String description) {
		this.description = emptyStringToNull(description);
	}

	/**
	 * ISINを取得する。
	 * @return ISIN
	 */
	@Column(name = "isin")
	public String getISIN() {
		return this.isin;
	}

	/**
	 * ISINコードを設定する。
	 * @param ISIN
	 */
	public void setISIN(String isin) {
		this.isin = emptyStringToNull(isin);
	}

	/**
	 * HOUJINNOを取得する。
	 * @return HOUJINNO
	 */
	@Column(name = "houjinno")
	public String getHOUJINNO() {
		return this.houjinno;
	}

	/**
	 * 法人番号を設定する。
	 * @param HOUJINNO
	 */
	public void setHOUJINNO(String houjinno) {
		this.houjinno = emptyStringToNull(houjinno);
	}

	/**
	 * RANKを取得する。
	 * @return RANK
	 */
	@Column(name = "rank")
	public String getRANK() {
		return this.rank;
	}

	/**
	 * お客様ランクを設定する。
	 * @param RANK
	 */
	public void setRANK(String rank) {
		this.rank = emptyStringToNull(rank);
	}

	/**
	 * CUSTCLASS1を取得する。
	 * @return CUSTCLASS1
	 */
	@Column(name = "custclass1")
	public String getCUSTCLASS1() {
		return this.custclass1;
	}

	/**
	 * お客様区分1を設定する。
	 * @param CUSTCLASS1
	 */
	public void setCUSTCLASS1(String custclass1) {
		this.custclass1 = emptyStringToNull(custclass1);
	}

	/**
	 * CUSTCLASS2を取得する。
	 * @return CUSTCLASS2
	 */
	@Column(name = "custclass2")
	public String getCUSTCLASS2() {
		return this.custclass2;
	}

	/**
	 * お客様区分2を設定する。
	 * @param CUSTCLASS2
	 */
	public void setCUSTCLASS2(String custclass2) {
		this.custclass2 = emptyStringToNull(custclass2);
	}

	/**
	 * CHECKBOXを取得する。
	 * @return CHECKBOX
	 */
	@Column(name = "checkbox")
	public String getCHECKBOX() {
		return this.checkbox;
	}

	/**
	 * 顧客承認フラグを設定する。
	 * @param CHECKBOX
	 */
	public void setCHECKBOX(String checkbox) {
		this.checkbox = emptyStringToNull(checkbox);
	}

	/**
	 * TIMEのDB値を取得する。
	 * @return TIMEのDB値
	 */
	@Version
	@Column(name = "time")
	public Timestamp getTIME_db() {	
		return super.get_resourceVersion();
	}

	/**
	 * タイムスタンプの文字列表記を取得する。
	 * @return TIMEの文字列表記
	 */
	@Transient	
	public String getTIME() {
		return super.get_resourceVersionString();
	}

	/**
	 * タイムスタンプのDB値を設定する。
	 * @param TIMEのDB値
	 */
	public void setTIME_db(Timestamp time) {
		super.set_resourceVersion(time);
	}

	/**
	 * タイムスタンプの文字列表記を設定する。
	 * @param TIMEの文字列表記
	 */
	public void setTIME(String time) {
		super.set_resourceVersionString(emptyStringToNull(time));
	}

	/**
	 * CONTACTを取得する。
	 * @return CONTACT
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	@JoinColumn(name="customerid", referencedColumnName="customerid", insertable=false, updatable=false)
	@OrderBy("pk.CUSTOMERID ASC, pk.CONTACTID ASC")
	public List<CONTACT> getCONTACT() {
		return contact;
	}

	/**
	 * CONTACTを設定する。
	 * @param CONTACT
	 */
	public void setCONTACT(List<CONTACT> contact) {
		this.contact = contact;
	}
	/**
	 * APPROVELOGを取得する。
	 * @return APPROVELOG
	 */
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	@JoinColumn(name="customerid", referencedColumnName="customerid", insertable=false, updatable=false)
	public APPROVELOG getAPPROVELOG() {
		return approvelog;
	}

	/**
	 * APPROVELOGを設定する。
	 * @param APPROVELOG
	 */
	public void setAPPROVELOG(APPROVELOG approvelog) {
		this.approvelog = approvelog;
	}
	/**
	 * ADDRESSを取得する。
	 * @return ADDRESS
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	@JoinColumn(name="customerid", referencedColumnName="customerid", insertable=false, updatable=false)
	@OrderBy("pk.CUSTOMERID ASC, pk.ADDRESSCD ASC")
	public List<ADDRESS> getADDRESS() {
		return address;
	}

	/**
	 * ADDRESSを設定する。
	 * @param ADDRESS
	 */
	public void setADDRESS(List<ADDRESS> address) {
		this.address = address;
	}
	/**
	 * STAFFを取得する。
	 * @return STAFF
	 */
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	@JoinColumn(name="inchargeid", referencedColumnName="employeeno", insertable=false, updatable=false)
	public STAFF getSTAFF() {
		return staff;
	}

	/**
	 * STAFFを設定する。
	 * @param STAFF
	 */
	public void setSTAFF(STAFF staff) {
		this.staff = staff;
	}

}