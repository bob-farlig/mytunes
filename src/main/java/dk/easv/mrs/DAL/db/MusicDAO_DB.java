package dk.easv.mrs.DAL.db;

import dk.easv.mrs.BE.Music;
import dk.easv.mrs.DAL.IMusicDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO_DB implements IMusicDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public MusicDAO_DB() throws IOException {
    }

    @Override
    public List<Music> getAllMovies() throws Exception {

        List<Music> music = new ArrayList<>();

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
                music.add(new Music(id, year, title));

            }

        }

        // lukker db connect (lukker connection automatisk fordi den er inde i try with resources)

        return music;
    }

    @Override
    public Music createMovie(Music newMusic) throws Exception {

            String sql = "INSERT INTO dbo.Movie (Title,Year) VALUES (?,?);";

            try (Connection conn = dbConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // Bind parameters
                stmt.setString(1, newMusic.getTitle());
                stmt.setInt(2, newMusic.getYear());

                // Run the specified SQL statement
                stmt.executeUpdate();

                // Get the generated ID from the DB
                ResultSet rs = stmt.getGeneratedKeys();
                int id = 0;

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                // Create movie object and send up the layers
                Music createdMusic = new Music(id, newMusic.getYear(), newMusic.getTitle());

                return createdMusic;
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                throw new Exception("Could not create movie", ex);
            }

    }

    @Override
    public void updateMovie(Music music) throws Exception {

        String sql = "UPDATE Movie SET Title = ?, Year = ? WHERE Id = ?";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setString(1, music.getTitle());
            stmt.setInt(2, music.getYear());
            stmt.setInt(3, music.getId());

            // Run the specified SQL statement
            stmt.executeUpdate();

        }
    }

    @Override
    public void deleteMovie(Music music) throws Exception {

        String sql = "DELETE FROM Movie WHERE Id = ?";

        // try-with-resources
        try (Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // bind parameters til ?
            stmt.setInt(1, music.getId());

            // run the sql command
            stmt.executeUpdate();
        }

    }
}
