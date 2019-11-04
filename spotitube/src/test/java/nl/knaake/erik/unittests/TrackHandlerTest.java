package nl.knaake.erik.unittests;

import nl.knaake.erik.BasicMockitoTest;
import nl.knaake.erik.domain.track.ITracksDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import nl.knaake.erik.domain.track.TrackHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackHandlerTest extends BasicMockitoTest {

    @InjectMocks
    TrackHandler trackHandler;

    @Mock
    ITracksDAO tracksDAOMock = mock(ITracksDAO.class);

    @Test
    void getTracksNotInPlaylist() {
        int playlistId = 1;
        TrackCollectionDTO expected = new TrackCollectionDTO();
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "test", "performer", 123, "album", 5, "10-10-2018", "description", false));
        expected.setTracks(tracks);

        when(tracksDAOMock.getTracksNotInPlaylist(playlistId)).thenReturn(expected);

        TrackCollectionDTO actual = trackHandler.getTracksNotInPlaylist(playlistId);

        verify(tracksDAOMock, times(1)).getTracksNotInPlaylist(playlistId);
        assertEquals(expected, actual);
    }
}
