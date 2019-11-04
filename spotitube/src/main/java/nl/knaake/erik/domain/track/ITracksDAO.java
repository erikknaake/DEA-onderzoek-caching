package nl.knaake.erik.domain.track;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;

/**
 * Allows communication with the persistance system to query tracks
 */
public interface ITracksDAO {
    TrackCollectionDTO getTracksNotInPlaylist(int playlistId);
}
