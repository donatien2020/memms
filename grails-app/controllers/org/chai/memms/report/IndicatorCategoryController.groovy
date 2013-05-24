package org.chai.memms.report

import java.util.Map;
import java.util.Set;
import org.chai.location.CalculationLocation;
import org.chai.location.DataLocationType;
import org.chai.memms.AbstractEntityController;


import org.chai.memms.report.IndicatorCategory;


class IndicatorCategoryController extends AbstractEntityController{
	def indicatorCategoryService

	def getEntity(def id) {
		return IndicatorCategory.get(id);
	}

	def createEntity() {
		return new IndicatorCategory();
	}

	def getTemplate() {
		return "/entity/reports/dashboard/indicatorCategory/createIndicatorCategory";
	}

	def getLabel() {
		return "indicator.category.label";
	}

	def getEntityClass() {
		return IndicatorCategory.class;
	}
	def deleteEntity(def entity) {
		
			super.deleteEntity(entity);
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	

	def getModel(def entity) {
		[
			indicatorCategory: entity
		]
	}
			
	def list = {
		adaptParamsForList()
		
		
		def categories = IndicatorCategory.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc");
		if(request.xhr)
			this.ajaxModel(categories,"")
		else{
			render(view:"/entity/list",model:model(categories) << [
				template:"/reports/dashboard/indicatorCategory/indicatorCategoryList",
				listTop:"/reports/dashboard/indicatorCategory/listTop",
				
			])
		}
	}
	
	def search = {
		adaptParamsForList()
		def types  = indicatorCategoryService.searchIndicatorCategory(params['q'],null,params)

		if(request.xhr)
			this.ajaxModel(types,params['q'])	
		else {
			render(view:"/entity/list",model:model(types) << [
				template:"reports/dashboard/indicatorCategory/indicatorCategoryList",
				listTop:"reports/dashboard/indicatorCategory/listTop",
				
			])
		}
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
		def listHtml = g.render(template:"/entity/reports/dashboard/indicatorCategory/indicatorCategoryList",model:model)
		render(contentType:"text/json") { results = [listHtml] }
	}

	
//	def getAjaxData = {
//		def cotecories = indicatorCategoryService.searchIndicatorCategory(params['term'],[:])
//		render(contentType:"text/json") {
//			elements = array {
//				cotecories.each { cotecory ->				
//					elem (
//							key: cotecory.id,
//							value: cotecory.getName(languageService.getCurrentLanguage()) + ' ['+cotecory.code+']'
//							)
//				}
//			}
//			htmls = array {
//				cotecories.each { cotecory ->
//					elem (
//							key: cotecory.id,
//							html: g.render(template:"/templates/cotecoryFormSide",model:[cotecory:cotecory,label:label,cssClass:"form-aside-hidden",field:'name'])
//							)
//				}
//			}
//		}
//	}	
	
	
	
}
