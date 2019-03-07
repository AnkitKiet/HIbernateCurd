package global;

import entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Constant {
    public static SessionFactory SESSION_FACTORY;

     protected static void initializeContext() {
        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);
        config.configure("./resources/hibernate.cfg.xml");
        Properties prop = config.getProperties();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(prop);
        ServiceRegistry registry = builder.build();
        SESSION_FACTORY = config.buildSessionFactory(registry);
    }

}
