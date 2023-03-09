package com.example.webcustomertracker3.daoImpl;

import com.example.webcustomertracker3.dao.StudentDAO;
import com.example.webcustomertracker3.entity.Course;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Course> getCourses() {

        Session session = entityManager.unwrap(Session.class);

        Query<Course> query = session.createQuery("from Course", Course.class);

        List<Course> courseList = query.getResultList();

        return courseList;
    }

    @Override
    public Course getCourse(int id) {

        Session session = entityManager.unwrap(Session.class);

        Course course = session.get(Course.class, id);

        return course;
    }

    @Override
    public void addCourses(int id) {
        Session session = entityManager.unwrap(Session.class);

        Course course = session.get(Course.class, id);

        session.persist(course);
    }

    @Override
    public void updateCourse(Course course) {
        Session session = entityManager.unwrap(Session.class);
        Course courseToUpdate = session.get(Course.class, course.getId());
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setContent(course.getContent());

        session.persist(courseToUpdate);
    }


}



