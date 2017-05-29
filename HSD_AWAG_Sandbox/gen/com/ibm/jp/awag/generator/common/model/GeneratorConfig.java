package com.ibm.jp.awag.generator.common.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneratorConfig {

	/** DataLoader */
	@XmlElement(name = "dataLoaderClassName")
	private String dataLoaderClassName;
	
	/** 出力ファイルにタイムスタンプを出力するか */
	@XmlElement(defaultValue="false")
	private boolean printTimeStampToOutputFile = false;
	
	/** TemplateSets */
	@XmlElementWrapper(name = "templateSets")
	@XmlElement(name = "templateSet")
	private List<TemplateSet> templateSets;

	/** TemplateSetMap */
	@XmlTransient
	private Map<String, TemplateSet> templateSetMap;

	/** TemplateSet設定を示すインナー・クラス */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class TemplateSet {
		
		/** TemplateSet Name */
		@XmlAttribute(name = "name")
		private String name;
		
		/** CommonFileフラグ */
		@XmlElement
		private boolean commonfile;
		
		/** 出力ディレクトリ */
		@XmlElement
		private String outputdir;
		
		/** エンコーディング */
		@XmlElement
		private String encoding;
		
		/** パッケージ名 */
		@XmlElement
		private String templatePackage;
		
		/** 画面コードフラグ */
		@XmlElement
		private boolean webCode; 

		/** 出力ファイルにタイムスタンプを出力するか */
		@XmlElement(defaultValue="false")
		private boolean printTimeStampToOutputFile = false;
		
		/** TemplateList */
		@XmlElementWrapper(name = "templates")
		@XmlElement(name = "template")
		private List<TemplateConfig> templates;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isCommonfile() {
			return commonfile;
		}

		public void setCommonfile(boolean commonfile) {
			this.commonfile = commonfile;
		}

		public String getOutputdir() {
			return outputdir;
		}

		public void setOutputdir(String outputdir) {
			this.outputdir = outputdir;
		}

		public String getEncoding() {
			return encoding;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}

		public String getTemplatePackage() {
			return templatePackage;
		}

		public void setTemplatePackage(String templatePackage) {
			this.templatePackage = templatePackage;
		}

		public List<TemplateConfig> getTemplateList() {
			return templates;
		}

		public void setTemplates(List<TemplateConfig> templates) {
			this.templates = templates;
		}

		public boolean isWebCode() {
			return webCode;
		}

		public void setWebCode(boolean webCode) {
			this.webCode = webCode;
		}

		public boolean isPrintTimeStampToOutputFile() {
			return printTimeStampToOutputFile;
		}

		public void setPrintTimeStampToOutputFile(boolean printTimeStampToOutputFile) {
			this.printTimeStampToOutputFile = printTimeStampToOutputFile;
		}
	}
	
	/** Excel定義シートマッピングを示すインナー・クラス */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DefinitionSheetMapping {

		/** 定義名 */
		@XmlAttribute(name = "name")
		private String name;
		
		/** セル・アドレス */
		@XmlValue
		private String address;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	}
	
	/** Template設定を示すインナー・クラス */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class TemplateConfig {

		/** Template Name */
		@XmlAttribute(name = "name")
		private String name;
		
		/** 出力ディレクトリ */
		@XmlElement
		private String dir;
		
		/** 出力ファイル名 */
		@XmlElement
		private String fileName;
		
		/** 上書きフラグ */
		@XmlElement(defaultValue="false")
		private boolean allowOverWrite;
		
		/** 関連パターンリスト */
		@XmlElement(name = "pattern")
		@XmlList
		List<String> patternList;
		
		/** 子リソース毎の出力フラグ */
		@XmlElement(name = "generateToEachEmbedded", defaultValue="false")
		private boolean generateToEachEmbedded;

		@XmlElement(name = "active")
		private boolean isActive = true;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public boolean isAllowOverWrite() {
			return allowOverWrite;
		}

		public void setAllowOverWrite(boolean allowOverWrite) {
			this.allowOverWrite = allowOverWrite;
		}
		
		public List<String> getPatternList() {
			return patternList;
		}

		public void setPatternList(List<String> patternList) {
			this.patternList = patternList;
		}

		public void setGenerateToEachEmbedded(boolean generateToEachEmbedded) {
			this.generateToEachEmbedded = generateToEachEmbedded;
		}

		public boolean isGenerateToEachEmbedded() {
			return this.generateToEachEmbedded;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
	}

	public String getDataLoaderClassName() {
		return dataLoaderClassName;
	}

	public void setDataLoaderClassName(String dataLoaderClassName) {
		this.dataLoaderClassName = dataLoaderClassName;
	}

	public boolean isPrintTimeStampToOutputFile() {
		return printTimeStampToOutputFile;
	}

	public void setPrintTimeStampToOutputFile(boolean printTimeStampToOutputFile) {
		this.printTimeStampToOutputFile = printTimeStampToOutputFile;
	}

	public List<TemplateSet> getTemplateSets() {
		return templateSets;
	}
	
	public Map<String, TemplateSet> getTemplateSetMap() {
		
		if(this.templateSetMap == null) {
			this.templateSetMap = this.templateSets.stream().collect(Collectors.toMap(TemplateSet::getName, s -> s));
		}
		
		return this.templateSetMap;
	}
}
