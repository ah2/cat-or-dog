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

public class Http_tools {
	String api_key;
	String api_base_url;
	int score;
	ArrayList<String> prev_ids;

	public Http_tools(String animal) {

		Properties prop = new Properties();
		try {
			prop.load(Http_tools.class.getClassLoader().getResourceAsStream("apikey.properties"));

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

	public Http_tools(String url, String key) {
		this.api_base_url = url;
		this.api_key = key;
		this.prev_ids = new ArrayList<String>();
	}

	public String get_rand_img_url() {
		String ur = api_base_url + "/search" + "?limit=" + 1;
		ArrayList<String> url = http_request_random_images(1,ur);
		if (url != null)
			return url.get(0);
		return null;
	}


	private ArrayList<String> pars_json_filter(String data, String target) {
		// System.err.println(data);
		ArrayList<String> items = new ArrayList<String>();
		try {
			JSONArray jArray = new JSONArray(data);
			for (Object o : jArray) {
				JSONObject json_Item = (JSONObject) o;
				String url = json_Item.getString(target);
				items.add(url);
			}

			// to url history
			return items;
		}

		catch (JSONException e) {
			System.err.println("not a json array!");
			return null;
		}
	}
	
	private ArrayList<String> http_request_random_images(int num){
		
		String ur = api_base_url + "/search" + "?limit=" + num;
		return http_request_random_images(num,ur);
	}


	private ArrayList<String> http_request_random_images(int num, String ur) {

		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request;

		try {
			request = HttpRequest.newBuilder().header("x-api-key", api_key)
					.uri(new URI(ur)).build();

			// System.err.println(request.headers());

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response != null)
				if (response.statusCode() == 200) {
					return pars_json_filter(response.body(), "url");
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
		Http_tools dogs = new Http_tools("dog");
		Http_tools cats = new Http_tools("cat");

		System.out.println("keys: " + cats.api_key + " - " + dogs.api_key);
		System.out.println("urls: " + cats.api_base_url + " - " + dogs.api_base_url);

		String cat = cats.get_rand_img_url();
		String dog = dogs.get_rand_img_url();
		
		ArrayList<String> dog_imgs = dogs.http_request_random_images(15);

		if (cat != null && dog != null) {
			System.out.println("received dog image url: " + cat);
			System.out.println("received cat image url: " + dog);

			StringBuilder sb = new StringBuilder();
			sb.append("received: " + dog_imgs.size() + " dog image urls:\n");

			for (String d : dog_imgs) {
				sb.append(d + "\n");
			}
			System.out.println(sb.toString());
		}
	}
}
