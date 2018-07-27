package com.solution.fromVC.entities;

import javax.persistence.Entity;
import java.util.ArrayList;


@Entity
public class Administrator extends Person{


    public Administrator(){
        this.groupsList =  new ArrayList<Groups>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.solution.fromVC.entities.Administrator[ id=" + id + " ]";
    }
}

