package com.stagora.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Functions {

	public Map<String, String> reponse(String type,String message){
		Map<String, String> reponse = new HashMap<>();
		reponse.put(type, message);
        
        return reponse;
	}
}
