package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.Category;
import com.solution.fromVC.entities.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 21.11.2016.
 */
@Stateless
public class ProductBean extends AbstractFacade<Product>{

    private static final Logger logger = Logger.getLogger(ProductBean.class.getCanonicalName());

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductBean() {
        super(Product.class);
    }

    public List<Product> findByCategory(int[] range, int categoryId){
        Category category = new Category();
        category.setId(categoryId);

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);
        query.where(cb.equal(product.get("category"), category));
        List<Product> result = this.findRange(range, query);

        logger.log(Level.FINEST, "Product List size: {0}", result.size());

        return result;
    }
}
