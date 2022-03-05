package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest extends Base {

    TeacherRepository teacherRepository;

    @BeforeEach
    void before() {
        teacherRepository = daoFactory.newTeacherRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveTeacher()  {
        // TODO
        final var classe = Fixtures.createClass();
        final var subject = Fixtures.createSubject();
        final var student =Fixtures.createStudent(classe);

        final var teacher = Fixtures.createTeacher(subject,classe,student);
        entityManager.getTransaction().begin();
        entityManager.persist(classe);
        entityManager.persist(subject);
        entityManager.persist(student);

        teacherRepository.save(teacher);
        entityManager.getTransaction().commit();
        entityManager.detach(teacher);

        var pTeacher = teacherRepository.findById(teacher.getId());
        assertThat(pTeacher).isNotNull().isNotSameAs(teacher);
        assertThat(pTeacher.getLastName()).isEqualTo(teacher.getLastName());
    }

    @Test
    void shouldFindHeadingGraduationClassByYearAndName() {
        final var classe = Fixtures.createClass();
        final var subject = Fixtures.createSubject();
        final var student =Fixtures.createStudent(classe);

        final var teacher = Fixtures.createTeacher(subject,classe,student);
        entityManager.getTransaction().begin();
        entityManager.persist(classe);
        entityManager.persist(subject);
        entityManager.persist(student);

        teacherRepository.save(teacher);
        entityManager.getTransaction().commit();
        entityManager.detach(teacher);
        var pTeacher = teacherRepository.findHeadingGraduationClassByYearAndName(classe.getYear(), classe.getName());
        assertThat(pTeacher).isNotNull().isNotSameAs(teacher);
        assertThat(pTeacher.getLastName()).isEqualTo(teacher.getLastName());
        // TODO
    }

}
