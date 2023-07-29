//package bitcomm.sqlbuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import bitcomm.sqlbuilder.operations.apis.JoinType;
//
//public  class SqlOrderModel 
//{
//    protected static final String EMPTY = "";
//
//    private static String SELECT = " SELECT ";
//
//    protected String _tablename;
//    protected List<String> orderByList = new ArrayList<>();
//    protected List<String> searchFieldList=new ArrayList<>();
//    protected List<String> groupBy = new ArrayList<>();
//    protected String HAVING = "";
//    protected String joinTableName = "";
//    protected JoinType joinType = new JoinType();
//    protected Integer LIMIT = null;
//    protected Integer OFFSET = null;
//
//    private static final String COLUMNS = "%s";
//    private static final String TABLE_NAME = "%s";
//    private static final String WHERE_CONDIONS = "%s";
//    private static final String GROUP_BY_CONDIONS = "%s";
//    private static final String HAVING_CONDIONS = "%s";
//    private static final String ORDER_BY_CONDIONS = "%s";
//    private static final String LIMIT_CONDTION = "%s";
//    private static final String OFFSET_CONDITION = "%s";
//    private static final String JOINS = "%s";
//
//    private static String sqlStringQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
//	    + WHERE_CONDIONS + " " + HAVING_CONDIONS + " " + ORDER_BY_CONDIONS + " " + GROUP_BY_CONDIONS + " "
//	    + LIMIT_CONDTION + " " + OFFSET_CONDITION + " ";
//
//    private static String sqlCountQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
//	    + WHERE_CONDIONS + " ";
//
//    public String getResultSqlFormat() {
//	return sqlStringQueryFormat;
//    }
//
//    public String getCountSqlFormat() {
//	return sqlCountQueryFormat;
//    }
//
//}
