package dblab1.dblab1_jdbc.model.entityClasses;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Representation of a book.
 * 
 * @author anderslm@kth.se
 */
public class Book {
    
    private int bookId;
    private String isbn; // should check format
    private String title;
    private Date published;

    private Genre genre;

    private int year;
    private String storyLine = "";
    private ArrayList<Author> authors;
    // TODO: 
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    //TODO: avkommentera arraylist med authors då detta krävs för att representera relationen mellan book och author
    // avkommentera också String author som parameter i konstruktiorn
    // när detta göra behöver man anpassa implementeringen i övriga programmet
    public Book(int bookId, String isbn, String title, /*String authors*/ Date published /*, Genre genre*/) {
      //  this.authors = new ArrayList<>();
     //   this.authors.add(author)
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        //this.genre = genre;
    }
    
    public Book(String isbn, String title, Date published) {

        this(-1, isbn, title, published);
    }

    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }
    public String getStoryLine() { return storyLine; }

    public Genre getGenera() { return genre; }
    
    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }
    
    @Override
    public String toString() {
        return title + ", " + isbn + ", " + published.toString();
    }
}
