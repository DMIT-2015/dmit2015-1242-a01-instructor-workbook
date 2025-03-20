package dmit2015.restclient;

import dmit2015.model.Student;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Optional;

@RequestScoped
@RegisterProvider(BadRequestResponseMapper.class)
@RegisterRestClient(baseUri = "https://dmit-2015-restapi-bootable-swu-nait-dev.apps.rm3.7wse.p1.openshiftapps.com/restapi/StudentDtos")
public interface StudentMpRestClient {

    @POST
    Response create(Student newStudent);

    @GET
    List<Student> findAll();

    @GET
    @Path("/{id}")
    Optional<Student> findById(@PathParam("id") Long id);

    @PUT
    @Path("/{id}")
    Student update(@PathParam("id") Long id, Student updatedStudent);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);

}