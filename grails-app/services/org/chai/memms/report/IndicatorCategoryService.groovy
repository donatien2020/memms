package org.chai.memms.report

import java.util.Map;
import org.chai.memms.util.Utils;
class IndicatorCategoryService {
	static transactional = true

	def sessionFactory


	def dataSource  //Auto Injected


	public void indicatorCategoryWritter(){

		if(!IndicatorCategory.count()){
			String indicatorCategoryFileContent = new File('web-app/resources/reportData/indicatorCategories.xml').text
			def categories = new XmlParser().parseText(indicatorCategoryFileContent)
			categories.category.each{

				Map<String,String> names=new HashMap<String,String>()
				names.put('en', it.name.text())
				names.put('fr', "To be added later")
				newIndicatorCategory(names,it.attribute("categoryCode"),it.minYellowValue.text(),it.maxYellowValue.text())

			}
		}else{
		println"already there"
		}


	}


	public  void newIndicatorCategory(Map<String,String> names,def code,def minYellowValue,def maxYellowValue){
		try{
			def category = new IndicatorCategory(code:code,minYellowValue:minYellowValue,maxYellowValue:maxYellowValue)
			Utils.setLocaleValueInMap(category,names,"Names")
			category.save(failOnError:true,flush:true)
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()

		}
	}


	public def searchIndicatorCategory(String text,Map<String, String> params) {
		text = text.trim()

		def criteria = IndicatorCategory.createCriteria()
		return criteria.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc"){

			ilike("code","%"+text+"%")
			ilike("name","%"+text+"%")


		}
	}
	def getIndicatorCategories(){
		return IndicatorCategory.findAll()
	}
}
