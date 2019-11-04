package nl.knaake.erik.crosscuttingconcerns.dtos;

import java.io.Serializable;

/**
 * Representation of a track
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class TrackDTO implements Serializable {
    private int id;
    private String title;
    private String performer;
    private int duration;
    private String album;
    private int playcount;
    private String publicationDate;
    private String description;
    private boolean offlineAvailable;

    public TrackDTO() {
    }

    public TrackDTO(int id, String title, String performer, int duration, String album, int playcount, String publicationDate, String description, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this.playcount = playcount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackDTO trackDTO = (TrackDTO) o;

        if (getId() != trackDTO.getId()) return false;
        if (getDuration() != trackDTO.getDuration()) return false;
        if (getPlaycount() != trackDTO.getPlaycount()) return false;
        if (isOfflineAvailable() != trackDTO.isOfflineAvailable()) return false;
        if (!getTitle().equals(trackDTO.getTitle())) return false;
        if (!getPerformer().equals(trackDTO.getPerformer())) return false;
        if (!getAlbum().equals(trackDTO.getAlbum())) return false;
        if (!getPublicationDate().equals(trackDTO.getPublicationDate())) return false;
        return getDescription().equals(trackDTO.getDescription());
    }

}
