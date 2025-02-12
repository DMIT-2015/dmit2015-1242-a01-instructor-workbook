package dmit2015.tools;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * This program generates the database schema for a persistence unit name
 * defined in `persistence.xml`with a RESOURCE_LOCAL transaction type.
 * <p>
 * The following is an example of a RESOURCE_LOCAL persistence unit:
 *
 * <pre>{@code
 *     <persistence-unit name="resource-local-mssql-dmit2015-jpa-pu" transaction-type="RESOURCE_LOCAL">
 *      <properties>
 *             <property name="hibernate.dialect" value="org.hibernate.dialect.SQLServerDialect"/>
 *             <property name="jakarta.persistence.jdbc.driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
 *             <property name="jakarta.persistence.jdbc.url" value="jdbc:sqlserver://localhost;databaseName=DMIT2015_CourseDB;TrustServerCertificate=true" />
 *             <property name="jakarta.persistence.jdbc.user" value="user2015" />
 *             <property name="jakarta.persistence.jdbc.password" value="Password2015" />
 *     </persistence-unit>
 * }
 * </pre>
 *
 * @author Sam Wu
 */
public class JakartaPersistenceDatabaseSchemaGenerator {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.hibernate.jpa.HibernatePersistenceProvider");
        // The persistenceUnitName can be passed as an argument otherwise it defaults to "local-mssql-dmit2015-jpa-pu"
        String persistenceUnitName = (args.length == 1) ? args[0] : "resource-local-postgresql-jpa-pu";
        // Create a map that defines properties to control database schema generation.
        Map<String, String> properties = new HashMap<>();
        // Set the map property to drop all mapped database tables then re-create the mapped database tables..
        // Valid value for jakarta.persistence.schema-generation.database.action are:  "none", "create", "drop-and-create", "drop"

        properties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        // https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#obtaining-an-entity-manager-factory-in-a-java-se-environment
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        emf.createEntityManager().close();
        emf.close();
        System.out.printf("Finished creating database schema for persistence unit %s\n", persistenceUnitName);
    }

}