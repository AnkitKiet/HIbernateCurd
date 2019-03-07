import controllers.Dao;
import entity.Student;
import global.Constant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Main extends Constant {
    public static void main(String[] args) {
        initializeContext();
        create();

        List<Student> students = readAll();
        if (students != null) {
            for (Student stu : students) {
                System.out.println(stu);
            }
        }
        SESSION_FACTORY.close();
    }

    public static void create() {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setAge(23 + i);
            student.setGender("M");
            student.setName("Ankit " + i);
            list.add(student);
        }
        Dao obj = new Dao();
        boolean sStatus = obj.insertData(list);
        System.out.println("Save Status - " + sStatus);
    }

    public static List<Student> readAll() {
        List<Student> students = null;
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            students = session.createQuery("FROM Student").list();
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