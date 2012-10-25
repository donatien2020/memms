package org.chai.memms.corrective.maintenance

import java.util.Date;

import org.chai.memms.Initializer;
import org.chai.memms.IntegrationTests
import org.chai.memms.corrective.maintenance.MaintenanceProcess;
import org.chai.memms.inventory.Equipment;
import org.chai.memms.corrective.maintenance.MaintenanceProcess.ProcessType;
import org.chai.memms.corrective.maintenance.WorkOrder.Criticality;
import org.chai.memms.corrective.maintenance.WorkOrder.FailureReason;
import org.chai.memms.corrective.maintenance.WorkOrderStatus.OrderStatus;
import org.chai.memms.security.User;

class MaintenanceProcessServiceSpec  extends IntegrationTests{
	def maintenanceProcessService
	
	def "can create a maintenance process using create maintenance method"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def user = newUser("user", "user")
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def workOrder = Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,user, Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		when:
		def process = maintenanceProcessService.createProcess(workOrder,ProcessType.ACTION,"name test",Initializer.now(),user)
		process.save()
		then:
		MaintenanceProcess.count() == 1
	}
	def "can create a add maintenance process using create process method"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def user = newUser("user", "user")
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def workOrder = Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,user, Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		when:
		maintenanceProcessService.addProcess(workOrder,ProcessType.ACTION,"name test",Initializer.now(),user)
		then:
		MaintenanceProcess.count() == 1
	}
	
	def "can delete  a maintenance process using delete process method"(){
		setup:
		setupLocationTree()
		setupEquipment()
		def user = newUser("user", "user")
		def equipment = Equipment.findBySerialNumber(CODE(123))
		def workOrder = Initializer.newWorkOrder(equipment, "Nothing yet", Criticality.NORMAL,user, Initializer.now(),FailureReason.NOTSPECIFIED,OrderStatus.OPENATFOSA)
		maintenanceProcessService.addProcess(workOrder,ProcessType.ACTION,"name test",Initializer.now(),user)
		when:
		maintenanceProcessService.deleteProcess(workOrder.processes.asList()[0],Initializer.now(),user)
		then:
		MaintenanceProcess.count() == 0
	}
}