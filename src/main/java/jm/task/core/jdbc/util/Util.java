package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1243";

    private Connection connection; //jdbc
    private static Util util = null; //jdbc

    private static SessionFactory sessionFactory; //hibernate

    //jdbc->
    public Util() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Util getUtil() {
        if (util == null){
            util = new Util();
        }
        return util;
    }

    public Connection getConnection() {
        return connection;
    }
    //<-jdbc


    //hibernate->
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties property = new Properties();
                property.put(Environment.URL, URL);
                property.put(Environment.USER, USERNAME);
                property.put(Environment.PASS, PASSWORD);
                property.put(Environment.SHOW_SQL, "true");
                property.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                property.put(Environment.HBM2DDL_AUTO, "update");

                Configuration configuration = new Configuration().setProperties(property);
                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
