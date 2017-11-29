package helpers;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ApplicationServletContext<T> implements Supplier<T>, Consumer<T>, ServletContextListener {

    private ApplicationServletContext(String name) {
        this.contextName = name;
    }


    public static final ApplicationServletContext<JdbcDAO> DataBase =
            new ApplicationServletContext<>("database");

    private final String contextName;
    private static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    @Override
    public void accept(T t) {
        servletContext.setAttribute(contextName, t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        return (T)servletContext.getAttribute(contextName);
    }
}
