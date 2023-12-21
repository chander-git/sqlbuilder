package bitcomm.sqlbuilder.operations.apis;

import java.util.Optional;

public interface WhereExpression extends OrExpression , AndExpression ,OperatorExpression{

    WhereExpression where(String column, String operator, Object value);

    WhereExpression whereNotNull(Object columns);

    OperatorExpression where(String column);
    
    OperatorExpression whereRaw(String raw);
    
    OperatorExpression where(String column, DataType dataType);
    
    WhereExpression whereIsNull(Object columns);
    
    Optional<String> build();
    
    void setWhereRightOperand(Object rightOperand);

}
