package bitcomm.sqlbuilder.operations.apis;

public interface OperatorExpression extends InExpression
{
    public SelectQuery notEqual(Object object);

    public SelectQuery gtThan(Object object);
    
    public SelectQuery like(Object object);
    
    public SelectQuery ilike(Object object);

    public SelectQuery lessThan(Object object);
    
    public SelectQuery gtThanEqual(Object object);

    public SelectQuery lessThanEqual(Object object);
    
    public SelectQuery equalTo(Object object);
    
    public SelectQuery between(Object start , Object end);

    
}
