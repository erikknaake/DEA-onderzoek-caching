package nl.knaake.erik.domain.playlist;

import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;

/**
 * Allows communication to the persistence for playlists
 */
public interface IPlaylistsDAO {
    PlaylistCollectionDTO getPlaylistsByToken(String token);
    void editPlaylistName(int playlistId, String token, String newPlaylistName);
    void deletePlaylist(String token, int playlistId);
    void addPlaylist(String token, PlaylistDTO newPlaylist);
}
