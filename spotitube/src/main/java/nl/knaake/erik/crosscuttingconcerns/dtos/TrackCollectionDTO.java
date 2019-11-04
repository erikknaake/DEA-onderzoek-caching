package nl.knaake.erik.crosscuttingconcerns.dtos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representation of a collection of tracks
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class TrackCollectionDTO implements Serializable {
    private ArrayList<TrackDTO> tracks;

    public TrackCollectionDTO() {
        tracks = new ArrayList<>();
    }

    public TrackCollectionDTO(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(TrackDTO track) {
        tracks.add(track);
    }

    public void removeTrack(int trackId) {
        for(TrackDTO t : tracks) {
            if(t.getId() == trackId) {
                tracks.remove(t);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "TrackCollectionDTO{" +
                "tracks=" + tracks +
                '}';
    }
}
