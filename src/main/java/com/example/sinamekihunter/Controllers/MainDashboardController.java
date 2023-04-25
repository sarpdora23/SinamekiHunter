package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Network.Proxy;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class MainDashboardController implements ControllersParent{
    private boolean isCreated = false;
    @FXML
    public Label targetDomainLabel;
    @FXML
    public TabPane domainsTabPane;
    private HashMap<Integer, ObservableList> tabRequestsMap;


    @FXML
    protected void createAddSubdomainTab(){
        Tab addSubdomain = new Tab("+");
        domainsTabPane.getTabs().add(addSubdomain);
        System.out.println("Deneme");
    }
    @Override
    public void InitController() {
        tabRequestsMap = new HashMap<>();
        isCreated = true;
        TargetModel target = TargetModel.getInstance();
        targetDomainLabel.setText(target.getPureDomain());
        domainsTabPane.getTabs().get(0).setText(target.getDomainList().get(0).getDomainUrl());

        createAddSubdomainTab();
        domainsTabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println("t1: " + t1 +" size:" + domainsTabPane.getTabs().size());
                if ((int)t1 == domainsTabPane.getTabs().size() - 1){
                    Tab addSubdomainTab = domainsTabPane.getTabs().get(t1.intValue());
                    FXMLLoader addSubdomainTabFXML = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.ADD_SUBDOMAIN_TAB_FXML));
                    try {
                        Parent addSubdomainTabRoot = addSubdomainTabFXML.load();
                        addSubdomainTab.setContent(addSubdomainTabRoot);
                        AddSubdomainTabController tabController = addSubdomainTabFXML.getController();
                        tabController.tabPane = domainsTabPane;

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    @FXML
    protected void onSelectSubdomainDiscovery() throws IOException {
        Stage subdomain_discovery_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.DISCOVERY_SUBDOMAIN_VIEW_FXML));
        Scene subdomain_discovery_scene = new Scene(fxmlLoader.load());
        DiscoverySubdomainViewController discovery_subdomain_controller = fxmlLoader.getController();
        subdomain_discovery_stage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        subdomain_discovery_stage.setScene(subdomain_discovery_scene);
        StageManager.getInstance().createStage(StringValues.StageNames.DISCOVERY_SUBDOMAIN_STAGE,subdomain_discovery_stage,StringValues.SceneNames.DISCOVERY_SUBDOMAIN,discovery_subdomain_controller);
        subdomain_discovery_stage.setOnCloseRequest(event -> {
            StageManager.getInstance().closeStage(StringValues.StageNames.DISCOVERY_SUBDOMAIN_STAGE);
        });
        subdomain_discovery_stage.show();
    }
    @FXML
    protected void startProxy() throws IOException {
        Stage start_proxy_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.PROXY_START_VIEW_FXML));
        Scene start_proxy_scene = new Scene(fxmlLoader.load());
        StartProxyViewController start_proxy_view_controller = fxmlLoader.getController();
        start_proxy_stage.setTitle("Proxy Settings");
        start_proxy_stage.setScene(start_proxy_scene);
        StageManager.getInstance().createStage(StringValues.StageNames.PROXY_START_VIEW_STAGE,start_proxy_stage,StringValues.SceneNames.PROXY_START_VIEW_SCENE,start_proxy_view_controller);
        start_proxy_stage.setOnCloseRequest(event -> {
            StageManager.getInstance().closeStage(StringValues.StageNames.PROXY_START_VIEW_STAGE);
        });
        start_proxy_stage.show();
    }
    @FXML
    protected void stopProxy() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Proxy Warning!");
        alert.setContentText("No Proxy Server is running !");
        if (Proxy.getInstance() != null){
            if(Proxy.getInstance().isRunning()){
                Proxy.getInstance().stopProxy();
                alert.setContentText("Proxy stopped!");
            }
        }
        alert.show();
    }
    public void addRequestToDashboard(RequestModel requestModel,int tab_index) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.REQUEST_ROOT_VIEW_FXML));
                Parent request_root_view = null;
                try {
                    request_root_view = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                RequestRootViewController requestRootViewController = fxmlLoader.getController();
                requestRootViewController.setRequestModel(requestModel);
                AnchorPane anchorPane = (AnchorPane) domainsTabPane.getTabs().get(tab_index).getContent();
                ScrollPane scrollPane = (ScrollPane) anchorPane.getChildren().get(0);
                VBox vbox = (VBox) scrollPane.getContent();
                vbox.getChildren().add(request_root_view);
            }
        });

    }
}
