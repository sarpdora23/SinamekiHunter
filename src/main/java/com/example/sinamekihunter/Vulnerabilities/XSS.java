package com.example.sinamekihunter.Vulnerabilities;

import com.example.sinamekihunter.Models.VulnModel;
import com.example.sinamekihunter.Utils.PayloadRegex;

import java.util.ArrayList;

public class XSS extends VulnModel {
    public XSS(String vuln_name, String vuln_full_name, ArrayList<PayloadRegex> payloadRegexes) {
        super(vuln_name, vuln_full_name, payloadRegexes);
    }
}
