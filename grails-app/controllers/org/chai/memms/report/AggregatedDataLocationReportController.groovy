package org.chai.memms.report

import org.chai.memms.AbstractEntityController
import java.util.Set
import java.util.Map

import org.apache.commons.lang.math.NumberUtils
import org.springframework.dao.DataIntegrityViolationException
class AggregatedDataLocationReportController  extends AbstractEntityController {
	static transactional = true

	def sessionFactory
	def dataSource  //Aut
	def aggregatedDataLocationReportService
	def indicatorValueService

	def getLabel() {
		return "aggregatedDataLocationReport.label"
	}

	def getEntityClass() {
		return AggregatedDataLocationReport.class
	}


	def getEntity(def id) {
		return AggregatedDataLocationReport.get(id)
	}

	def createEntity() {
		return new AggregatedDataLocationReport()
	}

	def getTemplate() {
		return "/entity/reports/dashboard/dataLocationReport/createDataLocationReportt"
	}

	def bindParams(def entity) {
		entity.properties = params
	}

	def getModel(def entity) {
		[
			aggregatedDataLocationReport: entity
		]
	}

	def model(def entities) {
		return [
			entities: entities,
			//entityCount: entities.totalCount,
			entityClass:getEntityClass(),
			code: getLabel()
		]
	}
	def ajaxModel(def entities,def searchTerm) {
		def model = model(entities) << [q:searchTerm]
		def listHtml = g.render(template:"/entity/reports/dashboard/dataLocationReport/dataLocationReportList",model:model)
		render(contentType:"text/json") { results = [listHtml]}
	}



	//	def search = {
	//		adaptParamsForList()
	//		def dataLocationReports  =AggregatedDataLocationReport.findAll()
	//
	//		if(request.xhr)
	//			this.ajaxModel(dataLocationReports,params['q'])
	//		else {
	//			render(view:"/entity/list",model:model(dataLocationReports) << [
	//				template:"/reports/dashboard/dataLocationReport/dataLocationReportList",
	//				listTop:"/reports/dashboard/dataLocationReport/listTop",
	//
	//			])
	//		}
	//	}
	//
	//	def list = {
	//
	//		adaptParamsForList()
	//
	//		def dataLocationReports = AggregatedDataLocationReport.findAll()
	//		if(request.xhr){
	//			this.ajaxModel(dataLocationReports,"")
	//		}
	//		else{
	//			render(view:"/entity/list",model:model(dataLocationReports) << [
	//				template:"/reports/dashboard/dataLocationReport/dataLocationReportList",
	//				listTop:"/reports/dashboard/dataLocationReport/listTop"
	//			])
	//		}
	//	}


	def dashboard ={


		adaptParamsForList()

		AggregatedDataLocationReport aggregatedDataLocationReport=new AggregatedDataLocationReport()

		aggregatedDataLocationReport.correctiveMaintenanceReports.addAll(aggregatedDataLocationReportService.getCorrectiveMaintenanceReports())
		aggregatedDataLocationReport.privantiveMaintenanceReports.addAll(aggregatedDataLocationReportService.getPreventiveMaintenanceReports())
		aggregatedDataLocationReport.manageMemmEquipmentRreports.addAll(aggregatedDataLocationReportService.getManageMemmsEquipmentReports())
		aggregatedDataLocationReport.manageMemmsUseReports.addAll(aggregatedDataLocationReportService.getMemmsParePartReports())
		aggregatedDataLocationReport.manageSparePartsReports.addAll(aggregatedDataLocationReportService.getManageMemmsUseReports())


		println"beforer corre :"+aggregatedDataLocationReportService.getCorrectiveMaintenanceReports().size()
		println"corre :"+aggregatedDataLocationReport.correctiveMaintenanceReports.size()
		println"maintenance :"+aggregatedDataLocationReport.privantiveMaintenanceReports.size()
		//List<AggregatedDataLocationReport> repors=new ArrayList<AggregatedDataLocationReport>()
		//def aggretions=AggregatedDataLocationReport.findAll ()
		//repors.add(aggregatedDataLocationReport)
		//aggretions.addAll(aggregatedDataLocationReportService.getCorrectiveMaintenanceReports())
		if(request.xhr){
			this.ajaxModel(aggregatedDataLocationReport,"")
		}
		else{
			render(view:"/entity/list",model:model(aggregatedDataLocationReport) << [
				template:"/reports/dashboard/dataLocationReport/dashboard",
				listTop:"/reports/dashboard/dataLocationReport/listTop"
			])
		}

	}




}
