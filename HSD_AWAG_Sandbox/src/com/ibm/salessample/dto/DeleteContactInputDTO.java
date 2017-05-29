/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.validator.DateTime;
import com.ibm.jp.awag2.common.validator.Number;
import com.ibm.jp.awag2.common.validator.StringFormat;
import com.ibm.jp.awag2.common.dto.DTOBase;

/**
 * DeleteContactのInput DTOクラス。
 *
 */
public class DeleteContactInputDTO  extends DTOBase {

	/** お客様番号(customerid) */
	@NotNull
	@StringFormat(type = StringFormat.Type.HALF_NUM, minLength = 8, maxLength = 8)			
	@DisplayName(name = "お客様番号")
	private String customerid;

	/** 履歴番号(contactid) */
	@NotNull
	@Number(type = Number.Type.INTEGER, maxLength = 3)			
	@DisplayName(name = "履歴番号")
	private String contactid;

	/** 担当ID(inchargeid) */
	@DisplayName(name = "担当ID")
	private String inchargeid;

	/** 内容(description) */
	@DisplayName(name = "内容")
	private String description;

	/** セールスコード(salescd) */
	@DisplayName(name = "セールスコード")
	private String salescd;

	/** オッズ(ozz) */
	@DisplayName(name = "オッズ")
	private String ozz;

	/** タイムスタンプ(time) */
	@NotNull
	@DateTime(type=DateTime.Type.TIMESTAMP)			
	@DisplayName(name = "タイムスタンプ")
	private String time;
	

	/**
	 * お客様番号を取得する。
	 * @return customerid
	 */
	public String getCustomerid() {
		return customerid;
	}

	/**
	 * お客様番号を設定する。
	 * @param customerid
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	/**
	 * 履歴番号を取得する。
	 * @return contactid
	 */
	public String getContactid() {
		return contactid;
	}

	/**
	 * 履歴番号を設定する。
	 * @param contactid
	 */
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}

	/**
	 * 担当IDを取得する。
	 * @return inchargeid
	 */
	public String getInchargeid() {
		return inchargeid;
	}

	/**
	 * 担当IDを設定する。
	 * @param inchargeid
	 */
	public void setInchargeid(String inchargeid) {
		this.inchargeid = inchargeid;
	}

	/**
	 * 内容を取得する。
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 内容を設定する。
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * セールスコードを取得する。
	 * @return salescd
	 */
	public String getSalescd() {
		return salescd;
	}

	/**
	 * セールスコードを設定する。
	 * @param salescd
	 */
	public void setSalescd(String salescd) {
		this.salescd = salescd;
	}

	/**
	 * オッズを取得する。
	 * @return ozz
	 */
	public String getOzz() {
		return ozz;
	}

	/**
	 * オッズを設定する。
	 * @param ozz
	 */
	public void setOzz(String ozz) {
		this.ozz = ozz;
	}

	/**
	 * タイムスタンプを取得する。
	 * @return time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * タイムスタンプを設定する。
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
}