<span class="right">
	<shiro:hasPermission permission="indicatorCategory:create">
		<a href="${createLinkWithTargetURI(controller:'indicatorCategory', action:'create',params:params)}" class="next medium left push-r"> 
			<g:message code="default.new.label" args="[entityName]" />
		</a>
	</shiro:hasPermission>
	
	<g:searchBox action="search"  controller="indicatorCategory"/>
</span>


