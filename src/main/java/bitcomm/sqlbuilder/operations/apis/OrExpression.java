package bitcomm.sqlbuilder.operations.apis;

import java.util.Optional;

public interface OrExpression {
    WhereExpression or();
    Optional<String> build();
}
