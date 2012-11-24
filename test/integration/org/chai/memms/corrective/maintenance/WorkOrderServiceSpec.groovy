package org.chai.memms.corrective.maintenance

import java.util.Date;
import java.util.Map;

import org.chai.location.CalculationLocation;
import org.chai.location.DataLocation
import org.chai.location.Location
import org.chai.memms.Initializer;
import org.chai.memms.IntegrationTests
import org.chai.memms.inventory.Equipment;
import org.chai.memms.corrective.maintenance.WorkOrder.Criticality;
import org.chai.memms.corrective.maintenance.WorkOrder.FailureReason;
import org.chai.memms.corrective.maintenance.WorkOrderStatus.OrderStatus;
import org.chai.memms.security.User;
import org.chai.memms.security.User.UserType;

/**
 * @author Eugene
 *
 */
class WorkOrderServiceSpec  extends IntegrationTests{
	
	def workOrderService
	def notificationWorkOrderService
	
	def "can search a work order"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def senderOne = newUser("senderOne", true,true)
		Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,senderOne, Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		
		when:
		
		//Search by description
		def workOrdersPassesDescription = workOrderService.searchWorkOrder("Nothing yet",null,null,adaptParamsForList())
		def workOrdersFailsDescription = workOrderService.searchWorkOrder("fails",null,null,adaptParamsForList())
		
		//search by DataLocation
		def workOrdersPassesDataLocation = workOrderService.searchWorkOrder("Nothing",DataLocation.findByCode(KIVUYE),null,[:])
		def workOrdersFailsDataLocation = workOrderService.searchWorkOrder("Nothing",DataLocation.findByCode(BUTARO),null,[:])
		
		//Search by Equipment
		def workOrdersPassesEquipment = workOrderService.searchWorkOrder("Nothing",null,equipment,[:])
		
		//Search by equipment serial number
		def workOrdersPassesEquipmentSerialnumber = workOrderService.searchWorkOrder(CODE(123),null,null,adaptParamsForList())
		
		//Search by equipment type
		def workOrdersPassesEquipmentType = workOrderService.searchWorkOrder("acce",null,null,adaptParamsForList())
		then:
		workOrdersFailsDescription.size() == 0
		workOrdersPassesDescription.size() == 1
		
		workOrdersFailsDataLocation.size() == 0
		workOrdersPassesDataLocation.size() == 1
		
		workOrdersPassesEquipment.size() == 1
		
		workOrdersPassesEquipmentSerialnumber.size() == 1
		
		workOrdersPassesEquipmentType.size() == 1
	}
	
	def "can filter workOrders"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def senderOne = newUser("senderOne", true,true)		
		Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,senderOne, Initializer.getDate(12, 9,2012),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.LOW,senderOne, Initializer.getDate(12, 9,2012),Initializer.getDate(18, 9,2012),FailureReason.NOTSPECIFIED,OrderStatus.CLOSEDFIXED)		
		when:
		def kivuye = DataLocation.findByCode(KIVUYE)
		def musanze = DataLocation.findByCode(MUSANZE)
		//Filter by DataLocation
		def workOrdersPassesDataLocation = workOrderService.filterWorkOrders(kivuye,null,null,null,null,null,[:])
		def workOrdersFailsDataLocation = workOrderService.filterWorkOrders(musanze,null,null,null,null,null,[:])
		//Filter by Equipment
		def workOrdersEquipment = workOrderService.filterWorkOrders(null,equipment,null,null,null,null,[:])
		
		//Filter by openOn
		def workOrdersopenOn = workOrderService.filterWorkOrders(null,null,Initializer.getDate(12, 9,2012),null,null,null,[:])
		
		//Filter by closedOn
		def workOrdersclosedOn = workOrderService.filterWorkOrders(null,null,null,Initializer.getDate(18, 9,2012),null,null,[:])
		
		//Filter by criticality
		def workOrdersCriticality = workOrderService.filterWorkOrders(null,null,null,null,Criticality.LOW,null,[:])
		
		//Filter by status
		def workOrdersStatus = workOrderService.filterWorkOrders(null,null,null,null,null,OrderStatus.OPENATFOSA,[:])
		
		then:
		workOrdersPassesDataLocation.size() == 2
		workOrdersFailsDataLocation.size() == 0
		
		workOrdersEquipment.size() == 2
		
		workOrdersopenOn.size() == 2
		
		workOrdersclosedOn.size() == 1
				
		workOrdersCriticality.size() == 1
		
		workOrdersStatus.size() == 1
	}
	
	def "can get WorkOrders By Equipment"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def clerk = newUser("clerk", true,true)
		clerk.userType = UserType.TITULAIREHC
		clerk.location = DataLocation.findByCode(KIVUYE)
		clerk.save(failOnError:true)
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def workOrder = Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,clerk,Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		notificationWorkOrderService.newNotification(workOrder, "Send for rapair",clerk,false)
		when:
		def equipments = workOrderService.getWorkOrdersByEquipment(equipment,[:])
		then:
		equipments.size() == 1
	}
	
	def "can get escalated WorkOrders - for Calculation locations that a user can access"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def sender = newOtherUser("sender", "sender", DataLocation.findByCode(KIVUYE))
		sender.userType = UserType.TITULAIREHC
		sender.save(failOnError:true)
		
		def senderWrong = newOtherUser("senderWrong", "senderWrong", DataLocation.findByCode(GITWE))
		senderWrong.userType = UserType.TITULAIREHC
		senderWrong.save(failOnError:true)
		
		def techdh = newOtherUser("receiverOne", "receiverOne", DataLocation.findByCode(BUTARO))
		techdh.userType = UserType.TECHNICIANDH
		techdh.save(failOnError:true)
		
		def techdhWrong = newOtherUser("techdhWrong", "techdhWrong", DataLocation.findByCode(MUSANZE))
		techdhWrong.userType = UserType.TECHNICIANDH
		techdhWrong.save(failOnError:true)
		
		def admin = newOtherUser("admin", "admin", Location.findByCode(RWANDA) )
		admin.userType = UserType.ADMIN
		admin.save(failOnError:true)
		
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def workOrderOne = Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,sender,Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		def workOrder = Initializer.newWorkOrder(equipment, "Nothing yet, not even after escalations", Criticality.NORMAL,sender,Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		when:
		def escalatedOneFalse = workOrderService.getEscalatedWorkOrders(sender,[:])
		def escalatedTwoFalse = workOrderService.getEscalatedWorkOrders(senderWrong,[:])
		def escalatedThreeFalse = workOrderService.getEscalatedWorkOrders(techdh,[:])
		def escalatedFourFalse = workOrderService.getEscalatedWorkOrders(techdhWrong,[:])
		def escalatedFiveFalse = workOrderService.getEscalatedWorkOrders(admin,[:])
		then:
		WorkOrder.count() == 2
		escalatedOneFalse.size() == 0
		escalatedTwoFalse.size() == 0
		escalatedThreeFalse.size() == 0
		escalatedFourFalse.size() == 0
		escalatedFiveFalse.size() == 0
		
		when:
		workOrderService.escalateWorkOrder(workOrderOne,"escalate this", techdh)
		def escalatedOneTrue = workOrderService.getEscalatedWorkOrders(sender,[:])
		def escalatedTwoTrue = workOrderService.getEscalatedWorkOrders(senderWrong,[:])
		def escalatedThreeTrue = workOrderService.getEscalatedWorkOrders(techdh,[:])
		def escalatedFourTrue = workOrderService.getEscalatedWorkOrders(techdhWrong,[:])
		def escalatedFiveTrue = workOrderService.getEscalatedWorkOrders(admin,[:])
		then:
		WorkOrder.count() == 2
		escalatedOneTrue.size() == 1
		escalatedTwoTrue.size() == 0
		escalatedThreeTrue.size() == 1
		escalatedFourTrue.size() == 0
		escalatedFiveTrue.size() == 1
	}
	
	def "can get workOrders by calculationLocation"(){
		setup:
		setupLocationTree()
		def equipment = newEquipment(CODE(123),DataLocation.findByCode(KIVUYE))
		def senderOne = newUser("senderOne", true,true)
		Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,senderOne, Initializer.getDate(12, 9,2012),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.LOW,senderOne, Initializer.getDate(12, 9,2012),Initializer.getDate(18, 9,2012),FailureReason.NOTSPECIFIED,OrderStatus.CLOSEDFIXED)
		when:
		def workOrdersPassesDataLocation = workOrderService.getWorkOrdersByCalculationLocation(DataLocation.findByCode(KIVUYE),[:])
		def workOrdersFailsDataLocation = workOrderService.getWorkOrdersByCalculationLocation(CalculationLocation.findByCode(BURERA),[:])
		
		then:
		workOrdersPassesDataLocation.size() == 2
		workOrdersFailsDataLocation.size() == 0
	}
}
