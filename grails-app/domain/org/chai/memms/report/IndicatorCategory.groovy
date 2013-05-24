package org.chai.memms.report
import i18nfields.I18nFields
import groovy.transform.EqualsAndHashCode;
@i18nfields.I18nFields
@EqualsAndHashCode(includes="code")
class IndicatorCategory {
	String code
	String names
	//color not yet decided
	Double minYellowValue
	Double maxYellowValue

	//*indicatorTypes
	static i18nFields = ["names"]
	static hasMany = [indicators:Indicator]
	static mapping ={
		table "memms_report_indicator_category"
		version false
		content type:"text"

	}

	static constraints = {
		code (blank:false, nullable:false)
		names (blank:true, nullable:true,size:3..100)


	}
	@Override
	public String toString() {
	return "IndicatorCategory[id="+id+"code=" + code + "]";
		//return names;
	}
}
