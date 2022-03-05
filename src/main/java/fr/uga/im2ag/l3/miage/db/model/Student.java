package fr.uga.im2ag.l3.miage.db.model;

import java.util.List;

import javax.persistence.*;

// TODO ajouter une named query pour une des requÃªtes Ã  faire dans le repository
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")

@Entity
@DiscriminatorValue("student")
public class Student extends Person {
	@ManyToOne
    private GraduationClass belongTo;
    @OneToMany
    private List<Grade> grades;

    public GraduationClass getBelongTo() {
        return belongTo;
    }

    public Student setBelongTo(GraduationClass belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Student setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }
}

