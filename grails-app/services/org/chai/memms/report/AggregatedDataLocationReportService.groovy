package org.chai.memms.report
import java.util.Set
import java.util.Map

import org.apache.commons.lang.math.NumberUtils
import org.springframework.dao.DataIntegrityViolationException
class AggregatedDataLocationReportService {
	static transactional = true

	def sessionFactory


	def dataSource
	def indicatorValueService

	public def getCorrectiveMaintenanceReports(){
		List<DataLocationReport> correctiveMainDatalocationReports=new ArrayList<DataLocationReport>()

		correctiveMainDatalocationReports.addAll(getDataLocationReportFromIndicatorValueBayIndicatorCategory("CORRECTIVE_MAINTENANCE"))
		//println" corective maintenance report categories :"+correctiveMainDatalocationReports.size()
		return correctiveMainDatalocationReports
	}

	public def getPreventiveMaintenanceReports(){
		List<DataLocationReport> preventiveMaintDatalocationReports=new ArrayList<DataLocationReport>()

		preventiveMaintDatalocationReports.addAll(getDataLocationReportFromIndicatorValueBayIndicatorCategory("PRIVENTIVE_MAINTENANCE"))

		return preventiveMaintDatalocationReports
	}
	public def getManageMemmsEquipmentReports(){
		List<DataLocationReport> mamageMedicalEquipmentDatalocationReports=new ArrayList<DataLocationReport>()

		mamageMedicalEquipmentDatalocationReports.addAll(getDataLocationReportFromIndicatorValueBayIndicatorCategory("MANAGEMENT_MEDICAL_EQUIPEMENT"))

		return mamageMedicalEquipmentDatalocationReports
	}

	public def getMemmsParePartReports(){
		List<DataLocationReport> mamageSparepartsDatalocationReports=new ArrayList<DataLocationReport>()

		mamageSparepartsDatalocationReports.addAll(getDataLocationReportFromIndicatorValueBayIndicatorCategory("MANAGEMENT_OF_SPARE_PARTS"))

		return mamageSparepartsDatalocationReports
	}
	public def getManageMemmsUseReports(){
		List<DataLocationReport> mamageMemmuseDatalocationReports=new ArrayList<DataLocationReport>()

		mamageMemmuseDatalocationReports.addAll(getDataLocationReportFromIndicatorValueBayIndicatorCategory("MANAGEMENT_MEMMS_USERS"))

		return mamageMemmuseDatalocationReports
	}

	public def getDataLocationReportFromIndicatorValueBayIndicatorCategory(String indicatorCategoryCode){
		Set<DataLocationReport> dataLocationReports=new HashSet<DataLocationReport>()
		def indicatorValues=indicatorValueService.getIndicatorValueByIndIcatorCategory(indicatorCategoryCode)
		//println"number of indicator values okooo :"+indicatorValues.size()
		for(IndicatorValue indicatorValue:indicatorValues){
			if(indicatorValue!=null)
				dataLocationReports.add(indicatorValue.dataLocationReport)
		}
		//println"list ok number of data locations in the set:"+dataLocationReports.size()
		return dataLocationReports.toList()
	}
}
