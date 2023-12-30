package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cat or Dog")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    //@Value("${apiKey}")
    //private String apiKey;
	private TextField name;
    private Button sayHello;

    public MainView() {
    	
    	//System.out.print("the key is:%s".format(apiKey));
    	
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
        
        Image image = new Image();
        //image.setSource(new ClassResource("/images/sponsor.png"));
        
        
        
    }

}
