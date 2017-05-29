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
package com.ibm.jp.awag2.generator.model.web;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * コード定義リストのWrapperクラス。
 *
 */
public class ArrayListWrapper{

	/** コード定義のリスト */
	ArrayList<DisplayCode> list = new ArrayList<DisplayCode>();;

	@XmlElementWrapper(name="arrayList")
	@XmlElement(name="displayCode")
	public ArrayList<DisplayCode> getList() {
		return list;
	}

	public void setList(ArrayList<DisplayCode> list) {
		this.list = list;
	}

}
