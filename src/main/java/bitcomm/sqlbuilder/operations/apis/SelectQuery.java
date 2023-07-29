package bitcomm.sqlbuilder.operations.apis;

public interface SelectQuery extends InExpression,WhereExpression,JoinExpression
{
  
    
    SelectQuery limit(Integer limit);
    
    SelectQuery offset(Integer offset);
    
    SelectQuery select(String... columns);
    
    SelectQuery orderBy(String column);
    
    SelectQuery orderByDesc(String column);
    
    SelectQuery groupBy(String... column);
    
    SelectQuery from(String tableName);
    
    SelectQuery selectAll();
    
    SelectQuery selectDistinct(String ...column);
    
    String countQuery();
}
