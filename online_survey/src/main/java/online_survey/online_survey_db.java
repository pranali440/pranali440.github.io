package online_survey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class online_survey_db {

	static void createDb(String dbName)
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","123");
			String query="create database "+dbName;
			Statement stmt=con.createStatement();
			stmt.executeLargeUpdate(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	static void createTbl(String dbname,String Tblname) {
		 try {
				Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,"postgres", "123");
				 String query = "CREATE TABLE IF NOT EXISTS " + Tblname + " (" +
	                       "Id SERIAL PRIMARY KEY, " +  
	                       "Name VARCHAR(20) NOT NULL, " +
	                       "Email VARCHAR(20) NOT NULL, " +
	                       "Question1 VARCHAR(20) NOT NULL, " +
	                       "Question2 VARCHAR(20) NOT NULL, " +
	                       "Question3 VARCHAR(20) NOT NULL )" ;
				Statement stmt=con.createStatement();//Create Statement
				stmt.executeLargeUpdate(query);
				}catch(SQLException e) {e.printStackTrace();}
				
	}
	public static void main(String[] args) {
		//createDb("online_survey");	
		createTbl("online_survey","survey_response");

	}

}
