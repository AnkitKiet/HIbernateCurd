import controllers.Dao;
import entity.Address;
import entity.Student;
import global.Constant;
import global.DbTables;
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
        Dao objDao = new Dao();
       // create(objDao);
        System.out.println("--------------------------------------------------------------Before Delete -------------------------------------------------------------");
        read(objDao);
        delete(objDao,6);
        System.out.println("--------------------------------------------------------------After Delete -------------------------------------------------------------");
        read(objDao);
        session.close();
    }

    private static void delete(Dao objDao, int id) {
        objDao.deleteData(Student.class,id);
    }

    private static void read(Dao objDao) {
        try {
            List<Student> students = (List<Student>) objDao.readData(DbTables.student_info);
            if (students != null) {
                for (Student stu : students) {
                    System.out.println(stu);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred in reading data " + e.getStackTrace());
        }

    }

    public static void create(Dao objDao) {
        ArrayList<Student> list = new ArrayList<>();
        Set<Address> addressSet = new HashSet<Address>();
        for (int i = 1; i < 5; i++) {
            Student student = new Student();
            student.setAge(23 + i);
            student.setGender("M");
            student.setName("Ankit " + i);
            Address addObj = new Address();
            addObj.setAddress("Ankit's Address " + i);
            addObj.setStudent(student);
            addressSet.add(addObj);
            student.setAddress(addressSet);
            list.add(student);
        }

        boolean sStatus = objDao.insertData(list);
        System.out.println("Save Status - " + sStatus);

        boolean aStatus = objDao.insertData(addressSet);
        System.out.println("Address Save Status " + aStatus);
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