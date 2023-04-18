package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Network.Proxy;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboardController implements ControllersParent{
    private boolean isCreated = false;
    @FXML
    public Label targetDomainLabel;
    @FXML
    public TabPane domainsTabPane;


    @FXML
    protected void createAddSubdomainTab(){
        Tab addSubdomain = new Tab("+");
        domainsTabPane.getTabs().add(addSubdomain);
        System.out.println("Deneme");
    }
    @Override
    public void InitController() {
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
}
