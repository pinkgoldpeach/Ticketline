package at.ac.tuwien.inso.ticketline.client.util;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Date;

/**
 * Helper to enhance DatePicker
 */
public class DatePickerHelper {

    public static void setEnhancedDatePicker(DatePicker picker) {
        picker.setConverter(new StringConverter<LocalDate>() {

            private final DateTimeFormatter fastFormatter1 = DateTimeFormatter.ofPattern("ddMMuuuu");
            private final DateTimeFormatter fastFormatter5 = DateTimeFormatter.ofPattern("d.M.uuuu");
            private final DateTimeFormatter fastFormatter2 = DateTimeFormatter.ofPattern("d.M.uu");
            private final DateTimeFormatter fastFormatter3 = DateTimeFormatter.ofPattern("d.MM.uuuu");
            private final DateTimeFormatter fastFormatter4 = DateTimeFormatter.ofPattern("dd.M.uuuu");
            private final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");

            @Override
            public String toString(LocalDate object) {
                if(object != null) {
                    return object.format(defaultFormatter);
                }else{
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string.length()>1) {
                    try {
                        return LocalDate.parse(string, fastFormatter1);
                    } catch (DateTimeParseException ignored) {
                    }
                    try {
                        return LocalDate.parse(string, fastFormatter2);
                    } catch (DateTimeParseException ignored) {
                    }
                    try {
                        return LocalDate.parse(string, fastFormatter3);
                    } catch (DateTimeParseException ignored) {
                    }
                    try {
                        return LocalDate.parse(string, fastFormatter4);
                    } catch (DateTimeParseException ignored) {
                    }
                    try {
                        return LocalDate.parse(string, fastFormatter5);
                    } catch (DateTimeParseException ignored) {
                    }

                }
                return LocalDate.parse(string, defaultFormatter);

            }
        });

        picker.getEditor().setOnKeyTyped(event -> {
            if (!"0123456789.".contains(event.getCharacter())) {
                return;
            }
        });

        picker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && picker.getValue()!=null && picker.getValue().toString().length()>=6){
                    picker.setValue(picker.getConverter().fromString(picker.getEditor().getText()));
                }
            }
        });
    }
}
