package com.example.sinamekihunter.Utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Pattern;

public class URLParseFunctions {
    public static String getProtocol(String url){
        return url.substring(0,url.indexOf(":"));
    }
    public static String getDomainHost(String url){
        int slash_counter = 0;
        int index_counter = 0;
        int last_index = 0;
        int start_index = 0;
        for (char url_char:url.toCharArray()) {
            if (url_char == '/'){
                slash_counter++;
                if(slash_counter == 2){
                    start_index = index_counter;
                }
                if (slash_counter == 3){
                    last_index = index_counter;
                    break;
                }
            }
            index_counter++;
        }
        return url.substring(start_index+1,last_index);
    }
    public static String getPureDomain(String domainUrl){
        String[] strings = domainUrl.split(Pattern.quote("."));
        if(strings.length == 3){
            return strings[1] + "." + strings[2];
        }
        return strings[0] + "." + strings[1];
    }
    //http://nahamstore.thm/product?id=1&name=Hoodie+%2B+Tee
    public static HashMap getGetRequestParams(String url){
        HashMap requestParams = new HashMap();
        String[] splitParts = url.split("/");
        String paramsPart = splitParts[splitParts.length - 1];
        if (paramsPart.indexOf('?') != -1){
            paramsPart = paramsPart.substring(paramsPart.indexOf('?')+1);
            String[] params = paramsPart.split("&");
            for (String paramValue: params) {
                String[] param_value = paramValue.split("=");
                if (param_value.length >= 2){
                    requestParams.put(param_value[0],URLDecoder.decode(param_value[1], Charset.defaultCharset()));
                }

            }

            return requestParams;
        }
        return null;
    }
    public static String requestParamsToUrlForm(HashMap<String,Object> requestParams){
        String paramsEndpoint = "";
        if (requestParams.keySet().size() > 0){
            boolean qu_mark = true;
            for (String key: requestParams.keySet()) {
                if (qu_mark){
                    qu_mark = false;
                    paramsEndpoint = "?";
                }
                else{
                    paramsEndpoint = paramsEndpoint + "&";
                }
                paramsEndpoint = paramsEndpoint + key + "=" + URLEncoder.encode((String) requestParams.get(key), StandardCharsets.UTF_8);
            }
        }
        return paramsEndpoint;
    }
}
