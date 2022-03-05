package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GradeTest extends Base {

    GradeRepository gradeRepository;

    @BeforeEach
    void before() {
        gradeRepository = daoFactory.newGradeRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveGrade() {
        final var subject = Fixtures.createSubject();

        final var grade = Fixtures.createGrade(subject);

        entityManager.getTransaction().begin();
        gradeRepository.save(grade);
        entityManager.persist(subject);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        Assertions.assertThat(pGrade).isNotNull().isNotSameAs(grade);
        Assertions.assertThat(pGrade.getValue()).isEqualTo(grade.getValue());
        Assertions.assertThat(pGrade.getWeight()).isEqualTo(grade.getWeight());

    }

    @Test
    void shouldFailUpgradeGrade() {
        final var subject = Fixtures.createSubject();

        final var grade = Fixtures.createGrade(subject);
        grade.setValue(15F);

        entityManager.getTransaction().begin();
        gradeRepository.save(grade);
        entityManager.persist(subject);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        final var gradeUpdated = gradeRepository.findById(grade.getId());
        entityManager.getTransaction().begin();
        entityManager.createQuery("Update Grade set grade = 17.0 where id = "+grade.getId()).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        assertEquals(15F,gradeRepository.findById(grade.getId()).getValue());
        // TODO, ici tester que la mise Ã  jour n'a pas eu lieu.

    }

    @Test
    void shouldFindHighestGrades() {
        // TODO
        final var subject = Fixtures.createSubject();
        final var grade1 = Fixtures.createGrade(subject);
        grade1.setValue(1F);
        final var grade2 = Fixtures.createGrade(subject);
        grade2.setValue(2F);
        final var grade3 = Fixtures.createGrade(subject);
        grade3.setValue(3F);
        final var grade4 = Fixtures.createGrade(subject);
        grade4.setValue(4F);

        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);

        entityManager.getTransaction().commit();
        entityManager.detach(subject);

        var pGrades = gradeRepository.findHighestGrades(2);
        assertEquals(2, pGrades.size());
        assertEquals(4F, pGrades.get(0).getValue());
        assertEquals(3F, pGrades.get(1).getValue());


    }

    @Test
    void shouldFindHighestGradesBySubject() {

        final var subject = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();

        final var grade1 = Fixtures.createGrade(subject);
        grade1.setValue(1F);
        final var grade2 = Fixtures.createGrade(subject);
        grade2.setValue(2F);
        final var grade3 = Fixtures.createGrade(subject);
        grade3.setValue(3F);
        final var grade4 = Fixtures.createGrade(subject2);
        grade4.setValue(4F);
        final var grade5 = Fixtures.createGrade(subject2);
        grade5.setValue(5F);
        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.persist(subject2);

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        gradeRepository.save(grade5);

        entityManager.getTransaction().commit();
        entityManager.detach(subject);

        var pGrades = gradeRepository.findHighestGradesBySubject(3, subject);
        var pGrades2 = gradeRepository.findHighestGradesBySubject(2, subject2);

        assertEquals(3, pGrades.size());
        assertEquals(3F, pGrades.get(0).getValue());
        assertEquals(2F, pGrades.get(1).getValue());
        assertEquals(1F, pGrades.get(2).getValue());

        assertEquals(2, pGrades2.size());
        assertEquals(5F, pGrades2.get(0).getValue());
        assertEquals(4F, pGrades2.get(1).getValue());
    }
}
