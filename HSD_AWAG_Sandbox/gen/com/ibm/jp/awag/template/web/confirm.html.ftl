<#--
Automated web application generator

Licensed Materials - Property of IBM
"Restricted Materials of IBM"
IPSC : 6949-63S
(C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
(C) Copyright IBM Corp. 2016 All Rights Reserved.
US Government Users Restricted Rights -
Use, duplication or disclosure restricted
by GSA ADP Schedule Contract with IBM Corp.
 -->
<!-- Generated by AWAG ver.${AWAGversion} at ${.now?iso_local} -->
<#assign isDependent = resourceParent??>
<form action="#">
  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${resource.resourceDisplayName} 確認</h2>
    </div>

    <div class="mdl-card__supporting-text mdl-card--border">
      <div class="mdl-grid">
      <!-- errors-->
        <div class="error mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--12-col" ng-show="m.error.code">
          <!-- [{{m.error.code}}] {{m.error.message}} -->
          <div ng-repeat="detail in m.error.details">
            <i class="material-icons">warning</i> [{{detail.code}}] {{detail.message}} <br>
          </div>
        </div>

<#list resource.columnDefinitions as column>
	<#if column.htmlType == "TEXT">
        <!-- ${column.displayName} Textfield with Floating Label -->
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}...</span>
          <span id="${column.fieldName}" class="mdl-textfield__output">{{m.model<#if isDependent>.child</#if>.${column.fieldName}}}</span>
        </div>
	</#if>
	<#if column.htmlType == "SPACER">
        <!-- space-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
	</#if>
	<#if column.htmlType == "TEXTAREA">
        <!-- ${column.displayName} Floating Multiline Textarea -->
        <div class="mdl-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--12-col is-upgraded" data-upgraded=",MaterialTextfield">
          <span class="input-floating-label">${column.displayName}...</span>
          <pre>{{m.model<#if isDependent>.child</#if>.${column.fieldName}}}</pre>
        </div>
	</#if>
	<#if column.htmlType == "SELECT">
        <!-- ${column.displayName} Pulldown-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}...</span>
          <select class="mdl-textfield__input height28" id="${column.fieldName}" ng-model="m.model<#if isDependent>.child</#if>.${column.fieldName}" disabled>
            <option ng-repeat="opt in m.${column.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
          </select>
        </div>
	</#if>
	<#if column.htmlType == "RADIO">
        <!-- ${column.displayName} Radio-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}</span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${column.fieldName}-{{opt.value}}" ng-repeat="opt in m.${column.codeListId}">
            <input class="mdl-radio__button" id="${column.fieldName}-{{opt.value}}" name="${column.fieldName}" type="radio" value="{{opt.value}}" ng-model="m.model<#if isDependent>.child</#if>.${column.fieldName}" disabled/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
	</#if>
	<#if column.htmlType == "CHECKBOX">
        <!-- ${column.displayName} Checkbox-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}</span>
          <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="${column.fieldName}-{{${column.codeListId}[1].value}}"">
            <input type="checkbox" id="${column.fieldName}-{{${column.codeListId}[1].value}}" class="mdl-checkbox__input" ng-model="m.model<#if isDependent>.child</#if>.${column.fieldName}" ng-true-value="'{{${column.codeListId}[1].value}}'" ng-false-value="'{{${column.codeListId}[0].value}}'" disabled/>
            <span class="mdl-checkbox__label">{{m.${column.codeListId}[1].name}}</span>
          </label>
        </div>
	</#if>
	<#if column.htmlType == "OUTPUT">
        <!-- ${column.displayName} output -->
        <div class="mdl-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone is-upgraded" data-upgraded=",MaterialTextfield">
          <span class="input-floating-label">${column.displayName}...</span>
          <span id="${column.fieldName}" class="mdl-textfield__output">{{m.model<#if isDependent>.child</#if>.${column.fieldName}}}</span>
        </div>
	</#if>
</#list>
      </div>
    </div>

    <div class="mdl-card__actions mdl-card--border">
      <!-- Raised button with ripple -->
<#if !isDependent>
      <div ng-if="m.mode == 'update'">
        <button type="button" id="btn_update" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="m.update()">
          更新
        </button>
        <button type="button" id="btn_goentry" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" onclick="location.href='#!/${resource.resourceName?lower_case}/entry'">
          入力へ戻る
        </button>
      </div>
      <div ng-if="m.mode == 'new'">
        <button type="button" id="btn_create" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="m.create()">
          登録
        </button>
        <button type="button" id="btn_goentry" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" onclick="location.href='#!/${resource.resourceName?lower_case}/entry'">
          入力へ戻る
        </button>
      </div>
      <div ng-if="m.mode == 'delete'">
        <button type="button" id="btn_delete" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="m.delete()">
          削除
        </button>
        <button type="button" id="btn_gosearch" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" onclick="location.href='#!/${resource.resourceName?lower_case}'">
          検索へ戻る
        </button>
      </div>
<#else>
      <button type="button" id="btn_goconfirm" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" onclick="location.href='#!/${resourceParent.resourceName?lower_case}/confirm'">
        戻る
      </button>
</#if>
    </div>
  </div>
</form>
