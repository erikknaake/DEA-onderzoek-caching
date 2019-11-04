package nl.knaake.erik.domain.trackinplaylist;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.restservicelayer.trackinplaylist.ITrackInPlaylistHandler;

import javax.inject.Inject;

/**
 * Offers logic to edit and query tracks in a playlist
 */
public class TrackInPlaylistHandler implements ITrackInPlaylistHandler {

    @Inject
    private ITrackInPlaylistDAO trackInPlaylistDAO;

    /**
     * Gets all tracks that belong to the given playlist
     * @param playlistId Playlist to get tracks from
     * @return Collection of tracks that are in the playlist
     */
    @Override
    public TrackCollectionDTO getAllTracksFromPlaylist(int playlistId) {
        return trackInPlaylistDAO.getAllTracksFromPlaylist(playlistId);
    }

    /**
     * Deletes the given track from a given playlist
     * @param playlistId Id of the playlist to remove a track from
     * @param trackId Id of the track to delete from the playlist
     */
    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        trackInPlaylistDAO.deleteTrackFromPlaylist(playlistId, trackId);
    }

    /**
     * Adds the given track to a given playlist
     * @param playlistId Id of the playlist to add a track to
     * @param trackDTO Information of the track to add to the playlist
     */
    @Override
    public void addTrackToPlaylist(int playlistId, TrackDTO trackDTO) {
        trackInPlaylistDAO.addTrackToPlaylist(playlistId, trackDTO.getId(), trackDTO.isOfflineAvailable());
    }

}
