package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.Column;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;


// TODO ajouter une named query pour une des requÃªtes Ã  faire dans le repository
@Entity
@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s")

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(unique=true)
    private String name;
    private Integer points;
    private Float hours;
    private Date start;

    @Column(name = "end_date")
    private Date end;

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public Subject setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public Float getHours() {
        return hours;
    }

    public Subject setHours(Float hours) {
        this.hours = hours;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public Subject setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public Subject setEnd(Date end) {
        this.end = end;
        return this;
        
    }
}
