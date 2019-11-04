package nl.knaake.erik.restservicelayer.login;

import nl.knaake.erik.crosscuttingconcerns.dtos.LoginDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Offers functionality to login
 */
@Path("/login")
public class Login {

    @Inject
    private ILoginHandler handler;

    /**
     * Logs in a user when the given account details are correct, also starts the users session
     * @param loginDTO Username and password combination that a users tries to use to login
     * @return Either 200 OK when login credentials are correct or a 401 UNAUTHORIZED when the login credentials are incorrect
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handleLogin(LoginDTO loginDTO) {
        if(handler.correctPassword(loginDTO)) {
            return Response.status(Response.Status.CREATED).entity(handler.getSession(loginDTO.getUser())).build();
        }
        else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
