package fr.uga.im2ag.l3.miage.db.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.*;

// TODO ajouter une named query pour une des requÃªtes Ã  faire dans le repository
@Entity
@DiscriminatorValue("teacher")
@NamedQuery(name="Teacher.findAll", query="SELECT t FROM Teacher t")
public class Teacher extends Person {
    @ManyToOne
    private Subject teaching;
    @OneToMany
    private List<Student> favorites;
    @OneToOne
    private GraduationClass heading;

    public Subject getTeaching() {
        return teaching;
    }

    public Teacher setTeaching(Subject teaching) {
        this.teaching = teaching;
        return this;
    }

    public List<Student> getFavorites() {
        return favorites;
    }

    public Teacher setFavorites(List<Student> favorites) {
        this.favorites = favorites;
        return this;
    }

    public GraduationClass getHeading() {
        return heading;
    }

    public Teacher setHeading(GraduationClass heading) {
        this.heading = heading;
        return this;
    }

}
