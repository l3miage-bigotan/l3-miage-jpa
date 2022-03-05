package fr.uga.im2ag.l3.miage.db.repository.impl;

import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.model.GraduationClass;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GraduationClassRepositoryImpl extends BaseRepositoryImpl implements GraduationClassRepository {

    public GraduationClassRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public GraduationClass findByYearAndName(Integer year, String name) {
        // TODO
        return (GraduationClass) entityManager.createQuery("Select g from GraduationClass g where g.name = '"+name+"' and g.year="+year).getSingleResult();
    }

    @Override
    public void save(GraduationClass entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(GraduationClass entity) {
        entityManager.remove(entity);
    }

    @Override
    public GraduationClass findById(Long id) {
        // TODO
        return entityManager.find(GraduationClass.class,id);
    }

    @Override
    public List<GraduationClass> getAll() {
        // TODO
        Query q = entityManager.createNamedQuery("GraduationClass.getAll");
        return q.getResultList();    }
}
