import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineExam {
    private static final int EXAM_DURATION_MINUTES = 60;
    private static final int TOTAL_QUESTIONS = 5;
    private static final int MAX_MARKS = 10;

    private Map<String, String> userCredentials;
    private Map<String, String> userProfiles;
    private Map<String, Map<String, Integer>> userAnswers;

    private String currentUser;
    private Scanner scanner;

    public OnlineExam() {
        userCredentials = new HashMap<>();
        userProfiles = new HashMap<>();
        userAnswers = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Online Exam ----------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the Online Exam system. Goodbye!");
    }

    private void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            currentUser = username;
            System.out.println("Login successful. Welcome, " + username + "!");
            showMenu();
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private void register() {
        System.out.print("Enter your desired username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your desired password: ");
        String password = scanner.nextLine();

        if (userCredentials.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            userCredentials.put(username, password);
            userProfiles.put(username, "");
            userAnswers.put(username, new HashMap<>());
            System.out.println("Registration successful. You can now login with your credentials.");
        }
    }

    private void showMenu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Update Profile and Password");
            System.out.println("2. Start Exam");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    updateProfileAndPassword();
                    break;
                case "2":
                    startExam();
                    break;
                case "3":
                    logout = true;
                    currentUser = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateProfileAndPassword() {
        System.out.println("---------- Update Profile and Password ----------");
        System.out.print("Enter your new profile information: ");
        String profile = scanner.nextLine();
        userProfiles.put(currentUser, profile);

        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        if (userCredentials.get(currentUser).equals(currentPassword)) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            userCredentials.put(currentUser, newPassword);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Incorrect current password. Password update failed.");
        }
    }

    private void startExam() {
        System.out.println("---------- Exam ----------");
        System.out.println("You have " + EXAM_DURATION_MINUTES + " minutes to complete the exam.");
        System.out.println("Each question carries " + MAX_MARKS + " marks.");
        System.out.println("Please select the correct option (A, B, C, D) for each question.");

        int totalMarks = 0;
        int timeRemaining = EXAM_DURATION_MINUTES;
        for (int i = 1; i <= TOTAL_QUESTIONS; i++) {
            System.out.println("---------- Question " + i + " ----------");
            String question = getQuestion(i);
            System.out.println("Q: " + question);
            System.out.println("A. Option A");
            System.out.println("B. Option B");
            System.out.println("C. Option C");
            System.out.println("D. Option D");

            String answer = "";
            while (!isValidAnswer(answer)) {
                System.out.print("Enter your answer (A, B, C, D): ");
                answer = scanner.nextLine().toUpperCase();
                if (!isValidAnswer(answer)) {
                    System.out.println("Invalid answer. Please select a valid option (A, B, C, D).");
                }
            }

            userAnswers.get(currentUser).put("Q" + i, calculateMarks(i, answer));
            totalMarks += calculateMarks(i, answer);
            timeRemaining -= 10; // Assume each question takes 10 minutes
            System.out.println("----------------------------------");
        }

        System.out.println("---------- Exam Summary ----------");
        System.out.println("Total Marks Obtained: " + totalMarks + " out of " + (MAX_MARKS * TOTAL_QUESTIONS));
        System.out.println("Time Remaining: " + timeRemaining + " minutes");
        System.out.println("----------------------------------");
    }

    private String getQuestion(int questionNumber) {
        switch (questionNumber) {
            case 1:
                return "WHO IS THE PM OFINDIA.";
            case 2:
                return "WHO IS CM OF AP.";
            case 3:
                return "WHO IS THE CENTRAL HOME MINISTER.";
            case 4:
                return "This is the PRESIDENT OF INDIA.";
            case 5:
                return "This is the DEFENCE MINISTER OF INDIA.";
            default:
                return "";
        }
    }

    private boolean isValidAnswer(String answer) {
        return answer.equals("A") || answer.equals("B") || answer.equals("C") || answer.equals("D");
    }

    private int calculateMarks(int questionNumber, String answer) {
        // Assuming the correct answer for each question is "A"
        return answer.equals("A") ? MAX_MARKS : 0;
    }

    public static void main(String[] args) {
        OnlineExam onlineExam = new OnlineExam();
        onlineExam.start();
    }
}
