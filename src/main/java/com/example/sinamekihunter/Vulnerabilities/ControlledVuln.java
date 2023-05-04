package com.example.sinamekihunter.Vulnerabilities;

public class ControlledVuln {
    private String request_found_part;
    private String response_found_part;
    private String vuln_name;
    public ControlledVuln(String request_found_part,String response_found_part,String vuln_name){
        this.request_found_part = request_found_part;
        this.response_found_part = response_found_part;
        this.vuln_name = vuln_name;
    }
    public ControlledVuln(String found_part,boolean is_request,String vuln_name){
        if (is_request){
            this.request_found_part = found_part;
        }
        else{
            this.response_found_part = found_part;
        }
        this.vuln_name = vuln_name;
    }
    public String getRequest_found_part(){
        return this.request_found_part;
    }
    public String getResponse_found_part(){
        return this.response_found_part;
    }
    public String getVuln_name(){
        return this.vuln_name;
    }
}
