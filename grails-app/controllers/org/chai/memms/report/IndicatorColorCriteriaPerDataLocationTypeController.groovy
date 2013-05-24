package org.chai.memms.report
import java.util.Set
import java.util.Map

import org.apache.commons.lang.math.NumberUtils
import org.chai.memms.AbstractEntityController;
import org.springframework.dao.DataIntegrityViolationException

class IndicatorColorCriteriaPerDataLocationTypeController extends AbstractEntityController{
	def indicatorColorCriteriaPerDataLocationTypeService

	def getLabel() {
		return "inndicator.color.label"
	}

	def getEntityClass() {
		return IndicatorColorCriteriaPerDataLocationType.class
	}


	def getEntity(def id) {
		return IndicatorColorCriteriaPerDataLocationType.get(id)
	}

	def createEntity() {
		return new IndicatorColorCriteriaPerDataLocationType()
	}

	def getTemplate() {
		return "/entity/reports/dashboard/indicatorColorCriteria/createIndicatorColorCriteria"
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	def getModel(def entity) {
		[
			indicatorColorCriteriaPerDataLocationType: entity
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
		def listHtml = g.render(template:"/entity/reports/dashboard/indicatorColorCriteria/indicatorColorCriteriaList",model:model)
		render(contentType:"text/json") { results = [listHtml]}
	}
	def search = {
		adaptParamsForList()
		def indicatorColorCriterias  = indicatorColorCriteriaPerDataLocationTypeService.searchIndicatorColorCriteriaPerDataLocationType(params['q'],null,params)

		if(request.xhr)
			this.ajaxModel(indicatorColorCriterias,params['q'])
		else {
			render(view:"/entity/list",model:model(indicatorColorCriterias) << [
				template:"/reports/dashboard/indicatorColorCriteria/indicatorColorCriteriaList",
				listTop:"/reports/dashboard/indicatorColorCriteria/listTop",

			])
		}
	}

	def list = {
		
		println"helooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"
		adaptParamsForList()
		
		def indicatorColorCriterias = IndicatorColorCriteriaPerDataLocationType.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc")
		if(request.xhr){
			this.ajaxModel(indicatorColorCriterias,"")
		}
		else{
			render(view:"/entity/list",model:model(indicatorColorCriterias) << [
				template:"/reports/dashboard/indicatorColorCriteria/indicatorColorCriteriaList",
				listTop:"/reports/dashboard/indicatorColorCriteria/listTop"
			])
		}
	}
}
