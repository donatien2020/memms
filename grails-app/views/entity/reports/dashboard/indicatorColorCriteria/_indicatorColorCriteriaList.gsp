<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'color.eval.code.label')}" params="[q:q]" />
			<g:sortableColumn property="minYellow"  title="${message(code: 'color.eval.min.yellow')}" params="[q:q]" />
			<g:sortableColumn property="maxYellow"  title="${message(code: 'color.eval.max.yellow')}" params="[q:q]" />
			<g:sortableColumn property="isIncreasing"  title="${message(code: 'color.eval.is.increasing')}" params="[q:q]" />
			<th><g:message code="data.location.type.label" /></th>
		    <th>  <g:message code="indicator.label" /></th>
		
		
		</tr>
	</thead>
	
	<tbody>
	<g:each in="${entities}" status="i" var="indicatorColorCriteriaPerDataLocationType">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="indicatorColorCriteriaPerDataLocationType:edit">
								<a href="${createLinkWithTargetURI(controller:'indicatorColorCriteriaPerDataLocationType', action:'edit', params:[id: indicatorColorCriteriaPerDataLocationType.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="indicatorColorCriteriaPerDataLocationType:delete">
								<a href="${createLinkWithTargetURI(controller:'indicatorColorCriteriaPerDataLocationType', action:'delete', params:[id: indicatorColorCriteriaPerDataLocationType.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				<td>${indicatorColorCriteriaPerDataLocationType.code}</td>
				<td>${indicatorColorCriteriaPerDataLocationType.minYellow}</td>
				<td>${indicatorColorCriteriaPerDataLocationType.maxYellow}</td>
				<td>${indicatorColorCriteriaPerDataLocationType.isIncreasing}</td>
				<td>${indicatorColorCriteriaPerDataLocationType.dataLocationType?.name}</td>
				<td>${indicatorColorCriteriaPerDataLocationType.indicator?.names}</td>
			</tr>
		</g:each>
</tbody>
	
	
</table>
<g:render template="/templates/pagination" />
