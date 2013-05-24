package org.chai.memms.report
import org.chai.location.DataLocationType;
class IndicatorColorCriteriaPerDataLocationType {
	String code
	String minYellow
	String maxYellow
	Boolean isIncreasing
	DataLocationType dataLocationType
	
	static belongsTo = [indicator:Indicator]
	static mapping ={
		table "memms_report_indicator_color_criteria"
		version false
		content type:"text"
		
	}
	static constraints = {
		code (blank:false, nullable:false)
	}
	@Override
	public String toString() {
		return "IndicatorColorCriteriaPerDataLocationTypecode[id="+id+"code=" + code + "]";
	}
}
