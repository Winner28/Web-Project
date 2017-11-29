package helpers;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface JdbcDAO extends Supplier<Connection> {



    default void withConnection(Consumer<Connection> connectionConsumer) {
        Connection connection = get();
        connectionConsumer.accept(connection);
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



    default void executeSql(String resourceName) {
        String sql = getInitSql(resourceName);
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
