<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'indicator.category.code.label')}" params="[q:q]" />
			<g:sortableColumn property="names"  title="${message(code: 'indicator.category.names.label')}" params="[q:q]" />
			<g:sortableColumn property="minYellowValue"  title="${message(code: 'indicator.category.min.yellow.value.label')}" params="[q:q]" />
			<g:sortableColumn property="maxYellowValue"  title="${message(code: 'indicator.category.min.yellow.value.label')}" params="[q:q]" />
			
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="indicatorCategory">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="indicatorCategory:edit">
								<a href="${createLinkWithTargetURI(controller:'indicatorCategory', action:'edit', params:[id: indicatorCategory.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="indicatorCategory:delete">
								<a href="${createLinkWithTargetURI(controller:'indicatorCategory', action:'delete', params:[id: indicatorCategory.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				<td>${indicatorCategory.code}</td>
				<td>${indicatorCategory.names}</td>
				<td>${indicatorCategory.minYellowValue}</td>
				<td>${indicatorCategory.maxYellowValue}</td>
				
			</tr>
		</g:each>
</tbody>
</table>
<g:render template="/templates/pagination" />
