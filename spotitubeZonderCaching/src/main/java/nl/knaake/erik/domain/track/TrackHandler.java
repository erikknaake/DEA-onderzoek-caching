package nl.knaake.erik.domain.track;

import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.restservicelayer.tracks.ITrackHandler;

import javax.inject.Inject;

/**
 * Offers logic to query tracks
 */
public class TrackHandler implements ITrackHandler {

    @Inject
    ITracksDAO tracksDAO;

    /**
     * Gets all tracks that are not in the given playlist
     * @param playlistId Id of the playlist to find other tracks for
     * @return Collection of tracks that are not yet in the given playlist
     */
    @Override
    public TrackCollectionDTO getTracksNotInPlaylist(int playlistId) {
        return tracksDAO.getTracksNotInPlaylist(playlistId);
    }

}
