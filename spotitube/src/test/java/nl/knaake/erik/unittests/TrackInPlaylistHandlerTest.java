package nl.knaake.erik.unittests;

import nl.knaake.erik.BasicMockitoTest;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.domain.trackinplaylist.ITrackInPlaylistDAO;
import nl.knaake.erik.domain.trackinplaylist.TrackInPlaylistHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackInPlaylistHandlerTest extends BasicMockitoTest {

    @InjectMocks
    TrackInPlaylistHandler trackInPlaylistHandler;

    @Mock
    ITrackInPlaylistDAO trackInPlaylistDAOMock = mock(ITrackInPlaylistDAO.class);

    @Test
    void getAllTracksFromPlaylist() {
        int playlistId = 1;
        TrackCollectionDTO expected = new TrackCollectionDTO();
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "test", "performer", 123, "album", 5, "10-10-2018", "description", false));
        expected.setTracks(tracks);

        when(trackInPlaylistDAOMock.getAllTracksFromPlaylist(playlistId)).thenReturn(expected);

        TrackCollectionDTO actual = trackInPlaylistHandler.getAllTracksFromPlaylist(playlistId);

        verify(trackInPlaylistDAOMock, times(1)).getAllTracksFromPlaylist(playlistId);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTrackFromPlaylist() {
        int playlistId = 1;
        int trackId = 2;

        trackInPlaylistHandler.deleteTrackFromPlaylist(playlistId, trackId);

        verify(trackInPlaylistDAOMock, times(1)).deleteTrackFromPlaylist(playlistId, trackId);
    }

    @Test
    void addTrackToPlaylist() {
        int playlistId = 1;
        int trackId = 2;
        TrackDTO trackToAdd = new TrackDTO(trackId, "test", "performer", 123, "album", 5, "10-10-2018", "description", false);

        trackInPlaylistHandler.addTrackToPlaylist(playlistId, trackToAdd);

        verify(trackInPlaylistDAOMock, times(1)).addTrackToPlaylist(playlistId, trackId, false);
    }
}
