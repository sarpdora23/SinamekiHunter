package com.example.sinamekihunter.Vulnerabilities;

import com.example.sinamekihunter.Models.VulnModel;
import com.example.sinamekihunter.Utils.PayloadRegex;

import java.util.ArrayList;

public class PrototypePollution extends VulnModel {
    public PrototypePollution(String vuln_name, String vuln_full_name, ArrayList<PayloadRegex> payloadRegexes) {
        super(vuln_name, vuln_full_name, payloadRegexes);
    }
}
