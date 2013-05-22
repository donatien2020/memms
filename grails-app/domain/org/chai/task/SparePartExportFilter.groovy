/**
 * 
 */
package org.chai.task

import org.chai.memms.spare.part.SparePart.SparePartStatus;
import org.chai.memms.spare.part.SparePartType;
import org.chai.memms.inventory.Provider;
import groovy.transform.EqualsAndHashCode;
import java.util.Map;
import java.util.Set;
import org.chai.memms.task.Exporter;
import org.chai.memms.util.Utils;

/**
 * @author Aphrodice Rwagaju
 *
 */
@EqualsAndHashCode(includes='id')
class SparePartExportFilter extends ExportFilter{
	SparePartStatus sparePartStatus
	
	static hasMany = [sparePartTypes:SparePartType,manufacturers:Provider,suppliers:Provider]
	
	static constraints = {
		sparePartStatus nullable: true
	}
	static mapping = {
		table "memms_spare_part_export_filter"
		version false
		sparePartTypes joinTable:[name:"memms_spare_part_spare_part_type_export_filter",key:"type_id",column:"spare_part_export_filter_id"]
		manufacturers joinTable:[name:"memms_spare_part_manufacturer_export_filter",key:"manufacturer_id",column:"spare_part_export_filter_id"]
		suppliers joinTable:[name:"memms_spare_part_supplier_export_filter",key:"supplier_id",column:"spare_part_export_filter_id"]
		}

}
