import java.time.LocalDate;
import java.util.Objects;

public class Grade {
    private int id;
    private Student student;
    private Subject subject;
    private int gradeValue;
    private LocalDate date;
    
    public Grade(int id, Student student, Subject subject, int gradeValue, LocalDate date) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.gradeValue = gradeValue;
        this.date = date;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public int getGradeValue() { return gradeValue; }
    public void setGradeValue(int gradeValue) { 
        if (gradeValue >= 1 && gradeValue <= 10) {
            this.gradeValue = gradeValue;
        } else {
            throw new IllegalArgumentException("Grade must be between 1 and 10");
        }
    }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return id == grade.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", student=" + student.getFullName() +
                ", subject=" + subject.getName() +
                ", gradeValue=" + gradeValue +
                ", date=" + date +
                '}';
    }
}