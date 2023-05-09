package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Models.DomainModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddSubdomainTab implements ControllersParent{
    public TabPane tabPane;
    @FXML
    private TextField newDomainTextField;
    @FXML
    private Button addDomainButton;
    @Override
    public void InitController() {

    }
    @FXML
    public void onAddButtonClick(){
        String url = newDomainTextField.getText() + "." + TargetModel.getInstance().getPureDomain();
        addSubdomain(url);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.PROXY_REQUEST_PARENT_VIEW));
                Parent parent;
                try {
                    parent = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tabPane.getTabs().get(tabPane.getTabs().size() - 2).setContent(parent);
                tabPane.getSelectionModel().selectLast();

            }
        });

    }
    public static void addSubdomain(String url){
        MainDashboard mainDashboardController = (MainDashboard) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
        TabPane tabPane = mainDashboardController.domainsTabPane;
        DomainModel domainModel = new DomainModel(url,tabPane.getTabs().size() - 1);
        TargetModel.getInstance().addDomainModel(domainModel);
        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setText(domainModel.getDomainUrl());
        mainDashboardController.createAddSubdomainTab();

    }
}
