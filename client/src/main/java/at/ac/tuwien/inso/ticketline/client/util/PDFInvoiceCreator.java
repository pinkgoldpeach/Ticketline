package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.gui.controller.LandingPageController;
import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class offers a public method createPDF which will create an Invoice as PDF and save it
 * to the local PC
 *
 * @author Christoph Hafner 1326088
 */
@Component
public class PDFInvoiceCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LandingPageController.class);
    private PDFont fontPlain = PDType1Font.HELVETICA;
    private PDFont fontBold = PDType1Font.HELVETICA_BOLD;
    private PDDocument document;

    /**
     * Method which prints a new Invoice for the given receipt
     *
     * @param receiptDto  a full receiptDto containing all informations from which a invoice should
     *                    be created
     * @param tickets     a Hashmap containing all tickets from the receipt with the ticketid as key and a ticketDTO as value
     * @param cancelation true if this is a cancelation invoice, false otherwise
     * @throws ServiceException will be thrown if a IOException will happen while creating the file
     *                          or if a needed value is null
     */
    public void createPdf(ReceiptDto receiptDto, HashMap<Integer, TicketDto> tickets, boolean cancelation) throws ServiceException {
        LOGGER.info("Creating PDF! Cancelation: " + cancelation);
        try {
            document = new PDDocument();
            List<ReceiptEntryDto> receiptEntryDtoList = receiptDto.getReceiptEntryDtos();
            ArrayList<ReceiptEntryDto> realReceiptEntryDtoList = new ArrayList<>();

            int count = 0;
            for (Map.Entry<Integer, TicketDto> entry : tickets.entrySet()) {
                if (cancelation && !(entry.getValue().getValid())) {
                    count++;
                    for (int i = 0; i < receiptEntryDtoList.size(); i++) {
                        if (receiptEntryDtoList.get(i).getTicketId() == entry.getKey()) {
                            realReceiptEntryDtoList.add(receiptEntryDtoList.get(i));
                        }
                    }
                } else if (!cancelation && entry.getValue().getValid()) {
                    count++;
                    for (int i = 0; i < receiptEntryDtoList.size(); i++) {
                        if (receiptEntryDtoList.get(i).getTicketId() == entry.getKey()) {
                            realReceiptEntryDtoList.add(receiptEntryDtoList.get(i));
                        }
                    }
                }
            }

            int pagesForProducts = (int) Math.ceil((((double) count / 20.0)));
            int productsOnLastPage = count - (pagesForProducts - 1) * 20;

            int realPageNumber;
            boolean onNewPage;
            if (productsOnLastPage < 17) {
                onNewPage = false;
                realPageNumber = pagesForProducts;
            } else {
                onNewPage = true;
                realPageNumber = pagesForProducts + 1;
            }

            int startProduct = 0;
            int endProduct;
            int toShow = 0;
            for (int i = 0; i < pagesForProducts; i++) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                createPlainDocumentPage(contentStream, page, i + 1, realPageNumber, receiptDto, cancelation);
                if ((i + 1) == pagesForProducts) {
                    endProduct = startProduct + productsOnLastPage;
                    toShow = productsOnLastPage;
                } else {
                    endProduct = startProduct + 20;
                    toShow = 20;
                }

                createProductTable(contentStream, page, realReceiptEntryDtoList.subList(startProduct, endProduct), tickets, cancelation, toShow);
                startProduct = endProduct;

                if ((i + 1) == pagesForProducts && !onNewPage) {
                    createFinalPriceInformation(contentStream, page, realReceiptEntryDtoList, productsOnLastPage, tickets, cancelation);
                }

                contentStream.close();
            }

            if (onNewPage) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                createPlainDocumentPage(contentStream, page, realPageNumber, realPageNumber, receiptDto, cancelation);
                createFinalPriceInformation(contentStream, page, realReceiptEntryDtoList, 0, tickets, cancelation);
                contentStream.close();
            }

            String invoiceNr = String.format(String.format("%06d", receiptDto.getId()));
            if (cancelation) {
                document.save(System.getProperty("user.home") + "/CancelationInvoice_C-" + invoiceNr + ".pdf");
            } else {
                document.save(System.getProperty("user.home") + "/Invoice_I-" + invoiceNr + ".pdf");
            }
        } catch (IOException e) {
            LOGGER.error("IOException in PDFCreator " + e.getMessage());
            throw new ServiceException(BundleManager.getBundle().getString("pdf.exception.serviceException") + e.getMessage());
        } catch (NullPointerException e) {
            LOGGER.error("Nullpointer Exception in PDFCreator " + e.getMessage()+" \n"+e.getStackTrace());
            throw new ServiceException(BundleManager.getBundle().getString("pdf.exception.serviceException") + e.getMessage());
        } finally {
            if(document != null) {
                try {
                    document.close();
                } catch (IOException e){
                    //Can not handle this!
                }
            }
        }
    }

    /**
     * Method which will draw a table to the page with the given content
     *
     * @param page          page on which the table should be drawn
     * @param contentStream the stream to use to draw the table to the page
     * @param y             position on page from where the table will be drawn
     * @param margin        margin on left and right of the table
     * @param content       values which should be drawn to the table
     * @throws IOException will be thrown if something goes wrong with the streams
     */
    private void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException {
        int rows = content.length;
        int cols = content[0].length;
        float rowHeight = 20f;
        float tableWidth = page.getCropBox().getWidth() - margin - margin;
        float tableHeight = rowHeight * rows;
        float colWidth = tableWidth / (float) cols;
        float cellMargin = 5f;

        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            nexty -= rowHeight;
        }

        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y - tableHeight);
            nextx += colWidth;
        }

        float textx = margin + cellMargin;
        float texty = y - 15;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                if (i == 0) {
                    contentStream.setFont(fontBold, 12);
                } else {
                    contentStream.setFont(fontPlain, 12);
                }
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx, texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    }

    /**
     * Method which will create a plain page containing information about the company
     * the customer and the invoice. All pages on the Invoice should have this style
     *
     * @param contentStream     the stream to use to draw the table to the page
     * @param page              page on which the table should be drawn
     * @param pagenumberCurrent pagenumber which is currently drawn
     * @param pageNumberTotal   number of pages in total
     * @param receiptDto        the valid receiptDto containing all information about the receipt
     * @param cancelation       true if this is a cancellation invoice, false otherwise
     * @throws IOException will be thrown if something goes wrong with the streams
     */
    private void createPlainDocumentPage(PDPageContentStream contentStream, PDPage page, int pagenumberCurrent,
                                         int pageNumberTotal, ReceiptDto receiptDto, boolean cancelation) throws IOException {
        /* Print Title */
        PDRectangle rect = page.getMediaBox();
        contentStream.beginText();
        contentStream.setFont(fontBold, 25);
        if (cancelation) {
            contentStream.newLineAtOffset(rect.getWidth() - 250, rect.getHeight() - 40);
            contentStream.showText(BundleManager.getBundle().getString("pdf.cancelationInvoice"));
        } else {
            contentStream.newLineAtOffset(rect.getWidth() - 140, rect.getHeight() - 40);
            contentStream.showText(BundleManager.getBundle().getString("pdf.invoice"));
        }
        contentStream.endText();

        /*Print company information */
        ArrayList<String> companyInformation = getCompanyInformation();
        for (int i = 0; i < companyInformation.size(); i++) {
            String text = companyInformation.get(i);
            contentStream.beginText();
            contentStream.setFont(fontPlain, 12);
            contentStream.newLineAtOffset(10, rect.getHeight() - 15 * (i + 1));
            contentStream.showText(text);
            contentStream.endText();
        }

        /*Print customer information*/
        ArrayList<String> customerInformation = getCustomerInformation(receiptDto.getCustomerDto());
        for (int i = 0; i < customerInformation.size(); i++) {
            String text = customerInformation.get(i);
            if (i == 0) {
                contentStream.setFont(fontBold, 12);
            } else {
                contentStream.setFont(fontPlain, 12);
            }
            contentStream.beginText();
            contentStream.newLineAtOffset(rect.getWidth() - 200, (rect.getHeight() - 75) - 15 * (i + 1));
            contentStream.showText(text);
            contentStream.endText();
        }

        /*Print invoice information*/
        String[][] invoiceInformation;
        if (cancelation) {
            invoiceInformation = new String[2][5];
            invoiceInformation[0][0] = BundleManager.getBundle().getString("pdf.cancelationNumber");
            invoiceInformation[0][1] = BundleManager.getBundle().getString("pdf.invoiceDate");
            invoiceInformation[0][2] = BundleManager.getBundle().getString("pdf.show");
            invoiceInformation[0][3] = BundleManager.getBundle().getString("pdf.payedBackBy");
            invoiceInformation[0][4] = BundleManager.getBundle().getString("pdf.payedBackWith");

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            invoiceInformation[1][0] = "C-" + String.format(String.format("%06d", receiptDto.getId()));
            invoiceInformation[1][1] = formatter.format(receiptDto.getTransactionDate());
            invoiceInformation[1][2] = receiptDto.getPerformanceName();
            invoiceInformation[1][3] = receiptDto.getEmployeeDto().getFirstName() + " " + receiptDto.getEmployeeDto().getLastName();
            if (receiptDto.getMethodOfPaymentDto().getStripeDto() == null) {
                invoiceInformation[1][4] = "Cash";
            } else {
                invoiceInformation[1][4] = "Stripe";
            }
        } else {
            invoiceInformation = new String[2][6];
            invoiceInformation[0][0] = BundleManager.getBundle().getString("pdf.invoiceNumber");
            invoiceInformation[0][1] = BundleManager.getBundle().getString("pdf.invoiceDate");
            invoiceInformation[0][2] = BundleManager.getBundle().getString("pdf.show");
            invoiceInformation[0][3] = BundleManager.getBundle().getString("pdf.status");
            invoiceInformation[0][4] = BundleManager.getBundle().getString("pdf.soldBy");
            invoiceInformation[0][5] = BundleManager.getBundle().getString("pdf.payedWith");

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            invoiceInformation[1][0] = "I-" + String.format(String.format("%06d", receiptDto.getId()));
            invoiceInformation[1][1] = formatter.format(receiptDto.getTransactionDate());
            invoiceInformation[1][2] = receiptDto.getPerformanceName();
            if(receiptDto.getTransactionState().equals(TransactionStateDto.CANCELLED_POSITIONS)){
                invoiceInformation[1][3] = TransactionStateDto.PAID.toString();
            } else {
                invoiceInformation[1][3] = receiptDto.getTransactionState().toString();
            }

            invoiceInformation[1][4] = receiptDto.getEmployeeDto().getFirstName() + " " + receiptDto.getEmployeeDto().getLastName();
            if (receiptDto.getMethodOfPaymentDto().getStripeDto() == null) {
                invoiceInformation[1][5] = "Cash";
            } else {
                invoiceInformation[1][5] = "Stripe";
            }


        }

        drawTable(page, contentStream, rect.getHeight() - 230, 10, invoiceInformation);

        /* Print thanks*/
        String thanksText = BundleManager.getBundle().getString("pdf.thanks");
        contentStream.setFont(fontBold, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(20, 50);
        contentStream.showText(thanksText);
        contentStream.endText();

        /*Print price information*/
        String priceInformation = BundleManager.getBundle().getString("pdf.price");
        contentStream.setFont(fontBold, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(20, 30);
        contentStream.showText(priceInformation);
        contentStream.endText();

        /* Print page number*/
        String pageInformation = BundleManager.getBundle().getString("pdf.page") + " " + pagenumberCurrent + " " + BundleManager.getBundle().getString("pdf.of") + " " + pageNumberTotal;
        contentStream.setFont(fontPlain, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset((rect.getWidth() / 2) - 40, 10);
        contentStream.showText(pageInformation);
        contentStream.endText();
    }

    /**
     * Methods which will print the product table to the page
     *
     * @param contentStream the stream to use to draw the table to the page
     * @param page          page on which the table should be drawn
     * @param entry         a List of products which should be drawn to the table
     * @param tickets       a Hashmap containing all tickets of the receipt, with id as key and ticketDTO as value
     * @param cancelation   true if this is a cancelation invoice, false otherwise
     * @param realHight     the real count of rows in the producttable
     * @throws IOException will be thrown if something goes wrong with the streams
     */
    private void createProductTable(PDPageContentStream contentStream, PDPage page, List<ReceiptEntryDto> entry,
                                    HashMap<Integer, TicketDto> tickets, boolean cancelation, int realHight) throws IOException {
        String[][] array = new String[realHight + 1][4];

        array[0][0] = BundleManager.getBundle().getString("pdf.description");
        array[0][1] = BundleManager.getBundle().getString("pdf.quantity");
        array[0][2] = BundleManager.getBundle().getString("pdf.priceEach");
        array[0][3] = BundleManager.getBundle().getString("pdf.totalPrice");

        int row = 1;
        for (Integer i = 1; i < entry.size() + 1; i++) {
            if (cancelation) {
                if (!(tickets.get(entry.get(i - 1).getTicketId()).getValid())) {
                    String descriptionText = "";
                    if (tickets.get(entry.get(i - 1).getTicketId()).getArea() != null) {
                       AreaDto area = tickets.get(entry.get(i - 1).getTicketId()).getArea();
                        descriptionText = String.valueOf(area.getAreaType());
                    } else {
                        SeatDto seat = tickets.get(entry.get(i - 1).getTicketId()).getSeat();
                        descriptionText = String.valueOf(seat.getDescription() + " No. " + seat.getOrder());
                    }

                    if (descriptionText.length() > 18) {
                        descriptionText = descriptionText.substring(0, 18);
                    }
                    array[row][0] = descriptionText;
                    array[row][1] = String.valueOf(entry.get(i - 1).getAmount());
                    array[row][2] = "- " + String.valueOf(entry.get(i - 1).getUnitPrice().toString());
                    array[row][3] = "- " + String.valueOf(entry.get(i - 1).getAmount() * entry.get(i - 1).getUnitPrice());
                    row++;
                }
            } else {
                if (tickets.get(entry.get(i - 1).getTicketId()).getValid()) {
                    String descriptionText = "";
                    if (tickets.get(entry.get(i - 1).getTicketId()).getArea() != null) {
                        AreaDto area = tickets.get(entry.get(i - 1).getTicketId()).getArea();
                        descriptionText = String.valueOf(area.getAreaType());
                    } else {
                        SeatDto seat = tickets.get(entry.get(i - 1).getTicketId()).getSeat();
                        descriptionText = String.valueOf(seat.getDescription() + " No. " + seat.getOrder());
                    }
                    if (descriptionText.length() > 24) {
                        descriptionText = descriptionText.substring(0, 24);
                    }
                    array[row][0] = descriptionText;
                    array[row][1] = String.valueOf(entry.get(i - 1).getAmount());
                    array[row][2] = String.valueOf(entry.get(i - 1).getUnitPrice().toString());
                    array[row][3] = String.valueOf(entry.get(i - 1).getAmount() * entry.get(i - 1).getUnitPrice());
                    row++;
                }
            }
        }

        drawTable(page, contentStream, page.getMediaBox().getHeight() - 300, 10, array);
    }

    /**
     * A method which will print final finance informations to the of the invoice
     *
     * @param contentStream      the stream to use to draw the table to the page
     * @param page               page on which the table should be drawn
     * @param entrys             all entrys of the invoice to calculate the final price
     * @param productsOnLastPage count of products on the last page, which is needed to place the content
     *                           on the right position
     * @param tickets            a Hashmap containing all tickets of the receipt, with id as key and ticketDTO as value
     * @param cancelation        true if this is a cancelation invoice, false otherwise
     * @throws IOException will be thrown if something goes wrong with the streams
     */
    private void createFinalPriceInformation(PDPageContentStream contentStream, PDPage page, List<ReceiptEntryDto> entrys, int productsOnLastPage,
                                             HashMap<Integer, TicketDto> tickets, boolean cancelation) throws IOException {
        ArrayList<String> information = new ArrayList<>();
        ArrayList<String> priceValues = new ArrayList<>();

        if (cancelation) {
            information.add(BundleManager.getBundle().getString("pdf.total"));
            information.add(BundleManager.getBundle().getString("pdf.paidBack"));
            information.add(BundleManager.getBundle().getString("pdf.remaining"));
            double totalBack = 0;
            for (int i = 0; i < entrys.size(); i++) {
                if (!(tickets.get(entrys.get(i).getTicketId()).getValid())) {
                    totalBack += (entrys.get(i).getUnitPrice() * entrys.get(i).getAmount());
                }
            }
            totalBack = Math.round(totalBack * 100);
            totalBack = totalBack / 100;

            priceValues.add("- " + String.valueOf(totalBack));
            priceValues.add(String.valueOf(totalBack));
            priceValues.add(String.valueOf(totalBack - totalBack));
        } else {
            information.add(BundleManager.getBundle().getString("pdf.totalWithTax"));
            information.add(BundleManager.getBundle().getString("pdf.tax"));
            information.add(BundleManager.getBundle().getString("pdf.totalWithoutTax"));
            information.add(BundleManager.getBundle().getString("pdf.paid"));
            information.add(BundleManager.getBundle().getString("pdf.remaining"));
            double totalPrice = 0;
            for (int i = 0; i < entrys.size(); i++) {
                if (tickets.get(entrys.get(i).getTicketId()).getValid()) {
                    totalPrice += (entrys.get(i).getUnitPrice() * entrys.get(i).getAmount());
                }
            }

            double withoutTax = (totalPrice / 1.2);
            double payed = totalPrice;
            double tax = totalPrice - withoutTax;

            totalPrice = Math.round(totalPrice * 100);
            totalPrice = totalPrice / 100;
            withoutTax = Math.round(withoutTax * 100);
            withoutTax = withoutTax / 100;
            tax = Math.round(tax * 100);
            tax = tax / 100;

            priceValues.add(String.valueOf(totalPrice));
            priceValues.add(String.valueOf(tax));
            priceValues.add(String.valueOf(withoutTax));
            priceValues.add(String.valueOf(totalPrice));
            priceValues.add(String.valueOf(totalPrice - totalPrice));
        }

        int lineInformation = 1;
        for (int i = 0; i < information.size(); i++) {
            String text = information.get(i);
            contentStream.setFont(fontBold, 12);
            contentStream.beginText();
            if (productsOnLastPage == 0) {
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 240, page.getMediaBox().getHeight() - 300 - 15 * lineInformation);
            } else {
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 240, page.getMediaBox().getHeight() - 330 - (productsOnLastPage * 20) - 15 * lineInformation);
            }
            contentStream.showText(text);
            contentStream.endText();
            lineInformation++;
        }

        int linePriceInformation = 1;
        for (int i = 0; i < priceValues.size(); i++) {
            String text1 = priceValues.get(i);
            contentStream.setFont(fontBold, 12);
            contentStream.beginText();
            if (productsOnLastPage == 0) {
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 80, page.getMediaBox().getHeight() - 300 - 15 * linePriceInformation);
            } else {
                contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 80, page.getMediaBox().getHeight() - 330 - (productsOnLastPage * 20) - 15 * linePriceInformation);
            }
            contentStream.showText(text1);
            contentStream.endText();
            linePriceInformation++;
        }

    }

    /**
     * Returns an arrayList with company information
     *
     * @return an ArrayList with the company information as text
     */
    private ArrayList<String> getCompanyInformation() {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add("");
        returnList.add("Ticketline GmbH");
        returnList.add("Somestreet 20/10/2");
        returnList.add("1234 Somecity");
        returnList.add("Tel: 0234/34534234");
        returnList.add("Email: support@ticketline.to");
        returnList.add("UUID: 0123456789");
        return returnList;
    }

    /**
     * Returns the customer Information as an arraylist for further use
     *
     * @param customerDto a valid customerDto containing all needed informations
     * @return an ArrayList with the customer Information as a text
     */
    private ArrayList<String> getCustomerInformation(CustomerDto customerDto) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(BundleManager.getBundle().getString("pdf.customerInformation"));
        returnList.add(customerDto.getFirstName() + " " + customerDto.getLastName());
        if (customerDto.getFirstName().equals("Anonymous") && customerDto.getLastName().equals("Customer")) {
            returnList.add("");
            returnList.add("");
            returnList.add("");
            returnList.add("");
        } else {
            returnList.add(customerDto.getAddress().getStreet());
            returnList.add(customerDto.getAddress().getPostalCode() + customerDto.getAddress().getCity());
            returnList.add(customerDto.getAddress().getCountry());
            returnList.add(customerDto.getEmail());
        }
        return returnList;
    }

}
