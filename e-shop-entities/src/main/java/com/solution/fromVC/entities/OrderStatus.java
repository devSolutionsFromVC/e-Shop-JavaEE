package com.solution.fromVC.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "ORDER_STATUS")
@NamedQueries({
        @NamedQuery(name = "OrderStatus.findAll", query = "SELECT o FROM OrderStatus o"),
        @NamedQuery(name = "OrderStatus.findById", query = "SELECT o FROM OrderStatus o WHERE o.id = :id"),
        @NamedQuery(name = "OrderStatus.findByStatus", query = "SELECT o FROM OrderStatus o WHERE o.status = :status")})
public class OrderStatus implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "STATUS")
    @Size(min = 3, max = 45, message = "{order.status}")
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStatus")
    private List<CustomerOrder> customerOrderList;

    @Basic(optional = false)
    @Size(min = 0, max = 200, message = "Description has maximum of 200 characters")
    @Column(name = "DESCRIPTION")
    private String description;

    public OrderStatus() {
    }

    public OrderStatus(Integer id) {
        this.id = id;
    }

    public OrderStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrderStatus)) {
            return false;
        }
        OrderStatus other = (OrderStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.solution.fromVC.entities.OrderStatus[id=" + id + "]";
    }
}
