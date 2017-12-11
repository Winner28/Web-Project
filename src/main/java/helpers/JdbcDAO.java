package helpers;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@SuppressWarnings("unchecked")
public interface JdbcDAO extends Supplier<Connection> {



    default void withConnection(Consumer<Connection> connectionConsumer) {
        Connection connection = get();
        connectionConsumer.accept(connection);
    }


    default <T>  T mapConnection(Function<Connection, T> tFunction)  {
        return tFunction.apply(get());
    }

    default void withStatement(Consumer<Statement> statementConsumer) {
        withConnection(connection -> {
            try {
                Statement statement = connection.createStatement();
                statementConsumer.accept(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }




    default <T> T mapStatement(Function<Statement, T> statementMapper) {

        return mapConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                return statementMapper.apply(statement);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();

            }
        });
    }


    default void withResultSet(Consumer<ResultSet> resultSetConsumer, String SQL) {
        withStatement(statement -> {

            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSetConsumer.accept(resultSet);

        });
    }


    default void withPreparedStatement(Consumer<PreparedStatement> preparedStatementConsumer,
                                       String SQL, Object ... objects) {

        withConnection(connection -> {
           try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
               for (int i = 0; i < objects.length;) {
                   Object param = objects[i++];
                   preparedStatement.setObject(i, param);
               }
               preparedStatementConsumer.accept(preparedStatement);
           } catch (SQLException e) {
               e.printStackTrace();
           }
        });
    }



    default <T> T mapPreparedStatement(Function<PreparedStatement, T> preparedStatementTFunction,
                                       String SQL, Object... objects) {
        return mapConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                for (int i =0; i<objects.length;) {
                    Object param = objects[i++];
                    preparedStatement.setObject(i, param);
                }

                return preparedStatementTFunction.apply(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();

            }


        });

    }


    default <T> T mapPreparedStatementFlagged(Function<PreparedStatement, T> preparedStatementMapper,
                                              String sql,
                                              Object [] params) {


        return mapConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

                for (int i = 0; i < params.length; ) {
                    Object param = params[i];
                    preparedStatement.setObject(++i, param);
                }

                return preparedStatementMapper.apply(preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException("e");
            }
        });
    }



    default void executeSql(String resourceName) {
        String sql = getInitSql(resourceName);
        System.out.println(sql);
        withStatement(statement -> {
            try {
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    default String getInitSql(String name) {

        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(name))
                .useDelimiter(System.lineSeparator());

             Stream<String> lines = StreamSupport.stream(
                     Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED), false)) {

            return lines.collect(Collectors.joining());
        }
    }


}
