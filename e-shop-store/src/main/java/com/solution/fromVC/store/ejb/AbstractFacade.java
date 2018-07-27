package com.solution.fromVC.store.ejb;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Влад on 17.11.2016.
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade() {
    }

    public AbstractFacade(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity){
        getEntityManager().persist(entity);
    }

    public void edit(T entity){
        getEntityManager().merge(entity);
    }

    public T find(Object id){
       return getEntityManager().find(entityClass, id);
    }

    public void remove(T entity){
        getEntityManager().remove(entity);
    }

    public List<T> findAll(){
        CriteriaQuery query = getEntityManager().getCriteriaBuilder().createQuery();
        query.select(query.from(entityClass));
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<T> findRange(int[] range){
        CriteriaQuery query = getEntityManager().getCriteriaBuilder().createQuery();
        Query q = getEntityManager().createQuery(query.select(query.from(entityClass)));
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findRange(int[] range, CriteriaQuery query) {
        Query q = getEntityManager().createQuery(query);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }



}
