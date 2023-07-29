package bitcomm.sqlbuilder.operations.apis;

import java.util.Optional;

public interface AndExpression {
    WhereExpression and();
    Optional<String> build();
}
