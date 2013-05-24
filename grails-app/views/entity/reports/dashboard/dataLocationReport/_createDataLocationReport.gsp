<%@ page import="org.chai.memms.inventory.EquipmentType.Observation" %>
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${dataLocationReport.id != null}">
				<g:message code="default.edit.label" args="[message(code:'equipment.dataLocationReport.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'equipment.dataLocationReport.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'dataLocationReport', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	
  		<g:input name="code" label="${message(code:'entity.code.label')}" bean="${dataLocationReport}" field="code"/>
  		<g:selectFromList id="dataLocation" label="${message(code:'dataLocation.label')}" name="dataLocation.id"  bean="${dataLocation}"
  			from="${org.chai.location.DataLocation.list()}" value="${dataLocationReportInstance?.dataLocation?.id}" values="${org.chai.location.DataLocation.list().collect{it.names}}" optionKey="id"/>
  		<g:if test="${dataLocationReport.id != null}">
  			<input dataLocationReport="hidden" name="id" value="${dataLocationReport.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
