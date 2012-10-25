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
package org.chai.memms.corrective.maintenance

import org.chai.location.CalculationLocation;
import java.util.Date;
import java.util.Map;
import org.chai.location.DataLocation;
import org.chai.location.Location;
import org.chai.memms.AbstractEntityController;
import org.chai.memms.corrective.maintenance.Comment;
import org.chai.memms.corrective.maintenance.MaintenanceProcess;
import org.chai.memms.corrective.maintenance.WorkOrder;
import org.chai.memms.corrective.maintenance.WorkOrderStatus;
import org.chai.memms.inventory.Equipment;
import org.chai.memms.inventory.EquipmentStatus;
import org.chai.memms.inventory.EquipmentType;
import org.chai.memms.inventory.Provider;
import org.chai.memms.inventory.EquipmentStatus.Status;
import org.chai.memms.corrective.maintenance.MaintenanceProcess.ProcessType;
import org.chai.memms.corrective.maintenance.WorkOrder.Criticality;
import org.chai.memms.corrective.maintenance.WorkOrder.FailureReason;
import org.chai.memms.corrective.maintenance.WorkOrderStatus.OrderStatus;
import org.chai.memms.security.User;
import org.chai.memms.security.User.UserType;


/**
 * @author Jean Kahigiso M.
 *
 */
class WorkOrderController extends AbstractEntityController{
	
	def workOrderService
	def grailsApplication
	def correctiveMaintenanceService
	def locationService
	def equipmentStatusService
	def workOrderStatusService
	def commentService
	def maintenanceProcessService
	def workOrderNotificationService
	def userService

	def getEntity(def id) {
		return WorkOrder.get(id)
	}

	def createEntity() {
		return new WorkOrder();
	}

	def getModel(entity) {
		def equipments =  []
		if(entity.equipment) equipments << entity.equipment
		[
			order:entity,
			equipments: equipments,
			currencies: grailsApplication.config.site.possible.currency,
			orderClosed:(entity.currentStatus == OrderStatus.CLOSEDFIXED || entity.currentStatus == OrderStatus.CLOSEDFORDISPOSAL)? true:false,
			//entity can be null
			technicians : userService.getActiveUserByTypeAndLocation(UserType.TECHNICIANFACILITY,entity.equipment?.dataLocation, [:])
		]
	}

	def bindParams(def entity) {
		if(!entity.id){
			entity.addedBy = user
			entity.openOn = now
			entity.failureReason = FailureReason.NOTSPECIFIED
			entity.currentStatus = OrderStatus.OPENATFOSA
		}else{
			entity.lastModifiedOn = now
			entity.lastModifiedBy = user
			if(entity.currentStatus == OrderStatus.CLOSEDFIXED || entity.currentStatus == OrderStatus.CLOSEDFORDISPOSAL)
				entity.closedOn = now
			else entity.closedOn = null
			
		}
		params.oldStatus = entity.currentStatus
		entity.properties = params
	}
		
	def saveEntity(def entity) {
		def currentEquipmentStatus
		def currentWorkOrderStatus
		def newEntity = false
		def escalation = false
		def users = []
		
		//Change Equipment Status and Create first workOrderStatus to the new workOrder
		if(entity.id==null){
			newEntity=true
			currentEquipmentStatus = equipmentStatusService.createEquipmentStatus(now,user,Status.UNDERMAINTENANCE,entity.equipment,true,now,[:])
			currentWorkOrderStatus = workOrderStatusService.createWorkOrderStatus(entity,OrderStatus.OPENATFOSA,user,now,escalation)
		}else{
			if(log.isDebugEnabled()) log.debug("Old status stored in params: "+params.oldStatus)
			//If status has be changed
			if(entity.currentStatus != params.oldStatus){
				//Escalate
				if(entity.currentStatus == OrderStatus.OPENATMMC && params.oldStatus == OrderStatus.OPENATFOSA) escalation = true			
				//Change Equipment Status When closing workorder
				if(entity.currentStatus == OrderStatus.CLOSEDFIXED)
					currentEquipmentStatus = equipmentStatusService.createEquipmentStatus(now,user,Status.OPERATIONAL,entity.equipment,true,now,[:])
				if(entity.currentStatus == OrderStatus.CLOSEDFORDISPOSAL)
					currentEquipmentStatus = equipmentStatusService.createEquipmentStatus(now,user,Status.FORDISPOSAL,entity.equipment,true,now,[:])
				currentWorkOrderStatus = workOrderStatusService.createWorkOrderStatus(entity,entity.currentStatus,user,now,escalation)
			}
		}
		
		entity.save(failOnError: true)
		if(log.isDebugEnabled()) log.debug("Created WorkOrder: "+entity)
		if(newEntity || escalation){ //TODO define default message
			users = userService.getNotificationGroup(entity,user,escalation)
			workOrderNotificationService.sendNotifications(entity,message(code:"workorder.creation.default.message"),user,users)
		}
		(!currentEquipmentStatus)?:currentEquipmentStatus.save(flush:true)
		(!currentWorkOrderStatus)?:currentWorkOrderStatus.save(flush:true)
		
	}

	def getTemplate() {
		return "/entity/workOrder/createWorkOrder";
	}

	def getLabel() {
		return "work.order.label";
	}

	def getEntityClass() {
		return WorkOrder.class;
	}

	def list = {
		adaptParamsForList()
		List<WorkOrder> orders= []
		Equipment equipment = null
		CalculationLocation  location = null
		if(params["dataLocation.id"]) location = CalculationLocation.get(params.int("dataLocation.id"))
		if(params["equipment.id"]) equipment = Equipment.get(params.int("equipment.id"))
		
		if(location)
			orders = workOrderService.getWorkOrdersByCalculationLocation(location,params)	
		if(equipment)
		 	orders= workOrderService.getWorkOrdersByEquipment(equipment,params)

		render(view:"/entity/list", model:[
					template:"workOrder/workOrderList",
					filterTemplate:"workOrder/workOrderFilter",
					entities: orders,
					entityCount: orders.totalCount,
					code: getLabel(),
					entityClass: getEntityClass(),
					equipment:equipment,
					dataLocation:location
				])
	}

	def summaryPage = {
		if(user.location instanceof DataLocation) redirect (controller: "workOrder", action: "list",params:['dataLocation.id':user.location.id])

		def location = Location.get(params.int('location'))
		def dataLocationTypesFilter = getLocationTypes()
		def template = null
		def correctiveMaintenances = null

		adaptParamsForList()

		def locationSkipLevels = correctiveMaintenanceService.getSkipLocationLevels()

		if (location != null) {
			template = '/correctiveMaintenance/sectionTable'
			correctiveMaintenances = correctiveMaintenanceService.getCorrectiveMaintenancesByLocation(location,dataLocationTypesFilter,params)
		}
		render (view: '/correctiveMaintenance/summaryPage', model: [
					correctiveMaintenances:correctiveMaintenances?.correctiveMaintenanceList,
					currentLocation: location,
					currentLocationTypes: dataLocationTypesFilter,
					template: template,
					entityCount: correctiveMaintenances?.totalCount,
					locationSkipLevels: locationSkipLevels,
					entityClass: getEntityClass()
				])
	}


	def addProcess = {
		WorkOrder order = WorkOrder.get(params.int("order.id"))
		def type = params["type"].toUpperCase()
		type = ProcessType."$type"
		def value = params["value"]
		def result = false
		def html =""
		if (order == null || type==null || value.equals(""))
			response.sendError(404)
		else {
				if (log.isDebugEnabled()) log.debug("addProcess params: "+params)
				maintenanceProcessService.addProcess(order,type,value,now,user)	
				if(order!=null){
					result=true
					def processes = (type==ProcessType.ACTION)? order.actions:order.materials
					html = g.render(template:"/templates/processList",model:[processes:processes,type:type.name])
				}
				render(contentType:"text/json") { results = [result,html,type.name] }
		}
	}

	def removeProcess = {
		MaintenanceProcess  process = MaintenanceProcess.get(params.int("process.id"))
		def result = false
		def html =""
		def type =null
		if(!process) response.sendError(404)
		else{
			type = process.type
			WorkOrder order = maintenanceProcessService.deleteProcess(process,now,user)
			result = true
			def processes = (type==ProcessType.ACTION)? order.actions:order.materials
			html = g.render(template:"/templates/processList",model:[processes:processes,type:type.name])
		}
		render(contentType:"text/json") { results = [result,html,type.name]}
	}
	
	def addComment ={
		WorkOrder order = WorkOrder.get(params.int("order.id"))
		def html =""
		def content = params["content"]
		def result = false
		if (order == null || content.equals("") )
			response.sendError(404)
		else {
			def comment = commentService.createComment(order,user, now,content)
			if(comment==null) response.sendError(404)
			else{ 
				order.addToComments(comment)
				order.lastModifiedOn = now
				order.lastModifiedBy = user
				order.save(flush:true)
				result=true
				html = g.render(template:"/templates/comments",model:[order:order])
			}
		}
		render(contentType:"text/json") { results = [result,html] }
	}

	
	def escalate = {
		WorkOrder order = WorkOrder.get(params.int("order"))
		def result = false
		def html = ""
		if (order == null)
			response.sendError(404)
		else {
			def equipment = order.equipment
			//TODO define default escalation message
			def content = "Please review work order on equipment serial number: ${order.equipment.code}"
			workOrderService.escalateWorkOrder(order, content, user)
			result=true 
			def orders= workOrderService.getWorkOrdersByEquipment(equipment,[:])
			html = g.render(template:"/entity/workorder/workOrderList",model:[equipment:equipment,entities:orders])
		}
		render(contentType:"text/json") { results = [result,html] }
	}

	def getWorkOrderClueTipsAjaxData = {
		def workOrder = WorkOrder.get(params.long("id"))
		def html = g.render(template:"/templates/workOrderClueTip",model:[workOrder:workOrder])
		render(contentType:"text/plain", text:html)
	}

	def removeComment = {
		Comment comment = Comment.get(params.int("comment.id"))
		WorkOrder order
		def html =""
		def result = false
		if(!comment) response.sendError(404)
		else{
			order = comment.workOrder
			order.comments.remove(comment)
			comment.delete()
			order.lastModifiedOn = now
			order.lastModifiedBy = user
			order.save(flush:true)
			result = true
			html = g.render(template:"/templates/comments",model:[order:order])
		}
		render(contentType:"text/json") { results = [result,html] }
	}
	
	def search = {
		adaptParamsForList()
		Equipment equipment = null
		DataLocation dataLocation = null
		if(params["equipment.id"]){
			equipment = Equipment.get(params.long("equipment.id"))
		}else if(params["dataLocation.id"]){
			dataLocation = DataLocation.get(params.long('dataLocation.id'))
		}
		List<WorkOrder> workOrders = workOrderService.searchWorkOrder(params['q'],dataLocation,equipment,params)
		render (view: '/entity/list', model:[
					template:"workOrder/workOrderList",
					filterTemplate:"workOrder/workOrderFilter",
					entities: workOrders,
					entityCount: workOrders.totalCount,
					code: getLabel(),
					equipment:equipment,
					dataLocation:dataLocation,
					q:params['q']
				])
	}

	def filter = { FilterWorkOrderCommand cmd ->
		if(log.isDebugEnabled()) log.debug(cmd)
		adaptParamsForList()
		List<WorkOrder> orders = workOrderService.filterWorkOrders(cmd.dataLocation,cmd.equipment,cmd.openOn,cmd.closedOn,cmd.criticality,cmd.currentStatus,params)

		render(view:"/entity/list", model:[
					template:"workOrder/workOrderList",
					filterTemplate:"workOrder/workOrderFilter",
					entities: orders,
					entityCount: orders.totalCount,
					code: getLabel(),
					equipment:cmd.equipment,
					dataLocation:cmd.dataLocation,
					entityClass: getEntityClass(),
					filterCmd:cmd
				])
	}
}

class FilterWorkOrderCommand {
	
	Date openOn
	Date closedOn
	Criticality criticality
	OrderStatus currentStatus
	DataLocation dataLocation
	Equipment equipment


	static constraints = {
		dataLocation  nullable:true
		equipment nullable:true
		openOn nullable:true
		closedOn nullable:true
		currentStatus nullable:true
		criticality nullable:true
	}

	String toString() {
		return "FilterCommand[OrderStatus="+currentStatus+", Criticality="+criticality+ 
		", closedOn="+closedOn+", openOn="+openOn+"]"
	}
}
