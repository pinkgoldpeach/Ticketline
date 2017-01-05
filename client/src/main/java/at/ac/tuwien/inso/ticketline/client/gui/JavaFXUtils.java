package at.ac.tuwien.inso.ticketline.client.gui;

import static at.ac.tuwien.inso.ticketline.client.util.BundleManager.getBundle;
import static at.ac.tuwien.inso.ticketline.client.util.BundleManager.getExceptionBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import com.google.common.base.Throwables;

/**
 * This class provides helper methods for JavaFX
 *
 */
public class JavaFXUtils {

	/**
	 * Creates a dialog for an exception
	 * Based on <a href="http://code.makery.ch/blog/javafx-dialogs-official/">Makery</a>
	 * 
	 * @param e Exception the exception
	 * @return the dialog which shows the exception
	 */
	public static Alert createAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(getBundle().getString("app.name") + " - " + getExceptionBundle().getString("error"));
		alert.setHeaderText(getExceptionBundle().getString("exception.unexpected"));
		alert.setContentText(e.getMessage());
		
		String exceptionText = Throwables.getStackTraceAsString(e);

		Label label = new Label(getExceptionBundle().getString("exception.stacktrace"));

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		return alert;
	}
}
