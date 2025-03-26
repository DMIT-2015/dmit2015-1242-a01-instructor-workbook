package dmit2015.service;

import dmit2015.faces.LoginSession;
import dmit2015.model.Student;
import dmit2015.restclient.StudentMpRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@Named("currentMpRestClientStudentService")
@ApplicationScoped
public class MpRestClientStudentService implements StudentService {

    @Inject
    private LoginSession _loginSession;

    @Inject
    @RestClient
    private StudentMpRestClient mpRestClient;

    @Override
    public Student createStudent(Student student) {
        String authorizationHeader = _loginSession.getAuthorization();
        Response response = mpRestClient.create(authorizationHeader, student);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new RuntimeException("Create HTTP request failed : HTTP error code : " + response.getStatus());
        } else {
            String location = response.getHeaderString("Location");
            int resourceIdIndex = location.lastIndexOf("/") + 1;
            Long resourceId = Long.parseLong(location.substring(resourceIdIndex));
            student.setId(resourceId);
        }
        return student;
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        String authorizationHeader = _loginSession.getAuthorization();
        return mpRestClient.findById(authorizationHeader,id);
    }

    @Override
    public List<Student> getAllStudents() {
        String authorizationHeader = _loginSession.getAuthorization();
        return mpRestClient.findAll(authorizationHeader);
    }

    @Override
    public Student updateStudent(Student student) {
        String authorizationHeader = _loginSession.getAuthorization();
        return mpRestClient.update(authorizationHeader,student.getId(), student);
    }

    @Override
    public void deleteStudentById(Long id) {
        String authorizationHeader = _loginSession.getAuthorization();
        mpRestClient.delete(authorizationHeader, id);
    }
}