package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Discovery.SubdomainDiscovery;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.http.HttpHeaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DiscoverySubdomainViewController implements ControllersParent{
    @FXML
    private TextField targetDomainTextField;
    @FXML
    private Slider threadSpeedSlider;
    @FXML
    private Label threadSpeedValue;
    @FXML
    private Button fileChooseButton;
    @FXML
    private Label filePathLabel;
    private int speedValue;
    private File wordlist;
    @Override
    public void InitController() {
        fileChooseButton.setText("");
        filePathLabel.setText("");
        targetDomainTextField.setText(TargetModel.getInstance().getPureDomain());
        targetDomainTextField.editableProperty().set(false);
        threadSpeedValue.setText(String.valueOf((int) threadSpeedSlider.getValue()));
        threadSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int val  = Math.round(t1.floatValue());
                threadSpeedSlider.setValue(val);
                threadSpeedValue.setText(String.valueOf(val));
                speedValue = val;
            }
        });

    }

    @FXML
    protected void onStartDiscoverySubdomain() throws IOException, InterruptedException {
        String url = TargetModel.getInstance().getPureDomain();
        SubdomainDiscovery subdomainDiscovery = new SubdomainDiscovery(this.wordlist,this.speedValue);
        HashMap header_data = new HashMap<>();
        HashMap body_data = new HashMap<>();
        HashMap json_data = new HashMap<>();
        header_data.put(HttpHeaders.HOST,"FUZZ");
        subdomainDiscovery.startDiscovery(url,header_data,body_data,json_data);
    }
    @FXML
    protected void onSelectWordlist(){
        FileChooser fileChooser = new FileChooser();
        File wordlist_file = fileChooser.showOpenDialog(StageManager.getInstance().getStage(StringValues.StageNames.DISCOVERY_SUBDOMAIN_STAGE));
        if (wordlist_file != null){
            filePathLabel.setText(wordlist_file.getPath());
            this.wordlist = wordlist_file;
        }
    }
}
