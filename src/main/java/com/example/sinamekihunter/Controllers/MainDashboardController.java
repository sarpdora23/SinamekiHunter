package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

    protected void onChangeActiveTab(){

    }
}
