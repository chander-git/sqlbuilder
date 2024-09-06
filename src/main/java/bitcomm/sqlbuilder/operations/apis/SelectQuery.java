package bitcomm.sqlbuilder.operations.apis;

public interface SelectQuery extends InExpression,WhereExpression,JoinExpression, PageRangeExpression
{
    
    SelectQuery select(String... columns);
    
    SelectQuery orderBy(String column);
    
    SelectQuery orderByDesc(String column);
    
    SelectQuery groupBy(String... columns);
    
    SelectQuery from(String tableName);
    
    SelectQuery selectAll();
    
    SelectQuery selectDistinct(String ...columns);
    
    String countQuery();

    SelectQuery from(String[] tableNames);
    
    SelectQuery from(SelectQuery selectQuery, String alias);
    
    SelectQuery union(SelectQuery query);

    SelectQuery orderBy(String column, String orderBy);

    
}
