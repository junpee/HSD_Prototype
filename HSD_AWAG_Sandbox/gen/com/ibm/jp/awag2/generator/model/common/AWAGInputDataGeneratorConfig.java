/**
 * 
 */
package com.ibm.jp.awag2.generator.model.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AWAG InputDataGeneratorの設定ファイルを示すクラス。
 * JAXBでXMLからオブジェクトへ設定値をマップする。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "config")
public class AWAGInputDataGeneratorConfig {

	/** AWAG Version */
	@XmlElement(name = "version")
	private String version;
	
	/**
	 * versionを設定する。
	 * @param version 設定するversion
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

}
