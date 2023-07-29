package bitcomm.sqlbuilder.operations.apis;

import java.util.Optional;

public interface InExpression {

    public SelectQuery in (Object list);
    Optional<String> build();
}
