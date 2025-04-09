package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;
import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 * An ItemProcessor is executed after an ItemReader has finished.
 */
@Named
@Dependent
public class EnforcementZoneCentreStringToEntityItemProcessor implements ItemProcessor {

    /**
     * Change the return type of this method to the type of object (JsonObject, String, etc) you are processing
     * Process one item returned from an ItemReader
     */
    @Override
    public EnforcementZoneCentre processItem(Object item) throws Exception {
       String line = (String) item;

       return EnforcementZoneCentre.parseCsv(line).orElseThrow();
    }

}