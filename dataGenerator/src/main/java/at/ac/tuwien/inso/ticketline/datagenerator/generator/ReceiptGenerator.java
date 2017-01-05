package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.CashDao;
import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.ReceiptDao;
import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.TransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Carla on 17/05/2016.
 */
@Component
public class ReceiptGenerator implements DataGenerator{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptGenerator.class);

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CashDao cashDao;

    @Override
    public void generate() {
        /*Receipt receipt = new Receipt();
        receipt.setEmployee(employeeDao.findOne(1));
        receipt.setCustomer(customerDao.findOne(1));
        receipt.setMethodOfPayment(cashDao.findOne(1));
        receipt.setTransactionState(TransactionState.IN_PROCESS);
        receipt.setTransactionDate(getUtilDate("2016-06-07 00:00:00"));
        receipt.setPerformanceName("hallo");

        receiptDao.save(receipt);*/
    }

    public Date getUtilDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateUtil;
    }
}
