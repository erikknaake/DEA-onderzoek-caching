package nl.knaake.erik.crosscuttingconcerns.dtos;

import java.util.ArrayList;

/**
 * Representation of a list of playlists with the total playlength
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class PlaylistCollectionDTO {
    private ArrayList<PlaylistDTO> playlists;
    private int length;

    public PlaylistCollectionDTO() {
        playlists = new ArrayList<>();
    }

    public PlaylistCollectionDTO(ArrayList<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addPlaylist(PlaylistDTO playlist) {
        playlists.add(playlist);
    }
}
