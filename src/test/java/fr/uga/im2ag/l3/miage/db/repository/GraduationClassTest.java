package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GraduationClassTest extends Base {

    GraduationClassRepository classRepository;

    @BeforeEach
    void before() {
        classRepository = daoFactory.newGraduationClassRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveClass() {
        final var classe = Fixtures.createClass();
        entityManager.getTransaction().begin();

        classRepository.save(classe);
        entityManager.getTransaction().commit();
        entityManager.detach(classe);
        // TODO
        var pClasse = classRepository.findById(classe.getId());
        assertThat(pClasse).isNotNull().isNotSameAs(classe);
        assertThat(pClasse.getName()).isEqualTo(classe.getName());
        assertThat(pClasse.getYear()).isEqualTo(classe.getYear());

    }


    @Test
    void shouldFindByYearAndName() {
        final var classe = Fixtures.createClass();
        final var classe2 = Fixtures.createClass();

        entityManager.getTransaction().begin();

        classRepository.save(classe);
        classRepository.save(classe);

        entityManager.getTransaction().commit();
        entityManager.detach(classe);
        entityManager.detach(classe2);

        var pClasse = classRepository.findByYearAndName(classe.getYear(),classe.getName());
        assertThat(pClasse).isNotNull().isNotSameAs(classe);
        assertThat(pClasse.getId()).isEqualTo(classe.getId());
        assertThat(pClasse.getName()).isEqualTo(classe.getName());
        assertThat(pClasse.getYear()).isEqualTo(classe.getYear());

    }

}
