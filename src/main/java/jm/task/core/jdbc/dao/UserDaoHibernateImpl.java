package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

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

        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createSQLQuery(query).executeUpdate();
            currentSession.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";

        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createSQLQuery(query).executeUpdate();
            currentSession.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.save(new User(name, lastName, age));
            currentSession.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.delete(currentSession.get(User.class, id));
            currentSession.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;

        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            users = currentSession.createSQLQuery("SELECT * FROM users").addEntity(User.class).getResultList();
            currentSession.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "DELETE FROM users";

        try (Session currentSession = Util.getSessionFactory().getCurrentSession()) {
            currentSession.beginTransaction();
            currentSession.createSQLQuery(query).executeUpdate();
            currentSession.getTransaction().commit();
        }
    }
}