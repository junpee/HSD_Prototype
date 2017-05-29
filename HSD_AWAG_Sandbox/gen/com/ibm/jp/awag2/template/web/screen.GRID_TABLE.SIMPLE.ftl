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
  <div class="">
    <div class="">
      <h2 class="">${screen.screenDisplayName}</h2>
    </div>

    <div class="">
      <!-- errors-->
      <div class="error" ng-show="vm.error.details">
        <div ng-repeat="detail in vm.error.details">
            {{detail.message}} <span ng-show="detail.code.length>0">[</span>{{detail.code}}<span ng-show="detail.code.length>0">]</span> <br/>
        </div>
      </div>
      <div class="x-table">
          <table class="">
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
                <th class="<#if !isNumeric></#if>">${field.displayName}</th>
			</#if>
		</#list>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.model.${fieldGroup.listName}">
		<#if screen.screenType == "GRID_SELECT_TABLE">
                <td>
                  <label class="" for="selectedKey{{$index}}">
                    <input type="checkbox" class="" id="selectedKey{{$index}}" ng-model="item.selected" />
                  </label>
                </td>
		</#if>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "TEXT">
                <td class="<#if !isNumeric></#if>" ng-form="itemForm${field?counter}">
				<#if !field.readonly>
					<#assign inputtype="text">
					<#if field.options?? && field.options.input_type??>
						<#assign inputtype=field.options.input_type>
					</#if>
					<#if field.formatValidationRule??>
						<#if field.options?? && field.options.regexpValidation??>
                  <input class="" id="${field.fieldName}" type="${inputtype}" pattern="${field.options.regexpValidation}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> />
						<#else>
                  <input class="" id="${field.fieldName}" type="${inputtype}" pattern="${field.formatValidationRule.regex}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> />
						</#if>
						<#if field.options?? && field.options.validationMessage??>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">${field.options.validationMessage}</span>
						<#else>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">${field.formatValidationRule.message}</span>
						</#if>
					<#else>
                  <input class="mdl-textfield__input" id="${field.fieldName}" type="${inputtype}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> />
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
					</#if>
				<#else>
                  {{item.${field.fieldName}}}
				</#if>
                </td>
			<#elseif field.htmlType == "TEXTAREA">
                <td class="" ng-form="itemForm${field?counter}">
				<#if !field.readonly>
                  <textarea class="" id="${field.fieldName}" rows="3" type="text" ng-model="item.${field.fieldName}" <#if field.required>required</#if>></textarea>
				<#else>
                  <pre>{{item.${field.fieldName}}}</pre>
				</#if>
                </td>
			<#elseif field.htmlType == "SELECT">
                <td class="" ng-form="itemForm${field?counter}">
                  <select class="" id="${field.fieldName}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>>
                    <option ng-repeat="opt in vm.${field.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
                  </select>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "RADIO">
                <td class="" ng-form="itemForm${field?counter}">
                  <label class="" for="${field.fieldName}-{{opt.value}}-{{$parent.$index}}" ng-repeat="opt in vm.${field.codeListId}">
                    <input class="" id="${field.fieldName}-{{opt.value}}-{{$parent.$index}}" name="${field.fieldName}-{{$parent.$index}}" type="radio" value="{{opt.value}}" ng-model="item.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
                    <span class="">{{opt.name}}&nbsp;&nbsp;</span>
                  </label>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "CHECKBOX">
                <td class="" ng-form="itemForm${field?counter}">
                  <label class="" for="${field.fieldName}-{{vm.${field.codeListId}[1].value}}-{{$index}}">
                    <input type="checkbox" id="${field.fieldName}-{{vm.${field.codeListId}[1].value}}-{{$index}}" class="mdl-checkbox__input" ng-model="item.${field.fieldName}" ng-true-value="'{{vm.${field.codeListId}[1].value}}'" ng-false-value="'{{vm.${field.codeListId}[0].value}}'" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
                    <span class="">{{vm.${field.codeListId}[1].name}}</span>
                  </label>
                  <span class="awag-textfield__error" ng-show="!itemForm${field?counter}.$valid">必須です</span>
                </td>
			<#elseif field.htmlType == "OUTPUT">
                <td class="" ng-form="itemForm${field?counter}">
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

    <div class="">
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
