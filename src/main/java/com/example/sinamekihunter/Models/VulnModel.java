package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Utils.PayloadRegex;
import com.example.sinamekihunter.Vulnerabilities.ControlledVuln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VulnModel {
    private String vuln_name;
    private String vuln_full_name;
    private ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
    private ArrayList<String> methods = new ArrayList<>();
    /*
     public static final String LFI = "LFI";
        public static final String SSRF = "SSRF";
        public static final String SSTI = "SSTI";
        public static final String OPEN_REDIRECT = "OPEN REDIRECT";
        public static final String XSS = "XSS";
     */
    private static VulnModel LFI;
    private static VulnModel SSRF = null;
    private static VulnModel SSTI = null;
    private static VulnModel OPEN_REDIRECT = null;
    private static VulnModel XSS = null;
    private VulnModel(String vuln_name,String vuln_full_name,ArrayList<PayloadRegex> payloadRegexes){
        this.vuln_name = vuln_name;
        this.vuln_full_name = vuln_full_name;
        this.payloadRegexes = payloadRegexes;
    }
    public void checkVulnerability(RequestModel requestModel){
        ControlledVuln controlledVuln;

        for (PayloadRegex payloadRegex:payloadRegexes) {

            if (!payloadRegex.isIs_crossed()){

                if(payloadRegex.getRequestTextPayload() != null && payloadRegex.getResponseTextPayload() == null){

                    Pattern request_pattern = Pattern.compile(payloadRegex.getRequestTextPayload());
                    Matcher request_matcher = request_pattern.matcher(requestModel.getRequestText());
                    if (request_matcher.find()){
                        System.out.println("Vulnerability FOUND!");
                        controlledVuln = new ControlledVuln(request_matcher.group(),true,this.vuln_name);
                        requestModel.addControlledVuln(controlledVuln);
                        requestModel.addPossibleVuln(this.vuln_name);
                    }
                }
                else if (payloadRegex.getResponseTextPayload() == null && payloadRegex.getResponseTextPayload() != null) {
                    Pattern response_pattern = Pattern.compile(payloadRegex.getResponseTextPayload());
                    Matcher response_matcher = response_pattern.matcher(requestModel.responseModel.getContentString());
                    if (response_matcher.find()){
                        controlledVuln =new ControlledVuln(response_matcher.group(),false,this.vuln_name);
                        requestModel.addControlledVuln(controlledVuln);
                        requestModel.addPossibleVuln(this.vuln_name);
                    }
                }
                else if (payloadRegex.getRequestTextPayload() != null && payloadRegex.getResponseTextPayload() != null) {
                    Pattern request_pattern = Pattern.compile(payloadRegex.getRequestTextPayload());
                    Pattern response_pattern = Pattern.compile(payloadRegex.getResponseTextPayload());
                    Matcher request_matcher = request_pattern.matcher(requestModel.getRequestText());
                    Matcher response_matcher = response_pattern.matcher(requestModel.responseModel.getContentString());
                    if (request_matcher.find() && response_matcher.find()){
                        controlledVuln = new ControlledVuln(request_matcher.group(),response_matcher.group(),this.vuln_name);
                        requestModel.addControlledVuln(controlledVuln);
                        requestModel.addPossibleVuln(this.vuln_name);
                    }
                }
            }

        }
    }
    public static VulnModel getLFI(){
        if (LFI == null){
            ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
            PayloadRegex payloadRegex1 = new PayloadRegex("[?].+=.+[.].+[&|\s]",true,false);
            payloadRegexes.add(payloadRegex1);
            LFI = new VulnModel("LFI","Local File Inclusion",payloadRegexes);
        }
        return LFI;
    }
    public static VulnModel getSSRF(){
        if(SSRF == null){
            ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
            SSRF = new VulnModel("SSRF","Server-Side Request Forgery",payloadRegexes);
        }
        return SSRF;
    }
    public static VulnModel getSSTI(){
        if(SSTI == null){
            ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
            SSTI = new VulnModel("SSTI","Server-Side Template Injection",payloadRegexes);
        }
        return SSTI;
    }
    public static VulnModel getOpenRedirect(){
        if(OPEN_REDIRECT == null){
            ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
            OPEN_REDIRECT = new VulnModel("Open Redirect","Open Redirect",payloadRegexes);
        }
        return OPEN_REDIRECT;
    }
    public static VulnModel getXSS(){
        if(XSS == null){
            ArrayList<PayloadRegex> payloadRegexes = new ArrayList<>();
            XSS = new VulnModel("XSS","Cross Site Scripting",payloadRegexes);
        }
        return XSS;
    }
}
