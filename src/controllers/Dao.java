package controllers;

import entity.Student;
import global.Constant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Dao extends Constant {

    public boolean insertData(Object data) {
        boolean status = Boolean.TRUE;
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;
        try {
            //Insert into this if list
            if (data instanceof List) {
                if (((List) data).size() > 0 && ((List) data).get(0) instanceof Student) {
                    for (Student studentElement : (ArrayList<Student>) data) {
                        transaction = session.beginTransaction();
                        session.save(studentElement);
                        transaction.commit();
                    }
                }
            }
            //Insert below if static data
            else if (data instanceof Student) {
                transaction = session.beginTransaction();
                session.save(data);
                transaction.commit();
            }
        } catch (HibernateException ex) {
            if (transaction != null || session != null) {
                transaction.rollback();
            }
            status = Boolean.FALSE;
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return status;
    }

}
