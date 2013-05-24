package org.chai.memms.report
import java.util.Set
import java.util.Map
import org.apache.commons.lang.math.NumberUtils
import org.chai.memms.AbstractEntityController


class IntermediateVariableController extends AbstractEntityController {
	def intermediateVariableService
	
		def getEntity(def id) {
			return IntermediateVariable.get(id);
		}
	
		def createEntity() {
			return new IntermediateVariable();
		}
	
		def getTemplate() {
			return "/entity/reports/dashboard/intermediateVariable/createIntermediateVariable";
		}
	
		def getLabel() {
			return "equipment.type.label";
		}
	
		def getEntityClass() {
			return IntermediateVariable.class;
		}
		def deleteEntity(def entity) {
			
				super.deleteEntity(entity);
		}
	
		def bindParams(def entity) {
			entity.properties = params
		}
	
		def getExportClass() {
			return null
		}
	
		def getModel(def entity) {
			[
				intermediateVariable: entity
			]
		}
				
		def list = {
			adaptParamsForList()
			def types = IntermediateVariable.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc");
			if(request.xhr)
				this.ajaxModel(types,"")
			else{
				render(view:"/entity/list",model:model(types) << [
					template:"reports/dashboard/intermediateVariable/intermediateVariableList",
					listTop:"reports/dashboard/intermediateVariable/listTop",
					
				])
			}
		}
		
		def search = {
			adaptParamsForList()
			def types  = intermediateVariableService.searchIntermediateVariable(params['q'],null,params)
	
			if(request.xhr)
				this.ajaxModel(types,params['q'])
			else {
				render(view:"/entity/list",model:model(types) << [
					template:"reports/dashboard/intermediateVariable/intermediateVariableList",
					listTop:"reports/dashboard/intermediateVariable/listTop",
					
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
			def listHtml = g.render(template:"/entity/intermediateVariable/intermediateVariableList",model:model)
			render(contentType:"text/json") { results = [listHtml] }
		}
	
		/*The form side will be discueesd*/
		def getAjaxData = {
			def intermediateVariables = intermediateVariableService.searchIntermediateVariable(params['term'],[:])
			render(contentType:"text/json") {
				elements = array {
					intermediateVariables.each { intermediateVariable ->
						elem (
								key: intermediateVariable.id,
								value: intermediateVariable.getNames(languageService.getCurrentLanguage()) + ' ['+intermediateVariable.code+']'
								)
					}
				}
				htmls = array {
					intermediateVariables.each { intermediateVariable -> 
						elem (
								key: intermediateVariable.id,
								html: g.render(template:"/templates/typeFormSide",model:[type:intermediateVariable,label:label,cssClass:"form-aside-hidden",field:'type'])
								)
					}
				}
			}
		}
		
		
		
    
}
