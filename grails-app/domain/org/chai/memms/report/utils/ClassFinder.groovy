package org.chai.memms.report.utils

class ClassFinder {
	
	//public static final String[] searchPackages = {"my.company","my.company.other" };
	
	  public Class findClassByName(String className) {
		  Class classname = null
		  try{
			classname = Class.forName('org.chai.memms.inventory.'+className, true, Thread.currentThread().contextClassLoader)
		  } catch (ClassNotFoundException e){
			e.printStackTrace()
		  e.printStackTrace()
		  } catch (Exception e){
			//deal with other problems...
		  e.printStackTrace()
		  }
		
		//nothing found: return null or throw ClassNotFoundException
		return classname;
	  }
}
