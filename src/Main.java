import controllers.Dao;
import entity.Address;
import entity.Student;
import global.Constant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Constant {
    public static void main(String[] args) {
        initializeContext();
        create();

        session.close();
    }

    public static void create() {
        ArrayList<Student> list = new ArrayList<>();
        Set<Address> addressSet = new HashSet<Address>();
         for (int i = 1; i < 5; i++) {
        Student student = new Student();
        student.setAge(23+i);
        student.setGender("M");
        student.setName("Ankit "+i);
        Address addObj = new Address();
        addObj.setAddress("Ankit's Address "+i);
        addObj.setStudent(student);
        addressSet.add(addObj);
        student.setAddress(addressSet);
        list.add(student);
         }
        Dao obj = new Dao();
        boolean sStatus = obj.insertData(list);
        System.out.println("Save Status - " + sStatus);

        boolean aStatus = obj.insertData(addressSet);
        System.out.println("Address Save Status " + aStatus);
        List<Student> students = readAll();
        if (students != null) {
            for (Student stu : students) {
                System.out.println(stu);
            }
        }


        session.close();
    }

    public static List<Student> readAll() {
        List<Student> students = null;

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