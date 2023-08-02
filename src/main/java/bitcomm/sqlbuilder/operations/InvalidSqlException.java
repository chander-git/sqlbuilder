package bitcomm.sqlbuilder.operations;

public class InvalidSqlException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidSqlException() {
	super();
    }
    
    
    public InvalidSqlException(String message) 
    {
	super(message);
    }
    
}
