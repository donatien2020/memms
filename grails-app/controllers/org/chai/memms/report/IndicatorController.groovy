package org.chai.memms.report

import java.util.List;
import java.util.Set
import java.util.Map

import org.apache.commons.lang.math.NumberUtils

import org.chai.location.DataLocation
import org.chai.location.DataLocationType
import org.chai.memms.AbstractEntityController;
import org.chai.memms.report.utils.ExecutorProvider;


class IndicatorController  extends AbstractEntityController{
	def indicatorService
	def intermediateVariableService
	def indicatorValueService
	def getLabel() {
		return "indicator.label"
	}

	def getEntityClass() {
		return Indicator.class
	}


	def getEntity(def id) {
		return Indicator.get(id)
	}

	def createEntity() {
		return new Indicator()
	}

	def getTemplate() {
		return "/entity/reports/dashboard/indicator/createIndicator"
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	def getModel(def entity) {
		[
			indicator: entity
		]
	}

	def model(def entities) {
		return [
			entities: entities,
			entityCount: entities.totalCount,
			entityClass:getEntityClass(),
			code: getLabel()
		]
	}
	def ajaxModel(def entities,def searchTerm) {
		def model = model(entities) << [q:searchTerm]
		def listHtml = g.render(template:"/entity/reports/dashboard/indicator/indicatorList",model:model)
		render(contentType:"text/json") { results = [listHtml]}
	}
	def search = {
		adaptParamsForList()
		def indicators  = indicatorService.searchIndicator(params['q'],null,params)

		if(request.xhr)
			this.ajaxModel(indicators,params['q'])
		else {
			render(view:"/entity/list",model:model(indicators) << [
				template:"/reports/dashboard/indicator/indicatorList",
				listTop:"/reports/dashboard/indicator/listTop",

			])
		}
	}

	def list = {
		// <g:set var="total" value="${total+(m.q*m.sts.uf)}"/>
		//http://grails.org/doc/2.2.0/ref/Tags/set.html

		//indicatorService.testQuery()
		//		Date today=new Date()
		//		List<IntermediateVariable> intermidiateValiables
		//for(DataLocation location:indicatorService.getDataLications()){
		//			println"location "+location
		//			String reportCod=ExecutorProvider.idGenerator(""+today.toString())
		//
		//			DataLocationType type=location.type
		//			DataLocationReport facilityReport=new DataLocationReport(code:reportCod,dataLocation:location,dataLocationType:type,generatedAt:today)
		//			facilityReport.save(failOnError: true)
		//
		//			DataLocationReport savedFacilityReport=DataLocationReport.findByCode(reportCod)
		//			Indicator indicator=Indicator.findById("7")
		//			//Indicator indicator=Indicator.findByCode("MANA_EQUIPMENT_SHARE_OF_EQUIPMENT_UNDER_ACTIVE_WARRANTY_OR_UNDER_ACTIVE_SERVICE_PROVIDER_CONTRACT")
		//			indicatorService.indicatorValueCalculatorFactory(indicator.code,intermidiateValiables,location,facilityReport)
		//indicatorService.getIndicatorValueForActiveWarantOrServiceProviderContarct(location)
		//}


 

		//indicatorService.testQuery()
     intermediateVariableService.intermediateVariableWriter()
		//indicatorValueService.getIndicatorValueByIndIcatorCategory("CORRECTIVE_MAINTENANCE")
		indicatorService.indicatorWriterFromXml()
		//		DataLocation dataLocation=DataLocation.findById("16")
		//		int valuee=indicatorService.getIndicatorValueForActiveWarantOrServiceProviderContarct(dataLocation,)
		//
		//		println" the valuuuuuuuuuuuuuuuuuuuuuuuuu :"+valuee
	indicatorService.reportingEngine()
		adaptParamsForList()

		def indicators = Indicator.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc")
		if(request.xhr){
			this.ajaxModel(indicators,"")
		}
		else{
			render(view:"/entity/list",model:model(indicators) << [
				template:"/reports/dashboard/indicator/indicatorList",
				listTop:"/reports/dashboard/indicator/listTop"
			])
		}
	}
}
