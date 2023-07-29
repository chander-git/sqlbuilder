package bitcomm.sqlbuilder.operations;

import java.util.LinkedList;
import java.util.List;
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

    @Override
    public WhereExpression where(String column, String operator, Object value) 
    {
	try {
	    if (value==null) 
	    {
		removeWhereCondition();
	    }
	    else
	    {
		if (operator!=null)
		{
		    if (value instanceof Number) {

			queryList.add(" "+column+" "+operator+" "+value);
		    }
		    else if (value instanceof String) {

			queryList.add(" "+column+" "+operator+" '"+value+"' ");
		    }
		    else 
			queryList.add(" "+column+" "+operator+" '"+value+"' ");
		}
		else {
		    // LIKE oR ILIKE etc
		    queryList.add(" "+column+ " "+ value);
		}
	    }
	} finally {
	    rightOperand=null;
	}
	return  this;
    }

    private void removeWhereCondition() {
	queryList.removeLast();
    }

    @Override
    public WhereExpression whereNotNull(Object columns) 
    {
	this.where(columns.toString(), null, "NOT NULL");
	return  this;
    }

    @Override
    public WhereExpression whereIsNull(Object columns) {

	this.where(columns.toString(), null, "IS NOT NULL");
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

	StringBuilder stringBuilder=new StringBuilder(" WHERE ");
	for (String string : queryList) 
	{
	    if (string.equals(OR)|| string.equals(AND))
	    {
		stringBuilder.append(string);
	    }
	    else {
		stringBuilder.append(string);
	    }
	    stringBuilder.append(" ");
	}

	return Optional.of(stringBuilder.toString());
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

	selectQuery.where(rightOperand, ">", object);

	return selectQuery;
    }
    public SelectQuery like(Object object){

	selectQuery.where(rightOperand, null, object==null
		?null:"LIKE '%" +object+"%'");

	return selectQuery;
    }


    public SelectQuery lessThan(Object object){
	selectQuery.where(rightOperand, "<", object);
	return selectQuery;
    }

    public SelectQuery gtThanEqual(Object object){
	selectQuery.where(rightOperand, ">=", object);

	return selectQuery;
    }

    public SelectQuery lessThanEqual(Object object){
	selectQuery.where(rightOperand, "<=", object);

	return selectQuery;
    }

    public SelectQuery equalTo(Object object){
	selectQuery.where(rightOperand, "=", object);

	return selectQuery;
    }
    public SelectQuery between(Object start , Object end){
	if (end !=null && start!=null) {
	    selectQuery.where(rightOperand,null, "BETWEEN "+ start+" AND "+end);
	}
	else selectQuery.where(rightOperand, null, null);

	return selectQuery;
    }

    @Override
    public SelectQuery in(List list) {
	if (list==null) 
	    selectQuery.where(rightOperand, null, null);

	else
	    selectQuery.where(rightOperand,null,"("+listToCommaArray(list)+")" );

	return selectQuery;
    }
    private static String listToCommaArray(List list) {
	return (String) list.stream().map(str -> String.format("'%s'", str.toString())).collect(Collectors.joining(","));
    }


    @Override
    public void setWhereRightOperand(Object rightOperand) {
	this.rightOperand=rightOperand.toString();
    }

    @Override
    public SelectQuery ilike(Object object) {
	selectQuery.where(rightOperand, null, object==null
		?null:"ILIKE '%" +object+"%'");

	return selectQuery;
    }


}
