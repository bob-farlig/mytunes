package dk.easv.mrs.DAL;

//java imports
import java.util.List;

//project import
import dk.easv.mrs.BE.Movie;


public interface IMovieDataAccess {

    List<Movie> getAllMovies() throws Exception;

    Movie createMovie(Movie newMovie) throws Exception;

    void updateMovie(Movie movie) throws Exception;

    void deleteMovie(Movie movie) throws Exception;

}
