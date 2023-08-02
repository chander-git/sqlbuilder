package bitcomm.sqlbuilder.operations.apis;

public interface SelectQuery extends InExpression,WhereExpression,JoinExpression, PageRangeExpression
{
    
    SelectQuery select(String... columns);
    
    SelectQuery orderBy(String column);
    
    SelectQuery orderByDesc(String column);
    
    SelectQuery groupBy(String... column);
    
    SelectQuery from(String tableName);
    
    SelectQuery selectAll();
    
    SelectQuery selectDistinct(String ...column);
    
    String countQuery();

    
}
