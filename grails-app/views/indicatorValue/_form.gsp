<%@ page import="org.chai.memms.report.IndicatorValue" %>



<div class="fieldcontain ${hasErrors(bean: indicatorValueInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="indicatorValue.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="code" required="" value="${indicatorValueInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: indicatorValueInstance, field: 'value', 'error')} required">
	<label for="value">
		<g:message code="indicatorValue.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="value" value="${fieldValue(bean: indicatorValueInstance, field: 'value')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: indicatorValueInstance, field: 'dataLocationReport', 'error')} required">
	<label for="dataLocationReport">
		<g:message code="indicatorValue.dataLocationReport.label" default="Data Location Report" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="dataLocationReport" name="dataLocationReport.id" from="${org.chai.memms.report.DataLocationReport.list()}" optionKey="id" required="" value="${indicatorValueInstance?.dataLocationReport?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: indicatorValueInstance, field: 'generatedAt', 'error')} required">
	<label for="generatedAt">
		<g:message code="indicatorValue.generatedAt.label" default="Generated At" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="generatedAt" precision="day"  value="${indicatorValueInstance?.generatedAt}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: indicatorValueInstance, field: 'indicator', 'error')} required">
	<label for="indicator">
		<g:message code="indicatorValue.indicator.label" default="Indicator" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="indicator" name="indicator.id" from="${org.chai.memms.report.Indicator.list()}" optionKey="id" required="" value="${indicatorValueInstance?.indicator?.id}" class="many-to-one"/>
</div>

