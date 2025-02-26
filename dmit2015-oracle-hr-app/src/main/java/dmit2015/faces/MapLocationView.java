package dmit2015.faces;

import dmit2015.model.MapLocation;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.util.List;

@Named("currentMapLocationView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
// class must implement Serializable
public class MapLocationView implements Serializable {

    // Declare read only properties (field + getter) for data sources
    @Getter
    private List<MapLocation> locations = List.of(
            new MapLocation("Home Depot Edmonton Skyview",53.5997,-113.552_13360),
            new MapLocation("Home Depot Edmonton Westmount",53.560425,-113.573074),
            new MapLocation("Home Depot Edmonton Westend",53.5361,-113.621)
    );

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onSubmit() {
        try {


        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onSelected() {
        try {


        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onClear() {


    }

    /**
     * This method is used to handle exceptions and display root cause to user.
     *
     * @param ex The Exception to handle.
     */
    protected void handleException(Exception ex) {
        var details = new StringBuilder();
        Throwable causes = ex;
        while (causes.getCause() != null) {
            details.append(ex.getMessage());
            details.append("    Caused by:");
            details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
        Messages.create(ex.getMessage()).detail(details.toString()).error().add("errors");
    }

}