package bitcomm.sqlbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    protected String _tablename;
    protected List<String> orderByList = new ArrayList<>();
    protected List<String> searchFieldList=new ArrayList<>();
    protected List<String> groupBy = new ArrayList<>();
    protected String HAVING = "";
    protected String joinTableName = "";
    protected JoinType joinType = new JoinType();
    protected Integer LIMIT = null;
    protected Integer OFFSET = null;

    private static final String COLUMNS = "%s";
    private static final String TABLE_NAME = "%s";
    private static final String WHERE_CONDIONS = "%s";
    private static final String GROUP_BY_CONDIONS = "%s";
    private static final String HAVING_CONDIONS = "%s";
    private static final String ORDER_BY_CONDIONS = "%s";
    private static final String LIMIT_CONDTION = "%s";
    private static final String OFFSET_CONDITION = "%s";
    private static final String JOINS = "%s";

    private final static String sqlStringQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
	    + WHERE_CONDIONS + " " + HAVING_CONDIONS + " " + ORDER_BY_CONDIONS + " " + GROUP_BY_CONDIONS + " "
	    + LIMIT_CONDTION + " " + OFFSET_CONDITION + " ";

    private final static String sqlCountQueryFormat = SELECT + COLUMNS + " FROM " + TABLE_NAME + " " + JOINS + " "
	    + WHERE_CONDIONS + " ";

    public String getResultSqlFormat() {
	return sqlStringQueryFormat;
    }

    public String getCountSqlFormat() {
	return sqlCountQueryFormat;
    }

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

   

    public AbstractSelectQuery() {
	this(null);
    }

    private AbstractSelectQuery(String tableName) 
    {
	this._tablename = tableName;
	this.where = new Where(this);
	this.join = new Join(this);
	this.groupBy = new ArrayList<String>();
    }

    @Override
    public WhereExpression or() {
	where.or();
	return where;
    }

    @Override
    public WhereExpression and() {
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

	whereString=where.build().orElse(EMPTY);

	String j = String.join(",", groupBy);
	if (j != null && !j.isEmpty()) {
	    groupByString = "GROUP BY " + j;
	}

	havingString = HAVING == null ? "" : HAVING;

	String o = String.join(",", orderByList);
	if (o != null && !o.isEmpty()) {
	    orderByString = "ORDER BY " + o;
	}

	limitString=LIMIT==null?"":("LIMIT "+LIMIT);

	offsetString=OFFSET==null?"":("OFFSET "+OFFSET);


	return	Optional.of(

		String.format(

			getResultSqlFormat(),

			searchField, _tablename, joinsString,

			whereString, groupByString,  havingString,

			orderByString, limitString,  offsetString

			)
		);

    }

    private String countQuery(String countOf) 
    {
	return
		 "SELECT "+countOf+" FROM ( "+
		
		String.format(getCountSqlFormat(), 

			searchField , _tablename , joinsString ,

			whereString 

			)
		+")countQuery ";
    }
    
    @Override
    public String toString() {
	return build().orElse("");
    }


    @Override
    public SelectQuery limit(int limit) {
	this.LIMIT=limit;
	return this;
    }

    @Override
    public SelectQuery offset(int offset) {
	return this;
    }

    @Override
    public SelectQuery select(String... columns) {

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
    public SelectQuery orderBy(String column) {

	String orderBy = (" ORDER BY "+column+" ASC " );
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

	if (columns!=null)
	{

	    //	    searchFieldList.clear();

	    for (String column : columns) {

		groupBy.add(column);

	    }
	    //	    groupBy.forEach(t->{
	    //		searchFieldList.add(t);
	    //
	    //	    });

	}
	return this;
    }

    @Override
    public SelectQuery from(String tableName)
    {
	this._tablename=tableName;
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


}
