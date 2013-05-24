package org.chai.memms.report;

import java.util.Map;
import org.chai.memms.report.IndicatorColorCriteriaPerDataLocationType
public class IndicatorColorCriteriaPerDataLocationTypeService {
	public def getIndicatorColorCriteriaPerDataLocationTypes(){
		return IndicatorColorCriteriaPerDataLocationType.findAll()
	}

	public def searchIndicatorColorCriteriaPerDataLocationType(String text,Map<String, String> params) {
		text = text.trim()
		def criteria = IndicatorColorCriteriaPerDataLocationType.createCriteria()
		return criteria.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc"){
			or{
				ilike("code","%"+text+"%")
				ilike("names","%"+text+"%")
				ilike("type","%"+text+"%")
			}
		}
	}

}
