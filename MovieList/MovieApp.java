package movieList;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList; 
import java.lang.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import movieList.User; 

public class MovieApp {
	// TODO Auto-generated method stub
	public ArrayList<User> userList = new ArrayList<User>(); 
	private String apiKey = "4eedeed2f2046b932b139a93e55bd977";
	private String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=";
	private String detailedUrl = "https://api.themoviedb.org/3/movie/";
	private Scanner scan = new Scanner(System.in);
	
	public MovieApp() { 
		User rando = new User(); 
		userList.add(rando); 
	}
	
	private JSONObject searchMovie(URL url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode); 
            }
			else {
				
			}
			String inline = "";
		    Scanner scanner = new Scanner(url.openStream());
		    
		    while (scanner.hasNext()) {
			       inline += scanner.nextLine();
			}
		    
		    scanner.close();

		    //Using the JSON simple library parse the string into a json object
		    JSONParser parse = new JSONParser();
		    JSONObject data_obj = (JSONObject) parse.parse(inline);
		    
		    return data_obj; 
		    
		}
		catch(Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	private void Add() {
		try {
			System.out.println("Add Movie"); 
			
			String userInput = scan.nextLine(); 
			while(userInput == "") {
				System.out.println("Error: No Movie Inputted");
				System.out.println("Try A Different Movie"); 
				
				userInput = scan.nextLine(); 
			}
			userInput = userInput.replaceAll("\\s", "+"); 
			
			String inputUrl = searchUrl + apiKey + "&query=" + userInput + "\r\n"; 
			
			URL url = new URL(inputUrl);
			JSONObject search= searchMovie(url); 
			JSONArray searchResults = (JSONArray) search.get("results");
			JSONObject details = (JSONObject) searchResults.get(0); 
			String id = details.get("id").toString(); 
			
			inputUrl = detailedUrl + id + "?" + "api_key=" + apiKey + "\r\n"; 
			url = new URL(inputUrl); 
			
			JSONObject data_obj = searchMovie(url); 
			String title = data_obj.get("title").toString();  
			
		    JSONArray movieGenres = (JSONArray) data_obj.get("genres");
		    ArrayList<String >genres = new ArrayList<String>(); 
		    for (int i = 0; i < movieGenres.size(); i++) {
		    	JSONObject genre = (JSONObject) movieGenres.get(i);
		    	genres.add(genre.get("name").toString()); 
		    }
		    
		    userList.get(0).Add(title, genres, id);
		    System.out.println("Successfully Added Movie!"); 
		}
		catch(Exception e) {
			System.out.print(e);
		}
		   
	}
			
	private void Delete() {
		System.out.println("What movie would you like to delete?");
		String userInput = scan.nextLine(); 
		if(userInput != "") {
			if(userList.get(0).Delete(userInput)){
				System.out.println(userInput + " was successfully deleted");
			}
			else {
				System.out.println(userInput + " was not in the list"); 
			}
		}
		else {
			System.out.println("Error: No Input");
		}
		
			
	}
	
	private void Modify() {
		System.out.println("What is the number of the first movie you would like to swap?"); 
		try {
			int movieOne = scan.nextInt();
			System.out.println("What is the number of the second movie you would like to swap?");
			int movieTwo = scan.nextInt(); 
			if(!(userList.get(0).Modify(movieOne, movieTwo))){
				System.out.println("Error: Not a valid movie"); 
			}
		}
		catch(Exception e) {
			System.out.println("Error: Not a valid movie"); 
			return; 
		}		
		
	}
	
	private void Print() {
		userList.get(0).printList(); 
	}
	public void Run() {
		System.out.println("Welcome to MovieList!");
		System.out.println("A - Add, D - Delete, P - Print List, Q - Quit");
		System.out.println(""); 
		String input = ""; 
		char command = 'Q'; 
		while (input == "") {
			try {
				input = scan.nextLine().toUpperCase();
				command = input.charAt(0);
			}
			catch(Exception e) {
				System.out.println("Error: No Input");
				System.out.println("Try Again"); 
				input = ""; 
			}
		
		}
		
		while (command != 'Q') {
			switch (command) {
				case 'A': 
					Add(); 
					break;
				case 'D':
					Delete(); 
					break;
				case 'P':
					Print(); 
					break;
				case 'S':
					Modify();
					break; 
				default:
					System.out.println("Invalid Command!!!"); 
			}
			System.out.println("Enter a new command");
			while (input == "") {
				System.out.println("Error: No Input");
				System.out.println("Try Again"); 
				
				input = scan.nextLine(); 
				
			}
			if (command == 'S') {
				scan.nextLine(); 
			}
			input = scan.nextLine();
		    input = input.toUpperCase(); 
			command = input.charAt(0);
			
		}
		System.out.println("Have a good day!"); 
	

	}
}


