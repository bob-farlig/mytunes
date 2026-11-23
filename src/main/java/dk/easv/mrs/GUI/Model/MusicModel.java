package dk.easv.mrs.GUI.Model;

// java imports
import dk.easv.mrs.BE.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

// project imports
import dk.easv.mrs.BLL.MusicManager;
import javafx.collections.transformation.FilteredList;


public class MusicModel {

    private FilteredList<Music> filteredList;
    private ObservableList<Music> moviesToBeViewed;
    private MusicManager musicManager;

    public MusicModel() throws Exception {
        musicManager = new MusicManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(musicManager.getAllMovies());
        filteredList = new FilteredList<>(moviesToBeViewed);

    }



    public FilteredList<Music> getObservableMovies() {
        return filteredList;
    }

    public void searchMovie(String query) throws Exception {
        List<Music> searchResults = musicManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public void createMovie(Music newMusic) throws Exception {
        Music createdmovie = musicManager.createMovie(newMusic);
        moviesToBeViewed.add(createdmovie);
    }

    public void updateMovie(Music moviesToBeUpdated) throws Exception {
        musicManager.updateMovie(moviesToBeUpdated);

        int index = moviesToBeViewed.indexOf(moviesToBeUpdated);
        if (index != -1) {
            moviesToBeViewed.set(index, moviesToBeUpdated);
        }

    }

    public void deleteMovie(Music musicToBeDeleted) throws Exception {

        // delete movie in file/database
        musicManager.deleteMovie(musicToBeDeleted);
        // delete movie in list
        moviesToBeViewed.remove(musicToBeDeleted);
    }
}
