package live.hamd.ah2.catdog.views.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class landing_page extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public landing_page() {
		
		Div w = new Div();
		
		w.setWidth("150px");
		w.setHeight("150px");
		w.setText("W.I.P.");
		
		
		Div anchor = new Div();
		anchor.getElement().setProperty("innerHTML", "<span style=\"color:blue\"><b>go to cat or dog</b></span>");
		
		anchor.setClassName("bluetext");
		
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		
		add(w);
		add(anchor);
	}
}
