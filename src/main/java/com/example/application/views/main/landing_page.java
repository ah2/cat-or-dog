package com.example.application.views.main;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class landing_page extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	public landing_page() {
		// IFrame iFrame = new IFrame("hello-world.html");
		Anchor anchor = new Anchor();
		anchor.setText("go to cat or dog");
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		add(anchor);
	}

}
