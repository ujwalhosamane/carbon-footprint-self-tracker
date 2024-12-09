package com.carbon.footprint.suggestion.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiClient {
	@Autowired
	private RestTemplate restTemplate;

	public String generateTextWithSpring(String prompt) {

	  String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + getApiKey();
	  String contentType = "application/json";
	  String requestBody = "{\"contents\": [{ \"parts\":[{\"text\": \"" + prompt + "\"}] }] }";

	  HttpHeaders headers = new HttpHeaders();
	  headers.setContentType(MediaType.parseMediaType(contentType));

	  HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	  ResponseEntity<String> response = restTemplate.exchange(
	      url, 
	      HttpMethod.POST, 
	      entity, 
	      String.class
	  );
	  String textResponse = "";
	  try {
		  textResponse = extractText(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	  return textResponse;
	}

	private String getApiKey() {
	  return "AIzaSyA5pkD2vQ0zHA_Q-Ybx0OmgqBeO2RvzddQ"; 
	}
	
	public String extractText(String response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);
        JsonNode textNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");

        String text = textNode.asText();
        text = text.replaceAll("\\.\\s+", ". ");
        return text;
	}
}
