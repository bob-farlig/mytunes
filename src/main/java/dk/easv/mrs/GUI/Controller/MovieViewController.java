package dk.easv.mrs.GUI.Controller;

// java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

// project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;



public class MovieViewController implements Initializable {

    @FXML
    private Button btnCreate, btnUpdate, btnDelete;

    @FXML
    private ListView<Movie> lstMovies;

    private MovieModel movieModel;

    @FXML
    private TextField txtMovieTitle, txtMovieYear, txtMovieSearch;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

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

        Movie newMovie = new Movie(-1, year, title);

        try {
            movieModel.createMovie(newMovie);
        }
        catch (Exception err) {
            displayError(err);
        }


    }

    public void handleUpdateMovie(ActionEvent actionEvent) {

        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {

            // update selectedMovie
            selectedMovie.setTitle(txtMovieTitle.getText());
            selectedMovie.setYear(Integer.parseInt(txtMovieYear.getText()));

            try {
                movieModel.updateMovie(selectedMovie);
                lstMovies.refresh();
            }
            catch (Exception err) {
                displayError(err);
            }

        }
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {

        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {

            try {
                movieModel.deleteMovie(selectedMovie);
                lstMovies.refresh();
            } catch (Exception err) {
                displayError(err);
            }

        }

    }

}
