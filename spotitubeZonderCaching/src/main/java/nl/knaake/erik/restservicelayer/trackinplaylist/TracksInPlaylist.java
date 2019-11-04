package nl.knaake.erik.restservicelayer.trackinplaylist;



import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.domain.trackinplaylist.TrackInPlaylistHandler;
import nl.knaake.erik.restservicelayer.EndpointWithSession;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Handles any request that has to do with a specific playlist and the playlists tracks
 */
@Path("/playlists/{playlistid}/tracks")
public class TracksInPlaylist extends EndpointWithSession {

    @Inject
    private TrackInPlaylistHandler handler;

    /**
     * Get all tracks from the given playlist if the user is authenticated
     * @param token Authentication token of the user
     * @param playlistId Playlist to get tracks from
     * @return Either a 200 OK with the tracks from the playlist OR a 403 FORBIDDEN response when the token is invalid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlistid") int playlistId) {
        if(getSessionHandler().validateSession(token)) {
            return Response.ok().entity(handler.getAllTracksFromPlaylist(playlistId)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Delete the given track from the given playlist if the user is authenticated
     * @param token Authentication token of the user
     * @param playlistId Playlist from where the track should be deleted
     * @param trackId Track that should be deleted from the playlist
     * @return Either a 200 OK with the modified playlist OR a 403 FORBIDDEN response when the token is invalid
     */
    @DELETE
    @Path("/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistid") int playlistId, @PathParam("trackId") int trackId) {
        if(getSessionHandler().validateSession(token)) {
            handler.deleteTrackFromPlaylist(playlistId, trackId);
            return Response.ok().entity(handler.getAllTracksFromPlaylist(playlistId)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Adds a track to the given playlist if the user is authenticated
     * @param token Authentication token of the user
     * @param playlistId Playlist to add the track to
     * @param trackDTO All track information of the track to add to the playlist
     * @return Either a 200 OK with the modified playlist OR a 403 FORBIDDEN response when the token is invalid
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("playlistid") int playlistId, TrackDTO trackDTO) {
        if(getSessionHandler().validateSession(token)) {
            handler.addTrackToPlaylist(playlistId, trackDTO);
            return Response.status(Response.Status.CREATED).entity(handler.getAllTracksFromPlaylist(playlistId)).build();
        }
        else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
