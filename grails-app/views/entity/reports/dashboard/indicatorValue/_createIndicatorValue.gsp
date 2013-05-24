<%@ page import="org.chai.memms.report.IndicatorValue" %>
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${indicatorValue.id != null}">
				<g:message code="default.edit.label" args="[message(code:'indicator.value.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'indicator.value.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'indicatorValue', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	
  		<g:input name="code" label="${message(code:'entity.code.label')}" bean="${indicatorValue}" field="code"/>
  		<g:input name="computedValue" label="${message(code:'entity.computedValue.label')}" bean="${indicatorValue}" field="code"/>
  		<g:if test="${indicatorValue?.id != null}">
  		  <g:selectFromList name="department.id" label="${message(code:'department.label')}" bean="${indicatorValue}" field="department" optionKey="id" multiple="false"
    			ajaxLink="${createLink(controller:'indicator', action:'getAjaxData')}"
    			from="${indicators}" value="${indicatorValue?.indicator?.id}" values="${indicators.collect{it.names}}" />
    			</g:if>
    		<g:if test="${indicatorValue?.id != null}">	
    	 <g:selectFromList name="dataLocationReport" label="${message(code:'indicator.label')}" bean="${indicatorValue}" field="dataLocationReport" optionKey="id" multiple="false"
    			ajaxLink="${createLink(controller:'dataLocationReport', action:'getAjaxData')}"
    			from="${dataLocationReports}" value="${indicatorValue?.dataLocationReport?.id}" values="${dataLocationReports.collect{it.code}}" />
    			</g:if>
  		
  		<g:if test="${indicatorValue?.id != null}">
  			<input type="hidden" name="id" value="${indicatorValue.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
