public class Movie {
    private int movieID;
    private String title;
    private String genre;
    private String duration;  // Format: HH-MM-SS HH:MM:SS
    private double price;

    // Constructor
    public Movie(int movieID, String title, String genre, String duration, double price) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
    }

    // Getters and setters for each property
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + movieID + ", Title: " + title + ", Genre: " + genre + ", Duration: " + duration + ", Price: " + price;
    }
}
