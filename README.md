
Here's an overview of the main components of the code:

1. `AbstractSelectQuery` class: This is an abstract class that implements the `SelectQuery` interface, providing common functionality for building SQL select queries.

2. Fields:
   - `_tablename`: The name of the table from which to select data.
   - `orderByList`: A list of columns to order the result set.
   - `searchFieldList`: A list of columns to be selected in the query.
   - `groupBy`: A list of columns for grouping the results.
   - `HAVING`: The HAVING clause condition for aggregating functions.
   - `joinTableName`: The name of the table to join in the query.
   - `joinType`: An object representing the type of join (INNER, LEFT, RIGHT).
   - `LIMIT`: The limit on the number of rows to be returned in the query.
   - `OFFSET`: The offset for pagination.

3. Constants:
   - `EMPTY`: An empty string constant.
   - `SELECT`: The SELECT keyword for SQL queries.
   - Various string format constants for building the final SQL query.

4. Components:
   - `WhereExpression`: A component for building the WHERE clause of the SQL query.
   - `JoinExpression`: A component for building the JOIN clause of the SQL query.

5. Methods:
   - Various methods for constructing the SQL query, including those for the WHERE clause, JOIN clause, GROUP BY, ORDER BY, and pagination.
   - `build()`: A method that constructs the final SQL query based on the provided parameters.

6. Other methods:
   - `getResultSqlFormat()`: Returns the SQL query format string.
   - `countQuery(String countOf)`: Constructs a SQL count query based on the provided count function.

This abstract class is a part of a larger SQL query builder framework that aims to provide a simplified and structured way to create SQL select queries in Java applications. Concrete subclasses are expected to provide specific implementations for the abstract methods and potentially extend the functionality to support other SQL query types (e.g., INSERT, UPDATE, DELETE).