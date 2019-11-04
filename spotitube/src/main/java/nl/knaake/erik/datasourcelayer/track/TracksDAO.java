package nl.knaake.erik.datasourcelayer.track;

import nl.knaake.erik.domain.track.ITracksDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Offers access to the database for tracks
 */
public class TracksDAO extends TrackParsingDAO implements ITracksDAO {

    /**
     * Gets all tracks that are not currently in the given playlist
     * @param playlistId Id of the playlist to get tracks for
     * @return All tracks that are not currently in the given playlist
     */
    @Override
    public TrackCollectionDTO getTracksNotInPlaylist(int playlistId) {
        PreparedStatement getTracks = getDb().makePreparedStatement("SELECT * FROM Track t INNER JOIN Performer pe ON t.performer = pe.id LEFT JOIN (SELECT a.albumNaam as albumnaam, s.id as songid FROM Album a INNER JOIN Song s ON a.albumId = s.albumId) as song ON t.id = song.songid LEFT JOIN Video v ON t.id = v.id WHERE t.id NOT IN (SELECT trackId FROM TrackInPlaylist WHERE playlistId = ?)");
        TrackCollectionDTO tracks = new TrackCollectionDTO();
        try {
            getTracks.setInt(1, playlistId);
            ResultSet set = getTracks.executeQuery();
            getTracksDTOFromResultSet(tracks, set, true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
        return tracks;
    }
}
