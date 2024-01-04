package live.hamd.ah2.catdog.views.main;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
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
		
		
		Element element = ElementFactory.createDiv("go to cat or dog");
		element.appendChild(ElementFactory.createSpan("color:blue"));
		
		System.out.println( element.getText());
		
		
		Div anchor = new Div();
		anchor.setText("go to cat or dog");
		anchor.getElement().addEventListener("click", e -> anchor.getUI().ifPresent(ui -> ui.navigate("catordog")));
		
		add(w);
		add(anchor);
	}
}
