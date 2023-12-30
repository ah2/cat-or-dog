package com.example.application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;


public class the_cat_handler {
	
    public static void main(String[] args) {
    	cat();
    }

	public static void cat() {
        // Define the API endpoint
        String apiUrl = "https://api.thecatapi.com/v1/images/search";

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest with GET method
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            // Send the HTTP request and receive the response
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (status code 200)
            if (httpResponse.statusCode() == 200) {
                // Print the response body (JSON containing cat information)
                System.out.println("Response Body:\n" + httpResponse.body());
            	
            	// Parse the JSON response
                JSONArray jsonArray = new JSONArray(httpResponse.body());

                // Extract the image URL from the first object in the array
                if (jsonArray.length() > 0) {
                    JSONObject catObject = jsonArray.getJSONObject(0);
                    String imageUrl = catObject.getString("url");

                    // Print the image URL
                    System.out.println("Cat Image URL: " + imageUrl);
                } else {
                    System.out.println("No cat images found in the response.");
                }
            	
            	
            } else {
                System.out.println("Failed to fetch cat information. Status code: " + httpResponse.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
