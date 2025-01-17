package dmit2015.faces;

import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;

@Named("currentCounterApplicationScopedView")
@ApplicationScoped  // create this object once for the application
public class CounterApplicationScopedView {

    @Getter
    private int counter = 0;

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() {
        // Code to access fields annotated with @Inject

    }

    public void onSubmit() {
        counter++;
        Messages.addGlobalInfo("ApplicationScoped Counter = {0}", counter);
    }

    /**
     * This method is used to handle exceptions and display root cause to user.
     *
     * @param ex The Exception to handle.
     */
    protected void handleException(Exception ex) {
        StringBuilder details = new StringBuilder();
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