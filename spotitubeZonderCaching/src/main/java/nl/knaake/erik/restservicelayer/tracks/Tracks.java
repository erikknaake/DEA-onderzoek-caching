package nl.knaake.erik.restservicelayer.tracks;

import nl.knaake.erik.restservicelayer.EndpointWithSession;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Responsible for the routing of request on the /tracks endpoint.
 * Handles any request that has to do with a specific track
 */
@Path("/tracks")
public class Tracks extends EndpointWithSession {

    @Inject
    private ITrackHandler handler;

    /**
     * Gets all tracks that are not in the given playlist if the user is authenticated
     * @param token Authentication token of the user
     * @param playlistId Playlist to get other tracks for
     * @return Either 200 OK with a list of tracks that are not currently in the playlist OR a 403 FORBIDDEN if the token is invalid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotInPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        if(getSessionHandler().validateSession(token)) {
            return Response.ok().entity(handler.getTracksNotInPlaylist(playlistId)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
