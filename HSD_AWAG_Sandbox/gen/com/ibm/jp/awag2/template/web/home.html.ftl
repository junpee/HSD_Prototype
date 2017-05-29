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
<div class="mdl-card  mdl-shadow--2dp myfitcard">
  <div class="mdl-card__title mdl-card--border card-title">
    <h2 class="mdl-card__title-text card-title-text"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">home</i>Home</h2>
  </div>
  <div class="mdl-card__supporting-text mdl-card--border">
    <div class="" ng-controller="HomeCtrl as home">
      {{home.message}}
      <br /><br />
<#list project.usecases as usecase>
      <a href="#!/${usecase.usecaseName?lower_case}" ng-click="home.start()">
        <button id="btn_${usecase.usecaseName?lower_case}" class="mdl-button mdl-js-button mdl-button--primary" formaction="#!/${usecase.usecaseName?lower_case}">
          ${usecase.usecaseDisplayName}
        </button></a>
      <br />
</#list>
    </div>
  </div>
</div>
