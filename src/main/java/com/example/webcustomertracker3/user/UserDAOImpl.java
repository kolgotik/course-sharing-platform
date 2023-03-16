package com.example.webcustomertracker3.user;

import com.example.webcustomertracker3.dao.UserDAO;
import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;


    public User findByUsername(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username =: username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    @Override
    public List<Course> getCoursesByUserId(int userId) {
        Query query = entityManager.createQuery("SELECT c FROM Course c JOIN c.users u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public void deleteCourse(int courseId, int userId) {
        Query query = entityManager.createNativeQuery("DELETE FROM `user-has-course` WHERE course_id = :courseId AND user_id = :userId");
        query.setParameter("courseId", courseId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public boolean isUsernameUnique(String username) {
        Query query = entityManager.createQuery("SELECT u.username FROM User u");
        List<String> usernameList = query.getResultList();
        for (String name : usernameList) {
            if (username.equals(name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Query query = entityManager.createQuery("SELECT u.email FROM User u");
        List<String> emailList = query.getResultList();
        for (String emailFromList : emailList) {
            if (email.equals(emailFromList)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void createCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public List<User> getUsers() {
        Session session = entityManager.unwrap(Session.class);
        org.hibernate.query.Query<User> query = session.createQuery("FROM User", User.class);
        List<User> temp = query.getResultList();
        List<User> users = new ArrayList<>();
        for (User u : temp) {
            if (u.getAvatar() != null){
                users.add(u);
            }
        }
        return temp;

    }

    @Override
    public void updateUserAvatarFromComment(String oldFileName, String newFileName) {
        Session session = entityManager.unwrap(Session.class);
        org.hibernate.query.Query<Comment> query = session.createQuery("FROM Comment", Comment.class);

        List<Comment> comments = query.getResultList();
        for (Comment comment : comments) {
            if (Objects.equals(comment.getUser().getAvatar(), oldFileName)) {
                User user = comment.getUser();
                user.setAvatar(newFileName);
                entityManager.persist(user);
            }
        }
    }


    public void persist(User user) {
        entityManager.persist(user);
    }

    @Override
    public <S extends User> S save(S entity) {

        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }
}
