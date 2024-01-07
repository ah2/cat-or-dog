package live.hamd.ah2.catdog.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "main")
public class landing_page extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public landing_page() {
		
		Div wip = new Div();
		
		wip.setWidth("150px");
		wip.setHeight("150px");
		wip.setText("W. I. P.");
		wip.setClassName("wip");
		
		
		Button anchor = new Button();
		anchor.getElement().setProperty("innerHTML", "<span style=\"color:blue\"><b>go to cat or dog</b></span>");
		
		anchor.setClassName("bluetext");
		
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		
		add(wip);
		add(anchor);
	}
}
