package bitcomm.sqlbuilder.operations.apis;

public interface SelectQuery extends InExpression,WhereExpression,JoinExpression
{
  
    
    SelectQuery limit(int limit);
    
    SelectQuery offset(int offset);
    
    SelectQuery select(String... columns);
    
    SelectQuery orderBy(String column);
    
    SelectQuery orderByDesc(String column);
    
    SelectQuery groupBy(String... column);
    
    SelectQuery from(String tableName);
    
    SelectQuery selectAll();
    
    String countQuery();
}
