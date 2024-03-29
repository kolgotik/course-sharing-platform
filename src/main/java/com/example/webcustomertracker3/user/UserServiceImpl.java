package com.example.webcustomertracker3.user;


import com.example.webcustomertracker3.dao.UserDAO;
import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;



    public User findByUsername(String username) {
        User user = userDAO.findByUsername(username);
        return user;
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
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
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
    @Transactional
    public void persist(User user) {
        userDAO.persist(user);
    }

    @Override
    public User authenticate(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder().matches(password, user.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return user;
    }

    @Override
    public List<Course> getCoursesByUserId(int userId) {

        List<Course> courses = userDAO.getCoursesByUserId(userId);

        return courses;
    }

    @Override
    @Transactional
    public void deleteCourse(int courseId, int userId) {
        userDAO.deleteCourse(courseId, userId);
    }

    @Override
    @Transactional
    public boolean isUsernameUnique(String username) {
        return userDAO.isUsernameUnique(username);
    }

    @Override
    @Transactional
    public boolean isEmailUnique(String email) {
        return userDAO.isEmailUnique(email);
    }

    @Override
    @Transactional
    public void createCourse(Course course) {
        userDAO.createCourse(course);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void updateUserAvatarFromComment(String oldFileName, String newFileName) {
        userDAO.updateUserAvatarFromComment(oldFileName, newFileName);
    }

    @Override
    @Transactional
    public List<Course> searchCourse(String courseTitle) {
        return userDAO.searchCourse(courseTitle);
    }


    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
