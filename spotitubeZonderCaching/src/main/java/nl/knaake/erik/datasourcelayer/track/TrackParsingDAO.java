package nl.knaake.erik.datasourcelayer.track;

import nl.knaake.erik.datasourcelayer.BasicDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Offers functionallity to parse a result set to a <code>TrackCollectionDTO</code>
 */
public abstract class TrackParsingDAO extends BasicDAO {

    /**
     * Parses a result set with fields: {"t.id", "t.title", "pe.name", "duration", "albumnaam", "playCount", "publicationDate", "description", "offlineAvailable"}
     * to a <code>TrackCollectionDTO</code>
     * @param tracks Track collection to add tracks to
     * @param set Set to parse to a <code>TrackCollectionDTO</code>
     * @param autoFalseOfflineAvailable If this is set to true no field "offlineAvailable" is required and that field will be automaticly set to false
     * @return Collection of tracks parsed from the given result set
     */
    protected TrackCollectionDTO getTracksDTOFromResultSet(TrackCollectionDTO tracks, ResultSet set, boolean autoFalseOfflineAvailable) {
        try {
            while (set.next()) {
                tracks.addTrack(new TrackDTO(
                        set.getInt("t.id"),
                        set.getString("t.title"),
                        set.getString("pe.name"),
                        set.getInt("duration"),
                        set.getString("albumnaam"),
                        set.getInt("playCount"),
                        DateConverter.convertDateToString(set.getDate("publicationDate")),
                        set.getString("description"),
                        (!autoFalseOfflineAvailable && set.getBoolean("offlineAvailable"))
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
