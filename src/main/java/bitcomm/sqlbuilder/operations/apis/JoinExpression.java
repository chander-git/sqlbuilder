package bitcomm.sqlbuilder.operations.apis;

import java.util.Optional;

public interface JoinExpression {

    SelectQuery join(String tbl,String rightColumn,String reftColumn , JoinType joinType);
    
    SelectQuery innerJoin(String joinTblName);
    
    SelectQuery leftJoin(String joinTblName);
    
    SelectQuery rightJoin(String joinTblName);
    
    SelectQuery on(String leftCol,String rightCol);
    
    Optional<String> build();
    
    
}
