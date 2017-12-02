package helpers;

import lombok.SneakyThrows;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Supplier;




@WebListener
public final class ApplicationServletContext implements ServletContextListener {

    public ApplicationServletContext() {

    }

    private final String contextName = "database";
    private static ServletContext servletContext;


    /*public static ApplicationServletContext getAppContextInstance() {
        if(appContextInstance == null){
            synchronized (ApplicationServletContext.class) {
                if(appContextInstance == null){
                    appContextInstance = new ApplicationServletContext();
                }
            }
        }

        return appContextInstance;
    }
*/
    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        Class.forName("org.h2.Driver");
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/h2");
        Connection connection = dataSource.getConnection();
        JdbcDAO jdbcDAO = () -> connection;

        jdbcDAO.executeSql("/h2.sql");

        servletContext.setAttribute(contextName, jdbcDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    public JdbcDAO take() {
        if (servletContext.getAttribute(contextName) == null) {
            throw new RuntimeException("Its null, why? idk");
        }
        return (JdbcDAO) servletContext.getAttribute(contextName);
    }


}
