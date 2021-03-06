/** 
 * Copyright (c) 2012, Clinton Health Access Initiative.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chai.memms.reports

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.chai.location.CalculationLocation;
import org.chai.location.DataLocation;
import org.chai.location.DataLocationType;
import org.chai.location.Location;
import org.chai.location.LocationLevel;
import org.chai.memms.AbstractController;
import org.chai.memms.AbstractEntityController;
import org.chai.memms.corrective.maintenance.WorkOrder;
import org.chai.memms.corrective.maintenance.WorkOrderStatus;
import org.chai.memms.corrective.maintenance.WorkOrderStatus.OrderStatus;
import org.chai.memms.corrective.maintenance.WorkOrderStatus.WorkOrderStatusChange;
import org.chai.memms.inventory.Department;
import org.chai.memms.inventory.Equipment;
import org.chai.memms.inventory.EquipmentStatus;
import org.chai.memms.inventory.EquipmentStatus.Status;
import org.chai.memms.inventory.EquipmentStatus.EquipmentStatusChange;
import org.chai.memms.inventory.EquipmentType;
import org.chai.memms.preventive.maintenance.PreventiveOrder.PreventiveOrderStatus;
//import org.chai.memms.preventive.maintenance.PreventiveOrder.PreventiveOrderStatusChange;
import org.chai.memms.preventive.maintenance.PreventiveOrder.PreventionResponsible;
import org.chai.memms.report.listing.CorrectiveMaintenanceReport;
import org.chai.memms.report.listing.EquipmentReport;
import org.chai.memms.report.listing.PreventiveMaintenanceReport;
import org.chai.memms.report.listing.SparePartReport
import org.chai.memms.spare.part.SparePartStatus.StatusOfSparePart;
//import org.chai.memms.spare.part.SparePartStatus.StatusOfSparePartChange;
import org.chai.memms.spare.part.SparePartType;
import org.chai.memms.security.User;
import org.chai.memms.security.User.UserType;
import org.chai.memms.util.Utils;
import org.chai.memms.util.Utils.ReportType;
import org.chai.memms.util.Utils.ReportSubType;
import org.joda.time.DateTime;
import org.chai.memms.Warranty;

/**
 * @author Jean Kahigiso M.
 *
 */
class ListingController extends AbstractController{

	def equipmentListingReportService
	def workOrderListingReportService
	def preventiveOrderListingReportService
	def sparePartListingReportService
	def userService

	def getEntityClass() {
		return Object.class;
	}

	def getLabel() {
		return "equipment.listing.report.label";
	}

	def model(def entities, def dataLocation) {
		return [
			entities: entities,
			entityCount: entities.totalCount,
			dataLocation: dataLocation,
			entityClass: getEntityClass(),
			code: getLabel()
		]
	}

	def index ={
		redirect(action: "view", params: params)
	}

	def view ={
		redirect(action: "generalEquipmentsListing", params: params)
	}

	// predefined reports start

	// inventory

	def generalEquipmentsListing={
		if (log.isDebugEnabled()) log.debug("listing.generalEquipmentsListing start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)
		if (log.isDebugEnabled()) log.debug("listing.generalEquipmentsListing savedReports:"+savedReports.size())

		adaptParamsForList()
		def equipments = equipmentListingReportService.getGeneralReportOfEquipments(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.all.equipments.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def obsoleteEquipments={
		if (log.isDebugEnabled()) log.debug("listing.obsoleteEquipments start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)

		adaptParamsForList()
		def equipments = equipmentListingReportService.getObsoleteEquipments(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.obsolete.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def disposedEquipments={
		if (log.isDebugEnabled()) log.debug("listing.disposedEquipments start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)

		adaptParamsForList()
		def equipments = equipmentListingReportService.getDisposedEquipments(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.disposed.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def underMaintenanceEquipments={
		if (log.isDebugEnabled()) log.debug("listing.underMaintenanceEquipments start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)

		adaptParamsForList()
		def equipments = equipmentListingReportService.getUnderMaintenanceEquipments(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.under.maintenance.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def inStockEquipments={
		if (log.isDebugEnabled()) log.debug("listing.inStockEquipments start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)

		adaptParamsForList()
		def equipments = equipmentListingReportService.getInStockEquipments(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.in.stock.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def underWarrantyEquipments={
		if (log.isDebugEnabled()) log.debug("listing.underWarrantyEquipments start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)

		adaptParamsForList()
		def warrantyExpirationDate
		def equipments = equipmentListingReportService.getUnderWarrantyEquipments(user,params)

		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(equipments, "") <<
			[
				reportType: ReportType.INVENTORY,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.in.stock.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	// corrective

	def generalWorkOrdersListing={
		if (log.isDebugEnabled()) log.debug("listing.generalWorkOrdersListing start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.CORRECTIVE)

		adaptParamsForList()
		def workOrders = workOrderListingReportService.getAllWorkOrders(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(workOrders, "") <<
			[
				reportType: ReportType.CORRECTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.all.work.order.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def lastMonthWorkOrders={
		if (log.isDebugEnabled()) log.debug("listing.lastMonthWorkOrders start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.CORRECTIVE)

		adaptParamsForList()
		def workOrders = workOrderListingReportService.getWorkOrdersOfLastMonth(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(workOrders, "") <<
			[
				reportType: ReportType.CORRECTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.work.order.last.month.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def workOrdersEscalatedToMMC={
		if (log.isDebugEnabled()) log.debug("listing.workOrdersEscalatedToMMC start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.CORRECTIVE)

		adaptParamsForList()
		def workOrders = workOrderListingReportService.getWorkOrdersEscalatedToMMC(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(workOrders, "") <<
			[
				reportType: ReportType.CORRECTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.work.order.escalated.to.mmc.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def lastYearClosedWorkOrders={
		if (log.isDebugEnabled()) log.debug("listing.lastYearClosedWorkOrders start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.CORRECTIVE)

		adaptParamsForList()
		def workOrders = workOrderListingReportService.getClosedWorkOrdersOfLastYear(user, params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(workOrders, "") <<
			[
				reportType: ReportType.CORRECTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.work.order.closed.last.year.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	// preventive

	def generalPreventiveOrdersListing={
		if (log.isDebugEnabled()) log.debug("listing.generalPreventiveOrdersListing start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.PREVENTIVE)

		adaptParamsForList()
		def preventiveOrders = preventiveOrderListingReportService.getAllPreventions(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(preventiveOrders, "") <<
			[
				reportType: ReportType.PREVENTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.all.preventive.order.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def equipmentsWithPreventionPlan={
		if (log.isDebugEnabled()) log.debug("listing.equipmentsWithPreventionPlan start, params:"+params)

		def savedReports = userService.getSavedReportsByUser(user, ReportType.PREVENTIVE)

		adaptParamsForList()
		def preventiveOrders = preventiveOrderListingReportService.getEquipmentsWithPreventionPlan(user,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(preventiveOrders, "") <<
			[
				reportType: ReportType.PREVENTIVE,
				reportSubType: ReportSubType.WORKORDERS,
				reportName: message(code:'default.equipments.with.prevention.label'),
				savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	// spare parts

	def generalSparePartsListing={
		if (log.isDebugEnabled()) log.debug("listing.generalSparePartsListing start, params:"+params)

		// def savedReports = userService.getSavedReportsByUser(user, ReportType.SPAREPARTS)

		adaptParamsForList()
		def type = SparePartType.get(params.long('type.id'))
		def spareParts = sparePartListingReportService.getGeneralReportOfSpareParts(user,type, params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(spareParts, "") <<
			[
				reportType: ReportType.SPAREPARTS,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.all.spare.parts.label'),
				// savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	def pendingOrderSparePartsListing={
		if (log.isDebugEnabled()) log.debug("listing.generalSparePartsListing start, params:"+params)

		// def savedReports = userService.getSavedReportsByUser(user, ReportType.SPAREPARTS)

		adaptParamsForList()
		def type = SparePartType.get(params.long('type.id'))
		def spareParts = sparePartListingReportService.getPendingOrderSparePartsReport(user,type,params)
		if(!request.xhr)
			render(view:"/reports/reports",
			model: model(spareParts, "") <<
			[
				reportType: ReportType.SPAREPARTS,
				reportSubType: ReportSubType.INVENTORY,
				reportName: message(code:'default.pending.order.spare.parts.label'),
				// savedReports: savedReports,
				template:"/reports/listing/listing"
			])
	}

	// predefined reports end

	// customized report wizard steps start

	def step1 ={
		if (log.isDebugEnabled()) log.debug("listing.step1 start, params:"+params)

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		//params to pass along to step 2
		def step1Params = [:]

		//params to load step 1
		def step1Model = [
			reportType: reportType,
			reportSubType: reportSubType
		]
		step1Model << [step1Params: step1Params]

		if (log.isDebugEnabled()) log.debug("listing.step1 end, step1Model:"+step1Model)
		render(template:"/reports/listing/customizedReport/step1",
		model: step1Model <<
		[
			reportType: reportType,
			reportSubType: reportSubType,
			dataLocations: dataLocations,
			step1Params: step1Params
		])
	}

	// customized report wizard step1 'remoteFunction'

	def customizedReportSubType ={
		if (log.isDebugEnabled())
			log.debug("listing.step1.customizedReportSubType start, params:"+params)

		def reportType = getReportType()

		if (log.isDebugEnabled()) log.debug("listing.step1.customizedReportSubType end, reportType:"+reportType)
		render(template:"/reports/listing/customizedReport/customizedReportSubType",
		model:
		[
			reportType: reportType
		])
	}

	def step2 ={
		if (log.isDebugEnabled()) log.debug("listing.step2 start, params:"+params)

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		//params from step 1 to pass along to step 3
		def step2Params = [:]
		step2Params.putAll params

		//params to load step 2
		def step2Model = [
			reportType: reportType,
			reportSubType: reportSubType,
			currencies: grailsApplication.config.site.possible.currency
		]
		step2Model << [step2Params: step2Params]

		if (log.isDebugEnabled()) log.debug("listing.step2 end, step2Model:"+step2Model)
		render(template:"/reports/listing/customizedReport/step2", model:step2Model)
	}

	def step3 ={
		if (log.isDebugEnabled()) log.debug("listing.step3 start, params:"+params)

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		//params from step 2 to pass along to step 4
		def step3Params = [:]
		step3Params.putAll params

		//params to load step 3
		def step3Model = [
			reportType: reportType,
			reportSubType: reportSubType
		]
		step3Model << [step3Params: step3Params]

		if (log.isDebugEnabled()) log.debug("listing.step3 end, step3Model:"+step3Model)
		render(template:"/reports/listing/customizedReport/step3", model:step3Model)
	}

	def step4 ={
		if (log.isDebugEnabled()) log.debug("listing.step4 start, params:"+params)

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		//params from step 3 to pass along to customized listing
		def step4Params = [:]
		step4Params.putAll params

		//params to load step 4
		def step4Model = [
			reportType: reportType,
			reportSubType: reportSubType
		]
		step4Model << [step4Params: step4Params]

		if (log.isDebugEnabled()) log.debug("listing.step4 end, step4Model:"+step4Model)
		render(template:"/reports/listing/customizedReport/step4", model:step4Model)
	}

	// customized report wizard steps end

	// customized report listing start

	def customizedListing ={
		if (log.isDebugEnabled()) log.debug("listing.customizedListing start, params:"+params)

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		def customizedListingParams = [:]
		customizedListingParams.putAll params

		def customizedReportName = params.get('customizedReportName')
		if(customizedReportName == null || customizedReportName.empty){
			def customizedReportTimestamp = new Date()
			def reportTypeTimestamp = message(code:'reports.type.'+reportType?.reportType)
			def reportSubTypeTimestamp = message(code:'reports.subType.'+reportSubType?.reportSubType)
			customizedReportName = 
				"Custom Report "+reportTypeTimestamp+" "+reportSubTypeTimestamp+" "+customizedReportTimestamp.format('yyyyMMddHHmmss')
		}
		def customizedReportSave = params.get('customizedReportSave')
		customizedListingParams << [
			customizedReportName:customizedReportName,
			customizedReportSave: customizedReportSave
		]

		if (log.isDebugEnabled()) log.debug("listing.customizedListing end, customizedListingParams:"+customizedListingParams)
		switch(reportType){
			case ReportType.INVENTORY:
				redirect(action: "customEquipmentListing", params: customizedListingParams)
				break;
			case ReportType.CORRECTIVE:
				redirect(action: "customWorkOrderListing", params: customizedListingParams)
				break;
			case ReportType.PREVENTIVE:
				redirect(action: "customPreventiveOrderListing", params: customizedListingParams)
				break;
			case ReportType.SPAREPARTS:
				if(reportSubType == ReportSubType.INVENTORY || reportSubType == ReportSubType.STATUSCHANGES)
					redirect(action: "customSparePartsListing", params: customizedListingParams)
				if(reportSubType == ReportSubType.STOCKOUT || reportSubType == ReportSubType.USERATE)
					redirect(action: "customSparePartTypesListing", params: customizedListingParams)
				break;
			default:
				break;
		}
	}

	def savedCustomizedListing ={
		if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing start, params:"+params)

		def reportType = getReportType()

		def savedReport = getSavedReport()
		if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing savedReport:"+savedReport)

		if(savedReport == null){
			switch(reportType){
				case ReportType.INVENTORY:
					redirect(action:"generalEquipmentsListing")
					break;
				case ReportType.CORRECTIVE:
					redirect(action:"generalWorkOrdersListing")
					break;
				case ReportType.PREVENTIVE:
					redirect(action:"generalPreventiveOrdersListing")
					break;
				case ReportType.SPAREPARTS:
					redirect(action:"generalSparePartsListing")
					break;
			}
		}
		else{
			def customizedListingParams = [:]
			def savedCustomizedListingReport = null
			
			switch(reportType){
				case ReportType.INVENTORY:
					customizedListingParams = [
						reportType: savedReport.reportType,
						reportSubType: savedReport.reportSubType,
						dataLocations: savedReport.dataLocations,
						departments: savedReport.departments,
						equipmentTypes: savedReport.equipmentTypes,
						fromCost: savedReport.lowerLimitCost,
						toCost: savedReport.upperLimitCost,
						costCurrency: savedReport.currency,
						noCost: savedReport.noCostSpecified
					]
					if(savedReport.reportSubType == ReportSubType.INVENTORY){
						customizedListingParams << [
							fromAcquisitionPeriod: savedReport.fromDate,
							toAcquisitionPeriod: savedReport.toDate,
							noAcquisitionPeriod: savedReport.noAcquisitionPeriod,
							equipmentStatus: savedReport.equipmentStatus,
							obsolete: savedReport.obsolete,
							warranty: savedReport.underWarranty
						]
					}
					// DONE AR
					 if(savedReport.reportSubType == ReportSubType.STATUSCHANGES){
					 	customizedListingParams << [
					 		statusChanges: savedReport.statusChanges,
					 		fromStatusChangesPeriod: savedReport.fromStatusChangesPeriod,
					 		toStatusChangesPeriod: savedReport.toStatusChangesPeriod
					 	]
					 }

					adaptParamsForList()
					savedCustomizedListingReport = equipmentListingReportService.getCustomReportOfEquipments(user,customizedListingParams,params)
					if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing # of equipments:"+savedCustomizedListingReport.size())
					break;
				case ReportType.CORRECTIVE:
					customizedListingParams = [
						reportType: savedReport.reportType,
						reportSubType: savedReport.reportSubType,
						dataLocations: savedReport.dataLocations,
						departments: savedReport.departments,
						equipmentTypes: savedReport.equipmentTypes,
						fromCost: savedReport.lowerLimitCost,
						toCost: savedReport.upperLimitCost,
						costCurrency: savedReport.currency,
						noCost: savedReport.noCostSpecified,
						warranty: savedReport.underWarranty
					]
					// DONE AR
					 if(savedReport.reportSubType == ReportSubType.WORKORDERS){
					 	customizedListingParams << [
					 		workOrderStatus: savedReport.workOrderStatus,
					 		fromWorkOrderPeriod: savedReport.fromDate,
					 		toWorkOrderPeriod: savedReport.toDate
					 	]
					 }
					// TODO AR - Add these properties to saved report
					 if(savedReport.reportSubType == ReportSubType.STATUSCHANGES){
					 	customizedListingParams << [
					 		statusChanges: savedReport.statusChanges,
					 		fromStatusChangesPeriod: savedReport.fromStatusChangesPeriod,
					 		toStatusChangesPeriod: savedReport.toStatusChangesPeriod
					 	]
					 }
					customizedListingParams << [reportTypeOptions: savedReport.displayOptions]
					adaptParamsForList()
					savedCustomizedListingReport = workOrderListingReportService.getCustomReportOfWorkOrders(user,customizedListingParams,params)
					if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing # of workOrders:"+savedCustomizedListingReport.size())
					break;
				case ReportType.PREVENTIVE:
					customizedListingParams = [
						reportType: savedReport.reportType,
						reportSubType: savedReport.reportSubType,
						dataLocations: savedReport.dataLocations,
						departments: savedReport.departments,
						equipmentTypes: savedReport.equipmentTypes,
						fromCost: savedReport.lowerLimitCost,
						toCost: savedReport.upperLimitCost,
						costCurrency: savedReport.currency,
						noCost: savedReport.noCostSpecified
					]
					// DONE AR
					if(savedReport.reportSubType == ReportSubType.WORKORDERS){
					 	customizedListingParams << [
					 		workOrderStatus: savedReport.preventiveOrderStatus,
					 		fromWorkOrderPeriod: savedReport.fromDate,
					 		toWorkOrderPeriod: savedReport.toDate,
					 		whoIsResponsible: savedReport.preventionResponsible 
					 	]
					 }
					customizedListingParams << [reportTypeOptions: savedReport.displayOptions]
					adaptParamsForList()
					savedCustomizedListingReport = preventiveOrderListingReportService.getCustomReportOfPreventiveOrders(user,customizedListingParams,params)
					if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing # of preventiveOrders:"+savedCustomizedListingReport.size())
					break;
				case ReportType.SPAREPARTS:
					customizedListingParams = [
						reportType: savedReport.reportType,
						reportSubType: savedReport.reportSubType,
						dataLocations: savedReport.dataLocations,
						sparePartTypes: savedReport.sparePartTypes,
					]
					if(savedReport.reportSubType == ReportSubType.INVENTORY){
						customizedListingParams << [
							sparePartStatus: savedReport.sparePartStatus,
							fromAcquisitionPeriod: savedReport.fromDate,
							toAcquisitionPeriod: savedReport.toDate,
							noAcquisitionPeriod: savedReport.noAcquisitionPeriod,
						]
					}
					// TODO AR - Add these properties to saved report
					// if(savedReport.reportSubType == ReportSubType.STATUSCHANGES){
					// 	customizedListingParams << [
					// 		statusChanges: statusChanges,
					// 		fromStatusChangesPeriod: fromStatusChangesPeriod,
					// 		toStatusChangesPeriod: toStatusChangesPeriod
					// 	]
					// }
					// TODO AR - Add these properties to saved report
					// if(savedReport.reportSubType == ReportSubType.STOCKOUT){
					// 	customizedListingParams << [
					// 		stockOut: stockOut,
					// 		stockOutMonths: stockOutMonths
					// 	]
					// }
					// TODO AR - Add these properties to saved report
					// if(savedReport.reportSubType == ReportSubType.USERATE){ }
					customizedListingParams << [reportTypeOptions: savedReport.displayOptions]
					adaptParamsForList()
					savedCustomizedListingReport = sparePartListingReportService.getCustomReportOfSpareParts(user,customizedListingParams,params)
					if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing # of spareParts:"+savedCustomizedListingReport.size())
					break;
				default:
					break;
			}

			def savedReports = null
			// TODO get rid of this condition
			if(reportType != ReportType.SPAREPARTS){
				savedReports = userService.getSavedReportsByUser(user, reportType)
			}

			customizedListingParams << [
				selectedReportId: savedCustomizedListingReport.id,
				selectedReport: savedReport,
				savedReports: savedReports,
				template:"/reports/listing/listing"
			]

			if (log.isDebugEnabled()) log.debug("listing.savedCustomizedListing end, customizedListingParams:"+customizedListingParams)

			if(!request.xhr)
			render(view:"/reports/reports",
				model: model(savedCustomizedListingReport, "") << customizedListingParams)
		}
	}

	def deleteCustomizedListing ={
		if (log.isDebugEnabled()) log.debug("listing.deleteCustomizedListing start, params:"+params)

		def savedReport = getSavedReport()
		if (log.isDebugEnabled()) log.debug("listing.deleteCustomizedListing savedReport:"+savedReport)

		def result = false
		if(savedReport != null){
			savedReport.delete(flush: true)
			if (log.isDebugEnabled()) log.debug("listing.deleteCustomizedListing savedReport deleted")
			result = true
		}

		render(contentType:"text/json") { result = [result] }
	}

	// inventory

	def customEquipmentListing ={
		adaptParamsForList()
		if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing start, params:"+params)

		def selectedReportId = params.int('selectedReportId')
		if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report id:"+selectedReportId)
		if(selectedReportId > 0){
			redirect(action: 'savedCustomizedListing', params: [id: selectedReportId])
		}

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		def dataLocations = getDataLocations()
		def departments = getDepartments()
		def equipmentTypes = getEquipmentTypes()

		def fromCost = null
		if(params.get('fromCost') != null && !params.get('fromCost').empty)
			fromCost = Double.parseDouble(params.get('fromCost'))
		def toCost = null
		if(params.get('toCost') != null && !params.get('toCost').empty)
			toCost = Double.parseDouble(params.get('toCost'))
		def costCurrency = params.get('costCurrency')
		def noCost = params.get('noCost')

		def customizedListingParams = [
			reportType: reportType,
			reportSubType: reportSubType,
			dataLocations: dataLocations,
			departments: departments,
			equipmentTypes: equipmentTypes,
			fromCost: fromCost,
			toCost: toCost,
			costCurrency: costCurrency,
			noCost: noCost
		]

		if(reportSubType == ReportSubType.INVENTORY){
			def fromAcquisitionPeriod = getPeriod('fromAcquisitionPeriod')
			def toAcquisitionPeriod = getPeriod('toAcquisitionPeriod')
			def noAcquisitionPeriod = params.get('noAcquisitionPeriod')
			def equipmentStatus = getInventoryStatus()
			def obsolete = params.get('obsolete')
			def warranty = params.get('warranty')
			customizedListingParams << [
				fromAcquisitionPeriod: fromAcquisitionPeriod,
				toAcquisitionPeriod: toAcquisitionPeriod,
				noAcquisitionPeriod: noAcquisitionPeriod,
				equipmentStatus: equipmentStatus,
				obsolete: obsolete,
				warranty: warranty
			]
		}

		if(reportSubType == ReportSubType.STATUSCHANGES){
			def statusChanges = getInventoryStatusChanges()
			def fromStatusChangesPeriod = getPeriod('fromStatusChangesPeriod')
			def toStatusChangesPeriod = getPeriod('toStatusChangesPeriod')
			customizedListingParams << [
				statusChanges: statusChanges,
				fromStatusChangesPeriod: fromStatusChangesPeriod,
				toStatusChangesPeriod: toStatusChangesPeriod
			]
		}

		def reportTypeOptions = getReportTypeOptions('inventoryOptions')
		def customizedReportName = params.get('customizedReportName')
		customizedListingParams << [
			reportTypeOptions: reportTypeOptions,
			customizedReportName: customizedReportName
		]

		def customizedReportSave = params.get('customizedReportSave')
		if(customizedReportSave){
			def selectedReport = equipmentListingReportService.saveEquipmentReportParams(user,customizedListingParams,params)
			if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report:"+selectedReport+", id:"+selectedReport.id)
			redirect(action:"savedCustomizedListing", params:[savedReportId: selectedReport.id, reportType: selectedReport.reportType])
		}
		else{
			def equipments = equipmentListingReportService.getCustomReportOfEquipments(user,customizedListingParams,params)
			if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing # of equipments:"+equipments.size())

			def savedReports = userService.getSavedReportsByUser(user, ReportType.INVENTORY)
			if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing # of savedReports:"+savedReports.size())

			customizedListingParams << [
				savedReports: savedReports,
				template:"/reports/listing/listing"
			]

			if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing end, customizedListingParams:"+customizedListingParams)

			if(!request.xhr){
				render(view:"/reports/reports",
					model: model(equipments, "") << customizedListingParams)
			}
		}
	}

	// corrective

	def customWorkOrderListing ={
		adaptParamsForList()
		if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing start, params:"+params)

		def selectedReportId = params.int('selectedReportId')
		if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report id:"+selectedReportId)
		if(selectedReportId > 0){
			redirect(action: 'savedCustomizedListing', params: [id: selectedReportId])
		}

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		def dataLocations = getDataLocations()
		def departments = getDepartments()
		def equipmentTypes = getEquipmentTypes()

		def fromCost = null
		if(params.get('fromCost') != null && !params.get('fromCost').empty)
			fromCost = Double.parseDouble(params.get('fromCost'))
		def toCost = null
		if(params.get('toCost') != null && !params.get('toCost').empty)
			toCost = Double.parseDouble(params.get('toCost'))
		def costCurrency = params.get('costCurrency')
		def noCost = params.get('noCost')

		def warranty = params.get('warranty')

		def customizedListingParams = [
			reportType: reportType,
			reportSubType: reportSubType,
			dataLocations: dataLocations,
			departments: departments,
			equipmentTypes: equipmentTypes,
			fromCost: fromCost,
			toCost: toCost,
			costCurrency: costCurrency,
			noCost: noCost,
			warranty: warranty
		]

		if(reportSubType == ReportSubType.WORKORDERS){
			def workOrderStatus = getCorrectiveStatus()
			def fromWorkOrderPeriod = getPeriod('fromWorkOrderPeriod')
			def toWorkOrderPeriod = getPeriod('toWorkOrderPeriod')
			customizedListingParams << [
				workOrderStatus: workOrderStatus,
				fromWorkOrderPeriod: fromWorkOrderPeriod,
				toWorkOrderPeriod: toWorkOrderPeriod
			]
		}

		def statusChanges = null
		if(reportSubType == ReportSubType.STATUSCHANGES){
			statusChanges = getCorrectiveStatusChanges()
			def fromStatusChangesPeriod = getPeriod('fromStatusChangesPeriod')
			def toStatusChangesPeriod = getPeriod('toStatusChangesPeriod')
			customizedListingParams << [
				statusChanges: statusChanges,
				fromStatusChangesPeriod: fromStatusChangesPeriod,
				toStatusChangesPeriod: toStatusChangesPeriod
			]
		}

		def reportTypeOptions = getReportTypeOptions('correctiveOptions')
		def customizedReportName = params.get('customizedReportName')
		customizedListingParams << [
			reportTypeOptions: reportTypeOptions,
			customizedReportName: customizedReportName
		]

		if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing, customizedListingParams:"+customizedListingParams)

		def customizedReportSave = params.get('customizedReportSave')
		if(customizedReportSave){
			def selectedReport = workOrderListingReportService.saveWorkOrderReportParams(user, customizedListingParams, params)
			if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing saved/selected report:"+selectedReport+", id:"+selectedReport.id)
			redirect(action:"savedCustomizedListing", params:[savedReportId: selectedReport.id, reportType: selectedReport.reportType])
		}
		else{
			def workOrders = workOrderListingReportService.getCustomReportOfWorkOrders(user,customizedListingParams,params)
			if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing # of workOrder:"+workOrders.size())

			def savedReports = userService.getSavedReportsByUser(user, ReportType.CORRECTIVE)
			if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing # of savedReports:"+savedReports.size())

			customizedListingParams << [
				savedReports: savedReports,
				template:"/reports/listing/listing"
			]

			if (log.isDebugEnabled()) log.debug("listing.customWorkOrderListing end, customizedListingParams:"+customizedListingParams)

			if(!request.xhr){
				render(view:"/reports/reports",
					model: model(workOrders, "") << customizedListingParams)
			}
		}
	}

	// preventive

	def customPreventiveOrderListing ={
		adaptParamsForList()
		if (log.isDebugEnabled()) log.debug("listing.customPreventiveOrderListing start, params:"+params)

		def selectedReportId = params.int('selectedReportId')
		if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report id:"+selectedReportId)
		if(selectedReportId > 0){
			redirect(action: 'savedCustomizedListing', params: [id: selectedReportId])
		}

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		def dataLocations = getDataLocations()
		def departments = getDepartments()
		def equipmentTypes = getEquipmentTypes()

		def fromCost = null
		if(params.get('fromCost') != null && !params.get('fromCost').empty)
			fromCost = Double.parseDouble(params.get('fromCost'))
		def toCost = null
		if(params.get('toCost') != null && !params.get('toCost').empty)
			toCost = Double.parseDouble(params.get('toCost'))
		def costCurrency = params.get('costCurrency')
		def noCost = params.get('noCost')

		def customizedListingParams = [
			reportType: reportType,
			reportSubType: reportSubType,
			dataLocations: dataLocations,
			departments: departments,
			equipmentTypes: equipmentTypes,
			fromCost: fromCost,
			toCost: toCost,
			costCurrency: costCurrency,
			noCost: noCost
		]

		if(reportSubType == ReportSubType.WORKORDERS){
			def workOrderStatus = getPreventiveStatus()
			def fromWorkOrderPeriod = getPeriod('fromWorkOrderPeriod')
			def toWorkOrderPeriod = getPeriod('toWorkOrderPeriod')
			def whoIsResponsible = getPreventionResponsible('whoIsResponsible')
			customizedListingParams << [
				workOrderStatus: workOrderStatus,
				fromWorkOrderPeriod: fromWorkOrderPeriod,
				toWorkOrderPeriod: toWorkOrderPeriod,
				whoIsResponsible: whoIsResponsible
			]
		}

		def reportTypeOptions = getReportTypeOptions('preventiveOptions')
		def customizedReportName = params.get('customizedReportName')
		customizedListingParams << [
			reportTypeOptions: reportTypeOptions,
			customizedReportName: customizedReportName
		]

		if (log.isDebugEnabled()) log.debug("listing.customPreventiveOrderListing, customizedListingParams:"+customizedListingParams)

		def customizedReportSave = params.get('customizedReportSave')
		if(customizedReportSave){
			def selectedReport = preventiveOrderListingReportService.savePreventiveOrderReportParams(user, customizedListingParams, params)
			if (log.isDebugEnabled()) log.debug("listing.customPreventiveOrderListing saved/selected report:"+selectedReport+", id:"+selectedReport.id)
			redirect(action:"savedCustomizedListing", params:[savedReportId: selectedReport.id, reportType: selectedReport.reportType])
		}
		else{
			def preventiveOrders = preventiveOrderListingReportService.getCustomReportOfPreventiveOrders(user,customizedListingParams,params)
			if (log.isDebugEnabled()) log.debug("listing.customPreventiveOrderListing # of preventiveOrders:"+preventiveOrders.size())

			def savedReports = userService.getSavedReportsByUser(user, ReportType.PREVENTIVE)

			customizedListingParams << [
				savedReports: savedReports,
				template:"/reports/listing/listing"
			]

			if (log.isDebugEnabled()) log.debug("listing.customPreventiveOrderListing end, customizedListingParams:"+customizedListingParams)

			if(!request.xhr){
				render(view:"/reports/reports",
					model: model(preventiveOrders, "") << customizedListingParams)
			}
		}
	}

	// spare parts

	def customSparePartsListing ={
		adaptParamsForList()
		if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing start, params:"+params)

		def selectedReportId = params.int('selectedReportId')
		if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report id:"+selectedReportId)
		if(selectedReportId > 0){
			redirect(action: 'savedCustomizedListing', params: [id: selectedReportId])
		}

		def reportType = getReportType()
		def reportSubType = getReportSubType()

		def dataLocations = getDataLocations()
		def sparePartTypes = getSparePartTypes()

		def customizedListingParams = [
			reportType: reportType,
			reportSubType: reportSubType,
			dataLocations: dataLocations,
			sparePartTypes: sparePartTypes
		]

		if(reportSubType == ReportSubType.INVENTORY){
			def sparePartStatus = getSparePartStatus()
			def fromAcquisitionPeriod = getPeriod('fromAcquisitionPeriod')
			def toAcquisitionPeriod = getPeriod('toAcquisitionPeriod')
			def noAcquisitionPeriod = params.get('noAcquisitionPeriod')
			customizedListingParams << [
				sparePartStatus: sparePartStatus,
				fromAcquisitionPeriod: fromAcquisitionPeriod,
				toAcquisitionPeriod: toAcquisitionPeriod,
				noAcquisitionPeriod: noAcquisitionPeriod
			]
		}

		if(reportSubType == ReportSubType.STATUSCHANGES){
			def statusChanges = getSparePartStatusChanges()
			def fromStatusChangesPeriod = getPeriod('fromStatusChangesPeriod')
			def toStatusChangesPeriod = getPeriod('toStatusChangesPeriod')
			customizedListingParams << [
				statusChanges: statusChanges,
				fromStatusChangesPeriod: fromStatusChangesPeriod,
				toStatusChangesPeriod: toStatusChangesPeriod
			]
		}

		def reportTypeOptions = getReportTypeOptions('spartPartsOptions')
		def customizedReportName = params.get('customizedReportName')
		customizedListingParams << [
			reportTypeOptions: reportTypeOptions,
			customizedReportName: customizedReportName
		]

		def customizedReportSave = params.get('customizedReportSave')
		if(customizedReportSave){
			def selectedReport = sparePartListingReportService.saveSparePartReportParams(user, customizedListingParams, params)
			if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing saved/selected report:"+selectedReport+", id:"+selectedReport.id)
			redirect(action:"savedCustomizedListing", params:[savedReportId: selectedReport.id, reportType: selectedReport.reportType])
		}
		else{
			def spareParts = sparePartListingReportService.getCustomReportOfSpareParts(user, customizedListingParams, params)
			if (log.isDebugEnabled()) log.debug("WWWWWWWWWWWHY DON'T I SEE THIS VALUE ON THE INTERFACE?:"+spareParts)

			def savedReports = null
			// savedReports = userService.getSavedReportsByUser(user, ReportType.SPAREPARTS)

			customizedListingParams << [
				savedReports: savedReports,
				template:"/reports/listing/listing"
			]

			if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing, customizedListingParams:"+customizedListingParams)

			if(!request.xhr){
				render(view:"/reports/reports",
					model: model(spareParts, "") << customizedListingParams)
			}
		}
	}

	// spare part types

	// def customSparePartTypesListing ={
	// 	adaptParamsForList()
	// 	if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing start, params:"+params)

	// 	def selectedReportId = params.int('selectedReportId')
	// 	if (log.isDebugEnabled()) log.debug("listing.customEquipmentListing saved/selected report id:"+selectedReportId)
	// 	if(selectedReportId > 0){
	// 		redirect(action: 'savedCustomizedListing', params: [id: selectedReportId])
	// 	}

	// 	def reportType = getReportType()
	// 	def reportSubType = getReportSubType()

	// 	def dataLocations = getDataLocations()
	// 	def sparePartTypes = getSparePartTypes()

	// 	def customizedListingParams = [
	// 		reportType: reportType,
	// 		reportSubType: reportSubType,
	// 		dataLocations: dataLocations,
	// 		sparePartTypes: sparePartTypes
	// 	]

	// 	if(reportSubType == ReportSubType.STOCKOUT){
	// 		def stockOut = params.get('stockOut')
	// 		def stockOutMonths = params.get('stockOutMonths')
	// 		customizedListingParams << [
	// 			stockOut: stockOut,
	// 			stockOutMonths: stockOutMonths
	// 		]
	// 	}

	// 	if(reportSubType == ReportSubType.USERATE){ }

	// 	def reportTypeOptions = getReportTypeOptions('spartPartsOptions')
	// 	def customizedReportName = params.get('customizedReportName')
	// 	customizedListingParams << [
	// 		reportTypeOptions: reportTypeOptions,
	// 		customizedReportName: customizedReportName
	// 	]

	// 	def customizedReportSave = params.get('customizedReportSave')
	// 	if(customizedReportSave){
	// 		def selectedReport = sparePartListingReportService.saveSparePartReportParams(user, customizedListingParams, params)
	// 		if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing saved/selected report:"+selectedReport+", id:"+selectedReport.id)
	// 		redirect(action:"savedCustomizedListing", params:[savedReportId: selectedReport.id, reportType: selectedReport.reportType])
	// 	}
	// 	else{
	// 		def spareParts = sparePartListingReportService.getCustomReportOfSpareParts(user, customizedListingParams, params)
	// 		if (log.isDebugEnabled()) log.debug("WWWWWWWWWWWHY DON'T I SEE THIS VALUE ON THE INTERFACE?:"+spareParts)

	// 		def savedReports = null
	// 		// savedReports = userService.getSavedReportsByUser(user, ReportType.SPAREPARTS)

	// 		customizedListingParams << [
	// 			savedReports: savedReports,
	// 			template:"/reports/listing/listing"
	// 		]

	// 		if (log.isDebugEnabled()) log.debug("listing.customSparePartsListing, customizedListingParams:"+customizedListingParams)

	// 		if(!request.xhr){
	// 			render(view:"/reports/reports",
	// 				model: model(spareParts, "") << customizedListingParams)
	// 		}
	// 	}
	// }

	// customized report listing end

	// customized report wizard params start

	def getReportType(){
		ReportType reportType = params.get('reportType')
		if(log.isDebugEnabled())
			log.debug("abstract.reportType param:"+reportType+")")
		if(reportType == null) reportType = ReportType.INVENTORY
		return reportType
	}
	def getReportSubType(){
		ReportSubType reportSubType = params.get('reportSubType')
		if(log.isDebugEnabled())
			log.debug("abstract.reportSubType param:"+reportSubType+")")
		if(reportSubType == null) reportSubType = ReportSubType.INVENTORY
		return reportSubType
	}

	def getSavedReport(){
		def reportType = getReportType()
		def savedReportId = params.int('savedReportId')
		def savedReport = null
		switch(reportType){
			case ReportType.INVENTORY:
				savedReport = EquipmentReport.get(savedReportId);
				break;
			case ReportType.CORRECTIVE:
				savedReport = CorrectiveMaintenanceReport.get(savedReportId);
				break;
			case ReportType.PREVENTIVE:
				savedReport = PreventiveMaintenanceReport.get(savedReportId);
				break;
			case ReportType.SPAREPARTS:
				savedReport = SparePartReport.get(savedReportId);
				break;
		}
		if (log.isDebugEnabled()) log.debug("listing.getSavedReport savedReport:"+savedReport)
		return savedReport
	}

	public Set<DataLocation> getDataLocations() {
		if(log.isDebugEnabled()) log.debug("abstract.dataLocations params:"+params)
		Set<DataLocation> dataLocations = new HashSet<DataLocation>()
		if(params.get('allDataLocations')){
			if(log.isDebugEnabled()) log.debug("abstract.dataLocations ALL")
			if(user.location instanceof Location)
				dataLocations.addAll(user.location.collectDataLocations(null))
			else{
				dataLocations = []
				dataLocations.add(user.location as DataLocation)
				if(userService.canViewManagedEquipments(user))
					dataLocations.addAll(((DataLocation)user.location).manages)
			}
		}
		else if (params.list('dataLocations') != null && !params.list('dataLocations').empty) {
			if(log.isDebugEnabled()) log.debug("abstract.dataLocations CUSTOM")
			def types = params.list('dataLocations')
			dataLocations.addAll(types.collect{ it ->
				if(log.isDebugEnabled())
					log.debug("abstract.dataLocations dataLocation:"+it+", isNumber:"+NumberUtils.isNumber(it as String))
				NumberUtils.isNumber(it as String) ? DataLocation.get(it) : null
			} - null)
		}

		return dataLocations
	}
	public Set<Department> getDepartments() {
		if(log.isDebugEnabled()) log.debug("abstract.departments params:"+params)
		Set<Department> departments = new HashSet<Department>()
		if(params.get('allDepartments')){
			if(log.isDebugEnabled()) log.debug("abstract.departments ALL")
			// TODO ?
			departments = Department.list()
		}
		else if (params.list('departments') != null && !params.list('departments').empty) {
			if(log.isDebugEnabled()) log.debug("abstract.departments CUSTOM")
			def types = params.list('departments')
			departments.addAll(types.collect{ it ->
				if(log.isDebugEnabled())
					log.debug("abstract.departments department:"+it+", isNumber:"+NumberUtils.isNumber(it as String))
				NumberUtils.isNumber(it as String) ? Department.get(it) : null
			} - null)
		}

		return departments
	}
	public Set<EquipmentType> getEquipmentTypes() {
		if(log.isDebugEnabled()) log.debug("abstract.equipmentTypes params:"+params)
		Set<EquipmentType> equipmentTypes = new HashSet<EquipmentType>()
		if(params.get('allEquipmentTypes')){
			if(log.isDebugEnabled()) log.debug("abstract.equipmentTypes ALL")
			//TODO ?
			equipmentTypes = EquipmentType.list()
		}
		else if (params.list('equipmentTypes') != null && !params.list('equipmentTypes').empty) {
			if(log.isDebugEnabled()) log.debug("abstract.equipmentTypes CUSTOM")
			def types = params.list('equipmentTypes')
			equipmentTypes.addAll(types.collect{ it ->
				if(log.isDebugEnabled())
					log.debug("abstract.equipmentTypes equipmentType:"+it+", isNumber:"+NumberUtils.isNumber(it as String))
				NumberUtils.isNumber(it as String) ? EquipmentType.get(it) : null
			} - null)
		}
		return equipmentTypes
	}
	public Set<SparePartType> getSparePartTypes() {
		if(log.isDebugEnabled()) log.debug("abstract.sparePartTypes params:"+params)
		Set<SparePartType> sparePartTypes = new HashSet<SparePartType>()
		if(log.isDebugEnabled()) log.debug("abstract.sparePartTypes ALL")
		if(params.get('allSparePartTypes')){
			//TODO ?
			sparePartTypes = SparePartType.list()
		}
		else if (params.list('sparePartTypes') != null && !params.list('sparePartTypes').empty) {
			if(log.isDebugEnabled()) log.debug("abstract.sparePartTypes CUSTOM")
			def types = params.list('sparePartTypes')
			sparePartTypes.addAll(types.collect{ it ->
				if(log.isDebugEnabled())
					log.debug("abstract.sparePartTypes sparePartType:"+it+", isNumber:"+NumberUtils.isNumber(it as String))
				NumberUtils.isNumber(it as String) ? SparePartType.get(it) : null
			} - null)
		}
		return sparePartTypes
	}

	public Date getPeriod(String periodParam){
		def date = null
		def period = params.get(periodParam);
		if(log.isDebugEnabled()) log.debug("abstract.getPeriod period param:"+periodParam+", value:"+period+", class:"+period?.class)
		if(period != null && !period.empty) {
			date  = Utils.parseDate(period)
			if(log.isDebugEnabled()) log.debug("abstract.getPeriod date param:"+periodParam+", value:"+date+", class:"+date?.class)
			return date
		}
		return date
	}

	public Set<Status> getInventoryStatus(){
		if(log.isDebugEnabled()) log.debug("abstract.inventoryStatus params:"+params)
		Set<Status> inventoryStatus = new HashSet<Status>()
		if (params.list('equipmentStatus') != null && !params.list('equipmentStatus').empty) {
			def types = params.list('equipmentStatus')
			types.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.equipmentStatus equipmentStatus:"+it)
				if(it != null) inventoryStatus.add(Enum.valueOf(Status.class, it))
			}
		}
		return inventoryStatus
	}
	public List<EquipmentStatusChange> getInventoryStatusChanges(){
		List<EquipmentStatusChange> inventoryStatusChanges = []
		if(log.isDebugEnabled()) log.debug("abstract.inventoryStatusChanges start params:"+params)
		if (params.list('statusChanges') != null && !params.list('statusChanges').empty) {
			def statusChanges = params.list('statusChanges')
			if(log.isDebugEnabled()) log.debug("abstract.inventoryStatusChanges statusChanges:"+statusChanges)
			statusChanges.each { it ->
				if(log.isDebugEnabled()) log.debug("abstract.inventoryStatusChanges statusChange:"+it)
				if(it != null) inventoryStatusChanges.add(Enum.valueOf(EquipmentStatusChange.class, it))
			}
		}
		if(log.isDebugEnabled())
			log.debug("abstract.inventoryStatusChanges end statusChanges:"+inventoryStatusChanges)
		return inventoryStatusChanges
	}

	public Set<OrderStatus> getCorrectiveStatus(){
		if(log.isDebugEnabled()) log.debug("abstract.correctiveStatus params:"+params)
		Set<OrderStatus> correctiveStatus = new HashSet<OrderStatus>()
		if (params.list('workOrderStatus') != null && !params.list('workOrderStatus').empty) {
			def types = params.list('workOrderStatus')
			types.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.workOrderStatus workOrderStatus:"+it)
				if(it != null) correctiveStatus.add(Enum.valueOf(OrderStatus.class, it))
			}
		}
		return correctiveStatus
	}
	public List<WorkOrderStatusChange> getCorrectiveStatusChanges(){
		List<WorkOrderStatusChange> correctiveStatusChanges = []
		if(log.isDebugEnabled()) log.debug("abstract.correctiveStatusChanges start params:"+params)
		if (params.list('statusChanges') != null && !params.list('statusChanges').empty) {
			def statusChanges = params.list('statusChanges')
			if(log.isDebugEnabled()) log.debug("abstract.correctiveStatusChanges statusChanges:"+statusChanges)
			statusChanges.each { it ->
				if(log.isDebugEnabled()) log.debug("abstract.correctiveStatusChanges statusChange:"+it)
				if(it != null) correctiveStatusChanges.add(Enum.valueOf(WorkOrderStatusChange.class, it))
			}
		}
		if(log.isDebugEnabled())
			log.debug("abstract.correctiveStatusChanges end statusChanges:"+correctiveStatusChanges)
		return correctiveStatusChanges
	}

	public Set<PreventiveOrderStatus> getPreventiveStatus(){
		if(log.isDebugEnabled()) log.debug("abstract.preventiveStatus params:"+params)
		Set<PreventiveOrderStatus> preventiveStatus = new HashSet<PreventiveOrderStatus>()
		if (params.list('workOrderStatus') != null && !params.list('workOrderStatus').empty) {
			def types = params.list('workOrderStatus')
			types.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.preventiveStatus preventiveStatus:"+it)
				if(it != null) preventiveStatus.add(Enum.valueOf(PreventiveOrderStatus.class, it))
			}
		}
		return preventiveStatus
	}
	public List<PreventionResponsible> getPreventionResponsible(String preventionResponsibleParam){
		if(log.isDebugEnabled()) log.debug("abstract.PreventionResponsible params:"+params)
		List<PreventionResponsible> preventionResponsible = []
		if (params.list(preventionResponsibleParam) != null && !params.list(preventionResponsibleParam).empty) {
			def types = params.list(preventionResponsibleParam)
			types.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.PreventionResponsible PreventionResponsible:"+it)
				if(it != null) preventionResponsible.add(Enum.valueOf(PreventionResponsible.class, it))
			}
		}
		return preventionResponsible
	}

	public Set<StatusOfSparePart> getSparePartStatus(){
		if(log.isDebugEnabled()) log.debug("abstract.sparePartStatus params:"+params)
		Set<StatusOfSparePart> sparePartStatus = new HashSet<StatusOfSparePart>()
		if (params.list('sparePartStatus') != null && !params.list('sparePartStatus').empty) {
			def types = params.list('sparePartStatus')
			types.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.sparePartStatus sparePartStatus:"+it)
				if(it != null) sparePartStatus.add(Enum.valueOf(StatusOfSparePart.class, it))
			}
		}
		return sparePartStatus
	}

	/*public List<StatusOfSparePartChange> getSparePartStatusChanges(){
		List<StatusOfSparePartChange> sparePartStatusChanges = []
		if(log.isDebugEnabled()) log.debug("abstract.sparePartStatusChanges start params:"+params)
		if (params.list('statusChanges') != null && !params.list('statusChanges').empty) {
			def statusChanges = params.list('statusChanges')
			if(log.isDebugEnabled()) log.debug("abstract.sparePartStatusChanges statusChanges:"+statusChanges)
			statusChanges.each { it ->
				if(log.isDebugEnabled()) log.debug("abstract.sparePartStatusChanges statusChange:"+it)
				if(it != null) sparePartStatusChanges.add(it)
			}
		}
		if(log.isDebugEnabled()) log.debug("abstract.sparePartStatusChanges end statusChanges:"+sparePartStatusChanges)
		return sparePartStatusChanges
	}*/

	public Set<String> getReportTypeOptions(String reportTypeOptionParam){
		if(log.isDebugEnabled()) log.debug("abstract.reportTypeOptions params:"+params)
		Set<String> reportTypeOptions = new HashSet<String>()
		if (params.list(reportTypeOptionParam) != null && !params.list(reportTypeOptionParam).empty) {
			def options = params.list(reportTypeOptionParam)
			options.each{ it ->
				if(log.isDebugEnabled()) log.debug("abstract.reportTypeOption reportTypeOption:"+it)
				if(it != null) reportTypeOptions.add(it)
			}
		}
		return reportTypeOptions
	}
	
	// customized report wizard params end
	
	// customized report wizard end
}
