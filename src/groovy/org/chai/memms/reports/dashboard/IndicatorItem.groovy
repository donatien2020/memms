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
package org.chai.memms.reports.dashboard

import org.chai.memms.reports.dashboard.LocationReport;
import org.chai.memms.reports.dashboard.Indicator;
import org.chai.memms.reports.dashboard.IndicatorValue;
import org.chai.memms.reports.dashboard.MemmsReport;
import org.chai.memms.reports.dashboard.GroupIndicatorValue;
import org.chai.location.CalculationLocation
import org.chai.location.DataLocationType
import org.chai.location.DataLocation
import org.chai.location.Location
import org.chai.location.LocationLevel
import java.util.*
import org.chai.memms.security.User
import org.apache.shiro.SecurityUtils
import java.util.Collections;
import org.joda.time.DateTime
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class IndicatorItem {

    IndicatorComputationService indicatorComputationService
    Long id
    String facilityName
    String categoryCode
    Date dateTime
    String code
    String name
    String groupName
    String formula
    Double value
    String unit
    String color
    Double rankCounter=0
    List<HistoricalValueItem> historicalValueItems
    List<ComparisonValueItem> highestComparisonValueItems
    List<ComparisonValueItem> higherComparisonValueItems
    Integer itemRank
    List<ComparisonValueItem> lowerComparisonValueItems
    List<ComparisonValueItem> lowestComparisonValueItems
    List<GeographicalValueItem> geographicalValueItems
    Map<String, Double> valuesPerGroup
    Integer totalHistoryItems
    
    public IndicatorItem(){}

    public IndicatorItem(IndicatorValue iv) {
        this.id = iv.id
        this.categoryCode = iv.indicator.category.code
        this.dateTime = iv.computedAt
        this.code = iv.indicator.code
        this.name = iv.indicator.names
        this.formula = iv.indicator.formula
        this.value = iv.computedValue
        this.unit = iv.indicator.unit
        this.groupName = iv.indicator.groupName
        this.totalHistoryItems = iv.indicator.historyItems
        this.facilityName = iv.locationReport.location.names
        Double red = iv.indicator.redToYellowThreshold
        Double green =  iv.indicator.yellowToGreenThreshold
        if(red < green) {
            if(this.value < red) {
                this.color = "red"
            } else if(this.value < green) {
                this.color = "yellow"
            } else {
                this.color = "green"
            }
        } else {
            if(this.value > red) {
                this.color = "red"
            } else if(this.value > green) {
                this.color = "yellow"
            } else {
                this.color = "green"
            }
        }
        this.historicalValueItems = new ArrayList<HistoricalValueItem>()
        this.geographicalValueItems = new ArrayList<GeographicalValueItem>()
        this.valuesPerGroup=new HashMap<String,Double>()
        //####adding historical values
        for(IndicatorValue indV :  getHistoricValueItems(iv)) {
            this.historicalValueItems.add(new HistoricalValueItem(indV))
        }
        //#### adding geographical values
        for(IndicatorValue indV : getGeographicalValueItems(iv)) {
            this.geographicalValueItems.add(new GeographicalValueItem(indV))
        }
        //###adding comparison value items
        makeComparisonValueItems(iv)
        //## adding groupValues
        for(GroupIndicatorValue grV:iv.groupIndicatorValues){
            this.valuesPerGroup.put(grV.name,grV.value)
        }
    }
    /**
     *Get historical values for this indicators = for diferent location reports
     */
    public def getHistoricValueItems(IndicatorValue iv) {
        if(iv != null) {
            DateTime now = DateTime.now()
            def monthCounter = now.getMonthOfYear()
            def historicalPeriodsConditions = "" + now.getMonthOfYear()
            def historicalPeriods = 0
            if(iv.indicator.historicalPeriod.equals(Indicator.HistoricalPeriod.YEARLY)) {
                historicalPeriods = 12
            } else if(iv.indicator.historicalPeriod.equals(Indicator.HistoricalPeriod.QUARTERLY)) {
                historicalPeriods = 3
            }
            def periodCpounter=historicalPeriods            
            for(int i = 0; i < 12; i++) {
                periodCpounter--
                monthCounter--
                if(periodCpounter == 0) {
                    historicalPeriodsConditions = historicalPeriodsConditions + ", " + monthCounter
                    periodCpounter = historicalPeriods
                }
                if(monthCounter == 1) {
                    monthCounter=12
                }
            }
            def locationReports = null
            if(iv.indicator.historicalPeriod.equals(Indicator.HistoricalPeriod.MONTHLY)) {
                locationReports = LocationReport.findAll("from LocationReport as locationReport  where locationReport.location.id='"+iv.locationReport.location.id+"' order by locationReport.generatedAt desc limit "+iv.indicator.historyItems+"")
            } else {
                locationReports = LocationReport.findAll("from LocationReport as locationReport where month(locationReport.generatedAt) in ("+historicalPeriodsConditions+") and locationReport.location.id='"+iv.locationReport.location.id+"' order by locationReport.generatedAt desc limit "+iv.indicator.historyItems+"")
            }
            return IndicatorValue.findAllByLocationReportInListAndIndicator(locationReports,iv.indicator)
        }
        return null
    }

    public void makeComparisonValueItems(IndicatorValue currentValue) {
        this.highestComparisonValueItems = new ArrayList<ComparisonValueItem>()
        this.higherComparisonValueItems = new ArrayList<ComparisonValueItem>()
        this.itemRank = -1
        this.lowerComparisonValueItems = new ArrayList<ComparisonValueItem>()
        this.lowestComparisonValueItems = new ArrayList<ComparisonValueItem>()
        List<IndicatorValue> result = getComparisonValues(currentValue)
        if(!result.isEmpty()) {
            int i = 0;
            int n = result.size();
            for(IndicatorValue v : result) {
                if(v.id == currentValue.id) {
                    this.itemRank = i
                    break;
                }
                i++;
            }
            for(i = 0; (i < 3); i++) {
                if (i < (this.itemRank - 3)) {
                    this.highestComparisonValueItems.add(new ComparisonValueItem(result.get(i), i))
                }
            }
            for(i = (this.itemRank - 3); (i < this.itemRank); i++) {
                if (i >= 0) {
                    this.higherComparisonValueItems.add(new ComparisonValueItem(result.get(i), i))
                }
            }
            for(i = (this.itemRank + 1); (i < this.itemRank + 4); i++) {
                if (i < n) {
                    this.lowerComparisonValueItems.add(new ComparisonValueItem(result.get(i), i))
                }
            }
            for(i = (n - 3); (i < n); i++) {
                if (i > (this.itemRank + 3)) {
                    this.lowestComparisonValueItems.add(new ComparisonValueItem(result.get(i), i))
                }
            }
        }
    }
 
    public def getComparisonValues(IndicatorValue indicatorValue){
        def locations = null
        if(indicatorValue.locationReport.location instanceof DataLocation){
            locations = DataLocation.findAllByType(indicatorValue.locationReport.location.type)
        } else if(indicatorValue.locationReport.location instanceof Location){
            locations = Location.findAllByLevel(indicatorValue.locationReport.location.level)
        }
        def locationReports = LocationReport.findAllByMemmsReportAndLocationInList(indicatorValue.locationReport.memmsReport,locations)
        def sortOrder = (indicatorValue.indicator.redToYellowThreshold < indicatorValue.indicator.yellowToGreenThreshold)?"desc":"asc"
        return IndicatorValue.findAllByLocationReportInListAndIndicator(locationReports,indicatorValue.indicator,[sort: 'computedValue', order:sortOrder])
    }
    /**
     *
     *Gets geographical values for the current report
     **/
    public def getGeographicalValueItems(IndicatorValue indicatorValue){
        if(indicatorValue!=null){
            def  geographicalLocations=getDataLocationsInLocation(indicatorValue.locationReport.location)
            def locationReports=LocationReport.findAllByMemmsReportAndLocationInList(indicatorValue.locationReport.memmsReport,geographicalLocations)
            return IndicatorValue.findAllByLocationReportInListAndIndicator(locationReports,indicatorValue.indicator)
        }
        return null
    }
    /**
     *Gets  facilities or locations similar to the curent facility/location from this indicator value
     */
    public def getSimilarFacilitiesOrlocations(IndicatorValue indicatorValue){
        def  simmilarFacilitiesOrLocations=null
        def locationIds=getLocationsIdWithUsers()
        if(indicatorValue!=null){
            if(indicatorValue.locationReport.location instanceof DataLocation){
                simmilarFacilitiesOrLocations=DataLocation.findAllByTypeAndIdInList(indicatorValue.locationReport.location.type,locationIds)
            } else if(indicatorValue.locationReport.location instanceof Location){
                simmilarFacilitiesOrLocations=Location.findAllByLevelAndIdInList(indicatorValue.locationReport.location.level,locationIds)
            }
        }
        return simmilarFacilitiesOrLocations
    }

    public def getDataLocationsInLocation(CalculationLocation location) {
        List<DataLocation> dataLocations = new ArrayList<DataLocation>()
        if (location instanceof Location) {
            dataLocations = location.getDataLocations(LocationLevel.findAll(), null)
            dataLocations.addAll(location.getChildren(null))
        } else if (location instanceof DataLocation) {
            dataLocations.add(location)
            dataLocations.addAll(location.manages)
        }
        return dataLocations;
    }

    public def getLocationsIdWithUsers(){
        List<Long> locations=new ArrayList<Long>();
        for(User user:User.findAll()){
            if(user.location!=null)
            locations.add(user.location.id)
        }
        return locations
    }

    public def historicalTrendVAxisFormat() {
        if(unit == '%') {
            return '0%';
        }
        return '###,##0 ' + unit;
    }

    public def historicalTrendMaxValue() {
        if(unit == '%') {
            return ', maxValue: 1';
        }
        return '';
    }

    public def historicalTrendLineCount() {
        if(unit == '%') {
            return ', count: 5';
        }
        return ', count: -1';
    }

    public def historicalTrendData() {
        def ret = []
        def i = 0
        def fmt = org.joda.time.format.DateTimeFormat.forPattern("MMM d, y")
        for(HistoricalValueItem h: historicalValueItems) {
            ret[i] = ["\""+fmt.print(h.dateTime.time)+"\"",h.value]
            i++
        }
        if(i == 0) {
            ret = null
        }
        return ret
    }

    public geoData() {
        def ret = [["\'LATITUDE\'", "\'LONGITUDE\'", "\'LOCATION\'", "\'Value\'"]]
        def i = 1
        for(GeographicalValueItem geo: geographicalValueItems) {
            if((geo.latitude != null) && (geo.longitude != null) &&(geo.latitude != 0.0) && (geo.longitude != 0.0)) {
                ret[i] = geo.geoDataRow()
                i++;
            }
        }
        return ret
    }
    
    public hasGeoData() {
        if(geographicalValueItems == null) {
            return false
        }
        if(geographicalValueItems.isEmpty()) {
            return false
        }
        for(GeographicalValueItem geo: geographicalValueItems) {
            if((geo.latitude != null) && (geo.longitude != null) &&(geo.latitude != 0.0) && (geo.longitude != 0.0)) {
                return true
            }
        }
        return false
    }

    public geoValues() {
        Map<Double,String> map = new TreeMap<Double,String>()
        for(GeographicalValueItem geo: geographicalValueItems) {
            map.put(geo.value,geo.color)
        }
        def ret = []
        int i = 0
        for(Double val:map.keySet()) {
            ret[i++] = val
        }
        return ret
    }

    public geoColors() {
        Map<Double,String> map = new TreeMap<Double,String>()
        for(GeographicalValueItem geo: geographicalValueItems) {
            map.put(geo.value,geo.color)
        }
        def ret = []
        int i = 0
        for(Double val:map.keySet()) {
            ret[i++] = "\'"+map.get(val)+"\'"
        }
        return ret
    }

    public def geoChartValueFormat() {
        if(unit == '%') {
            return '0%';
        }
        return '###,##0 ' + unit;
    }
}