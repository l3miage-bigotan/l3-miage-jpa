package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubjectTest extends Base {

    SubjectRepository subjectRepository;

    @BeforeEach
    void before() {
        subjectRepository = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveSubject() {

        final var subject = Fixtures.createSubject();

        entityManager.getTransaction().begin();
        subjectRepository.save(subject);
        entityManager.getTransaction().commit();
        entityManager.detach(subject);

        var pSubject = subjectRepository.findById(subject.getId());
        assertThat(pSubject).isNotNull().isNotSameAs(subject);
        assertThat(pSubject.getName()).isEqualTo(subject.getName());

    }

    @Test
    void shouldFindTeachersForSubject() {
        // TODO
        final var subject = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();

        final var classe = Fixtures.createClass();

        final var teacher1 = Fixtures.createTeacher(subject,classe);
        final var teacher2 = Fixtures.createTeacher(subject,classe);
        final  var teacher3= Fixtures.createTeacher(subject2,classe);
        Collection<Teacher> Teachers = new ArrayList<>();
        Teachers.add(teacher1);
        Teachers.add(teacher2);
        Teachers.add(teacher3);

        entityManager.getTransaction().begin();
        subjectRepository.save(subject2);

        subjectRepository.save(subject);
        entityManager.persist(classe);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.persist(teacher3);

        entityManager.getTransaction().commit();
        entityManager.detach(subject);
        var pTeachers = subjectRepository.findTeachers(subject.getId());
        assertThat(Teachers).isNotNull().isNotSameAs(pTeachers);
        assertTrue(pTeachers.stream().anyMatch(s-> s.getLastName().equals(teacher1.getLastName())));
        assertTrue(pTeachers.stream().anyMatch(s-> s.getLastName().equals(teacher2.getLastName())));
        assertFalse(pTeachers.stream().anyMatch(s-> s.getLastName().equals(teacher3.getLastName())));
    }

}
