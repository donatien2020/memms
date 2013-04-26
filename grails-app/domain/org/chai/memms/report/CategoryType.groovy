package org.chai.memms.report

class CategoryType {
	String code
	String name
	//color not yet decided
	Double minYellowValue
	Double maxYellowValue 
	//*indicatorTypes
	
	static hasMany = [indicatorTypes:IndicatorType]
	static mapping ={
		table "memms_category_type"
		version false
		content type:"text"
		
	}
	
	static constraints = {
		code (blank:false, nullable:false)
		name (blank:false, nullable:false,unique: true, size:3..10, matches:"[a-zA-Z1-9_]+")
		
		
	}
}
