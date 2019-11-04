package nl.knaake.erik.domain.playlist;

import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;
import nl.knaake.erik.restservicelayer.playlists.IPlaylistsHandler;

import javax.inject.Inject;

/**
 * Offers logic for editing and querying playlists
 */
public class PlaylistsHandler implements IPlaylistsHandler {

    @Inject
    private IPlaylistsDAO playlistsDAO;

    /**
     * Gets all playlists associated with the user that currently holds the given token
     * @param token Authentication token of the user
     * @return All playlists that the user is allowed to see
     */
    @Override
    public PlaylistCollectionDTO getPlaylistsDTOByToken(String token) {
        return playlistsDAO.getPlaylistsByToken(token);
    }

    /**
     * Edits the name of a given playlist to a new given name
     * @param token Authentication token of the user
     * @param playlistId Id of the playlist to change the name for
     * @param newName New name for the playlist
     */
    @Override
    public void editPlaylistName(String token, int playlistId, String newName) {
        playlistsDAO.editPlaylistName(playlistId, token, newName);
    }

    /**
     * Deletes a given playlist
     * @param token Authentication token of the user
     * @param playlistId Id of the playlist to delete
     */
    @Override
    public void deletePlaylist(String token, int playlistId) {
        playlistsDAO.deletePlaylist(token, playlistId);
    }

    /**
     * Adds a playlist to the users account
     * @param token Authentication token of the user
     * @param playlistDTO Playlist to add to the user
     */
    @Override
    public void addPlaylist(String token, PlaylistDTO playlistDTO) {
        playlistsDAO.addPlaylist(token, playlistDTO);
    }

}
