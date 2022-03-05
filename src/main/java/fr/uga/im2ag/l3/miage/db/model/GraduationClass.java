package fr.uga.im2ag.l3.miage.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.*;

// TODO ajouter une named query pour une des requÃªtes Ã  faire dans le repository
@Entity
@NamedQuery(name="GraduationClass.findAll", query="SELECT g FROM GraduationClass g")
public class GraduationClass {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(nullable=false)
    private String name;
	@Column(name="annee")
    private Integer year;
    @OneToMany
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GraduationClass setId(Long id) {
        this.id = id;
        return this;
    }

    public GraduationClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public GraduationClass setYear(Integer year) {
        this.year = year;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public GraduationClass setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
