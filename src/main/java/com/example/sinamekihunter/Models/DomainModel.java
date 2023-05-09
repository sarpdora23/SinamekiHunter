package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Controllers.MainDashboard;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Utils.StringValues;

import java.io.IOException;
import java.util.ArrayList;

public class DomainModel {
    private String domainUrl;
    private ArrayList<RequestModel> request_list = new ArrayList();
    private int tabIndex;

    public DomainModel(String domainUrl,int tabIndex){
        this.domainUrl = domainUrl;
        this.tabIndex = tabIndex;
    }
    public String getDomainUrl(){
        return this.domainUrl;
    }
    public void addRequestToDomain(RequestModel requestModel) throws IOException {
        this.request_list.add(requestModel);
        vulnScan(requestModel);
        if (ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE) != null){

            MainDashboard mainDashboardController = (MainDashboard) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
            mainDashboardController.addRequestToDashboard(requestModel,this.tabIndex);
        }
        else{
            this.request_list.remove(this.request_list.size() - 1);
        }
    }
    private void vulnScan(RequestModel requestModel){

        VulnModel LFI = VulnModel.getLFI();
        VulnModel SSRF = VulnModel.getSSRF();
        VulnModel SSTI = VulnModel.getSSTI();
        VulnModel OPEN_REDIRECT = VulnModel.getOpenRedirect();
        VulnModel XSS = VulnModel.getXSS();
        LFI.checkVulnerability(requestModel);
        SSRF.checkVulnerability(requestModel);
        SSTI.checkVulnerability(requestModel);
        OPEN_REDIRECT.checkVulnerability(requestModel);
        XSS.checkVulnerability(requestModel);

    }
}
