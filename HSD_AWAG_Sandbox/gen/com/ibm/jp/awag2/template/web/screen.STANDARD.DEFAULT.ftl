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
          <label class="mdl-textfield__label" for="${field.fieldName}">${field.displayName} <#if field.required>*<#else>...</#if></label>
					<#if field.formatValidationRule??>
						<#assign inputtype="text">
						<#if field.options?? && field.options.input_type??>
							<#assign inputtype=field.options.input_type>
						</#if>
						<#if field.options?? && field.options.regexpValidation??>
		  <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.options.regexpValidation}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
						<#else>
		  <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" pattern="${field.formatValidationRule.regex}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
						</#if>
					<#else>
          <input class="mdl-textfield__input" id="${field.fieldName}" name="${field.fieldName}" type="${inputtype}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> />
					</#if>
					<#if field.formatValidationRule??>
						<#if field.options?? && field.options.validationMessage??>
          <span class="mdl-textfield__error" ng-show="form.${field.fieldName}.$error.pattern">${field.options.validationMessage}</span>
						<#else>
          <span class="mdl-textfield__error" ng-show="form.${field.fieldName}.$error.pattern">${field.formatValidationRule.message}</span>
						</#if>
					</#if>
				<#else>
          <span class="input-floating-label">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <span id="${field.fieldName}" class="mdl-textfield__output">{{vm.model.${field.fieldName}}}</span>
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
          <label class="mdl-textfield__label" for="${field.fieldName}">${field.displayName} <#if field.required>*<#else>...</#if></label>
          <textarea class="mdl-textfield__input" id="${field.fieldName}" rows="3" type="text" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if>></textarea>
				<#else>
          <span class="input-floating-label">${field.displayName}...</span>
          <pre>{{vm.model.${field.fieldName}}}</pre>
				</#if>
        </div>
			</#if>
			<#if field.htmlType == "SELECT">
        <!-- ${field.displayName} Pulldown-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <select class="mdl-textfield__input height28" id="${field.fieldName}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>>
            <option ng-repeat="opt in vm.${field.codeListId}" value="{{opt.value}}">{{opt.name}}</option>
          </select>
        </div>
			</#if>
			<#if field.htmlType == "RADIO">
        <!-- ${field.displayName} Radio-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="${field.fieldName}-{{opt.value}}" ng-repeat="opt in vm.${field.codeListId}">
            <input class="mdl-radio__button" id="${field.fieldName}-{{opt.value}}" name="${field.fieldName}" type="radio" value="{{opt.value}}" ng-model="vm.model.${field.fieldName}" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
            <span class="mdl-radio__label">{{opt.name}}&nbsp;&nbsp;</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "CHECKBOX">
        <!-- ${field.displayName} Checkbox-->
        <div class="mdl-textfield mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone">
          <span class="input-floating-label">${field.displayName} <#if field.required>*<#else>...</#if></span>
          <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="${field.fieldName}-{{vm.${field.codeListId}[1].value}}"">
            <input type="checkbox" id="${field.fieldName}-{{vm.${field.codeListId}[1].value}}" class="mdl-checkbox__input" ng-model="vm.model.${field.fieldName}" ng-true-value="'{{vm.${field.codeListId}[1].value}}'" ng-false-value="'{{vm.${field.codeListId}[0].value}}'" <#if field.required>required</#if> <#if field.readonly>disabled</#if>/>
            <span class="mdl-checkbox__label">{{vm.${field.codeListId}[1].name}}</span>
          </label>
        </div>
			</#if>
			<#if field.htmlType == "OUTPUT">
        <!-- ${field.displayName} output -->
        <div class="mdl-textfield mdl-textfield--floating-label mdl-cell mdl-cell--top mdl-cell--${screen.getPCColWidth()}-col mdl-cell--${screen.getTabletColWidth()}-col-tablet mdl-cell--${screen.getPhoneColWidth()}-col-phone is-upgraded" data-upgraded=",MaterialTextfield">
          <span class="input-floating-label">${field.displayName}...</span>
          <span id="${field.fieldName}" class="mdl-textfield__output">{{vm.model.${field.fieldName}}}</span>
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
			<#if event.eventName!="awagInit">
      <!-- Raised button with ripple -->
				<#if event.eventFireType?? && event.eventFireType=="ANYTIME">
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()">
				<#else>
      <button type="button" id="${event.eventName}" class="white-space mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" ng-click="vm.${event.eventName}()" ng-disabled="form.$invalid">
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
