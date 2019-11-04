package nl.knaake.erik.datasourcelayer.track;

import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;
import nl.knaake.erik.datasourcelayer.CacheFactory;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.domain.trackinplaylist.ITrackInPlaylistDAO;
import org.ehcache.Cache;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Offers access to the database and cache for tracks inside a playlist
 */
public class TrackInPlaylistDAO extends TrackParsingDAO implements ITrackInPlaylistDAO {

   // private CacheIdentityMap cache;
    private Cache<Integer, TrackCollectionDTO> cache;
    /**
     * Initializes the trackInPlaylistCache
     */
    public TrackInPlaylistDAO() {
        //cache = CacheIdentityMap.getInstance(Integer.class, TrackCollectionDTO.class);
        cache = CacheFactory.getInstance().createCache(CacheFactory.CacheType.TrackInPlaylist);
    }

    /**
     * Gets all tracks that are inside a given playlist
     * @param playlistId Id of the playlist to get the tracks for
     * @return Collection of tracks that are in the given playlist
     */
    @Override
    public TrackCollectionDTO getAllTracksFromPlaylist(int playlistId) {
        TrackCollectionDTO tracks = (TrackCollectionDTO) cache.get(playlistId);
        if(tracks != null) {
            return tracks;
        }

        PreparedStatement getTracks = getDb().makePreparedStatement("SELECT * FROM Track t INNER JOIN Performer pe ON t.performer = pe.id LEFT JOIN Video v ON t.id = v.id INNER JOIN TrackInPlaylist tp ON t.id = tp.trackId INNER JOIN Playlist p ON tp.playlistId = p.id LEFT JOIN Song s ON s.id = t.id LEFT JOIN Album a ON s.albumId = a.albumId WHERE p.id = ?");
        tracks = new TrackCollectionDTO();
        try {
            getTracks.setInt(1, playlistId);
            ResultSet set = getTracks.executeQuery();
            //tracks = getTracksDTOFromResultSet(set, false);
            getTracksDTOFromResultSet(tracks, set, false);
            cache.put(playlistId, tracks);
            set.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
        return tracks;
    }

    /**
     * Deletes a given track for a given playlist from the database and cache
     * @param playlistId Id of the playlist to delete the track from
     * @param trackId If of the track to delete from the playlist
     */
    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        PreparedStatement deleteTrack = getDb().makePreparedStatement("DELETE FROM TrackInPlaylist WHERE trackId = ? AND playlistID = ?");
        try {
            deleteTrack.setInt(1, trackId);
            deleteTrack.setInt(2, playlistId);
            deleteTrack.executeUpdate();
            deleteTrack.close();

            removeTrackFromCache(playlistId, trackId);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Delete a given playlist from the cache
     * @param playlistId Id of the playlist to delete the track from
     * @param trackId If of the track to delete from the playlist
     */
    private void removeTrackFromCache(int playlistId, int trackId) {
        TrackCollectionDTO trackCollectionDTO = (TrackCollectionDTO) cache.get(playlistId);
        if(trackCollectionDTO != null) {
            trackCollectionDTO.removeTrack(trackId);
            cache.remove(playlistId);
            cache.put(playlistId, trackCollectionDTO);
        }
    }

    /**
     * Adds a track to a given playlist
     * @param playlistId Id of the playlist to add the track to
     * @param trackId Id of the track to add to the playlist
     * @param offlineAvailable Whether or not the track should be marked as offlineAvailable withing the playlist
     */
    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        PreparedStatement addTrack = getDb().makePreparedStatement("INSERT INTO TrackInPlaylist (playlistId, trackId, offlineAvailable) VALUES (? ,?, ?)");
        try {
            addTrack.setInt(1, playlistId);
            addTrack.setInt(2, trackId);
            addTrack.setBoolean(3, offlineAvailable);
            addTrack.execute();
            addTrack.close();

            TrackCollectionDTO trackCollectionDTO = (TrackCollectionDTO) cache.get(playlistId);
            if(trackCollectionDTO != null) {
                TrackDTO addedTrack = getTrackById(trackId);
                addedTrack.setOfflineAvailable(offlineAvailable);
                trackCollectionDTO.addTrack(addedTrack);
                cache.remove(playlistId);
                cache.put(playlistId, trackCollectionDTO);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Gets a track by its track id, note offlineAvailable isnt set yet
     * @param id Id of track to get
     * @return Track that corresponds to the given id
     */
    private TrackDTO getTrackById(int id) {
        TrackDTO result = new TrackDTO();
        PreparedStatement getTrack = getDb().makePreparedStatement("SELECT * FROM Track t INNER JOIN Performer p ON t.performer = p.id LEFT JOIN Video v ON t.id = v.id LEFT JOIN Song s ON s.id = t.id LEFT JOIN Album a ON a.albumId = s.albumId WHERE t.id = ?");
        try {
            getTrack.setInt(1, id);
            ResultSet set = getTrack.executeQuery();
            if(set.next()) {
                result.setAlbum(set.getString("albumNaam"));
                result.setDescription(set.getString("description"));
                result.setId(set.getInt("t.id"));
                result.setDuration(set.getInt("duration"));
                result.setPerformer(set.getString("p.name"));
                result.setPublicationDate(DateConverter.convertDateToString(set.getDate("publicationDate")));
                result.setTitle(set.getString("title"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
        return result;
    }
}
