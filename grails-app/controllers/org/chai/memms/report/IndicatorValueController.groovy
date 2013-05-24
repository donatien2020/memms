package org.chai.memms.report
import java.util.Set
import java.util.Map

import org.apache.commons.lang.math.NumberUtils
import org.chai.memms.AbstractEntityController;


class IndicatorValueController extends AbstractEntityController {
	def indicatorValueService
	//def grailsApplication
	def getLabel() {
		return "indicatorValue.label"
	}

	def getEntityClass() {
		return IndicatorValue.class
	}


	def getEntity(def id) {
		return IndicatorValue.get(id)
	}

	def createEntity() {
		return new IndicatorValue()
	}

	def getTemplate() {
		return "/entity/reports/dashboard/indicatorValue/createIndicatorValue"
	}


	def deleteEntity(def entity) {

		super.deleteEntity(entity)
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	def getExportClass() {
		return "IndicatorValueExportTask"
	}

	def getModel(def entity) {
		[
			indicatorValue: entity
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
		def listHtml = g.render(template:"/entity/reports/dashboard/indicatorValue/indicatorValueList",model:model)
		render(contentType:"text/json") { results = [listHtml]}
	}
	def search = {
		adaptParamsForList()
		def indicatorValues  = indicatorValueService.searchIndicatorValue(params['q'],null,params)

		if(request.xhr)
			this.ajaxModel(indicatorValues,params['q'])
		else {
			render(view:"/entity/list/",model:model(indicatorValues) << [
				template:"/reports/dashboard/indicatorValue/indicatorValueList",
				listTop:"/reports/dashboard/indicatorValue/listTop",
			
			])
		}
	}
		
	
	def list = {
		println"i am there ok"
		adaptParamsForList()
		
		def indicatorValues = IndicatorValue.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc")
		if(request.xhr){
			this.ajaxModel(indicatorValues,"")
			println"i was just kiding okkkkkkkkkkkkkkkkkkkkkkk"
		}
		else{
			println"seriously doneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
			render(view:"/entity/list",model:model(indicatorValues) << [
				template:"/reports/dashboard/indicatorValue/indicatorValueList",
				listTop:"/reports/dashboard/indicatorValue/listTop"

			])
			println"hi how are you doing ok ??????????????????????????????????????????????????????????"
		}
	}
}
