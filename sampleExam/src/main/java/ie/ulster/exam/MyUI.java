package ie.ulster.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
       
    
        
        String connectionString = "jdbc:sqlserver://chrisdunleavy1.database.windows.net:1433;" + 
			  "database=sampleExam;" + 
			  "user=Chris@chrisdunleavy1;" + 
			  "password=ulsterStudent1;" + 
			  "encrypt=true;" + 
			  "trustServerCertificate=false;" + 
			  "hostNameInCertificate=*.database.windows.net;" +
			  "loginTimeout=30;";

	// Create the connection object
	Connection connection = null;  
        
        final VerticalLayout layout = new VerticalLayout(); //master layout
        HorizontalLayout hlayout = new HorizontalLayout(); //horizontal layout
        Grid<RoomBooking> myGrid = new Grid<>();            //grid
        myGrid.setWidth("1200px");
    
        Label logo = new Label("<H1>Marty Party Planners</H1> <p/> <h3>Please enter the details below and click Book</h3><br>");
        logo.setContentMode(ContentMode.HTML);
        final TextField name = new TextField();             //textfield for enter party name
        name.setCaption("Type your name here:");
    
        Slider s = new Slider(0, 250);                      // for how many people
        s.setWidth("500px");                                // width of slider
        s.setCaption("How many people are ?");
        s.addValueChangeListener(e ->{
            double x = s.getValue();
        });

        ComboBox<String> comboBox = new ComboBox<String>("Children aloowed?");
        comboBox.setItems("no", "yes");

        Button button = new Button("Click me");
        Label messageLabel = new Label();
        messageLabel.setContentMode(ContentMode.HTML);
               
        try {
            // Connect with JDBC driver to a database
	        connection = DriverManager.getConnection(connectionString);
	
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM rmBooking;");
            List<RoomBooking> rmBooking = new ArrayList<RoomBooking>();

            
            while(rs.next())
            {   
                // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
                rmBooking.add(new RoomBooking(rs.getString("room"), 
                            rs.getInt("capacity"), 
                            rs.getString("feature"),
                            rs.getBoolean("Alcohol_Allowed")));
            } 
         
        // Add my component, grid is templated with Customer
        
        // Set the items (List)
        myGrid.setItems(rmBooking);
        // Configure the order and the caption of the grid
        myGrid.addColumn(RoomBooking::getRoom).setCaption("Room");
        myGrid.addColumn(RoomBooking::getCapacity).setCaption("Capacity");
        myGrid.addColumn(RoomBooking::getFeature).setCaption("Feature");
        myGrid.addColumn(RoomBooking::isAlcohol).setCaption("Alcohol_Allowed");

        // Add the grid to the list
        //layout.addComponent(myGrid);
         } catch (Exception e) {
            //TODO: handle exception
            layout.addComponent(new Label(e.getMessage()));
        }

        myGrid.setSelectionMode(SelectionMode.MULTI);       //select multiple columns
       // initilizing the acton listen for grid.
        myGrid.addSelectionListener(event ->{
            
        });


        layout.addComponent(logo);
        hlayout.addComponents(name,s,comboBox);
        layout.addComponent(hlayout);
        layout.addComponent(myGrid);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
} 

