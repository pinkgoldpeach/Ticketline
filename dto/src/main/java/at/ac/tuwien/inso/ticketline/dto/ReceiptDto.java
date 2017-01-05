package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * This class is used for the receipt
 */
@ApiModel(value = "ReceiptDto", description = "The Receipt of the purchase")
public class ReceiptDto {

    @ApiModelProperty(value = "ID of the receipt")
    private Integer id;


    @ApiModelProperty(value = "Name of the Performance")
    @Size(max = 100)
    private String performanceName;

    @ApiModelProperty(value = "Transaction date of the receipt")
    private Date transactionDate;

    @NotNull
    @ApiModelProperty(value = "Transaction state of the receipt")
    private TransactionStateDto transactionState;

    @NotNull
    @ApiModelProperty(value = "Customer of the transaction", required = true)
    private CustomerDto customerDto;

    @ApiModelProperty(value = "Employee who does the transaction")
    private EmployeeDto employeeDto;

    @NotNull
    @ApiModelProperty(value = "Method payment of the transaction", required = true)
    private MethodOfPaymentDto methodOfPaymentDto;

    @ApiModelProperty(value = "Receipt entries of the receipt")
    private List<ReceiptEntryDto> receiptEntryDtos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionStateDto getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(TransactionStateDto transactionState) {
        this.transactionState = transactionState;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public EmployeeDto getEmployeeDto() {
        return employeeDto;
    }

    public void setEmployeeDto(EmployeeDto employeeDto) {
        this.employeeDto = employeeDto;
    }

    public MethodOfPaymentDto getMethodOfPaymentDto() {
        return methodOfPaymentDto;
    }

    public void setMethodOfPaymentDto(MethodOfPaymentDto methodOfPaymentDto) {
        this.methodOfPaymentDto = methodOfPaymentDto;
    }

    public List<ReceiptEntryDto> getReceiptEntryDtos() {
        return receiptEntryDtos;
    }

    public void setReceiptEntryDtos(List<ReceiptEntryDto> receiptEntryDtos) {
        this.receiptEntryDtos = receiptEntryDtos;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }
}
