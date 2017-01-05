package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Receipt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by dev on 24/05/16.
 */
public class ReceiptDaoImpl implements ReceiptDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Receipt> findReceiptByLastName(String lastname) {
        Query query = entityManager.createQuery("SELECT re FROM Receipt re " +
                "LEFT OUTER JOIN re.customer cu WHERE lower(cu.lastname) " +
                "LIKE Lower(concat('%',:firstname,'%') )");
        query.setParameter("firstname", lastname);
        return (List<Receipt>) query.getResultList();
    }
}
