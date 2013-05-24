package org.chai.memms.report

import java.util.Map;
import org.chai.location.DataLocationType
import org.chai.location.DataLocation
import org.grails.datastore.mapping.query.Query
class IndicatorValueService {
	static transactional = true

	def sessionFactory


	def dataSource  //Aut
	def dataLocationReportService

	public def getIndicatorValuesByDataLocation(DataLocation dataLocation){
		List<IndicatorValue> indicatorValues=new ArrayList<IndicatorValue>()
		def reports=dataLocationReportService.getDataLocationReportsByDataLocation(dataLocation)
		for(DataLocationReport dataLocationreport:reports){
			if(dataLocationreport.indicatorValues!=null){
				indicatorValues.addAll(dataLocationreport.indicatorValues)
			}
		}
		return indicatorValues
	}
	public def getIndicatorValuesByDataLocationType(DataLocationType dataLocationType){
		List<IndicatorValue> indicatorValues=new ArrayList<IndicatorValue>()
		def reports=dataLocationReportService.getDataLocationReportsByDataLocationType(dataLocationType)
		for(DataLocationReport dataLocationreport:reports){
			if(dataLocationreport.indicatorValues!=null){
				indicatorValues.addAll(dataLocationreport.indicatorValues)
			}
		}
		return indicatorValues
	}
	public def searchIndicatorValue(String text,Map<String, String> params) {
		text = text.trim()

		def criteria = IndicatorValue.createCriteria()
		return criteria.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc"){

			ilike("code","%"+text+"%")
			ilike("indicatorValue","%"+text+"%")
		}
	}

	public def getIndicatorValueByIndIcatorCategory(String indicatorCategoryCode) {
		//println" retrive indicators ok---------------------------by category--"+indicatorCategoryCode
		List<IndicatorValue> indicatorValues=new ArrayList<IndicatorValue>()

		IndicatorCategory indicatorCategory=IndicatorCategory.findByCode(indicatorCategoryCode)
		if(indicatorCategory!=null){
			def indicators =Indicator.findAllByIndicatorCategory(indicatorCategory)

			//println" all total indicators in a category========================="+indicators.size()

			for(Indicator indicator:indicators){
				def indicatorVls=IndicatorValue.findAllByIndicator(indicator)
				if(indicatorVls!=null){
					indicatorValues.addAll(indicatorVls)
				}
			}
		}
		println"Total number of indicator values :"+indicatorValues.size()+" for category :"+indicatorCategory
		return indicatorValues
	}

	def getIndicatorValues(){
		return IndicatorValue.findAll()
	}
}
