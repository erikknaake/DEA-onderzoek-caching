package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.domain.playlist.IPlaylistsDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Offers database access to the playlist collection
 */
public class PlaylistsDAO extends BasicDAO implements IPlaylistsDAO {

    /**
     * Gets all playlist a given user can view according to his authentication token
     * @param token Authentication token of the user
     * @return A collection of playlists the user can view with all owner variables set accordingly
     */
    @Override
    public PlaylistCollectionDTO getPlaylistsByToken(String token) {
        PreparedStatement stmt = getDb().makePreparedStatement("SELECT p.id as playlistid, p.name as playlistname, u.sessionToken = ? as isOwner, SUM(t.duration) as totalDuration FROM User u INNER JOIN Playlist p ON u.user = p.owner LEFT JOIN TrackInPlaylist tp ON p.id = tp.playlistId LEFT JOIN Track t ON tp.trackId = t.id GROUP BY p.id");
        PlaylistCollectionDTO playlistCollectionDTO = new PlaylistCollectionDTO();
        try {
            stmt.setString(1, token);
            ResultSet set = stmt.executeQuery();
            playlistCollectionDTO = constructPlaylistsDTO(set);
            set.close();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
        return playlistCollectionDTO;
    }

    /**
     * Edits the name of a given playlist if the given user owns that playlist
     * @param playlistId Id of the playlist to edit the name for
     * @param token Authentication token of the user
     * @param newPlaylistName New name for the given playlist
     */
    @Override
    public void editPlaylistName(int playlistId, String token, String newPlaylistName) {
        PreparedStatement updateName = getDb().makePreparedStatement("UPDATE Playlist SET name = ? WHERE owner = (SELECT user FROM User WHERE sessionToken = ?) AND id = ?");
        try {
            updateName.setString(1, newPlaylistName);
            updateName.setString(2, token);
            updateName.setInt(3, playlistId);
            updateName.executeUpdate();
            updateName.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Delete a given playlist if the given user owns that playlist
     * @param token Authentication token of the user
     * @param playlistId Id of the playlist to delete
     */
    @Override
    public void deletePlaylist(String token, int playlistId) {
        PreparedStatement deleteTrackInPlaylist = getDb().makePreparedStatement("DELETE FROM TrackInPlaylist WHERE playlistId = ?");
        PreparedStatement deletePlaylist = getDb().makePreparedStatement("DELETE FROM Playlist WHERE owner = (SELECT user FROM User WHERE sessionToken = ?) AND id = ?;");
        try {
            deleteTrackInPlaylist.setInt(1, playlistId);
            deletePlaylist.setString(1, token);
            deletePlaylist.setInt(2, playlistId);
            deleteTrackInPlaylist.executeUpdate();
            deletePlaylist.executeUpdate();
            deleteTrackInPlaylist.close();
            deletePlaylist.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Adds the given playlist to the users account
     * @param token Authentication token of the user
     * @param newPlaylist Playlist to add to the users account
     */
    @Override
    public void addPlaylist(String token, PlaylistDTO newPlaylist) {
        PreparedStatement addPlaylist = getDb().makePreparedStatement("INSERT INTO Playlist (name, owner) VALUES (?, (SELECT user FROM User WHERE sessionToken = ?))");
        try {
            addPlaylist.setString(1, newPlaylist.getName());
            addPlaylist.setString(2, token);
            addPlaylist.executeUpdate();
            addPlaylist.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Parses a result set which contains information for a <code>PlaylistCollectionDTO</code> to a <code>PlaylistCollectionDTO</code>
     * @param set Set to parse to a <code>PlaylistCollectionDTO</code>
     * @return Collection of playlists that was parsed from the result set
     * @throws SQLException Thows this when the given set is invalid
     */
    private PlaylistCollectionDTO constructPlaylistsDTO(ResultSet set) throws SQLException {
        PlaylistCollectionDTO playlistCollectionDTO = new PlaylistCollectionDTO();
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        int totalDuration = 0;
        while(set.next()) {
            playlists.add(new PlaylistDTO(set.getInt("playlistid"), set.getString("playlistname"), set.getBoolean("isOwner"), new ArrayList<TrackDTO>()));
            totalDuration += set.getInt("totalDuration");
        }
        playlistCollectionDTO.setLength(totalDuration);
        playlistCollectionDTO.setPlaylists(playlists);
        getDb().closeConnection();
        return playlistCollectionDTO;
    }
}
