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
package com.ibm.jp.awag2.generator.model.java;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * テーブル定義の関連リソース定義を表すクラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RelationDefinition {

	/** 関連パターン */
	@XmlElement
	private RelationPattern relationPattern;
	
	/** 子リソース */
	@XmlElement(name = "name")
	private String name;
	
	/** 結合キー */
	@XmlElementWrapper(name = "joinkey")
	@XmlElement(name = "key")
	private List<JoinKey> joinKeyList = new LinkedList<>();
	
	/** テーブル定義への参照 */
	private TableDefinition tableDefinition;
	
	/** 結合キーを示すインナー・クラス */
	public static class JoinKey{
		
		/** 結合キー（親） */
		private String keyParent;
		
		/** 結合キー（子） */
		private String keyDependence;
		
		public String getKeyParent() {
			return keyParent;
		}
		public void setKeyParent(String keyParent) {
			this.keyParent = keyParent;
		}
		public String getKeyDependence() {
			return keyDependence;
		}
		public void setKeyDependence(String keyDependence) {
			this.keyDependence = keyDependence;
		}
	}
	
	/** 関連パターン */
	public enum RelationPattern {
		OneToMany("1-N"),
		OneToOne("1-1");
		
		/** 関連パターン文字列 */
		private String patternString;
		
		private RelationPattern(String patternString) {
			this.patternString = patternString;
		}
		
		public String getPatternString() {
			return this.patternString;
		}

		public static RelationPattern patternOf(String valueString) {
			
			RelationPattern ret = null;
			
			for (RelationPattern pattern : values()) {
				if (pattern.getPatternString().equals(valueString)) {
					ret =  pattern;
				}
			}
			return ret;
		}
	}

	public RelationPattern getRelationPattern() {
		return relationPattern;
	}

	public void setRelationPattern(RelationPattern relationPattern) {
		this.relationPattern = relationPattern;
	}

	public String getName() {
		return name;
	}

	public void setName(String tableName) {
		this.name = tableName;
	}

	public List<JoinKey> getJoinKeyList() {
		return joinKeyList;
	}

	public void setJoinKeyList(List<JoinKey> joinKeyList) {
		this.joinKeyList = joinKeyList;
	}
	
	public void addJoinKey(JoinKey joinKey) {
		this.joinKeyList.add(joinKey);
	}

	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}

	public void setTableDefinition(TableDefinition resourceDefinition) {
		this.tableDefinition = resourceDefinition;
	}
}
