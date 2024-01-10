package live.hamd.ah2.catdog;

import java.io.FileWriter;
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

public class Api_Tools {
	String api_key;
	String api_base_url;
	int score;
	ArrayList<String> prev_ids;
	
	public static class Item{
		String id;
		String imgurl;
		int width;
		int hight;
		String[] breeds;
		
		Item(){
			this.id = "";
			this.imgurl = "";
			this.width = 0;
			this.hight = 0;
			this.breeds = null;
		}
		
		Item(String id, String imgurl, int w, int h, String[] breeds ){
			this.id = id;
			this.imgurl = imgurl;
			this.width = w;
			this.hight = h;
			this.breeds = breeds;
		}
		
		
		public String getUrl() {
			return this.imgurl;
		}	
	}

	public Api_Tools(String animal) {

		Properties prop = new Properties();
		try {
			prop.load(Api_Tools.class.getClassLoader().getResourceAsStream("apikey.properties"));

			switch (animal) {
			case "cat":
				this.api_key = prop.getProperty("API_CAT_KEY_VALUE");
				this.api_base_url = prop.getProperty("CAT_API_URL");
				break;
			case "dog":
				this.api_key = prop.getProperty("API_DOG_KEY_VALUE");
				this.api_base_url = prop.getProperty("DOG_API_URL");
				break;
			default:
				this.api_key = null;
				this.api_base_url = null;
			}
			this.prev_ids = new ArrayList<String>();
			this.score = 0;
		} catch (IOException e) {
			System.err.println("the apikey.properties file was not found or was not setup correctly");
			e.printStackTrace();
		}
	}

	public Api_Tools(String url, String key) {
		this.api_base_url = url;
		this.api_key = key;
		this.prev_ids = new ArrayList<String>();
	}

	public Item get_rand_img_url() {
		String ur = api_base_url + "/search" + "?limit=" + 1;
		ArrayList<Item> url = http_request_random_images(1,ur);
		if (url != null)
			return url.get(0);
		return null;
	}


	private ArrayList<Item> pars_json_filter(String data) {
		// System.err.println(data);
		ArrayList<Item> items = new ArrayList<Item>();
		try {
			JSONArray jArray = new JSONArray(data);
			
			
			FileWriter file;
			try {
				file = new FileWriter("./src/main/resources/api "+"0"+".json");
				file.write(jArray.toString());
				file.close();
			} catch (IOException e) {
				System.err.print("couldnt save file!");
				e.printStackTrace();
			}
			
			
			for (Object o : jArray) {
				JSONObject json_Item = (JSONObject) o;
				
				String id = json_Item.getString("id");
				String imgurl = json_Item.getString("url");
				int width =  json_Item.optInt("width", 0);
				int hight = json_Item.optInt("height", 0);
				JSONArray raw_breeds =  json_Item.getJSONArray("breeds");
				String[] breeds = {raw_breeds.toString()};
				
				items.add(new Item(id, imgurl, width, hight, breeds));
			}

			return items;
		}

		catch (JSONException e) {
			System.err.println("not a json array!");
			e.printStackTrace();
			return null;
		}
	}
	
	private ArrayList<Item> http_request_random_images(int num){
		
		String ur = api_base_url + "/search" + "?limit=" + num;
		return http_request_random_images(num,ur);
	}


	private ArrayList<Item> http_request_random_images(int num, String ur) {

		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request;

		try {
			request = HttpRequest.newBuilder().header("x-api-key", api_key)
					.uri(new URI(ur)).build();

			// System.err.println(request.headers());

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response != null)
				if (response.statusCode() == 200) {
					return pars_json_filter(response.body());
				}
			System.err.println("Error code: " + response.statusCode() + "!");
			return null;
			
			
		} catch (URISyntaxException | InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// demo code
	// example code outputs retrieved Url strings to console
	public static void api_demo() {
		Api_Tools dogs = new Api_Tools("dog");
		Api_Tools cats = new Api_Tools("cat");

		System.out.println("keys: " + cats.api_key + " - " + dogs.api_key);
		System.out.println("urls: " + cats.api_base_url + " - " + dogs.api_base_url);

		Item cat = cats.get_rand_img_url();
		Item dog = dogs.get_rand_img_url();
		
		ArrayList<Item> dog_imgs = dogs.http_request_random_images(15);

		if (cat != null && dog != null) {
			System.out.println("received dog image url: " + cat.getUrl());
			System.out.println("received cat image url: " + dog.getUrl());
		
		if(dog_imgs!=null) {
			StringBuilder sb = new StringBuilder();
			sb.append("received: " + dog_imgs.size() + " dog image urls:\n");

			for (Item d : dog_imgs) {
				sb.append(d.imgurl + "\n");
			}
			System.out.println(sb.toString());
			
			//System.out.println(""+dog.breeds.toString());
			
		}
		}
	}
}
