package com.solution.fromVC.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="PERSON")
@NamedQueries({
        @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
        @NamedQuery(name = "Person.findByFirstname", query = "SELECT p FROM Person p WHERE p.firstname = :firstname"),
        @NamedQuery(name = "Person.findByLastname", query = "SELECT p FROM Person p WHERE p.lastname = :lastname"),
        @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
        @NamedQuery(name = "Person.findByAddress", query = "SELECT p FROM Person p WHERE p.address = :address"),
        @NamedQuery(name = "Person.findByCity", query = "SELECT p FROM Person p WHERE p.city = :city")
})
public class Person implements Serializable{


    @JoinTable(name = "PERSON_GROUPS", joinColumns = {
        @JoinColumn(name = "PERSON_EMAIL", referencedColumnName = "EMAIL")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUPS_NAME", referencedColumnName = "NAME")})
    @ManyToMany
    List<Groups> groupsList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;

    @Column(name = "FIRSTNAME")
    @Basic(optional = false)
    @Size(min = 3, max = 50, message = "{person.firstname}")
    protected String firstname;

    @Column(name = "LASTNAME")
    @Basic(optional = false)
    @Size(min = 3, max = 100, message = "{person.lastname}")
    protected String lastname;

    @Column(name = "EMAIL")
    @Basic(optional = false)
    @Size(min = 3, max = 45, message = "{person.email}")
    @Pattern(regexp = ".+@.+\\\\.[a-z]+", message = "{person.email}")
    protected String email;

    @Column(name = "ADDRESS")
    @Basic(optional = false)
    @Size(min = 3, max = 45, message = "person.address")
    protected String address;

    @Column(name = "CITY")
    @Basic(optional = false)
    @Size(min = 3, max = 45, message = "{person.city}")
    protected String city;

    @Column(name = "PASSWORD")
    @Basic(optional = false)
    @Size(min = 7, max = 100, message = "{person.password}")
    protected String password;

    public Person() {
        this.groupsList = new ArrayList<Groups>();
    }

    public Person(List<Groups> listGroup,
                  String firstname,
                  String lastname,
                  String email,
                  String address,
                  String city,
                  String password) {
        this.groupsList = listGroup;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.password = password;
    }

    public List<Groups> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Groups> groupsList) {
        this.groupsList = groupsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.solution.fromVC.entities.Person[ id=" + id + " ]";
    }

}
