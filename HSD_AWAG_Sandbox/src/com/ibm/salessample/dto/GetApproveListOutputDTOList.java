/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.ibm.jp.awag2.common.dto.DTOBase;
import com.ibm.jp.awag2.common.util.TextUtility;

 /**
 * GetApproveList.ListのOutput DTOクラス。
 *
 */
@XmlType(propOrder = {"customerid", "nameen", "name", "inchargeid", "checkbox", "time", "staff", "approvelog"})
public class GetApproveListOutputDTOList  extends DTOBase {

	/** お客様番号(customerid) */
	private String customerid;

	/** お客様名（英字）(nameen) */
	private String nameen;

	/** お客様名（全角）(name) */
	private String name;

	/** 担当者ID(inchargeid) */
	private String inchargeid;

	/** 顧客承認フラグ(checkbox) */
	private String checkbox;

	/** タイムスタンプ(time) */
	private String time;

	/** 担当者(staff) */
	private GetApproveListOutputDTOStaff staff;


	/** 承認状況(approvelog) */
	private GetApproveListOutputDTOApprovelog approvelog;


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
	 * お客様名（英字）を取得する。
	 * @return nameen
	 */
	public String getNameen() {
		return nameen;
	}

	/**
	 * お客様名（英字）を設定する。
	 * @param nameen
	 */
	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	/**
	 * お客様名（全角）を取得する。
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * お客様名（全角）を設定する。
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 担当者IDを取得する。
	 * @return inchargeid
	 */
	public String getInchargeid() {
		return inchargeid;
	}

	/**
	 * 担当者IDを設定する。
	 * @param inchargeid
	 */
	public void setInchargeid(String inchargeid) {
		this.inchargeid = inchargeid;
	}

	/**
	 * 顧客承認フラグを取得する。
	 * @return checkbox
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * 顧客承認フラグを設定する。
	 * @param checkbox
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * タイムスタンプを取得する。
	 * @return time
	 */
	public String getTime() {
		return TextUtility.formatTimestamp(time);
	}

	/**
	 * タイムスタンプを設定する。
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 担当者を取得する。
	 * @return staff
	 */
	public GetApproveListOutputDTOStaff getStaff() {
		return staff;
	}

	/**
	 * 担当者を設定する。
	 * @param staff
	 */
	public void setStaff(GetApproveListOutputDTOStaff staff) {
		this.staff = staff;
	}

	/**
	 * 承認状況を取得する。
	 * @return approvelog
	 */
	public GetApproveListOutputDTOApprovelog getApprovelog() {
		return approvelog;
	}

	/**
	 * 承認状況を設定する。
	 * @param approvelog
	 */
	public void setApprovelog(GetApproveListOutputDTOApprovelog approvelog) {
		this.approvelog = approvelog;
	}
}