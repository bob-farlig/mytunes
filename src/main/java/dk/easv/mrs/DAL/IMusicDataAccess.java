package dk.easv.mrs.DAL;

//java imports
import java.util.List;

//project import
import dk.easv.mrs.BE.Music;


public interface IMusicDataAccess {

    List<Music> getAllMovies() throws Exception;

    Music createMovie(Music newMusic) throws Exception;

    void updateMovie(Music music) throws Exception;

    void deleteMovie(Music music) throws Exception;

}
