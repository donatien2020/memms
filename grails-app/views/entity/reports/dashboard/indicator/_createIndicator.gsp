<%@ page import="org.chai.memms.report.Indicator" %>
<%@ page import="org.chai.memms.report.IndicatorCategory" %>
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${indicator.id != null}">
				<g:message code="default.edit.label" args="[message(code:'indicator.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'indicator.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'indicator', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	
  		<g:input name="code" label="${message(code:'entity.code.label')}" bean="${indicator}" field="code"/>
  		<g:i18nInput name="names" label="${message(code:'entity.indicator.name.label')}" bean="${indicator}" field="names"/>
  		<g:textarea name="formula" bean="${indicator}" label="${message(code:'entity.formula.label')}" field="formula" height="150" width="300" maxHeight="150" />
  		<g:input name="type" label="${message(code:'entity.type.label')}" bean="${indicator}" field="type"/>
  		<g:selectFromList id="indicatorCategory" label="${message(code:'indicatorCategory.label')}" name="indicatorCategory.id"  bean="${indicatorCategory}"
  			from="${org.chai.memms.report.IndicatorCategory.list()}" value="${indicatorCategory?.id}" values="${org.chai.memms.report.IndicatorCategory.list().collect{it.names}}" optionKey="id"/>
  		
  		<g:if test="${indicator.id != null}">
  			<input type="hidden" name="id" value="${indicator.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
