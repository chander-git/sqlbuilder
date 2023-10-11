
package bitcomm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bitcomm.sqlbuilder.operations.InvalidSqlException;
import bitcomm.sqlbuilder.operations.apis.SelectQuery;
import bitcomm.sqlbuilder.psql.PsqlSelectQuery;



public class App
{
    private static  Logger log = LogManager.getLogger();
 
    
    public static String version="3.3.5";
    private App(String page) {
	
	System.out.println("App constructor page : "+page);
    }
    public static void main(String[] args) 
    {
	log.info("Sql Builder App runuing..");

	
//	historyFilter(UUID.fromString("a1430aa7-9830-4a2d-a556-88ab54d64571"), 1690548894000L, null, null, version, null, null);
    }
    
    private static void func(StringBuilder charSequence) {
	
	try {
	  Class<App> clazz=   (Class<App>) Class.forName("bitcomm.App");
	  
	  try {
	      
	      try {
		  Class<?>[] empty = {"".getClass()};
		Constructor<App> cons = clazz.getConstructor(empty);
		try {
		  App obj =(App) cons.newInstance("s");
		  obj.historyFilter(null, null, null, null, version, null, null);
		  
		} catch (IllegalArgumentException | InvocationTargetException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    } catch (NoSuchMethodException | SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
//	      App app = (App)clazz.newInstance();
	      
//	      app.historyFilter(null, null, null, null, version, null, null);
	      
	      
	      
	} catch (InstantiationException | IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
    }
    public static void  historyFilter(UUID deviceId, Long startTS, Long endTs, List<String> keys,
	    String dataSubType, Integer page, Integer pageSize){


	SelectQuery selectQuery=new PsqlSelectQuery();

	
	try {
	    selectQuery.select("d.name")
	    .from("device d")
	    .innerJoin("device_type dt")
	    .on("d.typeId", "dt.typeid")
	    .innerJoin("customer c")
	    .on("d.customer_id", "c.customerid")
	    .where("d.deviceId").equalTo(deviceId)

	    .where("d.name")
	    .like("NOQ")
	    //	.or()
	    //	.where("c.stncode").equalTo("MSV")

	    .where("d.updated_ts")
	    .between(null, null)
	    .limit(10)
	    .offset(20L)
	    .where("d.name").like(null)

	    .where("d.additionaldetail #>> '{remarks}'")
	    .equalTo("None")
	    .groupBy("d.updated_ts")
	    .where("d.name")
	    .ilike("APDJ".toLowerCase())
	    .or()
	    .where("  d.deviceid").equalTo(
		    new PsqlSelectQuery().selectDistinct("d2.deviceid")
		    .from("device d2").where("d2.deviceid").equalTo("a1430aa7-9830-4a2d-a556-88ab54d64571")
		    )  

	    .orderByDesc("d.updated_ts")
	    .groupBy("d.name")
	    .setPageRange(40, 0);
	} catch (InvalidSqlException e) {
	    e.printStackTrace();
	}



	String sqlString = selectQuery.toString();

	String long1= selectQuery.countQuery();

	System.out.println(long1);

	System.out.println(sqlString);

    }
}
