import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class for generating test data
 */
public class DataGenerator {
    private static final Random random = new Random();
    private static final List<String> FIRST_NAMES = Arrays.asList(
        "Ivan", "Petr", "Maria", "Anna", "Sergey", "Olga", "Alexey", "Elena", 
        "Dmitry", "Natalia", "Andrey", "Irina", "Mikhail", "Svetlana"
    );
    
    private static final List<String> LAST_NAMES = Arrays.asList(
        "Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov", 
        "Vasiliev", "Pavlov", "Semenov", "Golubev", "Vinogradov", "Bogdanov"
    );
    
    private static final List<String> SUBJECT_NAMES = Arrays.asList(
        "Mathematics", "Russian Language", "Physics", "Chemistry", "History", 
        "Literature", "Biology", "Geography", "Computer Science", "English Language"
    );
    
    /**
     * Generate test data
     */
    public static void generateTestData(JournalService journalService) {
        generateSubjects(journalService);
        generateStudents(journalService);
        generateGrades(journalService);
    }
    
    private static void generateSubjects(JournalService journalService) {
        for (int i = 0; i < SUBJECT_NAMES.size(); i++) {
            journalService.addSubject(new Subject(i + 1, SUBJECT_NAMES.get(i)));
        }
    }
    
    private static void generateStudents(JournalService journalService) {
        for (int i = 0; i < 15; i++) {
            String firstName = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
            String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
            int gradeLevel = 9 + random.nextInt(3); // Grades 9-11
            
            journalService.addStudent(new Student(i + 1, firstName, lastName, gradeLevel));
        }
    }
    
    private static void generateGrades(JournalService journalService) {
        int gradeId = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        
        for (Student student : journalService.getStudents()) {
            for (Subject subject : journalService.getSubjects()) {
                // Generate 5 to 15 grades for each subject for each student
                int gradeCount = 5 + random.nextInt(11);
                
                for (int i = 0; i < gradeCount; i++) {
                    int gradeValue = 3 + random.nextInt(3); // Grades 3, 4 or 5
                    LocalDate date = startDate.plusDays(random.nextInt(90));
                    
                    journalService.addGrade(new Grade(
                        gradeId++, 
                        student, 
                        subject, 
                        gradeValue, 
                        date
                    ));
                }
            }
        }
    }
}