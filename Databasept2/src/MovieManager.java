import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class MovieManager {
    private Connection conn;

    // Constructor to initialize connection to the database
    public MovieManager() {
        try {
            conn = DBConnection.connect(); // Connect to the database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get movie list from the database
    public ArrayList<Movie> getMovieList() {
        ArrayList<Movie> movieList = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("movieID"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("duration"),
                        rs.getDouble("price")
                );
                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    // Add movie to the database
    public void addMovie(Movie movie) {
        String query = "INSERT INTO movies (title, genre, duration, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setString(3, movie.getDuration());
            stmt.setDouble(4, movie.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit movie in the database
    public void editMovie(Movie oldMovie, Movie newMovie) {
        String query = "UPDATE movies SET title = ?, genre = ?, duration = ?, price = ? WHERE movieID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newMovie.getTitle());
            stmt.setString(2, newMovie.getGenre());
            stmt.setString(3, newMovie.getDuration());
            stmt.setDouble(4, newMovie.getPrice());
            stmt.setInt(5, oldMovie.getMovieID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete movie from the database
    public void deleteMovie(Movie movie) {
        String query = "DELETE FROM movies WHERE movieID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movie.getMovieID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Convert movie list to DefaultListModel for JList display
    public DefaultListModel<String> getMovieListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Movie movie : getMovieList()) {
            model.addElement(movie.toString());
        }
        return model;
    }
}
