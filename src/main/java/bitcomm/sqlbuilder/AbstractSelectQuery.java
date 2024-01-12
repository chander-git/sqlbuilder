package bitcomm.sqlbuilder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import bitcomm.sqlbuilder.operations.InvalidSqlException;
import bitcomm.sqlbuilder.operations.Join;
import bitcomm.sqlbuilder.operations.Where;
import bitcomm.sqlbuilder.operations.apis.DataType;
import bitcomm.sqlbuilder.operations.apis.JoinExpression;
import bitcomm.sqlbuilder.operations.apis.JoinType;
import bitcomm.sqlbuilder.operations.apis.OperatorExpression;
import bitcomm.sqlbuilder.operations.apis.SelectQuery;
import bitcomm.sqlbuilder.operations.apis.WhereExpression;

public abstract class AbstractSelectQuery  implements SelectQuery 
{

    protected static final String EMPTY = "";

    protected static final String SELECT = " SELECT ";

    protected Set<String> _tablename=new HashSet<>();
    protected Set<String>  orderByList = new HashSet<>();
    protected Set<String>  searchFieldList=new HashSet<>();
    protected Set<String> groupBy = new HashSet<>();
    protected String HAVING = EMPTY;
    protected String joinTableName = EMPTY;
    protected JoinType joinType =  new JoinType();
    protected Integer LIMIT = null;
    protected Long OFFSET = null;

    private static final String COLUMNS = "%s";
    private static final String TABLE_NAME = "%s";
    private static final String WHERE_CONDIONS = "%s";
    private static final String GROUP_BY_CONDIONS = "%s";
    private static final String HAVING_CONDIONS = "%s";
    private static final String ORDER_BY_CONDIONS = "%s";
    private static final String LIMIT_CONDTION = "%s";
    private static final String OFFSET_CONDITION = "%s";
    private static final String JOINS =  "%s";

    private final static String sqlStringQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
	    + WHERE_CONDIONS + " " + HAVING_CONDIONS + " " + ORDER_BY_CONDIONS + " " + GROUP_BY_CONDIONS + " "
	    + LIMIT_CONDTION + " " + OFFSET_CONDITION + " ";

    //    private final static String sqlCountQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
    //	    + WHERE_CONDIONS + " ";

    public String getResultSqlFormat() {
	return sqlStringQueryFormat;
    }

    //    public String getCountSqlFormat() {
    //	return sqlCountQueryFormat;
    //    }

    //////////////////////////////////////////////////////////
    private  WhereExpression where;
    
    private JoinExpression join;

    private String joinsString = EMPTY;

    private String whereString = EMPTY;

    private String groupByString = EMPTY;

    private String havingString = EMPTY ;

    private String orderByString = EMPTY;

    private String limitString = EMPTY;

    private String offsetString = EMPTY;

    private String searchField = EMPTY;

    private String tableNameString = EMPTY;

    private String unionString = EMPTY ;

    private boolean isBuildSuccess=false;

    
    public AbstractSelectQuery() {
	this.where = new Where(this);
	this.join = new Join(this);
    }

    @Override
    public WhereExpression or() {
	where.or();
	return where;
    }
    public WhereExpression where(WhereExpression whereExpression) 
    {
	where.where(whereExpression);
	return where;
    }
    
    @Override
    public  WhereExpression and() {
	where.and();
	return where;
    }

    @Override
    public void setWhereRightOperand(Object rightOperand) {
	where.setWhereRightOperand(rightOperand);
    }

    @Override
    public WhereExpression where(String column, String operator, Object value) {

	return where.where(column, operator, value);
    }

    @Override
    public WhereExpression whereNotNull(Object columns) {

	return where.whereNotNull(columns);

    }
    
    @Override
    public OperatorExpression whereRaw(String raw) {
        // TODO Auto-generated method stub
        return where.whereRaw(raw);
    }

    @Override
    public OperatorExpression where(String rightColumn) {
	return this.where(rightColumn, null);
    }

    @Override
    public WhereExpression whereIsNull(Object columns) {
	where.whereIsNull(columns);
	return this;
    }

    @Override
    public OperatorExpression where(String rightColumn, DataType dataType) {
	setWhereRightOperand(rightColumn);
	return where;
    }

    @Override
    public Optional<String> build() {


	joinsString=join.build().orElse(EMPTY);
	
	Optional<String> wBuild = where.build();
	if (wBuild.isPresent() && !wBuild.get().isEmpty()) 
	{
	    whereString=" WHERE " +wBuild.get();
	}else whereString=EMPTY;

	String j = String.join(",", groupBy);
	if (j != null && !j.isEmpty()) {
	    groupByString = "GROUP BY " + j;
	}

	havingString = HAVING == null ? EMPTY : HAVING;

	String o = String.join(",", orderByList);
	if (o != null && !o.isEmpty()) {
	    orderByString = "ORDER BY " + o;
	}

	limitString=LIMIT==null?EMPTY:("LIMIT "+LIMIT);

	offsetString=OFFSET==null?EMPTY:("OFFSET "+OFFSET);

	tableNameString = String.join(",", _tablename);
	
	String sqlQuery = String.format(

		getResultSqlFormat(),

		searchField,tableNameString , joinsString,

		whereString, groupByString,  havingString,

		orderByString, limitString,  offsetString

		)+unionString;
	
	Optional<String> buildEntity = Optional.of(sqlQuery);

	isBuildSuccess=true;
	return buildEntity;
    }

    public synchronized String countQuery(String countOf) 
    {
//	if (!isBuildSuccess) 
//	{
//	    build();
//	}
	String j = String.join(",", groupBy);
	if (j != null && !j.isEmpty()) {
	    groupByString = "GROUP BY " + j;
	}
	return
		SELECT +countOf+" FROM ( "+

	String.format(

		getResultSqlFormat(),

		searchField,tableNameString, joinsString,

		whereString, groupByString,  havingString,

		orderByString ,EMPTY,EMPTY

		)
		+")countQuery ";
    }

    @Override
    public String toString() 
    {
	Optional<String> isBuild = build();
	return isBuild.orElse("SELECT");
    }


    @Override
    public SelectQuery limit(Integer limit) {
	this.LIMIT=limit;
	return this;
    }

    @Override
    public SelectQuery offset(Long offset) {
	OFFSET=offset;
	return this;
    }

    @Override
    public SelectQuery select(String... columns) {
	searchField=EMPTY;
	searchFieldList.clear();
	for (String field : columns)
	{
	    searchFieldList.add(field);
	}
	searchField=String.join(",", searchFieldList);
	
	return this;
    }
    public SelectQuery selectDistinct(String... columns) {

	for (String field : columns)
	{
	    searchFieldList.add(field);
	}
	searchField="DISTINCT " +String.join(",", searchFieldList);
	return this;
    }
    
    @Override
    public SelectQuery from(SelectQuery selectQuery , String alias) {
	
	_tablename.add("("+selectQuery.toString()+") "+alias);
	return this;
    }
    
    @Override
    public SelectQuery orderBy(String column) {

	String orderBy = (" "+column+" ASC " );
	orderByList.add(orderBy);
	return this;
    }

    @Override
    public SelectQuery orderByDesc(String column) 
    {	
	String orderBy = (" "+column+" DESC " );
	orderByList.add(orderBy);
	return this;
    }

    @Override
    public SelectQuery groupBy(String... columns) {

	if (columns != null) 
	{
	    for (String column : columns) {

		groupBy.add(column);

	    }
	}
	return this;
    }

    @Override
    public SelectQuery from(String tableName)
    {
	this._tablename.add(tableName);
	return this;
    }
    
    @Override
    public SelectQuery from(String[] tableNames )
    {
	for (String table : tableNames) {
	    this._tablename.add(table);
	}
	return this;
    }

    @Override
    public SelectQuery selectAll() {
	searchField="*";
	searchFieldList.add(searchField);
	return this;
    }

    @Override
    public SelectQuery innerJoin(String joinTblName) {
	join.innerJoin(joinTblName);
	return this;

    }

    @Override
    public SelectQuery leftJoin(String joinTblName) {
	join.leftJoin(joinTblName);
	return this;
    }

    @Override
    public SelectQuery rightJoin(String joinTblName) {
	join.rightJoin(joinTblName);
	return this;
    }

    @Override
    public SelectQuery on(String rightCol, String leftCol) {
	join.on(rightCol, leftCol);
	return this;
    }

    @Override
    public SelectQuery join(String tbl, String rightColumn, String reftColumn, JoinType joinType) 
    {
	return join.join(tbl, rightColumn, reftColumn, joinType);
    }


    @Override
    public String countQuery() 
    {
	return countQuery("count(*)");
    }

    public SelectQuery notEqual(Object object){

	where.notEqual(object);

	return this;
    }


    public SelectQuery gtThan(Object object){

	where.gtThan(object);

	return this;
    }
    public SelectQuery like(Object object){

	where.like(object);

	return this;
    }

    @Override
    public SelectQuery ilike(Object object) {
	where.ilike(object);

	return this;
    }

    public SelectQuery lessThan(Object object){
	where.lessThan(object);

	return this;
    }

    public SelectQuery gtThanEqual(Object object){
	where.gtThanEqual(object);

	return this;
    }

    public SelectQuery lessThanEqual(Object object){
	where.lessThanEqual(object);

	return this;
    }

    public SelectQuery equalTo(Object object){
	where.equalTo(object);

	return this;
    }

    public SelectQuery between(Object start , Object end){
	where.between(start, end);
	return this;
    }

    @Override
    public SelectQuery in(Object list) {
	return where.in(list);
    }

    @Override
    public SelectQuery setPageRange(Integer pageSize, Integer page) throws InvalidSqlException {
	this.LIMIT=pageSize;
	this.OFFSET=getOffset(pageSize, page);
	return this;
    }
    
    @Override
    public SelectQuery union(SelectQuery query) {
	unionString=" "+query.toString();
        return null;
    }

}
