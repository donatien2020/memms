package org.chai.memms.report

import org.chai.location.DataLocation;

class IndicatorValue {
	
	
	public IndicatorValue(){}
	
	String code 
	Date generatedAt
	Double computedValue
	
	static belongsTo = [indicator:Indicator,dataLocationReport:DataLocationReport]
	static mapping ={
		table "memms_report_indicator_value"
		version false
		content type:"text"
		
	}
	static constraints = {
		code (blank:false, nullable:false)
		computedValue (blank:false, nullable:false)
		
		
	}
	@Override
	public String toString() {
		return  "IndicatorValue[id="+id+"code=" + code + "]";
	}
}
