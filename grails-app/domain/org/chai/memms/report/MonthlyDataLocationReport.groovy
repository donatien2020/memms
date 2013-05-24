package org.chai.memms.report

import java.util.Date;

class MonthlyDataLocationReport {
	String code
	Date generatedAt
	DataLocationReport dataLocationReport
	Integer generationYear
	Integer generationMonth
	Date startDate
	Date endDate
	static mapping ={
		table "memms_report_monthly_data_location"
		version false
		content type:"text"
		
	}
	static constraints = {
		code (blank:false, nullable:false)
	}
	@Override
	public String toString() {
		return code;
	}
}
