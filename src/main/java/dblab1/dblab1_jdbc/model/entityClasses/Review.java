package dblab1.dblab1_jdbc.model.entityClasses;

public class Review {
    private int grade;

    public Review(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Review{" +
                "grade=" + grade +
                '}';
    }
}
