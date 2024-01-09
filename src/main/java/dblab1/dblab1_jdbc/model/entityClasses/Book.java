package dblab1.dblab1_jdbc.model.entityClasses;

import java.util.ArrayList;
import java.util.Date;

/**
 * Representation of a book.
 * 
 * @author anderslm@kth.se
 */
public class Book {
    //TODO: check if all ints has to be converted to strings when displayed in
    // the booksPaneView
    private int bookId;
    private String isbn; // should check format
    private String title;
    private java.util.Date published;
    private int year;
    private int grade;
    private int pages;
    private String language =" ";
    private Genre genre;
    private int genre_id;

    private String storyLine = "";
    private ArrayList<Author> authors;
    // TODO: 
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    //TODO: avkommentera arraylist med authors då detta krävs för att representera relationen mellan book och author
    // avkommentera också String author som parameter i konstruktiorn
    // när detta göra behöver man anpassa implementeringen i övriga programmet
    public Book(int bookId, String isbn, String title/*, Author author,*/ , java.util.Date published, int genre_id, int grade   ) {
        this.bookId = bookId;
        //  this.authors = new ArrayList<>();
     //   this.authors.add(author)

        this.isbn = isbn;
        this.title = title;
       // this.authors = new ArrayList<>();
      //  this.authors.add(author);
        this.published = published;
     //   this.pages = pages;
     //   this.language = language;
          this.genre_id = genre_id;
        this.grade = grade;



    }

    //todo: Check if needed
//    public Book(String isbn, String title, int year/*Date published*/) {
//
//        this(isbn, title, year);
//    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }
    public String getStoryLine() { return storyLine; }
    
    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }
    
    @Override
    public String toString() {
        return bookId +", " + title + ", " + isbn + ", " /* year/*published.toString()*/;
    }
}
