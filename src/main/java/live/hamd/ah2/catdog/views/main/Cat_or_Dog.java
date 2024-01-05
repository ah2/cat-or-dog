package live.hamd.ah2.catdog.views.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import live.hamd.ah2.catdog.Http_tools;

@PageTitle("Cat or Dog")
@Route(value = "catordog")
public class Cat_or_Dog extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	
	private int dog_click_count;
	private int cat_click_count;
	private Div score_div;

	protected VerticalLayout dog_lay;
	protected VerticalLayout cat_lay;

	public Cat_or_Dog() {
		
		//setup api calling objects
		Http_tools dog_api = new Http_tools("dog");
		Http_tools cat_api = new Http_tools("cat");
		
		//create view
		VerticalLayout main = create_view(dog_api, cat_api);

		add(main);
	
	}

	private VerticalLayout create_view(Http_tools dogs, Http_tools cats) {
		
		Image catImage = new Image();
		Image dogImage = new Image();

		Div cat_box = new Div();
		Div dog_box = new Div();
		
		
		int width = 300;
		int height = 250;
		Unit unit = Unit.PIXELS;

		dog_box.setWidth(width, unit);
		dog_box.setHeight(height, unit);
		cat_box.setWidth(width, unit);
		cat_box.setHeight(height, unit);
		
		score_div = new Div();
		score_div.add(new Div("Cats or dog? you decide"));
		
		class imageClick implements ComponentEventListener<ClickEvent<Image>> {

			private static final long serialVersionUID = 1L;
			String name;

			public imageClick(String name) {
				this.name = name;
				switch (name) {
				case "cat":
					cat_click_count = 0;
					catImage.setSrc(cats.get_rand_img_url());
					break;
				case "dog":
					dog_click_count = 0;
					dogImage.setSrc(dogs.get_rand_img_url());
					break;
				default:
					System.err.println("image name not recognized");
				}
			}

			@Override
			public void onComponentEvent(ClickEvent<Image> event) {

				switch (name) {
				case "cat":
					cat_click_count++;
					event.getSource().setSrc(cats.get_rand_img_url());
					break;
				case "dog":
					dog_click_count++;
					event.getSource().setSrc(dogs.get_rand_img_url());
					break;
				default:
				}
				if (dog_click_count > cat_click_count) {
					score_div.removeAll();
					score_div.add(new Div(String.format("Dogs are winning %d to %d!", dog_click_count, cat_click_count)));
				} else if (cat_click_count > dog_click_count) {
					score_div.removeAll();
					score_div.add(new Div(String.format("Cats are winning %d to %d!", cat_click_count, dog_click_count)));
				} else {
					score_div.removeAll();
					score_div.add(new Div(String.format("its a tie %d to %d!", dog_click_count, cat_click_count)));
				}
			}
		}

		dogImage.addClickListener(new imageClick("dog"));
		catImage.addClickListener(new imageClick("cat"));

		dog_lay = new VerticalLayout();
		cat_lay = new VerticalLayout();
		dog_lay.setAlignItems(Alignment.CENTER);
		cat_lay.setAlignItems(Alignment.CENTER);

		Div dog_label = new Div("Dogs!");
		dog_label.getElement().setProperty("innerHTML", "<b>Dogs!</b>");
		dog_label.setSizeUndefined();

		Div cat_label = new Div();
		cat_label.getElement().setProperty("innerHTML", "<b>Cats!</b>");
		cat_label.setSizeUndefined();

		dog_box.add(dogImage);
		cat_box.add(catImage);
		dogImage.setSizeFull();
		catImage.setSizeFull();

		dog_lay.add(dog_label, dog_box);
		cat_lay.add(cat_label, cat_box);

		HorizontalLayout joint_Layout = new HorizontalLayout();
		joint_Layout.add(dog_lay);
		joint_Layout.add(cat_lay);
		
		VerticalLayout result= new VerticalLayout();
		result.setAlignItems(Alignment.CENTER);
		result.add(joint_Layout);
		result.add(score_div);

		return result;
	}
}
