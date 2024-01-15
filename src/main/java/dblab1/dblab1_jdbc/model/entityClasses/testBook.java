package dblab1.dblab1_jdbc.model.entityClasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class testBook {
    //TODO: check if all ints has to be converted to strings when displayed in
    // the booksPaneView
    private int bookId;
    private String isbn; // should check format
    private String title;
    private java.sql.Date published;
    private int year;
    private int grade;
    private int pages;
    private String language =" ";
    // private Genre genre;
    private String genre;

    private String storyLine = "";
    //  private String author;

    private ArrayList<Author> authors;
    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    //TODO: avkommentera arraylist med authors då detta krävs för att representera relationen mellan book och author
    // avkommentera också String author som parameter i konstruktiorn
    // när detta göra behöver man anpassa implementeringen i övriga programmet
    public testBook(int bookId, String isbn, String title, Author author , /*java.sql.Date published,*/ String genre, int grade   ) {
        this.bookId = bookId;

        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>();
        this.authors.add(author);
        //    this.author.setfName(author);

        this.published = published;
        this.genre = genre;
        this.grade = grade;

    }

    //todo: Check if needed
//    public Book(String isbn, String title, int year/*Date published*/) {
//
//        this(isbn, title, year);
//    }

//    public void addAuthor(Author author) {
//        this.authors.add(author);
//    }

    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }
    public String getStoryLine() { return storyLine; }

    public List<Author> getAuthor() {
        ArrayList<Author> copy = new ArrayList<>();
        for (int i = 0; i <authors.size(); i++){
            copy.add(new Author(authors.get(i).getId(), authors.get(i).getFullName()));

        }
        return copy;
    }

    public void printAuthors(){
      //  System.out.println("printAuthors");
        for (int i=0; i < authors.size(); i++){
            System.out.println("Name:" + authors.get(i).getFullName());
            System.out.println("ID: "+ authors.get(i).getId());

        }
       // System.out.println("Printauthors done");
    }

    public String getGenre() {
        return genre;
    }

    public int getGrade() {
        return grade;
    }

    //TODO: use with Authors list
//    public ArrayList<Author> getAuthors() {
//        return authors;
//    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublished(java.sql.Date published) {
        this.published = published;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }



    @Override
    public String toString() {
        return bookId +", "+ isbn + ", " + title + ", "  +  published + ", " + genre + ", " + grade;
    }
}
