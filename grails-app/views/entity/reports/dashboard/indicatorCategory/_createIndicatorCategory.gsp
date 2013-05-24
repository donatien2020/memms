<%@ page import="org.chai.memms.inventory.EquipmentType.Observation" %>
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${indicatorCategory.id != null}">
				<g:message code="default.edit.label" args="[message(code:'indicator.category.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'indicator.category.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'indicatorCategory', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	
  		<g:input name="code" label="${message(code:'entity.code.label')}" bean="${indicatorCategory}" field="code"/>
  		<g:i18nInput name="names" label="${message(code:'entity.names.label')}" bean="${indicatorCategory}" field="names"/>
  		<g:input name="minYellowValue" label="${message(code:'entity.minYellowValue.label')}" bean="${indicatorCategory}" field="minYellowValue"/>
  		<g:input name="maxYellowValue" label="${message(code:'entity.maxYellowValue.label')}" bean="${indicatorCategory}" field="maxYellowValue"/>
  		
  		<g:if test="${indicatorCategory.id != null}">
  			<input type="hidden" name="id" value="${indicatorCategory.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
