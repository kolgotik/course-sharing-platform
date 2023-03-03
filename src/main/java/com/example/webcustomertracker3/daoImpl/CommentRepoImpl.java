package com.example.webcustomertracker3.daoImpl;

import com.example.webcustomertracker3.dao.CommentRepo;
import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CommentRepoImpl implements CommentRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void persist(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getAllComments(int courseId) {

        Query query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.course.id = :courseId");
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Comment> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Comment> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Comment getOne(Integer integer) {
        return null;
    }

    @Override
    public Comment getById(Integer integer) {
        return null;
    }

    @Override
    public Comment getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Comment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Comment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Comment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Comment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Comment> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Comment> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findAllById(Iterable<Integer> integers) {
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
    public void delete(Comment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Comment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Comment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return null;
    }



}
