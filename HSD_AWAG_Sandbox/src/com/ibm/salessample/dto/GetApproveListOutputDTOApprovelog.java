/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.ibm.jp.awag2.common.dto.DTOBase;
import com.ibm.jp.awag2.common.util.TextUtility;

 /**
 * GetApproveList.ApprovelogのOutput DTOクラス。
 *
 */
@XmlType(propOrder = {"customerid", "comment", "rank", "status", "flag", "version"})
public class GetApproveListOutputDTOApprovelog  extends DTOBase {

	/** お客様番号(customerid) */
	private String customerid;

	/** コメント(comment) */
	private String comment;

	/** ランク(rank) */
	private String rank;

	/** ステータス(status) */
	private String status;

	/** 承認フラグ(flag) */
	private String flag;

	/** タイムスタンプ(version) */
	private String version;

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
	 * コメントを取得する。
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * コメントを設定する。
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * ランクを取得する。
	 * @return rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * ランクを設定する。
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	/**
	 * ステータスを取得する。
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * ステータスを設定する。
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 承認フラグを取得する。
	 * @return flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 承認フラグを設定する。
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * タイムスタンプを取得する。
	 * @return version
	 */
	public String getVersion() {
		return TextUtility.formatTimestamp(version);
	}

	/**
	 * タイムスタンプを設定する。
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}