package dk.easv.mrs.BLL;

// java projects
import java.io.IOException;
import java.util.List;

// project imports
import dk.easv.mrs.BE.Music;
import dk.easv.mrs.BLL.util.MusicSearcher;
import dk.easv.mrs.DAL.IMusicDataAccess;
import dk.easv.mrs.DAL.db.MusicDAO_DB;


public class MusicManager {

    private MusicSearcher musicSearcher = new MusicSearcher();
    private IMusicDataAccess movieDAO;

    public MusicManager() throws IOException {

        //movieDAO = new MovieDAO_File();
        movieDAO = new MusicDAO_DB();
        //movieDAO = new MovieDAO_Mock();
    }

    public List<Music> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Music> searchMovies(String query) throws Exception {
        List<Music> allMusics = getAllMovies();
        List<Music> searchResult = musicSearcher.search(allMusics, query);
        return searchResult;
    }

    public Music createMovie(Music newMusic) throws Exception {
        return movieDAO.createMovie(newMusic);
    }

    public void updateMovie(Music updatedMusic) throws Exception {
        movieDAO.updateMovie(updatedMusic);
    }

    public void deleteMovie(Music musicToBeDeleted) throws Exception {
        movieDAO.deleteMovie(musicToBeDeleted);
    }
}
