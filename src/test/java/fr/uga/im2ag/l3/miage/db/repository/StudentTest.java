package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest extends Base {

    StudentRepository studentRepository;

    @BeforeEach
    void before() {
        studentRepository = daoFactory.newStudentRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveStudent() {
        final var classe = Fixtures.createClass();
        final var student =Fixtures.createStudent(classe);

        entityManager.getTransaction().begin();
        entityManager.persist(classe);
        entityManager.persist(student);

        studentRepository.save(student);
        entityManager.getTransaction().commit();
        entityManager.detach(student);

        var pStudents = studentRepository.findById(student.getId());
        assertThat(pStudents).isNotNull().isNotSameAs(student);
        assertThat(pStudents.getLastName()).isEqualTo(student.getLastName());
        assertThat(pStudents.getFirstName()).isEqualTo(student.getFirstName());

        // TODO
    }

    @Test
    void shouldFindStudentHavingGradeAverageAbove() {

        final var classe = Fixtures.createClass();
        final var subject = Fixtures.createSubject();

        final var student1 =Fixtures.createStudent(classe);
        final  var note11 = Fixtures.createGrade(subject).setValue(20F).setWeight(1F);
        final  var note21 = Fixtures.createGrade(subject).setValue(10F).setWeight(3F);
        ArrayList<Grade> Grades1 = new ArrayList<>();
        Grades1.add(note11);
        Grades1.add(note21);
        student1.setGrades(Grades1);
        //moyenne 12.5
        final var student2 =Fixtures.createStudent(classe);
        final  var note12 = Fixtures.createGrade(subject).setValue(5F).setWeight(1F);
        final  var note22 = Fixtures.createGrade(subject).setValue(10F).setWeight(1F);
        ArrayList<Grade> Grades2 = new ArrayList<>();
        Grades2.add(note12);
        Grades2.add(note22);
        student2.setGrades(Grades2);
        //moyenne 7.5

        final var student3 =Fixtures.createStudent(classe);
        final  var note13 = Fixtures.createGrade(subject).setValue(20F).setWeight(2F);
        final  var note23 = Fixtures.createGrade(subject).setValue(5F).setWeight(1F);
        ArrayList<Grade> Grades3 = new ArrayList<>();
        Grades3.add(note13);
        Grades3.add(note23);
        student3.setGrades(Grades3);
        //moyenne 15

        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.persist(classe);
        entityManager.persist(note12);
        entityManager.persist(note22);
        entityManager.persist(note11);
        entityManager.persist(note21);
        entityManager.persist(note13);
        entityManager.persist(note23);
        entityManager.persist(classe);


        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        entityManager.getTransaction().commit();
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);


        //moyenne 15
        var pStudentsAbove7 = studentRepository.findStudentHavingGradeAverageAbove(7F);
        var pStudentsAbove12 = studentRepository.findStudentHavingGradeAverageAbove(12F);
        var pStudentsAbove14 = studentRepository.findStudentHavingGradeAverageAbove(14F);
        assertTrue(pStudentsAbove7.stream().anyMatch(s-> s.getLastName().equals(student1.getLastName())));
        assertTrue(pStudentsAbove7.stream().anyMatch(s-> s.getLastName().equals(student2.getLastName())));
        assertTrue(pStudentsAbove7.stream().anyMatch(s-> s.getLastName().equals(student3.getLastName())));

        assertTrue(pStudentsAbove12.stream().anyMatch(s-> s.getLastName().equals(student1.getLastName())));
        assertFalse(pStudentsAbove12.stream().anyMatch(s-> s.getLastName().equals(student2.getLastName())));
        assertTrue(pStudentsAbove12.stream().anyMatch(s-> s.getLastName().equals(student3.getLastName())));

        assertFalse(pStudentsAbove14.stream().anyMatch(s -> s.getLastName().equals(student1.getLastName())));
        assertFalse(pStudentsAbove14.stream().anyMatch(s -> s.getLastName().equals(student2.getLastName())));
        assertTrue(pStudentsAbove14.stream().anyMatch(s-> s.getLastName().equals(student3.getLastName())));

        // TODO
    }

}
