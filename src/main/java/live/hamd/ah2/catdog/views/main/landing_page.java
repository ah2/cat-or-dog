package live.hamd.ah2.catdog.views.main;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class landing_page extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public landing_page() {
		
		Label wip =new Label("W.I.P.");
		wip.setSizeFull();
		
		Div w = new Div();
		w.setWidth("90%");
		w.setHeight("90%");
		w.add(wip);
		
		Anchor anchor = new Anchor();
		anchor.setText("go to cat or dog");
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		
		add(w);
		add(anchor);
	}

}
