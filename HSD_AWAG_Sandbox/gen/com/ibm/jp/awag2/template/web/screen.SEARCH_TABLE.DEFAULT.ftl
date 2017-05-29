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
<form name="form" action="#">
<#list screen.fieldGroupList as fieldGroup>
	<#if !fieldGroup.list>
  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${screen.screenDisplayName}</h2>
    </div>
    <div class="mdl-card__supporting-text mdl-card--border">
      <div class="mdl-grid">
        <!-- errors-->
        <div class="error mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--12-col" ng-show="vm.error.details">
          <div ng-repeat="detail in vm.error.details">
            <i class="material-icons">warning</i> {{detail.message}} <span ng-show="detail.code.length>0">[</span>{{detail.code}}<span ng-show="detail.code.length>0">]</span> <br/>
          </div>
        </div>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "TEXT">
        <!-- ${field.displayName} Textfield with Floating Label -->
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
				<#if !field.readonly>
          <label class="mdl-textfield__label" for="${field.fieldName}">${field.displayName} ...</label>
					<#if field.formatValidationRule??>
						<#assign inputtype="text">
						<#if field.options?? && field.options.input_type??>
							<#assign inputtype=field.options.input_type>
						</#if>
						<#if field.options?? && field.options.regexpValidation??>
		  <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.options.regexpValidation}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}" <#if field.required>required</#if> />
						<#else>
		  <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.formatValidationRule.regex}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}" <#if field.required>required</#if> />
						</#if>
					<#else>
          <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}" <#if field.required>required</#if> />
					</#if>
					<#if field.formatValidationRule??>
						<#if field.options?? && field.options.validationMessage??>
          <span class="mdl-textfield__error" ng-show="form.${field.fieldName}.$error.pattern">${field.options.validationMessage}</span>
						<#else>
          <span class="mdl-textfield__error" ng-show="form.${field.fieldName}.$error.pattern">${field.formatValidationRule.message}</span>
						</#if>
					</#if>
				<#else>
          <span class="input-floating-label">${field.displayName}...</span>
          <span id="${field.fieldName}" class="mdl-textfield__output">{{vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}}}</span>
				</#if>
        </div>
			</#if>
			<#if field.htmlType == "SPACER">
        <!-- space-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
			</#if>
			<#if field.htmlType == "TEXTAREA">
        <!-- ${field.displayName} Floating Multiline Textarea -->
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--12-col">
				<#if !field.readonly>
          <textarea class="mdl-textfield__input" id="${field.fieldName}" rows="3" type="text" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}"></textarea>
          <label class="mdl-textfield__label" for="${field.fieldName}">${field.displayName} ...</label>
				<#else>
          <span class="input-floating-label">${field.displayName}...</span>
          <pre>{{vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}}}</pre>
				</#if>
        </div>
			</#if>
			<#if field.htmlType == "SELECT">
        <!-- ${field.displayName} Pulldown-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName} ...</span>
          <select class="mdl-textfield__input height28" id="${field.fieldName}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}" <#if field.readonly>disabled</#if>>
            <option ng-repeat="opt in vm.${field.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
          </select>
        </div>
			</#if>
			<#if field.htmlType == "RADIO">
        <!-- ${field.displayName} Radio-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName} ...</span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${field.fieldName}-{{opt.value}}" ng-repeat="opt in vm.${field.codeListId}">
            <input class="mdl-radio__button" id="${field.fieldName}-{{opt.value}}" name="${field.fieldName}" type="radio" value="{{opt.value}}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}" <#if field.readonly>disabled</#if>/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "CHECKBOX">
        <!-- ${field.displayName} Checkbox shown as radio in search -->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName}</span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${field.fieldName}-{{opt.value}}" ng-repeat="opt in vm.${field.codeListId}">
            <input type="radio" class="mdl-radio__button" id="${field.fieldName}-{{opt.value}}" name="${field.fieldName}" value="{{opt.value}}" ng-model="vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}"/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "OUTPUT">
        <!-- ${field.displayName} output -->
        <div class="mdl-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone is-upgraded" data-upgraded=",MaterialTextfield">
          <span class="input-floating-label">${field.displayName}...</span>
          <span id="${field.fieldName}" class="mdl-textfield__output">{{vm.model.<#if screen.screenType == "SEARCH_TABLE">search.</#if>${field.fieldName}}}</span>
        </div>
			</#if>
			<#if field.htmlType == "STATIC_TEXT">
        <!-- ${field.displayName} static text -->
        <div class="mdl-cell mdl-cell--top mdl-cell--12-col">
          ${field.value}
        </div>
			</#if>
		</#list>
      </div>
    </div>
    <div class="mdl-card__actions mdl-card--border">
		<#list fieldGroup.eventList as event>
			<#if event.eventName!="awagInit" && event.eventName!="awagSelect">
      <!-- Raised button with ripple -->
				<#if event.eventFireType?? && event.eventFireType=="ANYTIME">
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()">
				<#else>
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()" ng-disabled="form.$invalid">
				</#if>
        ${event.eventDisplayName}
      </button>
			</#if>
			<#if event.eventName=="awagSearch">
      (
      <label class="mdl-radio  mdl-js-radio mdl-js-ripple-effect" for="option-And">
        <input class="mdl-radio__button" id="option-And" name="option-AndOr" type="radio" value="false" ng-model="vm.model.search._inclusiveor"/>
        <span class="mdl-radio__label">And</span>
      </label>
      <label class="mdl-radio  mdl-js-radio mdl-js-ripple-effect" for="option-Or">
        <input class="mdl-radio__button" id="option-Or" name="option-AndOr" type="radio" value="true" ng-model="vm.model.search._inclusiveor"/>
        <span class="mdl-radio__label">Or</span>
      </label>
      <button type="reset" id="btn_reset" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" ng-click="vm.awagReset()">
        クリア
      </button>
      )
			</#if>
		</#list>
    </div>
  </div>

	<#elseif fieldGroup.list>
  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${screen.screenDisplayName} 一覧</h2>
    </div>

    <div class="mdl-card__supporting-text mdl-card--border">
      <div class="x-table">
          <table class="mdl-data-table mdl-shadow--2dp">
            <thead>
              <tr>
                <th>#</th>
		<#assign pkFields = []>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "NONE" || field.htmlType == "BUTTON">
			<#elseif field.formatValidationRule?? && field.formatValidationRule.formatType?? && (field.formatValidationRule.formatType == "HALF_NUM" || field.formatValidationRule.formatType == "SMALL_INT" || field.formatValidationRule.formatType == "INTEGER" || field.formatValidationRule.formatType == "BIG_INT" || field.formatValidationRule.formatType == "FLOAT" || field.formatValidationRule.formatType == "DECIMAL")>
                <th class=""                                  ng-click="vm.sort${fieldGroup.listName?cap_first}('${field.fieldName}')" ng-class="{'mdl-data-table__header--sorted-descending': vm.sortKey${fieldGroup.listName?cap_first}==='-${field.fieldName}', 'mdl-data-table__header--sorted-ascending': vm.sortKey${fieldGroup.listName?cap_first}==='+${field.fieldName}'}">${field.displayName}</th>
			<#else>
                <th class="mdl-data-table__cell--non-numeric" ng-click="vm.sort${fieldGroup.listName?cap_first}('${field.fieldName}')" ng-class="{'mdl-data-table__header--sorted-descending': vm.sortKey${fieldGroup.listName?cap_first}==='-${field.fieldName}', 'mdl-data-table__header--sorted-ascending': vm.sortKey${fieldGroup.listName?cap_first}==='+${field.fieldName}'}">${field.displayName}</th>
			</#if>
			<#if field.pk>
				<#assign pkFields = pkFields + [field.fieldName]>
			</#if>
		</#list>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.model.${fieldGroup.listName} | limitTo: vm.pager.len :vm.pager.start">
                <td>
                  <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="selectedKey{{$index}}">
                    <input class="mdl-radio__button" id="selectedKey{{$index}}" name="selectedKey" type="radio" ng-value="item" ng-model="vm.selectedKey" ng-click="vm.awagSelect()" />
                  </label>
                </td>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "NONE" || field.htmlType == "BUTTON">
			<#elseif field.formatValidationRule?? && field.formatValidationRule.formatType?? && (field.formatValidationRule.formatType == "HALF_NUM" || field.formatValidationRule.formatType == "SMALL_INT" || field.formatValidationRule.formatType == "INTEGER" || field.formatValidationRule.formatType == "BIG_INT" || field.formatValidationRule.formatType == "FLOAT" || field.formatValidationRule.formatType == "DECIMAL")>
                <td class="">{{item.${field.fieldName}}}</td>
			<#else>
				<#if field.htmlType == "SELECT" || field.htmlType == "RADIO" && field.fieldName!="awagSelect" || field.htmlType == "CHECKBOX">
                <td class="mdl-data-table__cell--non-numeric">{{vm.getNameFromValue(vm.${field.codeListId}, item.${field.fieldName})}}</td>
				<#else>
                <td class="mdl-data-table__cell--non-numeric">{{item.${field.fieldName}}}</td>
				</#if>
			</#if>
		</#list>
              </tr>
            </tbody>
          </table>
      </div>
      <span ng-repeat="page in vm.pager.pagerList">
        <a class="mdl-navigation__link" ng-click="vm.paginate(vm.pager, page )" ng-if="vm.pager.pagenum!=page" href="javascript:void(0)"> [{{page+1}}] </a>
        <span class="mdl-navigation__link" ng-click="vm.paginate(vm.pager, page )" ng-if="vm.pager.pagenum==page"> {{page+1}} </span>
      </span>Page
    </div>

    <div class="mdl-card__actions mdl-card--border">
		<#list fieldGroup.eventList as event>
			<#if event.eventName!="awagInit" && event.eventName!="awagSelect">
      <!-- Raised button with ripple -->
				<#if event.eventFireType?? && event.eventFireType=="ANYTIME">
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()">
				<#elseif event.eventFireType?? && event.eventFireType=="FORMVALID">
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()" ng-disabled="form.$invalid">
				<#else>
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()" ng-disabled="!vm.modelSelected">
				</#if>
        ${event.eventDisplayName}
      </button>
			</#if>
		</#list>
    </div>
  </div>

	</#if>
</#list>
</form>
