package listeners;

import helpers.ApplicationServletContext;
import helpers.JdbcDAO;
import lombok.SneakyThrows;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.function.Supplier;


@WebListener
public class DbListener implements ServletContextListener {

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/h2");

        if (dataSource == null) {
            throw new RuntimeException("datasource not found!");
        }

        Supplier<Connection> connection = (Supplier<Connection>) dataSource.getConnection();
        JdbcDAO jdbcDAO = (JdbcDAO) connection.get();
        jdbcDAO.executeSql("/h2.sql");

        ApplicationServletContext.DataBase.accept(jdbcDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
