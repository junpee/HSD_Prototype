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
<form action="#">
  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${resource.resourceDisplayName} 検索</h2>
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
	<#if column.searchParam>
		<#if column.htmlType == "TEXT">
        <!-- ${column.displayName} Textfield with Floating Label -->
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
			<#if column.formatValidationRule??>
          <input class="mdl-textfield__input" id="${column.fieldName}" type="text" pattern="${column.formatValidationRule.regex}" ng-model="m.search.${column.fieldName}"/>
			<#else>
          <input class="mdl-textfield__input" id="${column.fieldName}" type="text" ng-model="m.search.${column.fieldName}"/>
			</#if>
          <label class="mdl-textfield__label" for="${column.fieldName}">${column.displayName}...</label>
			<#if column.formatValidationRule??>
          <span class="mdl-textfield__error">${column.formatValidationRule.message}</span>
			</#if>
        </div>
		</#if>
		<#if column.htmlType == "SPACER">
        <!-- space-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
		</#if>
		<#if column.htmlType == "TEXTAREA">
        <!-- ${column.displayName} Floating Multiline Textarea -->
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--12-col">
          <textarea class="mdl-textfield__input" id="${column.fieldName}" rows="3" type="text" ng-model="m.search.${column.fieldName}"></textarea>
          <label class="mdl-textfield__label" for="${column.fieldName}">${column.displayName}...</label>
        </div>
		</#if>
		<#if column.htmlType == "SELECT">
        <!-- ${column.displayName} Pulldown-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}...</span>
          <select class="mdl-textfield__input height28" id="${column.fieldName}" ng-model="m.search.${column.fieldName}">
            <option ng-repeat="opt in m.${column.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
          </select>
        </div>
		</#if>
		<#if column.htmlType == "RADIO">
        <!-- ${column.displayName} Radio-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}</span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${column.fieldName}-{{opt.value}}" ng-repeat="opt in m.${column.codeListId}">
            <input class="mdl-radio__button" id="${column.fieldName}-{{opt.value}}" name="${column.fieldName}" type="radio" value="{{opt.value}}" ng-model="m.search.${column.fieldName}"/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
		</#if>
		<#if column.htmlType == "CHECKBOX">
        <!-- ${column.displayName} Checkbox-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <span class="input-floating-label">${column.displayName}</span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${column.fieldName}-{{opt.value}}" ng-repeat="opt in m.${column.codeListId}">
            <input class="mdl-radio__button" id="${column.fieldName}-{{opt.value}}" name="${column.fieldName}" type="radio" value="{{opt.value}}" ng-model="m.search.${column.fieldName}"/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
		</#if>
	</#if>
</#list>
      </div>
    </div>

    <div class="mdl-card__actions mdl-card--border">
      <!-- Raised button with ripple -->
      <button type="button" id="btn_search" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="m.doSearch()">
        検索
      </button>
      (
      <label class="mdl-radio  mdl-js-radio mdl-js-ripple-effect" for="option-And">
        <input class="mdl-radio__button" id="option-And" name="option-AndOr" type="radio" value="false" ng-model="m.search.inclusiveor"/>
        <span class="mdl-radio__label">And</span>
      </label>
      <label class="mdl-radio  mdl-js-radio mdl-js-ripple-effect" for="option-Or">
        <input class="mdl-radio__button" id="option-Or" name="option-AndOr" type="radio" value="true" ng-model="m.search.inclusiveor"/>
        <span class="mdl-radio__label">Or</span>
      </label>
      <button type="reset" id="btn_reset" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" ng-click="m.reset()">
        クリア
      </button>
      )
    </div>
  </div>

  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${resource.resourceDisplayName} 一覧</h2>
    </div>

    <div class="mdl-card__supporting-text mdl-card--border">
      <div class="mdl-grid">
        <div class="x-table mdl-cell mdl-cell--12-col">
          <span ng-repeat="page in m.pager.pagerList">
            <a class="mdl-navigation__link" ng-click="m.paginate(m.pager, page )" ng-if="m.pager.pagenum!=page" href="javascript:void(0)"> [{{page+1}}] </a>
            <span class="mdl-navigation__link" ng-click="m.paginate(m.pager, page )" ng-if="m.pager.pagenum==page"> {{page+1}} </span>
          </span>Page
          <table class="mdl-data-table mdl-shadow--2dp">
            <thead>
              <tr>
                <th>#</th>
<#list resource.columnDefinitions as column>
	<#if column.searchParam>
		<#if column.htmlType == "NONE">
		<#elseif column.dataType == "INTEGER" || column.dataType == "DECIMAL" || column.dataType == "NUMBER">
                <th class=""                                  ng-class="{'mdl-data-table__header--sorted-descending': m.${column.fieldName}Desc===true, 'mdl-data-table__header--sorted-ascending': m.${column.fieldName}Desc===false}" ng-click="m.resetDesc('${column.fieldName}');m.${column.fieldName}Desc=!m.${column.fieldName}Desc;m.sort('${column.fieldName}', m.${column.fieldName}Desc)">${column.displayName}</th>
		<#else>
                <th class="mdl-data-table__cell--non-numeric" ng-class="{'mdl-data-table__header--sorted-descending': m.${column.fieldName}Desc===true, 'mdl-data-table__header--sorted-ascending': m.${column.fieldName}Desc===false}" ng-click="m.resetDesc('${column.fieldName}');m.${column.fieldName}Desc=!m.${column.fieldName}Desc;m.sort('${column.fieldName}', m.${column.fieldName}Desc)">${column.displayName}</th>
		</#if>
	</#if>
</#list>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in m.searchList | limitTo: m.pager.len :m.pager.start">
                <td>
                  <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="selectedKey{{$index}}">
                    <input class="mdl-radio__button" id="selectedKey{{$index}}" name="selectedKey" type="radio" ng-value="<#list resource.pkFields as pkField>item.${pkField.fieldName}<#sep>+m.delimiter+</#list>" ng-model="m.selectedKey" ng-click="m.select()" />
                  </label>
                </td>
<#list resource.columnDefinitions as column>
	<#if column.searchParam>
		<#if column.htmlType == "NONE">
		<#elseif column.dataType == "INTEGER" || column.dataType == "DECIMAL" || column.dataType == "NUMBER">
                <td class="">{{item.${column.fieldName}}}</td>
		<#else>
			<#if column.htmlType == "SELECT" || column.htmlType == "RADIO" || column.htmlType == "CHECKBOX">
                <td class="mdl-data-table__cell--non-numeric">{{m.getNameFromValue(m.${column.codeListId}, item.${column.fieldName})}}</td>
			<#else>
                <td class="mdl-data-table__cell--non-numeric">{{item.${column.fieldName}}}</td>
			</#if>
		</#if>
	</#if>
</#list>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="mdl-card__actions mdl-card--border">
      <!-- Raised button with ripple -->
      <button type="button" id="btn_goupdate" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="m.goUpdate()" ng-disabled="m.updateDisabled">
        更新
      </button>
      <!-- Raised button with ripple -->
      <button type="button" id="btn_godelete" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" ng-click="m.goDelete()" ng-disabled="m.deleteDisabled">
        削除
      </button>
      <!-- Raised button with ripple -->
      <button type="button" id="btn_gonew" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" ng-click="m.goNew()">
        追加
      </button>
    </div>
  </div>
</form>
