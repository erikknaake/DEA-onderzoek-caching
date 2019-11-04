package nl.knaake.erik.restservicelayer.trackinplaylist;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;

/**
 * Offers functionality to edit a playlists tracks
 */
public interface ITrackInPlaylistHandler {
    TrackCollectionDTO getAllTracksFromPlaylist(int playlistId);
    void deleteTrackFromPlaylist(int playlistId, int trackId);
    void addTrackToPlaylist(int playlistId, TrackDTO trackDTO);
}
