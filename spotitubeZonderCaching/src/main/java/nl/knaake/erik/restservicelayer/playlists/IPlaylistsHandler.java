package nl.knaake.erik.restservicelayer.playlists;

import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;

/**
 * Offers functionality to edit playlists and to query playlists
 */
public interface IPlaylistsHandler {
    PlaylistCollectionDTO getPlaylistsDTOByToken(String token);
    void editPlaylistName(String token, int playlistId, String newName);
    void deletePlaylist(String token, int playlistId);
    void addPlaylist(String token, PlaylistDTO playlistDTO);
}
