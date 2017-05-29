package com.ibm.jp.awag.generator.model;

import java.util.LinkedList;
import java.util.List;

/**
 * リソース定義シートの関連リソース定義を示すクラス。
 *
 */
public class RelationDefinition {

	/** 連関パターン */
	private RelationPattern relationPattern;
	
	/** 子リソース */
	private String dependentResourceName;
	
	/** 結合キー */
	private List<JoinKey> joinKeyList = new LinkedList<>();
	
	/** リソース定義への参照 */
	private ResourceDefinition resourceDefinition;
	
	/** 結合キーを示すインナー・クラス */
	public static class JoinKey{
		
		/** 結合キー親 */
		private String keyParent;
		
		/** 結合キー子 */
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

	public String getDependentResourceName() {
		return dependentResourceName;
	}

	public void setDependentResourceName(String dependentResourceName) {
		this.dependentResourceName = dependentResourceName;
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

	public ResourceDefinition getResourceDefinition() {
		return resourceDefinition;
	}

	public void setResourceDefinition(ResourceDefinition resourceDefinition) {
		this.resourceDefinition = resourceDefinition;
	}
}
