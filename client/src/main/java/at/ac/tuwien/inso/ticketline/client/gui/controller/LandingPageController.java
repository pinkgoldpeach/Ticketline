package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.gui.JavaFXUtils;
import at.ac.tuwien.inso.ticketline.client.service.NewsService;
import at.ac.tuwien.inso.ticketline.client.service.rest.AuthRestClient;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

/**
 * Controller for the news window
 */
@Component
public class LandingPageController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LandingPageController.class);

    @FXML
    private AnchorPane landingPagePane;

    @FXML
    public Button btnReloadNews, btnShowAll, btnPublishNews;
    @FXML
    public Label informationLabel, labelLoggedInUser;
    @FXML
    private VBox vbNewsBox;


    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthRestClient authRestClient;


    private UserStatusDto userStatusDto;

    private boolean allNews = false;



    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        if (null != vbNewsBox) {
            initNewsBox();
        }

        userStatusDto = authRestClient.getUserStatus();
        labelLoggedInUser.setText(BundleManager.getBundle().getString("generic.loggedInAs") + " " + userStatusDto.getFirstName() + " " + userStatusDto.getLastName());

        if(!(userStatusDto.getRoles().get(0).equals("ROLE_ADMINISTRATOR"))){
            btnPublishNews.setVisible(false);
        }

        btnPublishNews.setText(BundleManager.getBundle().getString("generic.publishNews"));


    }

    /**
     * Inits the news box.
     */
    public void initNewsBox() {
        List<NewsDto> news;
        try {
            if(allNews){
                news = this.newsService.getAllNews();
            } else {
                news = this.newsService.getSpecificNews();
            }
        } catch (ServiceException e) {
            LOGGER.error("Could not retrieve news: {}", e.getMessage());
            Alert alert = JavaFXUtils.createAlert(e);
            alert.showAndWait();
            return;
        }

        vbNewsBox.getChildren().clear();

        int newsCount = 0;
        for (Iterator<NewsDto> newsDtoIterator = news.iterator(); newsDtoIterator.hasNext(); ) {
            newsCount++;
            NewsDto newsDto = newsDtoIterator.next();
            ObservableList<Node> vbNewsBoxChildren = vbNewsBox.getChildren();
            vbNewsBoxChildren.add(generateNewsElement(newsDto));
            if(newsDtoIterator.hasNext()) {
                Separator separator = new Separator();
                vbNewsBoxChildren.add(separator);
            }
        }

        if(newsCount != 0 && !allNews){
            if (newsCount==1) {
                informationLabel.setText(newsCount + " " + BundleManager.getBundle().getString("news.countNewNews1"));
            }else {
                informationLabel.setText(newsCount + " " + BundleManager.getBundle().getString("news.countNewNews"));
            }

        } else {
            informationLabel.setText("");
            ObservableList<Node> vbNewsBoxChildren = vbNewsBox.getChildren();
            NewsDto noNewMessage= new NewsDto();
            noNewMessage.setNewsText("");
            noNewMessage.setSubmittedOn(new Date());
            noNewMessage.setTitle(BundleManager.getBundle().getString("news.noNewNews"));
            vbNewsBoxChildren.add(generateNewsElement(noNewMessage));
        }
    }

    /**
     * Generates the news element.
     *
     * @param newsDto the news dto
     * @return the node
     */
    private Node generateNewsElement(NewsDto newsDto) {
        SpringFxmlLoader.LoadWrapper loadWrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newsElement.fxml");
        ((NewsElementController)loadWrapper.getController()).initializeData(newsDto);
        return (Node) loadWrapper.getLoadedObject();
    }

    /**
     * Opens a new window with all news which can be shown
     *
     */
    @FXML
    private void handleShowAllNews(){
        if(allNews){
            allNews = false;
            btnShowAll.setText(BundleManager.getBundle().getString("news.allNews"));
            initNewsBox();
        } else {
            allNews = true;
            btnShowAll.setText(BundleManager.getBundle().getString("news.new"));
            initNewsBox();
        }

    }

    public void reloadPane(){
        allNews = false;
        btnShowAll.setText(BundleManager.getBundle().getString("news.allNews"));
        initNewsBox();
    }

    /**
     * Reloads the newsBoxcontent
     *
     */
    @FXML
    private void handleReloadNews(){
        initNewsBox();
    }


    public VBox getNewsBox() {
        return vbNewsBox;
    }

    /**
     * Triggered of admin wants to publish news, opens new window
     */
    @FXML
    public void handleNewNews() {
        LOGGER.info("new news clicked");
        Stage newNews = new Stage();
        newNews.initModality(Modality.APPLICATION_MODAL);
        newNews.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/createNewsAdmin.fxml")));
        newNews.setResizable(false);
        newNews.setTitle(BundleManager.getBundle().getString("generic.newNewsTitle"));

        newNews.showAndWait();


        if (null != vbNewsBox) {
            initNewsBox();
        }
    }

   }
