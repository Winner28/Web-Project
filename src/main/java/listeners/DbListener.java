package listeners;

import helpers.ApplicationServletContext;
import helpers.JdbcDAO;
import lombok.SneakyThrows;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.function.Supplier;
import java.util.logging.Logger;




@WebListener
public class DbListener implements ServletContextListener {


    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Class.forName("org.h2.Driver");

        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/h2");



       /* if (dataSource == null) {
            throw new RuntimeException("datasource not found!");
        }
*/



       Connection connection = dataSource.getConnection();
       Supplier<Connection> connectionSupplier = (Supplier<Connection>) connection;


       /* jdbcDAO.executeSql("/h2.sql");

        ApplicationServletContext.DataBase.accept(jdbcDAO);*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }


}
