package dk.easv.mrs.DAL.db;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public MovieDAO_DB() throws IOException {
    }

    @Override
    public List<Movie> getAllMovies() throws Exception {

        List<Movie> movies = new ArrayList<>();

        // get data from db
        // try-with-resources
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            // sårbar overfor sql injection

            String sql = "SELECT * FROM Movie";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                // transfer rows to arraylist
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("Year");


                //Movie newMovie = new Movie(id, year, title); Anden måde at gøre det på
                //movies.add(newMovie);                        men mindre effektiv
                movies.add(new Movie(id, year, title));

            }

        }

        // lukker db connect (lukker connection automatisk fordi den er inde i try with resources)

        return movies;
    }

    @Override
    public Movie createMovie(Movie newMovie) throws Exception {

            String sql = "INSERT INTO dbo.Movie (Title,Year) VALUES (?,?);";

            try (Connection conn = dbConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // Bind parameters
                stmt.setString(1,newMovie.getTitle());
                stmt.setInt(2, newMovie.getYear());

                // Run the specified SQL statement
                stmt.executeUpdate();

                // Get the generated ID from the DB
                ResultSet rs = stmt.getGeneratedKeys();
                int id = 0;

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                // Create movie object and send up the layers
                Movie createdMovie = new Movie(id, newMovie.getYear(), newMovie.getTitle());

                return createdMovie;
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                throw new Exception("Could not create movie", ex);
            }

    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }
}
