package live.hamd.ah2.catdog;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "mytodo")
public class CatorDogApp implements AppShellConfigurator {

    private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		
		//demo for api calls
		Http_tools.api_demo();
		
		//starting the web app
        //SpringApplication.run(CatorDogApp.class, args);
    }

}
