<div  class="entity-form-container togglable">

	<div class="heading1-bar">
	  <g:locales/>
		<h1>
  		<g:if test="${locationLevel.id != null}">
				<g:message code="default.edit.label" args="[message(code:'location.level.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'location.level.label')]" />
			</g:else>
		</h1>
	</div>

	<div class="main">
  	<g:form url="[controller:'locationLevel', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
  		<g:i18nTextarea name="names" bean="${locationLevel}" label="${message(code:'entity.name.label')}" field="names" height="150" width="300" maxHeight="150" />

  		<g:input name="code" label="${message(code:'entity.code.label')}" bean="${locationLevel}" field="code"/>
		
  		<g:if test="${locationLevel.id != null}">
  			<input type="hidden" name="id" value="${locationLevel.id}"></input>
  		</g:if>
  		<br/>
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
