package dmit2015.faces;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import org.omnifaces.util.Messages;

import java.io.Serializable;

@Named("currentCounterSessionScopedView")
@SessionScoped  // create this object for one HTTP session and destroy after the HTTP session expires
// class must implement Serializable
public class CounterSessionScopedView implements Serializable {

    @Getter
    private int counter = 0;

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onSubmit() {
        try {
            counter++;
            Messages.addGlobalInfo("SessionScoped Counter = {0}", counter);

        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onSelected() {
        try {
            // TODO: add code to handle action

        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onClear() {
        // TODO: Set all fields to default values
        // currentCounterSessionScoped = new CounterSessionScoped();
        // selectedCounterSessionScoped = null;
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