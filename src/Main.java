import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main application class with console interface
 */
public class Main {
    private static JournalService journalService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        journalService = new JournalService();
        scanner = new Scanner(System.in);
        
        System.out.println("=== School Journal ===");
        System.out.println("Generating test data...");
        
        // Generate test data
        DataGenerator.generateTestData(journalService);
        
        System.out.println("Test data generated successfully!");
        System.out.println("Students: " + journalService.getStudents().size());
        System.out.println("Subjects: " + journalService.getSubjects().size());
        System.out.println("Grades: " + journalService.getGrades().size());
        System.out.println();
        
        // Main application loop
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Choose action: ");
            
            switch (choice) {
                case 1:
                    showAllStudents();
                    break;
                case 2:
                    searchGradesByStudent();
                    break;
                case 3:
                    filterStudentsByAverage();
                    break;
                case 4:
                    showAllAverages();
                    break;
                case 5:
                    showSubjectStatistics();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Show all students");
        System.out.println("2. Find grades by student");
        System.out.println("3. Filter students by average grade");
        System.out.println("4. Show all students average grades");
        System.out.println("5. Show subject statistics");
        System.out.println("0. Exit");
        System.out.println();
    }
    
    private static void showAllStudents() {
        System.out.println("=== All Students ===");
        List<Student> students = journalService.getStudents();
        
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            double average = journalService.calculateAverageGrade(student);
            System.out.printf("%2d. %s %s (Grade %d) - Average: %.2f%n",
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGradeLevel(),
                    average);
        }
    }
    
    private static void searchGradesByStudent() {
        System.out.println("=== Find Grades by Student ===");
        showAllStudents();
        
        int studentId = getIntInput("Enter student ID: ");
        Student student = journalService.getStudentById(studentId);
        
        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found!");
            return;
        }
        
        List<Grade> grades = journalService.findGradesByStudent(student);
        double average = journalService.calculateAverageGrade(student);
        
        System.out.printf("Grades for student: %s %s (Grade %d)%n",
                student.getFirstName(), student.getLastName(), student.getGradeLevel());
        System.out.printf("Average grade: %.2f%n", average);
        System.out.println("List of grades:");
        
        if (grades.isEmpty()) {
            System.out.println("No grades found.");
        } else {
            for (Grade grade : grades) {
                System.out.printf("  %s: %d (date: %s)%n",
                        grade.getSubject().getName(),
                        grade.getGradeValue(),
                        grade.getDate());
            }
        }
    }
    
    private static void filterStudentsByAverage() {
        System.out.println("=== Filter Students by Average Grade ===");
        double minAverage = getDoubleInput("Enter minimum average grade: ");
        
        List<Student> filteredStudents = journalService.filterStudentsByAverageGrade(minAverage);
        
        System.out.printf("Students with average grade >= %.2f:%n", minAverage);
        
        if (filteredStudents.isEmpty()) {
            System.out.println("No students matching the criteria.");
        } else {
            for (Student student : filteredStudents) {
                double average = journalService.calculateAverageGrade(student);
                System.out.printf("  %s %s (Grade %d) - %.2f%n",
                        student.getFirstName(),
                        student.getLastName(),
                        student.getGradeLevel(),
                        average);
            }
        }
    }
    
    private static void showAllAverages() {
        System.out.println("=== All Students Average Grades ===");
        Map<Student, Double> averages = journalService.getAllStudentsAverageGrades();
        
        averages.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    Student student = entry.getKey();
                    double average = entry.getValue();
                    System.out.printf("  %s %s (Grade %d) - %.2f%n",
                            student.getFirstName(),
                            student.getLastName(),
                            student.getGradeLevel(),
                            average);
                });
    }
    
    private static void showSubjectStatistics() {
        System.out.println("=== Subject Statistics ===");
        Map<Subject, Double> subjectAverages = journalService.getSubjectsAverageGrades();
        
        subjectAverages.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    System.out.printf("  %s - Average grade: %.2f%n",
                            entry.getKey().getName(),
                            entry.getValue());
                });
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter an integer.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number.");
            }
        }
    }
}