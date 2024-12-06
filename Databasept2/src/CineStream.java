import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class CineStream {
    private JFrame frame;
    private JTextField txtMovieID, txtTitle, txtGenre, txtDuration, txtPrice;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    private JList<String> movieListDisplay;
    private DefaultListModel<String> movieListModel;
    private MovieManager movieManager;
    private Movie selectedMovie;
    private JTextField txtUsername, txtPassword;
    private JFrame loginFrame;
    private LoginManager loginManager;

    public CineStream() {
        movieManager = new MovieManager(); // Connect to the database
        loginManager = new LoginManager();
        createLoginFrame();
    }

    // Main method (entry point)
    public static void main(String[] args) {
        // Create an instance of CineStream to launch the application
        SwingUtilities.invokeLater(() -> new CineStream());
    }

    // Create the login window
    private void createLoginFrame() {
        loginFrame = new JFrame("Login");
        loginFrame.setLayout(new GridLayout(3, 2));
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(null);

        JLabel lblUsername = new JLabel("Username: ");
        JLabel lblPassword = new JLabel("Password: ");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin());

        loginFrame.add(lblUsername);
        loginFrame.add(txtUsername);
        loginFrame.add(lblPassword);
        loginFrame.add(txtPassword);
        loginFrame.add(new JLabel());
        loginFrame.add(btnLogin);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    // Handle login attempt
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (loginManager.isValidLogin(username, password)) {
            loginFrame.setVisible(false);
            createMainFrame();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid Username or Password!");
        }
    }

    // Create the main management window
    private void createMainFrame() {
        frame = new JFrame("CineStream - Movie Management");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Add the components (buttons, text fields, etc.)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Movie ID:"));
        txtMovieID = new JTextField();
        panel.add(txtMovieID);

        panel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        panel.add(txtTitle);

        panel.add(new JLabel("Genre:"));
        txtGenre = new JTextField();
        panel.add(txtGenre);

        panel.add(new JLabel("Duration (HH-MM-SS):"));
        txtDuration = new JTextField();
        panel.add(txtDuration);

        panel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        panel.add(txtPrice);

        // Buttons for actions
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnClear);

        // Movie list display
        movieListModel = movieManager.getMovieListModel();
        movieListDisplay = new JList<>(movieListModel);
        JScrollPane scrollPane = new JScrollPane(movieListDisplay);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addMovie());
        btnEdit.addActionListener(e -> editMovie());
        btnDelete.addActionListener(e -> deleteMovie());
        btnClear.addActionListener(e -> clearFields());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Method to add a movie
    private void addMovie() {
        try {
            int movieID = Integer.parseInt(txtMovieID.getText());
            String title = txtTitle.getText();
            String genre = txtGenre.getText();
            String duration = txtDuration.getText();
            double price = Double.parseDouble(txtPrice.getText());

            // Validate duration format (HH-MM-SS)
            if (!isValidDuration(duration)) {
                JOptionPane.showMessageDialog(frame, "Invalid duration format! Use HH-MM-SS.");
                return;
            }

            Movie movie = new Movie(movieID, title, genre, duration, price);
            movieManager.addMovie(movie);
            clearFields();
            refreshMovieList();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid data.");
        }
    }

    // Helper method to validate duration format (HH-MM-SS)
    private boolean isValidDuration(String duration) {
        String[] parts = duration.split("-");
        if (parts.length != 3) return false;
        try {
            Integer.parseInt(parts[0]);  // Hours
            Integer.parseInt(parts[1]);  // Minutes
            Integer.parseInt(parts[2]);  // Seconds
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to edit an existing movie
    private void editMovie() {
        if (selectedMovie != null) {
            try {
                String title = txtTitle.getText();
                String genre = txtGenre.getText();
                String duration = txtDuration.getText();
                double price = Double.parseDouble(txtPrice.getText());

                if (!isValidDuration(duration)) {
                    JOptionPane.showMessageDialog(frame, "Invalid duration format!");
                    return;
                }

                Movie updatedMovie = new Movie(selectedMovie.getMovieID(), title, genre, duration, price);
                movieManager.editMovie(selectedMovie, updatedMovie);
                clearFields();
                refreshMovieList();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter valid data.");
            }
        }
    }

    // Method to delete a selected movie
    private void deleteMovie() {
        if (selectedMovie != null) {
            movieManager.deleteMovie(selectedMovie);
            clearFields();
            refreshMovieList();
        }
    }

    // Helper method to refresh the movie list from the database
    private void refreshMovieList() {
        movieListModel.clear();
        movieListModel = movieManager.getMovieListModel();
        movieListDisplay.setModel(movieListModel);
    }

    // Helper method to clear all input fields
    private void clearFields() {
        txtMovieID.setText("");
        txtTitle.setText("");
        txtGenre.setText("");
        txtDuration.setText("");
        txtPrice.setText("");
    }
}
