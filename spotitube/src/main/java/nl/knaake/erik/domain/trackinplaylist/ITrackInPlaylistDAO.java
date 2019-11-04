package nl.knaake.erik.domain.trackinplaylist;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;

/**
 * Allows communication with the persitance layer for tracks in a playlist
 */
public interface ITrackInPlaylistDAO {
    TrackCollectionDTO getAllTracksFromPlaylist(int playlistId);
    void deleteTrackFromPlaylist(int playlistId, int trackId);
    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);
}
