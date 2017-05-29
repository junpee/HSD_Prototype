/**
 * 
 */
package com.ibm.jp.awag2.generator.model.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * AWAGInputDataGeneratorの処理中に発生したエラーを格納するクラス。
 *
 */
@XmlType(propOrder = {"errorAtModel", "errorAtName", "errorAtDisplayName", "errorAtId", "messageList"})
@XmlAccessorType(XmlAccessType.FIELD)
public class AWAGInputDataGeneratorError {

	/** エラー発生箇所 */
	@XmlElement(name = "model")
	private String errorAtModel;
	
	/** errorAtName */
	@XmlElement(name = "name")
	private String errorAtName;
	
	/** errorAtDisplayName */
	@XmlElement(name = "displayName")
	private String errorAtDisplayName;
	
	/** errorAtId */
	@XmlElement(name = "modelId")
	private String errorAtId;
	
	/** エラーメッセージ */
	@XmlElement(name = "message")
	private List<String> messageList = new ArrayList<>();

	/**
	 * デフォルト・コンストラクタ。
	 */
	public AWAGInputDataGeneratorError() {
		super();
	}

	/**
	 * エラー情報を指定して初期化する。
	 * @param errorAtName
	 * @param errorAtDisplayName
	 * @param errorAtId
	 */
	public AWAGInputDataGeneratorError(String errorAtModel, String errorAtName, String errorAtDisplayName, String errorAtId) {
		super();
		this.errorAtModel = errorAtModel;
		this.errorAtName = errorAtName;
		this.errorAtDisplayName = errorAtDisplayName;
		this.errorAtId = errorAtId;
	}
	
	/**
	 * errorAtModelを取得する。
	 * @return errorAtModel
	 */
	public String getErrorAtModel() {
		return this.errorAtModel;
	}

	/**
	 * errorAtModelを設定する。
	 * @param errorAtModel 設定するerrorAtModel
	 */
	public void setErrorAtModel(String errorAtModel) {
		this.errorAtModel = errorAtModel;
	}

	/**
	 * errorListを取得する。
	 * @return messageList
	 */
	public List<String> getMessageList() {
		return this.messageList;
	}

	/**
	 * errorListを設定する。
	 * @param messageList 設定するerrorList
	 */
	public void setMessageList(List<String> errorList) {
		this.messageList = errorList;
	}
	
	/**
	 * エラーメッセージを追加する
	 * @param message
	 * @return
	 */
	public AWAGInputDataGeneratorError addMessage(String message) {
		this.messageList.add(message);
		return this;
	}
	
	/**
	 * エラーメッセージを追加する
	 * @param message
	 * @return
	 */
	public AWAGInputDataGeneratorError addMessages(List<String> messages) {
		this.messageList.addAll(messages);
		return this;
	}
	
}
