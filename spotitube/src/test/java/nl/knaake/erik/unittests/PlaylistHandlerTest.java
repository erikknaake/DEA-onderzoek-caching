package nl.knaake.erik.unittests;

import nl.knaake.erik.BasicMockitoTest;
import nl.knaake.erik.domain.playlist.IPlaylistsDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistCollectionDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.PlaylistDTO;
import nl.knaake.erik.domain.playlist.PlaylistsHandler;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistHandlerTest extends BasicMockitoTest {

    @InjectMocks
    PlaylistsHandler playlistsHandler;

    @Mock
    IPlaylistsDAO playlistsDAOMock = mock(IPlaylistsDAO.class);

    @Test
    void getPlaylistByToken() {
        String token = "1234-1234-1234";
        PlaylistCollectionDTO expected = new PlaylistCollectionDTO();
        expected.setLength(12);
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "test", "performer", 123, "album", 5, "10-10-2018", "description", false));
        expected.addPlaylist(new PlaylistDTO(2, "Erik", true, tracks));

        when(playlistsDAOMock.getPlaylistsByToken(token)).thenReturn(expected);

        PlaylistCollectionDTO actual = playlistsHandler.getPlaylistsDTOByToken(token);

        verify(playlistsDAOMock, times(1)).getPlaylistsByToken(token);
        assertEquals(expected, actual);
    }

    @Test
    void editPlaylistName() {
        String token = "1234-1234-1234";
        int playlistId = Integer.MAX_VALUE + 1;
        String newName = "New test name";

        playlistsHandler.editPlaylistName(token, playlistId, newName);

        verify(playlistsDAOMock, times(1)).editPlaylistName(playlistId, token, newName);
    }

    @Test
    void deletePlaylist() {
        String token = "2345-2345-2345";
        int playlistId = Integer.MAX_VALUE;

        playlistsHandler.deletePlaylist(token, playlistId);

        verify(playlistsDAOMock, times(1)).deletePlaylist(token, playlistId);
    }

    @Test
    void addPlaylist() {
        String token = "1234567890987644321";
        PlaylistDTO newPlaylist = new PlaylistDTO();
        newPlaylist.setId(1);
        newPlaylist.setName("test playlist");
        newPlaylist.setOwner(true);
        newPlaylist.addTrack(new TrackDTO());

        playlistsHandler.addPlaylist(token, newPlaylist);

        verify(playlistsDAOMock, times(1)).addPlaylist(token, newPlaylist);
    }
}
