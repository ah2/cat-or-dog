package live.hamd.ah2.catdog;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import live.hamd.ah2.catdog.views.main.Cat_or_Dog;

public class Http_tools {
	String key;
	String url;
	int score;
	ArrayList<String> prev_urls;
	

	public Http_tools(String url, String key) {
		this.url = url;
		this.key = key;
		this.prev_urls = new ArrayList<String>();
	}
	
	public String get_img_url(){
		 ArrayList<String> resp = get_img_url(1);
		 if(resp != null)
			 return resp.get(0);
		 return null;
	}

	public ArrayList<String> get_img_url(int num) {
		
		HttpResponse<String> response = http_request(num);
		
		if (response.statusCode() == 200) {
			return pars_json_return_urls(response.body());
		}
		
		System.err.println("Error code: " + response.statusCode()+"!");
		return null;
	}

	public ArrayList<String> pars_json_return_urls(String data) {
		//System.err.println(data);
		ArrayList<String> urls = new ArrayList<String>();
		try {
		JSONArray jArray = new JSONArray(data);
		for (Object o : jArray) {
			JSONObject json_Item = (JSONObject) o;
			String url = json_Item.getString("url");
			urls.add(url);
		}
		
		prev_urls.addAll(urls);
		return urls;
		}
		
		catch(JSONException e) {
			System.err.println("not a json array!");
			return null;
		}
	}
	
	public HttpResponse<String> http_request(int num) {
		
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request;
		
		try {
			request = HttpRequest.newBuilder()
			  .header("x-api-key", key)
			  .uri(new URI(url+"?limit="+num+"&mime_types=gif")).build();
			
			//System.err.println(request.headers());

			return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		//return null;
		} catch (URISyntaxException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//example code outputs retrieved Url strings to console
	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			// load a properties file from class path, inside static method
			prop.load(Cat_or_Dog.class.getClassLoader().getResourceAsStream("apikey.properties"));

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
			
			ArrayList<String> dog_imgs = dogs.get_img_url(15);

			if (cat != null && dog != null) {
				System.out.println("received dog image url: " + cat);
				System.out.println("received cat image url: " + dog);
				
				StringBuilder sb = new StringBuilder();
				sb.append("received: "+dog_imgs.size()+" dog image urls:\n");
				
				for(String d : dog_imgs) {
					sb.append(d+"\n"); }
				
				System.out.println(sb.toString());
				
			}
			
			//HttpResponse<String> tst = dogs.new_conn();
			//System.err.println(tst.body());

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
