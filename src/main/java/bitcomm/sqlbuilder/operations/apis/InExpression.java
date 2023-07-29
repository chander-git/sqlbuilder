package bitcomm.sqlbuilder.operations.apis;

import java.util.List;
import java.util.Optional;

public interface InExpression {

    public SelectQuery in (List list);
    Optional<String> build();
}
