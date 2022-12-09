package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Transaction transaction;

    public UserDaoHibernateImpl() {

    }

    private void executeQuery(String query) {
        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            currentSession.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS users
                (
                    id        INT         NOT NULL AUTO_INCREMENT,
                    name      VARCHAR(45) NOT NULL,
                    last_name VARCHAR(45) NOT NULL,
                    age       INT         NOT NULL,
                    PRIMARY KEY (id)
                )
                """;
        executeQuery(query);
    }


    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        executeQuery(query);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            currentSession.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            currentSession.delete(currentSession.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            users = currentSession.createSQLQuery("SELECT * FROM users").addEntity(User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "DELETE FROM users";
        executeQuery(query);
    }
}