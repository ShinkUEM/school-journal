import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервисный класс, содержащий основную бизнес-логику приложения
 */
public class JournalService {
    private List<Student> students;
    private List<Subject> subjects;
    private List<Grade> grades;
    
    public JournalService() {
        this.students = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
    }
    
    /**
     * Поиск всех оценок по ученику
     */
    public List<Grade> findGradesByStudent(Student student) {
        return grades.stream()
                .filter(grade -> grade.getStudent().equals(student))
                .collect(Collectors.toList());
    }
    
    /**
     * Поиск оценок по ID ученика
     */
    public List<Grade> findGradesByStudentId(int studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudent().getId() == studentId)
                .collect(Collectors.toList());
    }
    
    /**
     * Фильтрация учеников по минимальному среднему баллу
     */
    public List<Student> filterStudentsByAverageGrade(double minAverage) {
        return students.stream()
                .filter(student -> calculateAverageGrade(student) >= minAverage)
                .collect(Collectors.toList());
    }
    
    /**
     * Расчет среднего балла для ученика
     */
    public double calculateAverageGrade(Student student) {
        List<Grade> studentGrades = findGradesByStudent(student);
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        
        double sum = studentGrades.stream()
                .mapToInt(Grade::getGradeValue)
                .sum();
        
        return sum / studentGrades.size();
    }
    
    /**
     * Получение среднего балла по всем ученикам
     */
    public Map<Student, Double> getAllStudentsAverageGrades() {
        Map<Student, Double> averages = new HashMap<>();
        for (Student student : students) {
            averages.put(student, calculateAverageGrade(student));
        }
        return averages;
    }
    
    /**
     * Добавление ученика
     */
    public void addStudent(Student student) {
        students.add(student);
    }
    
    /**
     * Добавление предмета
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    /**
     * Добавление оценки
     */
    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    
    // Геттеры для списков
    public List<Student> getStudents() { return new ArrayList<>(students); }
    public List<Subject> getSubjects() { return new ArrayList<>(subjects); }
    public List<Grade> getGrades() { return new ArrayList<>(grades); }
    
    /**
     * Получение ученика по ID
     */
    public Student getStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Получение предмета по ID
     */
    public Subject getSubjectById(int id) {
        return subjects.stream()
                .filter(subject -> subject.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Получение статистики по предметам
     */
    public Map<Subject, Double> getSubjectsAverageGrades() {
        Map<Subject, Double> subjectAverages = new HashMap<>();
        
        for (Subject subject : subjects) {
            List<Grade> subjectGrades = grades.stream()
                    .filter(grade -> grade.getSubject().equals(subject))
                    .collect(Collectors.toList());
            
            if (!subjectGrades.isEmpty()) {
                double average = subjectGrades.stream()
                        .mapToInt(Grade::getGradeValue)
                        .average()
                        .orElse(0.0);
                subjectAverages.put(subject, average);
            }
        }
        
        return subjectAverages;
    }
}