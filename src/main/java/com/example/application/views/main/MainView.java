package com.example.application.views.main;

import java.io.IOException;
import java.util.Properties;

import com.example.application.Http_tools;
import com.vaadin.flow.component.Unit;
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

	protected VerticalLayout dog_lay;
	protected VerticalLayout cat_lay;

	public MainView() {

		if (cat_key == null || dog_key == null || catURL == null || dogURL == null)
			Get_api_keys();

		Http_tools dogs = new Http_tools(dogURL, dog_key);
		Http_tools cats = new Http_tools(catURL, cat_key);

		create_view(this, dogs, cats, 0, 0);

	}

	private void create_view(VerticalLayout main, Http_tools dogs, Http_tools cats, int dscore, int cscore) {

		main.removeAll();

		// getting start pics
		String cat = cats.get_img_url();
		String dog = dogs.get_img_url();

		Label dog_score = new Label("" + dscore);
		Label cat_score = new Label("" + cscore);

		Image catImage = new Image();
		Image dogImage = new Image();

		Div cat_box = new Div();
		Div dog_box = new Div();

		if (cat != null)
			catImage.setSrc(cats.get_img_url());
		catImage.setId("cat image");
		catImage.addClickListener(e -> {
			Notification.show("you choose cat!").setPosition(Notification.Position.MIDDLE);
			// catImage.setSrc(cats.get_img_url());
			create_view(main, dogs, cats, dscore, cscore + 1);
			// findElement(By.id("pushmebutton")).click();
		});

		if (dog != null)
			dogImage.setSrc(dogs.get_img_url());
		dogImage.setSizeFull();
		dogImage.setId("dog image");
		dogImage.addClickListener(e -> {
			Notification.show("you choose dog!").setPosition(Notification.Position.MIDDLE);
			// dogImage.setSrc(dogs.get_img_url());
			create_view(main, dogs, cats, dscore + 1, cscore);

		});

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
		
		
		dog_lay.add(dog_label, dog_box, dog_score);
		cat_lay.add(cat_label, cat_box, cat_score);

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
