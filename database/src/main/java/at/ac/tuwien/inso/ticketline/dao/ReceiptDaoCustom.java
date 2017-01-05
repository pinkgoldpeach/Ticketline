package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Receipt;

import java.util.List;

/**
 * Created by dev on 24/05/16.
 */
public interface ReceiptDaoCustom {
    /**
     * Find receipts by their customer's last name
     *
     * @param lastname lastname from the customer
     * @return list of receipt from specific customer
     */
    List<Receipt> findReceiptByLastName(String lastname);
}

