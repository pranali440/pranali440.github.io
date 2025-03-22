package online_survey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class online_survey {

	
	 private static final String URL = "jdbc:postgresql://localhost:5432/online_survey";
	    private static final String USER = "postgres";
	    private static final String PASSWORD = "123";

	    public static Connection getConnection() 
	    {
	        try {
	            return DriverManager.getConnection(URL, USER, PASSWORD);
	        } 
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	            return null;
	        }
	    }
	public static void main(String[] args) {
		
		        Scanner scanner = new Scanner(System.in);
		        System.out.println("Welcome to the Online Survey!");

		        // Collecting user data
		        System.out.print("Enter your name: ");
		        String name = scanner.nextLine();

		        System.out.print("Enter your email: ");
		        String email = scanner.nextLine();

		        System.out.println("Please answer the following questions:");
		        System.out.print("Question 1: What is your favorite programming language? ");
		        String question1 = scanner.nextLine();

		        System.out.print("Question 2: Do you prefer working in a team or alone? ");
		        String question2 = scanner.nextLine();

		        System.out.print("Question 3: What do you think about online surveys? ");
		        String question3 = scanner.nextLine();

		        // Create a SurveyResponse object
		      SurveyResponse response = new SurveyResponse(name, email, question1, question2, question3);
		    
		        // Submit the response to the database
		       Survey survey = new Survey();
		        survey.submitSurveyResponse(response);
		        
		        System.out.print("Do you want to update your response? (yes/no): ");
		        String updateChoice = scanner.nextLine();

		        if (updateChoice.equalsIgnoreCase("yes")) {
		            System.out.print("Enter the ID of the response you want to update: ");
		            int idToUpdate = scanner.nextInt();
		            scanner.nextLine(); // consume newline
		            System.out.print("Enter new response for Question 1: ");
		            String newQ1 = scanner.nextLine();
		            System.out.print("Enter new response for Question 2: ");
		            String newQ2 = scanner.nextLine();
		            System.out.print("Enter new response for Question 3: ");
		            String newQ3 = scanner.nextLine();

		            survey.updateSurveyResponse(idToUpdate, newQ1, newQ2, newQ3);
		        }

		        // Ask user if they want to delete a response
		        System.out.print("Do you want to delete a response? (yes/no): ");
		        String deleteChoice = scanner.nextLine();

		        if (deleteChoice.equalsIgnoreCase("yes")) {
		            System.out.print("Enter the ID of the response you want to delete: ");
		            int idToDelete = scanner.nextInt();
		            survey.deleteSurveyResponse(idToDelete);
		        }

		        scanner.close();
		    }
	

		
	}

 class SurveyResponse  
{
    private String name;
    private String email;
    private String question1;
    private String question2;
    private String question3;

     public SurveyResponse(String name, String email, String question1, String question2, String question3) {
        this.name = name;
        this.email = email;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getQuestion1() { return question1; }
    public void setQuestion1(String question1) { this.question1 = question1; }

    public String getQuestion2() { return question2; }
    public void setQuestion2(String question2) { this.question2 = question2; }

    public String getQuestion3() { return question3; }
    public void setQuestion3(String question3) { this.question3 = question3; }
}



 class Survey 
{
	
    public void submitSurveyResponse(SurveyResponse response) {
        Connection connection = online_survey.getConnection();
        
        if (connection != null) {
            String query = "INSERT INTO survey_response (name, email, question1, question2, question3) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, response.getName());
                preparedStatement.setString(2, response.getEmail());
                preparedStatement.setString(3, response.getQuestion1());
                preparedStatement.setString(4, response.getQuestion2());
                preparedStatement.setString(5, response.getQuestion3());
                
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    System.out.println("Survey response submitted successfully.");
                } else {
                    System.out.println("Failed to submit the survey response.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }
    public void updateSurveyResponse(int id, String newQuestion1, String newQuestion2, String newQuestion3) {
        Connection connection = online_survey.getConnection();

        if (connection != null) {
            String query = "UPDATE survey_response SET question1 = ?, question2 = ?, question3 = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newQuestion1);
                preparedStatement.setString(2, newQuestion2);
                preparedStatement.setString(3, newQuestion3);
                preparedStatement.setInt(4, id);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    System.out.println("Survey response updated successfully.");
                } else {
                    System.out.println("Failed to update the survey response.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete a Survey Response
    public void deleteSurveyResponse(int id) {
        Connection connection = online_survey.getConnection();

        if (connection != null) {
            String query = "DELETE FROM survey_response WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    System.out.println("Survey response deleted successfully.");
                } else {
                    System.out.println("Failed to delete the survey response.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
}

	      




