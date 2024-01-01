package com.example.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.application.views.main.MainView;

public class Http_tools {
	String key;
	String url;
	int score;

	public Http_tools(String url, String key) {
		this.url = url;
		this.key = key;
	}

	public String get_img_url() {
		String response = getImgJsonStr(url, key);
		// System.out.println("got Json: " + response);
		// Notification.show("got url: " + url);
		
		//System.out.println("url: " + url+" key: "+key);
		ArrayList<String> urls;
		if (response != null) {
			urls= pars_to_url(response);
			if (urls != null)
				return pars_to_url(response).get(0);
		}
		// System.out.println("got url: " + url);

		System.err.println("empty reponse!");
		return null;
	}

	public static ArrayList<String> pars_to_url(String data) {
		//System.out.println(data);
		ArrayList<String> urls = new ArrayList<String>();
		try {
		JSONArray jArray = new JSONArray(data);
		for (Object o : jArray) {
			JSONObject jsonLineItem = (JSONObject) o;
			String url = jsonLineItem.getString("url");
			urls.add(url);
		}
		return urls;
		}
		catch(JSONException e) {
			System.err.println("not a json array!");
			return null;
		}
	}

	public static String getImgJsonStr(String url, String api_key) {
		try {
			@SuppressWarnings("deprecation")
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("x-api-key", api_key);

			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				return response.toString();
			}
			System.err.println("Error response: " + responseCode + "!");
			return null;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}
	
	
	//example code outputs retrieved Url strings to console
	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			// load a properties file from class path, inside static method
			prop.load(MainView.class.getClassLoader().getResourceAsStream("apikey.properties"));

			// get the property value
			String cat_key = prop.getProperty("API_CAT_KEY_VALUE");
			String dog_key = prop.getProperty("API_DOG_KEY_VALUE");
			String catURL = prop.getProperty("CAT_API_URL");
			String dogURL = prop.getProperty("DOG_API_URL");
			
			
			System.out.println("keys: "+cat_key + " - " + dog_key);
			System.out.println("urls: "+catURL + " - " + dogURL);
			
			Http_tools dogs = new Http_tools(catURL, dog_key);
			Http_tools cats = new Http_tools(dogURL, cat_key);

			String cat = cats.get_img_url();
			String dog = dogs.get_img_url();

			if (cat != null && dog != null) {
				System.out.println("received dog image url: " + cat);
				System.out.println("received cat image url: " + dog);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
