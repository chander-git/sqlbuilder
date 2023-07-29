
package bitcomm;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bitcomm.sqlbuilder.operations.apis.SelectQuery;
import bitcomm.sqlbuilder.psql.PsqlSelectQuery;

public class App
{
    private static  Logger log = LogManager.getLogger();
    public static String version="3.3.5";
    public static void main(String[] args) 
    {
	log.info("Sql Builder App runuing..");
	//	List<UUID> l = Arrays.asList(UUID.fromString("a1430aa7-9830-4a2d-a556-88ab54d64571"),UUID.fromString("a1430aa7-9830-4a2d-a556-88ab54d64571"));
	//	
	// String s = ListToCommaArray(l);
	//	
	historyFilter(UUID.fromString("a1430aa7-9830-4a2d-a556-88ab54d64571"), 1690548894000L, null, null, version, null, null);
    }
    public static void  historyFilter(UUID deviceId, Long startTS, Long endTs, List<String> keys,
	    String dataSubType, Integer page, Integer pageSize){


	SelectQuery selectQuery=new PsqlSelectQuery();

	selectQuery.select("d.name")
	.from("device d")
	.innerJoin("device_type dt")
	.on("d.typeId", "dt.typeid")
	.innerJoin("customer c")
	.on("d.customer_id", "c.customerid")
	.where("d.deviceId").equalTo(deviceId)
	.and()
	.where("d.name")
	.like("NOQ")
//	.or()
//	.where("c.stncode").equalTo("MSV")
	.and()
	.where("d.updated_ts")
	.between(null, null)
	.and()
	.where("d.name").like(null)
	.and()
	.where("d.additionaldetail #>> '{remarks}'")
	.equalTo("None")
	.and().where("d.name")
	.ilike("APDJ".toLowerCase())
	.orderByDesc("d.updated_ts")
	.groupBy("d.updated_ts")
	.groupBy("d.name");


	String sqlString = selectQuery.toString();
	
	String long1= selectQuery.countQuery();

	System.out.println(long1);

	System.out.println(sqlString);

    }
}
