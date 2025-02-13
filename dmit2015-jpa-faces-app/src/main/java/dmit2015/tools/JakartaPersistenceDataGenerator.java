package dmit2015.tools;

import dmit2015.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

public class JakartaPersistenceDataGenerator {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.hibernate.jpa.HibernatePersistenceProvider");
        // The persistenceUnitName can be passed as an argument otherwise it defaults to "local-mssql-dmit2015-jpa-pu"
        String persistenceUnitName = (args.length == 1) ? args[0] : "resource-local-postgresql-jpa-pu";
        // https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#obtaining-an-entity-manager-factory-in-a-java-se-environment
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        JakartaPersistenceDataGenerator.generateData(em);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.printf("Finished creating database schema for persistence unit %s\n", persistenceUnitName);
    }

    public static void generateData(EntityManager em) {
        var faker = new Faker();
        for(int counter = 1; counter <= 5; counter++) {
            var currentStudent = new Student();
            currentStudent.setFirstName(faker.name().firstName());
            currentStudent.setLastName(faker.name().lastName());
            currentStudent.setCourseSection("DMIT2015-A01");
            em.persist(currentStudent);
        }
    }
}