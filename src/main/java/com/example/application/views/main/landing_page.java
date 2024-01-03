package com.example.application.views.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class landing_page extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public landing_page() {
		// IFrame iFrame = new IFrame("hello-world.html");
		Anchor anchor = new Anchor();
		anchor.setText("go to cat or dog");
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		add(anchor);

		Button button = new Button("Click me!");

		class MyClickListener implements ComponentEventListener<ClickEvent<Button>> {
			private static final long serialVersionUID = 1L;
			int count = 0;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				event.getSource().setText("You have clicked me " + (++count) + " times");
			}
		}
		button.addClickListener(new MyClickListener());

		add(button);
	}

}
