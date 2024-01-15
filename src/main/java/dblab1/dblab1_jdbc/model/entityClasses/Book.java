package dblab1.dblab1_jdbc.model.entityClasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of a book.
 *
 * @author anderslm@kth.se
 */
public class Book {
    private int bookId;
    private String isbn; // should check format
    private String title;
    private java.sql.Date published;

    private int grade;

    private String genre;
    private ArrayList<Author> authors;

    public Book(int bookId, String isbn, String title, /*Author author ,*/ java.sql.Date published, String genre, int grade   ) {
        this.bookId = bookId;

        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>();
        this.published = published;
        this.genre = genre;
        this.grade = grade;

    }


    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }

    /**
     * Returns a copy of the internal `authors` list as a `List<Author>` object.
     *
     * @return A `List<Author>` object containing a copy of the internal `authors` list.
     */
    public List<Author> getAuthors() {
        ArrayList<Author> copy = new ArrayList<>();
        for (int i = 0; i <authors.size(); i++){
            copy.add(new Author(authors.get(i).getId(), authors.get(i).getFullName()));
        }
        return copy;
    }

    /**
     * Gets the author at the specified index from the internal `authors` list.
     *
     * @param index The index of the author to retrieve.
     * @return A string representation of the author at the specified index.
     */
    public String getAuthorAtIndex(int index){
        return authors.get(index).toString();
    }

    public String getGenre() {
        return genre;
    }

    public int getGrade() {
        return grade;
    }

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

    public void setGrade(int grade) {
        this.grade = grade;
    }



    /**
     * Sets the genre of the book to the specified value.
     *
     * @param genre The new genre of the book.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Adds the specified list of `Author` objects to the internal `authors` list.
     *
     * @param authors The list of `Author` objects to add.
     */
    public void addAuthor(ArrayList<Author> authors) {
        this.authors = authors;
    }



    /**Help method
     * Prints the full names and IDs of all authors in the specified list of `Author` objects.
     *
     * authors is The list of `Author` objects to iterate over and print.
     */
    public void printAuthors(){

        for (int i=0; i < authors.size(); i++){
            System.out.println("Name:" + authors.get(i).getFullName());
            System.out.println("ID: "+ authors.get(i).getId());

        }

    }


    /**
     * Creates an ArrayList of authors' full names from the specified list of `Author` objects.
     *
     * @param authors The list of `Author` objects from which to extract full names.
     * @return An ArrayList containing the full names of the specified authors.
     */
    public ArrayList<String> getAuthorsNames(ArrayList<Author> authors){
        ArrayList<String> nameList = new ArrayList<>();

        for (int i=0; i < authors.size(); i++){
            nameList.add(authors.get(i).getFullName()) ;
        }
        return nameList;
    }

    @Override
    public String toString() {
        return bookId +", "+ isbn + ", " + title + ", " + getAuthorsNames(this.authors) + ", " +  published + ", " + genre + ", " + grade;
    }
}
