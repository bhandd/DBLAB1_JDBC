package dblab1.dblab1_jdbc.model.entityClasses;

public class EntityTestMain {
    public static void main(String[] args) {

        Author author = new Author(1, "kalle balle");
        System.out.println(author.getFullName());
        testBook testbook = new testBook(2,"255555","Drabant", author, "ASDF", 3);
        author.getFullName();
        System.out.println(testbook.getAuthor());
    }

}
