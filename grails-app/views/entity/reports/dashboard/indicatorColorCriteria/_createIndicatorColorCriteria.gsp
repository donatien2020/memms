<%@ page import="org.chai.memms.report.IndicatorColorCriteriaPerDataLocationType" %>
<%@ page import="org.chai.memms.report.Indicator" %>
<%@ page import="org.chai.location.DataLocationType" %>
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${indicatorColorCriteriaPerDataLocationType.id != null}">
				<g:message code="default.edit.label" args="[message(code:'indicator.color.criteria.per.data.location.type.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'indicator.color.criteria.per.data.location.type.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'indicatorColorCriteriaPerDataLocationType', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	
	    <g:input name="code" label="${message(code:'entity.code.label')}" bean="${indicatorColorCriteriaPerDataLocationType}" field="code"/>
  		<g:input name="minYellow" label="${message(code:'entity.minYellow.label')}" bean="${indicatorColorCriteriaPerDataLocationType}" field="minYellow"/>
  		<g:input name="maxYellow" label="${message(code:'entity.maxYellow.label')}" bean="${indicatorColorCriteriaPerDataLocationType}" field="maxYellow"/>
  		<g:input name="isIncreasing" label="${message(code:'entity.isIncreasing.label')}" bean="${indicatorColorCriteriaPerDataLocationType}" field="isIncreasing"/>
  		<g:selectFromList id="dataLocationType" label="${message(code:'dataLocationType.label')}" name="dataLocationType.id"  bean="${dataLocationType}"
  			from="${org.chai.location.DataLocationType.list()}" value="${dataLocationType?.id}" values="${org.chai.location.DataLocationType.list().collect{it.names}}" optionKey="id"/>
  			
  			<g:selectFromList id="indicator" label="${message(code:'indicator.label')}" name="indicator.id"  bean="${indicator}"
  			from="${org.chai.memms.report.Indicator.list()}" value="${indicator?.id}" values="${org.chai.memms.report.Indicator.list().collect{it.names}}" optionKey="id"/>
  		
  		
  		<g:if test="${indicatorColorCriteriaPerDataLocationType.id != null}">
  			<input type="hidden" name="id" value="${indicatorColorCriteriaPerDataLocationType.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
