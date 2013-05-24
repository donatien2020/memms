<table class="items">
	<thead>
		<tr>
	
			
			<th></th>
			<g:sortableColumn property="classDomaine"  title="${message(code: 'queryParserHelper.classDomaine.label')}" params="[q:q]" />
			<g:sortableColumn property="executableScript"  title="${message(code: 'queryParserHelper.executableScript.label')}" params="[q:q]" />
			<g:sortableColumn property="followOperand"  title="${message(code: 'indicato.followOperand.label')}" params="[q:q]" />
			<g:sortableColumn property="isDenominator"  title="${message(code: 'queryParserHelpert.isDenominator.label')}" params="[q:q]" />
			<g:sortableColumn property="useCountFunction"  title="${message(code: 'queryParserHelper.useCountFunction.label')}" params="[q:q]" />
			<g:sortableColumn property="isIntermidiateVariable"  title="${message(code: 'queryParserHelper.isIntermidiateVariable.label')}" params="[q:q]" />
			<g:sortableColumn property="isCriteria"  title="${message(code: 'indicato.isCriteria.label')}" params="[q:q]" />
			<g:sortableColumn property="isDynamicFinder"  title="${message(code: 'queryParserHelpert.isDynamicFinder.label')}" params="[q:q]" />
		<g:sortableColumn property="type"  title="${message(code: 'queryParserHelpert.type.label')}" params="[q:q]" />
		<th><g:message code="indicator.label" /></th>
		<th><g:message code="queryParserHelper.intermediate.variable.label" /></th>
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="queryParserHelper">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="queryParserHelper:edit">
								<a href="${createLinkWithTargetURI(controller:'queryParserHelper', action:'edit', params:[id: queryParserHelper.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="queryParserHelper:delete">
								<a href="${createLinkWithTargetURI(controller:'queryParserHelper', action:'delete', params:[id: queryParserHelper.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				
				<td>${queryParserHelper.classDomaine}</td>
				
				<td><g:stripHtml field="${queryParserHelper.executableScript}" chars="20"/></td>
				<td>${queryParserHelper.followOperand}</td>
				<td>${queryParserHelper.isDenominator}</td>
				<td>${queryParserHelper.useCountFunction}</td>
				<td>${queryParserHelper.isIntermidiateVariable}</td>
				<td>${queryParserHelper.isCriteria}</td>
				<td>${queryParserHelper.isDynamicFinder}</td>
				<td>${queryParserHelper.type}</td>
				<td><g:stripHtml field="${queryParserHelper.indicator?.names}" chars="10"/></td>
				<td><g:stripHtml field="${queryParserHelper.intermediateVariable?.names}" chars="7"/></td>
			</tr>
		</g:each>
</tbody>

</table>
<g:render template="/templates/pagination" />
