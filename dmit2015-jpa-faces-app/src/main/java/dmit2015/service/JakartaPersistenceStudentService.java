package dmit2015.service;

import dmit2015.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Named("jakartaPersistenceStudentService")
@ApplicationScoped
public class JakartaPersistenceStudentService implements StudentService {

    @Inject
    private SecurityContext _securityContext;

    // Assign a unitName if there are more than one persistence unit defined in persistence.xml
    @PersistenceContext (unitName="postgresql-jpa-pu")
    private EntityManager _entityManager;

    @Override
    @Transactional
    public Student createStudent(Student student) {
        // If the primary key is not an identity column then write code below here to
        // 1) Generate a new primary key value
        // 2) Set the primary key value for the new entity

        String username = _securityContext.getCallerPrincipal().getName();
        student.setUsername(username);

        _entityManager.persist(student);
        return student;
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        try {
            Student querySingleResult = _entityManager.find(Student.class, id);
            if (querySingleResult != null) {
                return Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            // id value not found
            throw new RuntimeException(ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> getAllStudents() {
        // Deny access to anonymous users or users not in the role DMIT2015.1242.A01 or Sales.
        boolean hasRequiredRole =
                _securityContext.isCallerInRole("DMIT2015.1242.A01")
                ||
                _securityContext.isCallerInRole("Sales");
        if (!hasRequiredRole) {
            throw new SecurityException("Access denied. You don't have the permission to access this method.");
        }

        // For the DMIT2015.1242.A02 role return all Student associated username
        boolean isInDMIT2015A02Roles =  _securityContext.getCallerPrincipal().getName().equals("DMIT2015.1242.A02");
        if (isInDMIT2015A02Roles) {
            String username = _securityContext.getCallerPrincipal().getName();
            return _entityManager.createQuery(
                    "select o from Student o where o.username = :usernameValue", Student.class)
                    .setParameter("usernameValue", username)
                    .getResultList();
        }

        // For the Sales role return all Student
        return _entityManager.createQuery("SELECT o FROM Student o ", Student.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {

        Optional<Student> optionalStudent = getStudentById(student.getId());
        if (optionalStudent.isEmpty()) {
            String errorMessage = String.format("The id %s does not exists in the system.", student.getId());
            throw new RuntimeException(errorMessage);
        } else {
            var existingStudent = optionalStudent.orElseThrow();
            // Update only properties that is editable by the end user

            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setCourseSection(student.getCourseSection());
            existingStudent.setPicture(student.getPicture());

            student = _entityManager.merge(existingStudent);
        }
        return student;
    }

    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        Optional<Student> optionalStudent = getStudentById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.orElseThrow();
            // Write code to throw a RuntimeException if this entity contains child records
            _entityManager.remove(student);
        } else {
            throw new RuntimeException("Could not find Student with id: " + id);
        }
    }

}