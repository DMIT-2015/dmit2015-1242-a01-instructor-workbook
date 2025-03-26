package dmit2015.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("restapi") // The resource-wide application path that forms the base URI of all root resource classes.
@LoginConfig(authMethod = "MP-JWT", realmName = "dmit2015-realm")
@DeclareRoles({"Marketing","Sales","Administration"})
public class JaxRsApplication extends Application {

}