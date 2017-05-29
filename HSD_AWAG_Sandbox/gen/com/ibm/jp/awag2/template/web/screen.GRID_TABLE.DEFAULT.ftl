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
	<#if fieldGroup.list>
  <div class="mdl-card  mdl-shadow--2dp myfitcard">
    <div class="mdl-card__title mdl-card--border card-title">
      <h2 class="mdl-card__title-text card-title-text"><i class="material-icons">web_asset</i> ${screen.screenDisplayName}</h2>
    </div>

    <div class="mdl-card__supporting-text mdl-card--border">
      <!-- errors-->
      <div class="error mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-cell mdl-cell--12-col" ng-show="vm.error.details">
        <div ng-repeat="detail in vm.error.details">
            <i class="material-icons">warning</i> {{detail.message}} <span ng-show="detail.code.length>0">[</span>{{detail.code}}<span ng-show="detail.code.length>0">]</span> <br/>
        </div>
      </div>
      <div class="x-table">
          <table class="mdl-data-table mdl-shadow--2dp">
            <thead>
              <tr>
		<#if screen.screenType == "GRID_SELECT_TABLE">
                <th class=""></th>
		</#if>
		<#assign pkFields = []>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "NONE" || field.htmlType == "BUTTON">
			<#elseif field.formatValidationRule.formatType?? && (field.formatValidationRule.formatType == "HALF_NUM" || field.formatValidationRule.formatType == "SMALL_INT" || field.formatValidationRule.formatType == "INTEGER" || field.formatValidationRule.formatType == "BIG_INT" || field.formatValidationRule.formatType == "FLOAT" || field.formatValidationRule.formatType == "DECIMAL") >
				<#assign isNumeric=true>
                <th class="">${field.displayName}</th>
			<#else>
				<#assign isNumeric=false>
                <th class="<#if !isNumeric>mdl-data-table__cell--non-numeric</#if>">${field.displayName}</th>
			</#if>
		</#list>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.model.${fieldGroup.listName}">
		<#if screen.screenType == "GRID_SELECT_TABLE">
                <td>
                  <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="selectedKey{{$index}}">
                    <input type="checkbox" class="mdl-checkbox__input" id="selectedKey{{$index}}" ng-model="item.selected" />
                  </label>
                </td>
		</#if>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "TEXT">
                <td class="<#if !isNumeric>mdl-data-table__cell--non-numeric</#if>" ng-form="itemForm${field?counter}">
				<#if !field.readonly>
					<#assign inputtype="text">
					<#if field.options?? && field.options.input_type??>
						<#assign inputtype=field.options.input_type>
					</#if>
					<#if field.formatValidationRule??>
						<#if field.options?? && field.options.regexpValidation??>
                  <input class="mdl-textfield__input" id="${field.fieldName}" type="${inputtype}" pattern="${field.options.regexpValidation}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> style="min-width: 160px;" />
						<#else>
                  <input class="mdl-textfield__input" id="${field.fieldName}" type="${inputtype}" pattern="${field.formatValidationRule.regex}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> style="min-width: 160px;" />
						</#if>
						<#if field.options?? && field.options.validationMessage??>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">${field.options.validationMessage}</span>
						<#else>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">${field.formatValidationRule.message}</span>
						</#if>
					<#else>
                  <input class="mdl-textfield__input" id="${field.fieldName}" type="${inputtype}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> style="min-width: 160px;" />
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
					</#if>
				<#else>
                  {{item.${field.fieldName}}}
				</#if>
                </td>
			<#elseif field.htmlType == "TEXTAREA">
                <td class="mdl-data-table__cell--non-numeric" ng-form="itemForm${field?counter}">
				<#if !field.readonly>
                  <textarea class="mdl-textfield__input" id="${field.fieldName}" rows="3" type="text" ng-model="item.${field.fieldName}" <#if field.required>required</#if> style="min-width: 160px;"></textarea>
				<#else>
                  <pre>{{item.${field.fieldName}}}</pre>
				</#if>
                </td>
			<#elseif field.htmlType == "SELECT">
                <td class="mdl-data-table__cell--non-numeric" ng-form="itemForm${field?counter}">
                  <select class="mdl-textfield__input height28" id="${field.fieldName}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if> style="min-width: 80px;">
                    <option ng-repeat="opt in vm.${field.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
                  </select>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "RADIO">
                <td class="mdl-data-table__cell--non-numeric" ng-form="itemForm${field?counter}">
                  <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${field.fieldName}-{{opt.value}}-{{$parent.$index}}" ng-repeat="opt in vm.${field.codeListId}">
                    <input class="mdl-radio__button" id="${field.fieldName}-{{opt.value}}-{{$parent.$index}}" name="${field.fieldName}-{{$parent.$index}}" type="radio" value="{{opt.value}}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
                    <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
                  </label>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "CHECKBOX">
                <td class="mdl-data-table__cell--non-numeric" ng-form="itemForm${field?counter}">
                  <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="${field.fieldName}-{{vm.${field.codeListId}[1].value}}-{{$index}}">
                    <input type="checkbox" id="${field.fieldName}-{{vm.${field.codeListId}[1].value}}-{{$index}}" class="mdl-checkbox__input" ng-model="item.${field.fieldName}" ng-true-value="'{{vm.${field.codeListId}[1].value}}'" ng-false-value="'{{vm.${field.codeListId}[0].value}}'" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
                    <span class="mdl-checkbox__label">{{vm.${field.codeListId}[1].name}}</span>
                  </label>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "OUTPUT">
                <td class="<#if !isNumeric>mdl-data-table__cell--non-numeric</#if>" ng-form="itemForm${field?counter}">
                  {{item.${field.fieldName}}}
                </td>
			</#if>
		</#list>
              </tr>
            </tbody>
          </table>
      </div>
		<#if screen.screenType == "GRID_SELECT_TABLE">
      更新対象を選択してください。
		</#if>
    </div>

    <div class="mdl-card__actions mdl-card--border">
		<#list fieldGroup.eventList as event>
			<#if event.eventName!="awagInit" && event.eventName!="awagSelect" >
      <!-- Raised button with ripple -->
				<#if event.eventFireType?? && event.eventFireType=="ANYTIME">
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()">
				<#else>
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()" ng-disabled="form.$invalid<#if screen.screenType == "GRID_SELECT_TABLE"> || !vm.awagIsCheckboxSelected()</#if>">
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
