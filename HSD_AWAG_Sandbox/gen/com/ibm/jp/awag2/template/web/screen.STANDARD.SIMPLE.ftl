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
  <div class="">
    <div class="">
      <h2 class=""> ${screen.screenDisplayName}</h2>
    </div>
    <div class="">
      <div class="">
        <!-- errors-->
        <div class="error" ng-show="vm.error.details">
          <div ng-repeat="detail in vm.error.details">
            {{detail.message}} <span ng-show="detail.code.length>0">[</span>{{detail.code}}<span ng-show="detail.code.length>0">]</span> <br/>
          </div>
        </div>
		<#list fieldGroup.fieldList as field>
			<#if field.htmlType == "TEXT">
        <!-- ${field.displayName}  -->
        <div class="">
				<#if !field.readonly>
          <label class="" for="${field.fieldName}">${field.displayName} <#if field.required>*<#else>...</#if></label>
					<#if field.formatValidationRule??>
						<#assign inputtype="text">
						<#if field.options?? && field.options.input_type??>
							<#assign inputtype=field.options.input_type>
						</#if>
						<#if field.options?? && field.options.regexpValidation??>
		  <input class="" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.options.regexpValidation}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
						<#else>
		  <input class="" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.formatValidationRule.regex}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
						</#if>
					<#else>
          <input class="" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
					</#if>
					<#if field.formatValidationRule??>
						<#if field.options?? && field.options.validationMessage??>
          <span class="" ng-show="form.${field.fieldName}.$error.pattern">${field.options.validationMessage}</span>
						<#else>
          <span class="" ng-show="form.${field.fieldName}.$error.pattern">${field.formatValidationRule.message}</span>
						</#if>
					</#if>
				<#else>
          <span class="">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <span id="${field.fieldName}" class="">{{vm.model.${field.fieldName}}}</span>
				</#if>
        </div>
			</#if>
			<#if field.htmlType == "SPACER">
        <!-- space-->
        <div class=""></div>
			</#if>
			<#if field.htmlType == "TEXTAREA">
        <!-- ${field.displayName} Textarea -->
        <div class="">
				<#if !field.readonly>
          <label class="" for="${field.fieldName}">${field.displayName} <#if field.required>*<#else>...</#if></label>
          <textarea class="" id="${field.fieldName}" rows="3" type="text" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if>></textarea>
				<#else>
          <span class="">${field.displayName}...</span>
          <pre>{{vm.model.${field.fieldName}}}</pre>
				</#if>
        </div>
			</#if>
			<#if field.htmlType == "SELECT">
        <!-- ${field.displayName} Pulldown-->
        <div class="">
          <span class="">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <select class="" id="${field.fieldName}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>>
            <option ng-repeat="opt in vm.${field.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
          </select>
        </div>
			</#if>
			<#if field.htmlType == "RADIO">
        <!-- ${field.displayName} Radio-->
        <div class="">
          <span class="">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <label class="" for="${field.fieldName}-{{opt.value}}" ng-repeat="opt in vm.${field.codeListId}">
            <input class="" id="${field.fieldName}-{{opt.value}}" name="${field.fieldName}" type="radio" value="{{opt.value}}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
            <span class="">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "CHECKBOX">
        <!-- ${field.displayName} Checkbox-->
        <div class="">
          <span class="">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <label class="" for="${field.fieldName}-{{vm.${field.codeListId}[1].value}}"">
            <input type="checkbox" id="${field.fieldName}-{{vm.${field.codeListId}[1].value}}" class="" ng-model="vm.model.${field.fieldName}" ng-true-value="'{{vm.${field.codeListId}[1].value}}'" ng-false-value="'{{vm.${field.codeListId}[0].value}}'" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
            <span class="">{{vm.${field.codeListId}[1].name}}</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "OUTPUT">
        <!-- ${field.displayName} output -->
        <div class="" data-upgraded=",MaterialTextfield">
          <span class="">${field.displayName}...</span>
          <span id="${field.fieldName}" class="">{{vm.model.${field.fieldName}}}</span>
        </div>
			</#if>
			<#if field.htmlType == "STATIC_TEXT">
        <!-- ${field.displayName} static text -->
        <div class="">
          ${field.value}
        </div>
			</#if>
		</#list>
      </div>
    </div>
    <div class="">
		<#list fieldGroup.eventList as event>
			<#if event.eventName!="awagInit">
      <!-- button -->
				<#if event.eventFireType?? && event.eventFireType=="ANYTIME">
      <button type="button" id="${event.eventName}" class="white-space" ng-click="vm.${event.eventName}()">
				<#else>
      <button type="button" id="${event.eventName}" class="white-space" ng-click="vm.${event.eventName}()" ng-disabled="form.$invalid">
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
