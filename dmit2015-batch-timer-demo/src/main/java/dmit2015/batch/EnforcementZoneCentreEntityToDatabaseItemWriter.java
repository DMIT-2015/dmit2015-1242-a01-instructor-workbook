package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;
import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

/**
 * An ItemWriter is executed after an ItemProcessor has executed.
 */
@Named
@Dependent
public class EnforcementZoneCentreEntityToDatabaseItemWriter extends AbstractItemWriter {

    @PersistenceContext//(unitName = "mssql-jpa-pu")
    private EntityManager _entityManager;

    /**
     * Write a list of items returned from the ItemProcessor to a destination data source..
     */
    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object item : items) {
            EnforcementZoneCentre currentItem = (EnforcementZoneCentre) item;
            _entityManager.persist(currentItem);
        }
    }

}