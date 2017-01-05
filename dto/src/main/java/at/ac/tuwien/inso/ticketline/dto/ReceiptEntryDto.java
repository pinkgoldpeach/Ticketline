package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * This class is used for a specific receipt entry
 */
@ApiModel(value = "ReceiptEntryDto", description = "A specific receipt entry")
public class ReceiptEntryDto {

    @ApiModelProperty(value = "ID of the receipt entry")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Postion of the receipt entry", required = true)
    private Integer position;

    @NotNull
    @ApiModelProperty(value = "Amount of the receipt entry", required = true)
    private Integer amount;

    @NotNull
    @ApiModelProperty(value = "Unit price of the receipt entry", required = true)
    private Double unitPrice;

    @NotNull
    @ApiModelProperty(value = "Receipt of the receipt entry", required = true)
    private ReceiptDto receiptDto;

    @ApiModelProperty(value = "Ticket ID which is referenced in the receipt entry")
    private Integer ticketID;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ReceiptDto getReceiptDto() {
        return receiptDto;
    }

    public void setReceiptDto(ReceiptDto receiptDto) {
        this.receiptDto = receiptDto;
    }

    public Integer getTicketId() {
        return ticketID;
    }

    public void setTicketId(Integer ticketID) {
        this.ticketID = ticketID;
    }
}
