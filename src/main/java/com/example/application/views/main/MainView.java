package com.example.application.views.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.application.Http_tools;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Cat or Dog")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private String cat_key;
    private String dog_key;
    private String catURL;
    private String dogURL;

    public MainView() {
    	
    	if(cat_key == null || dog_key == null || catURL == null || dogURL == null)
    		Get_keys();
       
        
    	VerticalLayout main = new VerticalLayout();
        add(main);
        
        Http_tools dogs = new Http_tools(dogURL, dog_key);
		Http_tools cats = new Http_tools(catURL, cat_key);

		String cat = cats.get_img_url();
		String dog = dogs.get_img_url();

		Image catImage = new Image();
        if(cat!=null)
        	catImage.setSrc(cats.get_img_url());
        catImage.addClickListener(e -> {
            Notification.show("you choose cat!");
            catImage.setSrc(cats.get_img_url());
        });
        //cat_dog_tab.add(catImage);
        //add(catImage);
        
        Image dogImage = new Image();
        if (dog != null)
        	dogImage.setSrc(dogs.get_img_url());
        dogImage.addClickListener(e -> {
            Notification.show("you choose dog!");
            dogImage.setSrc(dogs.get_img_url());
        });
        
        catImage.setWidth("30");
        dogImage.setWidth("30");
        
        VerticalLayout vertL = new VerticalLayout();
        vertL.add(dogImage);

        VerticalLayout vertR = new VerticalLayout();
        vertR.add(catImage);

        
        vertL.setWidth(10f, Unit.CM);
        vertR.setWidth(10f, Unit.CM);
        
        HorizontalLayout layout = new HorizontalLayout(vertL, vertR);
        main.add(layout);
        //add(dogImage);
        
        
    }
   
    
	private String Get_keys() {
		Properties prop = new Properties();
		try {
		    //load a properties file from class path, inside static method
		    prop.load(MainView.class.getClassLoader().getResourceAsStream("apikey.properties"));

		    //get the property value
		    cat_key = prop.getProperty("API_CAT_KEY_VALUE");
		    dog_key = prop.getProperty("API_DOG_KEY_VALUE");
		    catURL = prop.getProperty("CAT_API_URL");
		    dogURL = prop.getProperty("DOG_API_URL");
		    
		    //	reading url from file is broken
		    catURL = "https://api.thecatapi.com/v1/images/search?limit=1";
		    dogURL = "https://api.thedogapi.com/v1/images/search?limit=1";
	    	
			
		 
		}
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		return null;
	}
}
