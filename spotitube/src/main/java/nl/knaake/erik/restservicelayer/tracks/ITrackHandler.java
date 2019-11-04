package nl.knaake.erik.restservicelayer.tracks;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;

/**
 * Interface to loosly couple TrackHandler
 */
public interface ITrackHandler {
    TrackCollectionDTO getTracksNotInPlaylist(int playlistId);
}
