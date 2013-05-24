package org.chai.memms.report
import java.util.Set
import java.util.Map
import org.apache.commons.lang.math.NumberUtils
import org.chai.memms.AbstractEntityController;
import org.springframework.dao.DataIntegrityViolationException

class QueryParserHelperController extends AbstractEntityController{
	def queryParserHelperService
	//def grailsApplication
	def getLabel() {
		return "queryParserHelper.label"
	}

	def getEntityClass() {
		return QueryParserHelper.class
	}


	def getEntity(def id) {
		return QueryParserHelper.get(id)
	}

	def createEntity() {
		
		return new QueryParserHelper()
	}

	def getTemplate() {
		return "/entity/reports/dashboard/queryParserHelper/createQueryParserHelper"
	}


	def deleteEntity(def entity) {

		super.deleteEntity(entity)
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	

	def getModel(def entity) {
		[
			queryParserHelper: entity
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
		def listHtml = g.render(template:"/entity/reports/dashboard/queryParserHelper/queryParserHelperList",model:model)
		render(contentType:"text/json") { results = [listHtml]}
	}
	def search = {
		adaptParamsForList()
		def queryParserHelpers  = queryParserHelperService.searchQueryParserHelper(params['q'],null,params)

		if(request.xhr)
			this.ajaxModel(queryParserHelpers,params['q'])
		else {
			render(view:"/entity/list",model:model(queryParserHelpers) << [
				template:"/reports/dashboard/queryParserHelper/queryParserHelperList",
				listTop:"/reports/dashboard/queryParserHelper/listTop",

			])
		}
	}



	def list = {
		adaptParamsForList()
		
		def queryParserHelpers = QueryParserHelper.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc")
		if(request.xhr){
			this.ajaxModel(queryParserHelpers,"")
			println"i was just kiding okkkkkkkkkkkkkkkkkkkkkkk"
		}
		else{
			println"seriously doneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
			render(view:"/entity/list",model:model(queryParserHelpers) << [
				template:"/reports/dashboard/queryParserHelper/queryParserHelperList",
				listTop:"/reports/dashboard/queryParserHelper/listTop"

			])
			println"hi how are you doing ok ??????????????????????????????????????????????????????????"
		}
	}
}
