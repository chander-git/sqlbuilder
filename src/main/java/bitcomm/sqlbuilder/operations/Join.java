package bitcomm.sqlbuilder.operations;

import static bitcomm.sqlbuilder.operations.apis.JoinType.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import bitcomm.sqlbuilder.operations.apis.JoinExpression;
import bitcomm.sqlbuilder.operations.apis.JoinType;
import bitcomm.sqlbuilder.operations.apis.SelectQuery;

public class Join implements JoinExpression {
    
    protected StringBuilder query;
    private SelectQuery selectQuery;
    private String joinTableName;
    private JoinType joinType;

    public Join(SelectQuery selectQuery){
	query=new StringBuilder();
	joinType=new JoinType();;
	this.selectQuery=selectQuery;
    }

    @Override
    public SelectQuery join(String tbl, String leftCol, String rightColumn, JoinType joinType) 
    {
	query.append(joinType.getName()+" ");
	query.append(tbl+" ");
	query.append("ON ");
	query.append(leftCol+" ");
	query.append("= ");
	query.append(rightColumn+" ");

	return selectQuery;
    }

    @Override
    public Optional<String> build() 
    {
	return Optional.of(query.toString())  ;
    }

    @Override
    public SelectQuery innerJoin(String joinTblName) {
	joinTableName=joinTblName;
	joinType.setType(INNER);
	return selectQuery;
    }

    @Override
    public SelectQuery leftJoin(String joinTblName) {
	joinTableName=joinTblName;
	joinType.setType(LEFT);
	return selectQuery;
    }

    @Override
    public SelectQuery rightJoin(String joinTblName) {
	joinTableName=joinTblName;
	joinType.setType(RIGHT);
	return selectQuery;
    }

    @Override
    public SelectQuery on(String leftCol, String rightCol) {
	this.join(joinTableName, rightCol, leftCol, joinType);
	return selectQuery;
    }
}
