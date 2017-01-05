package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Controller for single news elements
 */
@Component
@Scope(value = "prototype")
public class NewsElementController {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy | HH:mm");

    @FXML
    private Label lblDate, lblTitle, lblText;

    /**
     * Initialize the values of the controls.
     *
     * @param newsDto the news dto
     */
    public void initializeData(NewsDto newsDto) {
        this.lblDate.setText(DATE_FORMAT.format(newsDto.getSubmittedOn()));
        this.lblTitle.setText(newsDto.getTitle());
        this.lblText.setText(newsDto.getNewsText());
    }

}
