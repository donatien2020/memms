
<div  class="entity-form-container togglable">
	<div class="heading1-bar">
		<g:locales/>
		<h1>
		  <g:if test="${queryParserHelper.id != null}">
				<g:message code="default.edit.label" args="[message(code:'queryParserHelper.label')]" />
			</g:if>
			<g:else>
				<g:message code="default.new.label" args="[message(code:'queryParserHelper.label')]" />
			</g:else>
		</h1>
		
	</div>
	
	<div class="main">
  	<g:form url="[controller:'queryParserHelper', action:'save', params:[targetURI: targetURI]]" useToken="true" class="simple-list">
	    <g:input name="classDomaine" label="${message(code:'entity.classDomaine.label')}" bean="${queryParserHelper}" field="classDomaine"/>
  		<g:textarea name="formula" bean="${queryParserHelper}" label="${message(code:'entity.formula.label')}" field="formula" height="150" width="300" maxHeight="150" />
  		<g:textarea name="executableScript" bean="${queryParserHelper}" label="${message(code:'entity.executableScript.label')}" field="executableScript" height="150" width="300" maxHeight="150" />
  		<g:input name="followOperand" label="${message(code:'entity.queryParserHelper.followOperand.label')}" bean="${queryParserHelper}" field="followOperand"/>
  		<g:checkBox name="isDenominator"  bean="${queryParserHelper}" field="isDenominator"/>
  		<g:checkBox name="useCountFunction"  bean="${queryParserHelper}" field="useCountFunction"/>
  		<g:checkBox name="isIntermidiateVariable" bean="${queryParserHelper}" field="isIntermidiateVariable"/>
  		<g:checkBox name="isCriteria"  bean="${queryParserHelper}" field="isCriteria"/>
  		<g:checkBox name="isDynamicFinder"  bean="${queryParserHelper}" field="isDynamicFinder"/>
  		<g:input name="type" label="${message(code:'entity.queryParserHelper.name.label')}" bean="${queryParserHelper}" field="type"/>
  		
  		<g:if test="${queryParserHelper.id != null}">
  			<input type="hidden" name="id" value="${queryParserHelper.id}"></input>
  		</g:if>
  		<br />
  		<div class="buttons">
  			<button type="submit"><g:message code="default.button.save.label"/></button>
  			<a href="${createLink(uri: targetURI)}"><g:message code="default.link.cancel.label"/></a>
  		</div>
  	</g:form>
  </div>
</div>
