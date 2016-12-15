package com.solution.fromVC.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Влад on 09.11.2016.
 */
@Entity
@Table(name = "CATEGORY")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
        @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id"),
        @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name"),
        @NamedQuery(name = "Category.findByTags", query = "SELECT c FROM Category c WHERE c.tags = :tags")})
public class Category implements Serializable{

    private static final long serialVersionUID = -5400424750505982222L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 3, max = 45, message = "{category.name}")
    @Column(name = "NAME", nullable = false, length = 45)
    @Basic(optional = false)
    private String name;

    @Size(min = 3, max = 45, message = "{category.tags}")
    @Column(name = "TAGS")
    private String tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> productList;

    public Category() {
    }

    public Category(Integer id){
        this.id = id;
    }

    public Category(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName() + " [ID: " + id + "]";
    }
}
