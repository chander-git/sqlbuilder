package bitcomm.sqlbuilder.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import bitcomm.sqlbuilder.operations.apis.DataType;
import bitcomm.sqlbuilder.operations.apis.OperatorExpression;
import bitcomm.sqlbuilder.operations.apis.SelectQuery;
import bitcomm.sqlbuilder.operations.apis.WhereExpression;

public class Where  implements WhereExpression {
    protected LinkedList<String> queryList=new LinkedList<>();

    private SelectQuery selectQuery;

    private String rightOperand;
    private DataType rightOperandDataType=null;
    private final static String AND="AND";
    private final static String OR="OR";


    public Where(SelectQuery selectQuery ) {
	this.selectQuery=selectQuery;
    }

    private void appendDefaultLogicalOper() {
	try {

	    String lastkeyWord = queryList.getLast();

	    if (lastkeyWord!=null && !lastkeyWord.equals(AND) && !lastkeyWord.equals(OR)) 
	    {
		this.and();
	    }
	} catch (NoSuchElementException e) {
	}

    }

    @Override
    public WhereExpression where(String column, String operator, Object value ) 
    {
	try {

	    if (value==null) 
	    {
		removeLastWhereOperator();
	    }
	    else
	    {
		appendDefaultLogicalOper();

		if (operator!=null)
		{
		    if (value instanceof Number|| value instanceof Boolean) {

			queryList.add(" "+column+" "+operator+" "+value);
		    }
		    else if (value instanceof String) {

			queryList.add(" "+column+" "+operator+" '"+value+"' ");
		    }
		    else if (value instanceof SelectQuery) 
		    {
			queryList.add(" "+column+" "+operator+" ("+((SelectQuery)value).toString()+")");
		    }
		    else 
			queryList.add(" "+column+" "+operator+" '"+value+"' ");
		}
		else {
		    // LIKE oR ILIKE etc
		    if (value instanceof WhereExpression) 
		    {
			Optional<String> wQuery = ((WhereExpression)value).build();
			if (wQuery.isPresent()) {
			    queryList.add(" ( "+wQuery.get()+" )");
			}

		    }
		    else
			queryList.add(" "+column+ " "+ value.toString());
		}
	    }
	} finally {
	    rightOperand=null;
	}
	return  this;
    }

    private void removeLastWhereOperator() {
	try {
	    String lastkeyWord = queryList.getLast();

	    if (lastkeyWord != null && (lastkeyWord.equals(AND)||
		    lastkeyWord.equals(OR)))
	    {
		queryList.removeLast();
	    }

	} catch (NoSuchElementException e) {
	    // TODO: handle exception
	}
    }

    @Override
    public WhereExpression whereNotNull(Object columns) 
    {
	this.where(columns.toString(), null, "IS NOT NULL");
	return  this;
    }

    @Override
    public WhereExpression whereIsNull(Object columns) {

	this.where(columns.toString(), null, "IS NULL");
	return  this;

    }

    @Override
    public OperatorExpression where(String column) 
    {
	return where(column,null);
    }

    @Override
    public OperatorExpression where(String column, DataType dataType) 
    {
	rightOperand=column;
	this.rightOperandDataType=dataType;
	return this;
    }


    @Override
    public Optional<String> build() {

	if (queryList.isEmpty()==false)
	{
	    StringBuilder stringBuilder=new StringBuilder("");
	    for (String list : queryList) 
	    {
		stringBuilder.append(list);
		stringBuilder.append(" ");
	    }
	    return Optional.of(stringBuilder.toString());
	}
	else return Optional.empty();
    }

    @Override
    public WhereExpression and() {
	queryList.add(AND);
	return this;
    }

    @Override
    public WhereExpression or() {
	queryList.add(OR);
	return this;
    }

    public SelectQuery notEqual(Object object){

	where(rightOperand, "!=", object);

	return selectQuery;
    }


    public SelectQuery gtThan(Object object){

	where(rightOperand, ">", object);

	return selectQuery;
    }
    public SelectQuery like(Object object){

	where(rightOperand, null, object==null
		?null:"LIKE '%" +object+"%'");

	return selectQuery;
    }

    @Override
    public SelectQuery ilike(Object object) {
	where(rightOperand, null, object==null
		?null:"ILIKE '%" +object+"%'");

	return selectQuery;
    }

    public SelectQuery lessThan(Object object){
	where(rightOperand, "<", object);
	return selectQuery;
    }

    public SelectQuery gtThanEqual(Object object){
	where(rightOperand, ">=", object);

	return selectQuery;
    }

    public SelectQuery lessThanEqual(Object object){
	where(rightOperand, "<=", object);

	return selectQuery;
    }

    public SelectQuery equalTo(Object object){
	where(rightOperand, "=", object);

	return selectQuery;
    }
    public SelectQuery between(Object start , Object end){

	if (end !=null && start!=null 
		&&((start instanceof CharSequence && end instanceof CharSequence) || (start instanceof Date && end instanceof Date))) 
	{
	    where(rightOperand,null, "BETWEEN '"+ start+"' AND '"+end+"'");
	}
	else if (end !=null && start!=null)
	{
	    where(rightOperand,null, "BETWEEN "+ start+" AND "+end);
	}
	else where(rightOperand, null, null);

	return selectQuery;

    }

    @Override
    public SelectQuery in(Object object) {

	try {
	    boolean objNullCheck;
	    boolean objInstanceTypeCheck;
	    boolean objCollEmptCheck;
	    boolean objListTypeCheck;
	    boolean objStringArrCheck;	

	    objNullCheck = object == null;
	    objInstanceTypeCheck = object instanceof Collection;
	    objStringArrCheck = (object instanceof String[]);


	    if (objNullCheck || (objInstanceTypeCheck && ((Collection) object).isEmpty())) 
		where(rightOperand, null, null);

	    else if(objInstanceTypeCheck)
		where(rightOperand,null," IN ("+listToCommaArray((List)object)+")" );

	    else if(objStringArrCheck)
		where(rightOperand,null," IN ("+listToCommaArray(Arrays.asList(object))+")" );

	    else if(object instanceof SelectQuery) {
		SelectQuery  selectQ=(SelectQuery) object;
		where(rightOperand,null," IN ("+selectQ.toString()+")" );

	    }

	    return selectQuery;

	}
	catch (Exception e) {
	    e.printStackTrace();
	    return selectQuery;
	}
    }
    private static String listToCommaArray(List list) {
	return (String) list.stream().map(str -> String.format("'%s'", str.toString())).collect(Collectors.joining(","));
    }


    @Override
    public void setWhereRightOperand(Object rightOperand) {
	this.rightOperand=rightOperand.toString();
    }

    @Override
    public OperatorExpression whereRaw(String raw) {
	queryList.add(raw);
	return this;
    }

    @Override
    public WhereExpression where(WhereExpression whereExpression) {
	return this.where(null, null, whereExpression);
    }



}
