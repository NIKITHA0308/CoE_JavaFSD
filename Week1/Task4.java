
	import java.io.*;
	import java.util.ArrayList;
	import java.util.List;
import java.util.Scanner;
	class User {
	    
	    String name;
	    String email;

	    public User(String name, String email) {
	        this.name = name;
	        this.email = email;
	    }

	    @Override
	    public String toString() {
	        return name + "," + email;
	    }
	    public static User fromString(String userString) {
	        String[] parts = userString.split(",");
	        return new User(parts[0], parts[1]);
	    }
	}

	class UserManager {
	    
	    List<User> users;

	    public UserManager() {
	        users = new ArrayList<>();
	    }

	    
	    public void addUser(String name, String email) {
	        users.add(new User(name, email));
	    }

	    public void saveUsersToFile(String filename) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	            for (User user : users) {
	                writer.write(user.toString());
	                writer.newLine();
	            }
	            System.out.println("Users saved to file: " + filename);
	        } catch (IOException e) {
	            System.out.println("An error occurred while saving the users to the file.");
	            e.printStackTrace();
	        }
	    }

	    public void loadUsersFromFile(String filename) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                users.add(User.fromString(line));
	            }
	            System.out.println("Users loaded from file: " + filename);
	        } catch (IOException e) {
	            System.out.println("An error occurred while reading the users from the file.");
	            e.printStackTrace();
	        }
	    }

	    public void displayUsers() {
	        for (User user : users) {
	            System.out.println("Name: " + user.name + ", Email: " + user.email);
	        }
	    }
	}

	public class Task4 {
	    public static void main(String[] args) {
	      
	        UserManager userManager = new UserManager();
	        Scanner sc=new Scanner(System.in);
	        System.out.println("Enter name:");
	        String name1=sc.nextLine();
	        System.out.println("Enter email:");
	        String email1=sc.nextLine();
	        System.out.println("Enter name:");
	        String name2=sc.nextLine();
	        System.out.println("Enter email:");
	        String email2=sc.nextLine();
	        userManager.addUser(name1, email1);
	        userManager.addUser(name2, email2);
	        userManager.saveUsersToFile("C:\\Users\\Lenovo\\Desktop\\users.txt");
	        userManager = new UserManager();
	        userManager.loadUsersFromFile("C:\\Users\\Lenovo\\Desktop\\users.txt");
	        userManager.displayUsers();
	    }
	}


