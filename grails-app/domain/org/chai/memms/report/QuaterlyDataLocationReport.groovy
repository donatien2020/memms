package org.chai.memms.report

class QuaterlyDataLocationReport {
	
	String code
	DataLocationReport dataLocationReport
	Integer generationYear
	Integer generationMonth
	
	Date startDate
	Date endDate
	static mapping ={
		table "memms_report_quatery_data_location"
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