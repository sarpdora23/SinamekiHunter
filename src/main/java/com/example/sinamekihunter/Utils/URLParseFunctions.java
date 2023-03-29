package com.example.sinamekihunter.Utils;

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
}
