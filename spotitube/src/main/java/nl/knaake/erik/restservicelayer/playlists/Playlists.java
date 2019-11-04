package nl.knaake.erik.restservicelayer.playlists;

import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;
import nl.knaake.erik.restservicelayer.EndpointWithSession;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Offers functionalitty for the REST client to do CRUD operations for a users playlists
 */
@Path("/playlists")
public class Playlists extends EndpointWithSession {

    @Inject
    private IPlaylistsHandler handler;

    /**
     * Gets all playlist that a user can view if the user is authenticated
     * @param token Authentication token of the user
     * @return Either a 200 OK with a list of playlists or a 403 FORBIDDEN response if the token was invalid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if(getSessionHandler().validateSession(token)) {
            return Response.ok().entity(handler.getPlaylistsDTOByToken(token)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Deletes a playlist if the user is authenticated
     * @param token Authentication token of the user
     * @param id Id of the playlist to delete
     * @return Either a 200 OK with a full list of playlists that the user can view or a 403 FORBIDDEN response if the token was invalid
     */
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        if(getSessionHandler().validateSession(token)) {
            handler.deletePlaylist(token, id);
            return Response.ok().entity(handler.getPlaylistsDTOByToken(token)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Adds a new playlist to the users account
     * @param token Authentication token of the user
     * @param playlistDTO All inforamation required to add the new playlist to the account
     * @return Either a 200 OK with a full list of playlists that the user can view or a 403 FORBIDDEN response if the token was invalid
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
        if(getSessionHandler().validateSession(token)) {
            handler.addPlaylist(token, playlistDTO);
            return Response.status(Response.Status.CREATED).entity(handler.getPlaylistsDTOByToken(token)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Changes the name of the given playlist to the given name
     * @param token Authentication token of the user
     * @param id Id of the playlist to edit
     * @param playlistDTO Information of the playlist from which the name is extracted
     * @return Either a 200 OK with the modified list of playlists or a 403 FORBIDDEN if the token was invalid
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylistName(@QueryParam("token") String token, @PathParam("id") int id, PlaylistDTO playlistDTO) {
        if(getSessionHandler().validateSession(token)) {
            handler.editPlaylistName(token, id, playlistDTO.getName());
            return Response.ok().entity(handler.getPlaylistsDTOByToken(token)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
