package bitcomm.sqlbuilder.operations.apis;

import bitcomm.sqlbuilder.operations.InvalidSqlException;

public interface PageRangeExpression {

    SelectQuery  limit(Integer limit);

    SelectQuery offset(Long offset);

    SelectQuery setPageRange(Integer pageSize ,Integer page ) throws InvalidSqlException;

    default Long getOffset(Integer pageSize, Integer page) throws InvalidSqlException {

	if (page!=null &&  page<0) {
	    throw new InvalidSqlException("page must be > 0");
	}
	if (pageSize !=null && pageSize<1) 
	{
	    throw new InvalidSqlException("pageSize can not be negative");
	}

	if (pageSize!=null && page!=null) {
	    return  (long)page * (long) pageSize;
	}
	else if (pageSize==null && page!=null) {

	    return page*1L;
	}
	return null;
    }

}
