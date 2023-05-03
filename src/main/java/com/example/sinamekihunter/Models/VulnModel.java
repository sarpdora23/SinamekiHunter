package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Utils.PayloadRegex;
import com.example.sinamekihunter.Vulnerabilities.ControlledVuln;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class VulnModel {
    private String vuln_name;
    private String vuln_full_name;
    private ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
    private ArrayList<String> methods = new ArrayList<>();

    public VulnModel(String vuln_name,String vuln_full_name,ArrayList<PayloadRegex> payloadRegexes){
        this.vuln_name = vuln_name;
        this.vuln_full_name = vuln_full_name;
        this.payloadRegexes = payloadRegexes;
    }
    public ArrayList<ControlledVuln> checkVulnerability(RequestModel requestModel){
        ArrayList<ControlledVuln> controlledVulns = new ArrayList<>();

        return controlledVulns;
    }

}
