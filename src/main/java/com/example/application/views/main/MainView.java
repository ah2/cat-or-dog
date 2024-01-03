package com.example.application.views.main;

import java.io.IOException;
import java.util.Properties;

import com.example.application.Http_tools;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("removal")
@PageTitle("Cat or Dog")
@Route(value = "catordog")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private String cat_key;
	private String dog_key;
	private String catURL;
	private String dogURL;
	private int dog_c;
	private int cat_c;

	protected VerticalLayout dog_lay;
	protected VerticalLayout cat_lay;

	public MainView() {

		if (cat_key == null || dog_key == null || catURL == null || dogURL == null)
			Get_api_keys();

		Http_tools dogs = new Http_tools(dogURL, dog_key);
		Http_tools cats = new Http_tools(catURL, cat_key);

		create_view(this, dogs, cats);

	}

	private void create_view(VerticalLayout main, Http_tools dogs, Http_tools cats) {

		main.removeAll();

		Image catImage = new Image();
		Image dogImage = new Image();

		Div cat_box = new Div();
		Div dog_box = new Div();

		catImage.setSrc(cats.get_img_url());
		catImage.setId("cat image");
		
		dogImage.setSrc(dogs.get_img_url());
		dogImage.setSizeFull();
		dogImage.setId("dog image");

		class imageClick implements ComponentEventListener<ClickEvent<Image>> {

			private static final long serialVersionUID = 1L;
			String name;

			public imageClick(String name) {
				this.name = name;
				switch (name) {
				case "cat":
					cat_c = 0;
					break;
				case "dog":
					dog_c = 0;
					break;
				default:
					System.err.println("image name not recognized");
				}

			}

			@Override
			public void onComponentEvent(ClickEvent<Image> event) {

				switch (name) {
				case "cat":
					cat_c++;
					event.getSource().setSrc(cats.get_img_url());
					break;
				case "dog":
					dog_c++;
					event.getSource().setSrc(dogs.get_img_url());
					break;
				default:
				}
			}
		}

		dogImage.addClickListener(new imageClick("dog"));
		catImage.addClickListener(new imageClick("cat"));

		HorizontalLayout fittingLayout = new HorizontalLayout();

		dog_box.setWidth(250, Unit.PIXELS);
		dog_box.setHeight(250, Unit.PIXELS);
		cat_box.setWidth(250, Unit.PIXELS);
		cat_box.setHeight(250, Unit.PIXELS);

		dog_lay = new VerticalLayout();
		cat_lay = new VerticalLayout();
		dog_lay.setAlignItems(Alignment.CENTER);
		cat_lay.setAlignItems(Alignment.CENTER);

		Label dog_label = new Label("Dogs!");
		dog_label.getElement().setProperty("innerHTML", "<b>Dogs!</b>");

		dog_label.setSizeUndefined();
		Label cat_label = new Label("Cats!");
		cat_label.getElement().setProperty("innerHTML", "<b>Cats!</b>");
		cat_label.setSizeUndefined();

		dog_box.add(dogImage);
		cat_box.add(catImage);
		dogImage.setSizeFull();
		catImage.setSizeFull();

		dog_lay.add(dog_label, dog_box);
		cat_lay.add(cat_label, cat_box);

		fittingLayout.add(dog_lay);
		fittingLayout.add(cat_lay);

		main.add(fittingLayout);

		// Create layout
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setAlignItems(Alignment.CENTER);

		main.add(footerLayout);
	}

	private Float getARatio(Image element) {
		int h = Integer.parseInt(element.getHeight());
		int w = Integer.parseInt(element.getWidth());
		return (w * 1f) / h;
	}

	private Float getARatio(Div element) {
		int h = Integer.parseInt(element.getHeight());
		int w = Integer.parseInt(element.getWidth());
		return (w * 1f) / h;
	}

	private int getHeight(Image element) {
		return Integer.parseInt(element.getHeight());
	}

	private int getHeight(Div element) {
		return Integer.parseInt(element.getHeight());
	}

	private int getWidth(Image element) {
		return Integer.parseInt(element.getWidth());
	}

	private int getWidth(Div element) {
		return Integer.parseInt(element.getWidth());
	}

	private String Get_api_keys() {
		Properties prop = new Properties();
		try {
			// load a properties file from class path, inside static method
			prop.load(MainView.class.getClassLoader().getResourceAsStream("apikey.properties"));

			// get the property value
			cat_key = prop.getProperty("API_CAT_KEY_VALUE");
			dog_key = prop.getProperty("API_DOG_KEY_VALUE");
			catURL = prop.getProperty("CAT_API_URL");
			dogURL = prop.getProperty("DOG_API_URL");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
