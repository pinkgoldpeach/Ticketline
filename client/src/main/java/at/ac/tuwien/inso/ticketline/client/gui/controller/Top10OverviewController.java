package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.ShowService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.ShowTopTenDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Top 10 events
 */
@Component
public class Top10OverviewController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Top10OverviewController.class);

    @FXML
    public Label labelNoStatsCat;

    @FXML
    private AnchorPane top10OverviewPane;
    @FXML
    private BarChart<String, Number> barChartTopTen, barChartTopTenType;
    @FXML
    private CategoryAxis xAxisAll, xAxisType;
    @FXML
    private NumberAxis yAxisAll, yAxisType;
    @FXML
    private ChoiceBox<String> choiceBox;

    @Autowired
    private ShowService showService;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    private HashMap<String, PerformanceTypeDto> typeMap;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {

        typeMap = new HashMap<>();

        barChartTopTen.setAnimated(false);
        barChartTopTenType.setAnimated(false);

        labelNoStatsCat.setText("");

        barChartTopTen.setVisible(false);
        barChartTopTenType.setVisible(false);

        ObservableList choiceList = FXCollections.observableArrayList();
        for (PerformanceTypeDto performanceTypeDto : PerformanceTypeDto.values()) {
            typeMap.put(performanceTypeDto.name(),performanceTypeDto);
        }

        choiceList.addAll(typeMap.keySet());
        choiceList.add(BundleManager.getBundle().getString("generic.all"));

        choiceBox.setItems(choiceList);
        choiceBox.getSelectionModel().select(0);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            reloadPage(newValue);
        });

        reloadPage(BundleManager.getBundle().getString("generic.all"));
    }

    /**
     * Method which will reload all barcharts on the page.
     *
     * @param cat indicates new category for barchart
     */
    public void reloadPage(String cat) {
        if (cat.equals(BundleManager.getBundle().getString("generic.all"))) {
            barChartTopTen.setVisible(true);
            barChartTopTenType.setVisible(false);
            showStatisticsAll();
        }
        else {
            barChartTopTen.setVisible(false);
            barChartTopTenType.setVisible(true);
            showTop10ByCategories(typeMap.get(cat));
        }
    }

    /**
     * Method which will fill the barchart with the data of the overall top10
     */
    private void showStatisticsAll() {
        try {
            barChartTopTen.getData().clear();
            barChartTopTen.layout();

            List<ShowTopTenDto> values = showService.getTop10SoldShows();

            if (!values.isEmpty()) {
                barChartTopTen.setVisible(true);
                barChartTopTenType.setVisible(false);
                choiceBox.setVisible(true);
                labelNoStatsCat.setText("");
                labelNoStatsCat.setVisible(false);
            } else {
                barChartTopTen.setVisible(false);
                barChartTopTenType.setVisible(false);
                choiceBox.setVisible(false);
                labelNoStatsCat.setText(BundleManager.getBundle().getString("top10.noStatsAll"));
                labelNoStatsCat.setVisible(true);
            }

            HashMap<String, ShowDto> nameMap = new HashMap<>();
            HashMap<String, Long> realValueMap = new HashMap<>();
            HashMap<Integer, Long> relativeValues = getRelativeValues(values);

            xAxisAll.setTickLabelRotation(30);
            xAxisAll.setLabel(BundleManager.getBundle().getString("top10.events"));
            yAxisAll.setLabel(BundleManager.getBundle().getString("top10.ticketsSold"));
            yAxisAll.setTickLabelsVisible(false);

            barChartTopTen.setTitle(BundleManager.getBundle().getString("top10.all"));
            XYChart.Series series = new XYChart.Series();

            for (int i = 0; i < values.size(); i++) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
                String name = values.get(i).getShowDto().getPerformance().getName() + "\n" + formatter.format(values.get(i).getShowDto().getDateOfPerformance());
                LOGGER.info("Total TOP 10 --> " + name + ": " + relativeValues.get(values.get(i).getShowDto().getId()));
                series.getData().add((new XYChart.Data(name, relativeValues.get(values.get(i).getShowDto().getId()))));
                nameMap.put(name, values.get(i).getShowDto());
                realValueMap.put(name, values.get(i).getTimesSold());
            }

            barChartTopTen.getData().add(series);
            barChartTopTen.setLegendVisible(false);

            for (XYChart.Series<String, Number> serie1 : barChartTopTen.getData()) {
                for (XYChart.Data<String, Number> item : serie1.getData()) {
                    item.getNode().setOnMouseClicked((MouseEvent event) -> {
                        ShowDto show = nameMap.get(item.getXValue());
                        handleSellTickets(show);
                    });

                    Tooltip.install(item.getNode(), new Tooltip(realValueMap.get(item.getXValue()) + " " + BundleManager.getBundle().getString("top10.tooltip")));

                    item.getNode().setStyle("-fx-bar-fill: #1d6a80;");

                    //Adding class on hover
                    item.getNode().setOnMouseEntered(event -> item.getNode().setStyle("-fx-background-color: #ff3f00;"));

                    //Removing class on exit
                    item.getNode().setOnMouseExited(event -> item.getNode().setStyle("-fx-bar-fill: #1d6a80;"));
                }
            }
        } catch (ServiceException e) {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("top10.alert.content.serviceException"),
                    BundleManager.getBundle().getString("top10.alert.header.serviceException"),
                    BundleManager.getBundle().getString("top10.alert.title.serviceException"));
        }
    }

    /**
     * Method which will fill the barchart with the data of the top10 of the given type
     * @param type PerformanceTypeDto with the type which should be shown in this top10
     */
    private void showTop10ByCategories(PerformanceTypeDto type) {
        try {
            barChartTopTenType.getData().clear();
            barChartTopTenType.layout();

            List<ShowTopTenDto> values = showService.getTop10SoldShowsByCategory(type);

            if (!values.isEmpty()) {
                labelNoStatsCat.setText("");
                barChartTopTenType.setVisible(true);
                labelNoStatsCat.setVisible(false);

            } else {
                labelNoStatsCat.setText(BundleManager.getBundle().getString("top10.noStatsCat"));
                barChartTopTenType.setVisible(false);
                labelNoStatsCat.setVisible(true);
            }

            HashMap<String, ShowDto> nameMap = new HashMap<>();
            HashMap<String, Long> realValueMap = new HashMap<>();
            HashMap<Integer, Long> relativeValues = getRelativeValues(values);

            xAxisType.setTickLabelRotation(30);
            xAxisType.setLabel(BundleManager.getBundle().getString("top10.events"));
            yAxisType.setLabel(BundleManager.getBundle().getString("top10.ticketsSold"));
            yAxisType.setTickLabelsVisible(false);
            barChartTopTenType.setTitle(BundleManager.getBundle().getString("top10.top10of") + " " + type);

            XYChart.Series series = new XYChart.Series();

            for (int i = 0; i < values.size(); i++) {
                String name = values.get(i).getShowDto().getPerformance().getName();
                LOGGER.info("Statistic for type " + type + " --> " + name + ": " + relativeValues.get(values.get(i).getShowDto().getId()));
                series.getData().add((new XYChart.Data(name, relativeValues.get(values.get(i).getShowDto().getId()))));
                nameMap.put(name, values.get(i).getShowDto());
                realValueMap.put(name, values.get(i).getTimesSold());
            }

            barChartTopTenType.getData().add(series);
            barChartTopTenType.setLegendVisible(false);

            for (XYChart.Series<String, Number> serie1 : barChartTopTenType.getData()) {
                for (XYChart.Data<String, Number> item : serie1.getData()) {
                    item.getNode().setOnMouseClicked((MouseEvent event) -> {
                        ShowDto show = nameMap.get(item.getXValue());
                        handleSellTickets(show);
                    });

                    Tooltip.install(item.getNode(), new Tooltip(realValueMap.get(item.getXValue()) + " " + BundleManager.getBundle().getString("top10.tooltip")));

                    item.getNode().setStyle("-fx-bar-fill: #1d6a80;");

                    //Adding class on hover
                    item.getNode().setOnMouseEntered(event -> item.getNode().setStyle("-fx-background-color: #ff3f00;"));

                    //Removing class on exit
                    item.getNode().setOnMouseExited(event -> item.getNode().setStyle("-fx-bar-fill: #1d6a80;"));
                }
            }
        } catch (ServiceException e) {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("top10.alert.content.serviceException"),
                    BundleManager.getBundle().getString("top10.alert.header.serviceException"),
                    BundleManager.getBundle().getString("top10.alert.title.serviceException"));
        }
    }

    /**
     * Triggerd if user hits a bar on the barchart. Openes a selling window
     *
     * @param show - The selected show for buying tickets
     */
    private void handleSellTickets(ShowDto show) {
        if (show != null) {
            Stage sellReserveTickets = new Stage();
            SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/sellReserveTickets.fxml");
            sellReserveTickets.setScene(new Scene((Parent) wrapper.getLoadedObject()));

            SellReserveTicketsController controller = (SellReserveTicketsController) wrapper.getController();
            controller.setShow(show);
            sellReserveTickets.setResizable(false);
            sellReserveTickets.setTitle(BundleManager.getBundle().getString("salesPage.field.sellTickets"));
            sellReserveTickets.showAndWait();
        }
        reloadPage(choiceBox.getValue());
    }

    /**
     * Calulates the relative values for the given show
     *
     * @param shows a list of all shows with the total tickets sold
     * @return a Hashmap with the show id as key and the relative value to the highest
     * value as value
     */
    private HashMap<Integer, Long> getRelativeValues(List<ShowTopTenDto> shows) {
        HashMap<Integer, Long> returnMap = new HashMap<>();
        HashMap<Integer, Long> tempTimesSold = new HashMap<>();
        long highestValues = 0;

        for (int i = 0; i < shows.size(); i++) {
            int id = shows.get(i).getShowDto().getId();
            Long count = shows.get(i).getTimesSold();
            tempTimesSold.put(id, count);
            if (highestValues < count) {
                highestValues = count;
            }
        }

        for (HashMap.Entry<Integer, Long> entry : tempTimesSold.entrySet()) {
            double temp = Math.ceil((entry.getValue() * 100) / highestValues);
            returnMap.put(entry.getKey(),Math.round(temp) );
        }

        return returnMap;
    }

}