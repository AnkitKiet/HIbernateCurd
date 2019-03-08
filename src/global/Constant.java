package global;

import entity.Address;
import entity.Student;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.text.MessageFormat;
import java.util.Properties;

public class Constant {
    public static SessionFactory SESSION_FACTORY;
    public static Session session = null;

    protected static void initializeContext() {
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(Student.class);
            config.addAnnotatedClass(Address.class);
            config.configure("./resources/hibernate.cfg.xml");
            PropertyConfigurator.configure("D:\\projects\\personal\\HIbernateCurd\\src\\resources\\log4j.properties");
            Properties prop = config.getProperties();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(prop);
            ServiceRegistry registry = builder.build();
            SESSION_FACTORY = config.buildSessionFactory(registry);
            session = SESSION_FACTORY.openSession();
        } catch (Exception e) {
            System.out.println(MessageFormat.format("Exception in Initializing Context ", e.getStackTrace()));
            throw new ExceptionInInitializerError(e);
        }
    }

}
