import entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;

public class Main {
    private static final SessionFactory SESSION_FACTORY;

    static {
        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class);
        config.configure("./resources/hibernate.cfg.xml");
        Properties prop = config.getProperties();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(prop);
        ServiceRegistry registry = builder.build();
        SESSION_FACTORY = config.buildSessionFactory(registry);
    }

    public static void main(String[] args) {
        create("Alice", 22);
        create("Bob", 20);
        create("Charlie", 25);

        List<Student> students = readAll();
        if (students != null) {
            for (Student stu : students) {
                System.out.println(stu);
            }
        }
        SESSION_FACTORY.close();
    }

    public static void create(String name, int age) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Student stu = new Student();
            stu.setName(name);
            stu.setAge(age);
            stu.setGender("M");
            session.save(stu);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<Student> readAll() {
        List<Student> students = null;
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            students = session.createQuery("FROM entity.Student").list();
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return students;
    }

    public static void delete(int id) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Student stu = (Student) session.get(Student.class,
                    Integer.valueOf(id));
            session.delete(stu);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void upate(int id, String name, int age) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Student stu = (Student) session.get(Student.class,
                    Integer.valueOf(id));
            stu.setName(name);
            stu.setAge(age);
            session.update(stu);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}