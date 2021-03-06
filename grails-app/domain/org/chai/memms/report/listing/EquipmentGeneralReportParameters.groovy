/**
 * 
 */
package org.chai.memms.report.listing

import groovy.transform.EqualsAndHashCode;

import java.util.Date;

import org.chai.memms.inventory.Department;
import org.chai.memms.inventory.EquipmentType;
import org.chai.memms.security.User;
import org.chai.location.DataLocation;
import org.chai.memms.util.Utils.ReportType;
import org.chai.memms.util.Utils.ReportSubType;

/**
 * @author Aphrodice Rwagaju
 *
 */
@EqualsAndHashCode(includes='id')
class EquipmentGeneralReportParameters {

	String reportName
	ReportType reportType
	ReportSubType reportSubType
	Date dateCreated
	Double lowerLimitCost
	Double upperLimitCost
	String currency
	Date fromDate
	Date toDate
	User savedBy
	boolean noCostSpecified
	String displayOptions
	
	static hasMany = [dataLocations:DataLocation,departments:Department,equipmentTypes:EquipmentType]
	static constraints = {
		dataLocations nullable: true, blank: true
		departments nullable: true, blank: true
		equipmentTypes nullable: true, blank: true
		lowerLimitCost nullable: true, blank: true
		upperLimitCost nullable: true, blank: true
		currency nullable: true, blank: true
		fromDate nullable: true, blank: true
		toDate nullable: true, blank: true
		savedBy nullable: false
		noCostSpecified nullable: false
		displayOptions nullable: false
	}

	static mapping = {
		table "memms_equipment_report_listing"
		version false
		dataLocations joinTable:[name:"memms_equipment_data_location_report_listing",key:"data_location_id",column:"report_listing_id"]
		departments joinTable:[name:"memms_equipment_department_report_listing",key:"department_id",column:"report_listing_id"]
		equipmentTypes joinTable:[name:"memms_equipment_type_report_listing",key:"equipment_type_id",column:"report_listing_id"]
	}
}
