package dk.easv.mrs.GUI.Controller;

// java imports
import dk.easv.mrs.BE.Music;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

// project imports
import dk.easv.mrs.GUI.Model.MusicModel;
import javafx.scene.control.cell.PropertyValueFactory;


public class MusicViewController implements Initializable {

    @FXML
    private TableView<Music> tblMovies;

    @FXML
    private TableColumn<Music, String> colTitle;

    @FXML
    private TableColumn<Music, Integer> colYear;

    @FXML
    private Button btnCreate, btnUpdate, btnDelete;

    @FXML
    private ListView<Music> lstMovies;

    private MusicModel musicModel;

    @FXML
    private TextField txtMusicTitle, txtMovieYear, txtMovieSearch;

    public MusicViewController()  {

        try {
            musicModel = new MusicModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // setup columns in tableview
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        // connect tableview to the ObservableList (FilteredList)
        tblMovies.setItems(musicModel.getObservableMovies());

        lstMovies.setItems(musicModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {

                musicModel.getObservableMovies().setPredicate(movie -> {

                    // If filter text is empty, display all movies.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return Integer.toString(movie.getYear()).contains(lowerCaseFilter);
                });
                //movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        SortedList<Music> sortedData = new SortedList<>(musicModel.getObservableMovies());
        sortedData.comparatorProperty().bind(tblMovies.comparatorProperty());
        tblMovies.setItems(sortedData);

        tblMovies.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) ->
                {
                    // lambda expression
                    txtMovieTitle.setText(newValue.getTitle());
                    txtMovieYear.setText(newValue.getYear() + "");
                    //System.out.println(newValue);
                }
        ));

        lstMovies.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) ->
                {
                    // lambda expression
                    txtMovieTitle.setText(newValue.getTitle());
                    txtMovieYear.setText(newValue.getYear() + "");
                    //System.out.println(newValue);
                }
                ));

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void handleCreateNewMovie(ActionEvent actionEvent)
    {
        // get data from front-end

        String title = txtMovieTitle.getText();
        int year = Integer.parseInt(txtMovieYear.getText());

        Music newMusic = new Music(-1, year, title);

        try {
            musicModel.createMovie(newMusic);
        }
        catch (Exception err) {
            displayError(err);
        }


    }

    public void handleUpdateMovie(ActionEvent actionEvent) {

        Music selectedMusic = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMusic != null) {

            // update selectedMovie
            selectedMusic.setTitle(txtMovieTitle.getText());
            selectedMusic.setYear(Integer.parseInt(txtMovieYear.getText()));

            // comm with model
            try {
                musicModel.updateMovie(selectedMusic);
                lstMovies.refresh();
            }
            catch (Exception err) {
                displayError(err);
            }

        }
    }

    /**
     * Delete the movie
     * @param actionEvent
     */
    public void handleDeleteMovie(ActionEvent actionEvent) {

        // Find the selected movie
        Music selectedMusic = lstMovies.getSelectionModel().getSelectedItem();

        // check whether selected movie is null
        if (selectedMusic != null) {

            // communicate with model
            try {
                musicModel.deleteMovie(selectedMusic);
                //lstMovies.refresh();
            } catch (Exception err) {
                displayError(err);
            }

        }

    }


    //add new song to list
    public void handleNewSong(ActionEvent actionEvent) {
    }

    //delete a song from list
    public void handleDeleteSong(ActionEvent actionEvent) {
    }

    //add a new playlist
    public void handleAddPlaylist(ActionEvent actionEvent) {
    }

    //delete a playlist
    public void handleDeletePlaylist(ActionEvent actionEvent) {
    }

    //edits a playlist
    public void handleEditPlaylist(ActionEvent actionEvent) {
    }

    //add a song to a playlist
    public void handleAddSong(ActionEvent actionEvent) {
    }

    //remove a song from a playlist
    public void handleRemoveSong(ActionEvent actionEvent) {
    }

    //play or pause music
    public void handlePlayPause(ActionEvent actionEvent) {
    }

    //skips to next song
    public void handleNext(ActionEvent actionEvent) {
    }

    //goes back to last song
    public void handlePrevious(ActionEvent actionEvent) {
    }

}
