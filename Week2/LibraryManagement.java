import java.io.*;
	import java.util.*;
	import java.util.concurrent.locks.*;

	class Book implements Serializable {
	    private String title, author, ISBN;
	    private boolean isAvailable;

	    public Book(String title, String author, String ISBN) {
	        this.title = title;
	        this.author = author;
	        this.ISBN = ISBN;
	        this.isAvailable = true;
	    }

	    public String getTitle() { return title; }
	    public String getAuthor() { return author; }
	    public String getISBN() { return ISBN; }
	    public boolean isAvailable() { return isAvailable; }
	    public void setAvailable(boolean available) { this.isAvailable = available; }
	}

	class User implements Serializable {
	    private String name, userID;
	    private List<Book> borrowedBooks;
	    private static final int MAX_BOOKS = 3;

	    public User(String name, String userID) {
	        this.name = name;
	        this.userID = userID;
	        this.borrowedBooks = new ArrayList<>();
	    }

	    public String getName() { return name; }
	    public String getUserID() { return userID; }
	    public List<Book> getBorrowedBooks() { return borrowedBooks; }

	    public void borrowBook(Book book) throws MaxBooksAllowedException {
	        if (borrowedBooks.size() >= MAX_BOOKS) {
	            throw new MaxBooksAllowedException("User has reached max book limit!");
	        }
	        borrowedBooks.add(book);
	    }

	    public void returnBook(Book book) {
	        borrowedBooks.remove(book);
	    }
	}

	class BookNotFoundException extends Exception {
	    public BookNotFoundException(String message) { super(message); }
	}
	class UserNotFoundException extends Exception {
	    public UserNotFoundException(String message) { super(message); }
	}
	class MaxBooksAllowedException extends Exception {
	    public MaxBooksAllowedException(String message) { super(message); }
	}

	interface ILibrary {
	    void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;
	    void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
	    void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
	    Book searchBook(String title);
	}

	abstract class LibrarySystem implements ILibrary {
	    protected List<Book> books = new ArrayList<>();
	    protected List<User> users = new ArrayList<>();
	    protected final Lock lock = new ReentrantLock();

	    public abstract void addBook(Book book);
	    public abstract void addUser(User user);
	}

	class LibraryManager extends LibrarySystem {
	    @Override
	    public void addBook(Book book) { books.add(book); }
	    
	    @Override
	    public void addUser(User user) { users.add(user); }
	    
	    @Override
	    public Book searchBook(String title) {
	        return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
	    }
	    
	    @Override
	    public void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
	        lock.lock();
	        try {
	            Book book = books.stream().filter(b -> b.getISBN().equals(ISBN) && b.isAvailable()).findFirst().orElseThrow(() -> new BookNotFoundException("Book not found or unavailable!"));
	            User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new UserNotFoundException("User not found!"));
	            user.borrowBook(book);
	            book.setAvailable(false);
	        } finally {
	            lock.unlock();
	        }
	    }

	    @Override
	    public void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
	        lock.lock();
	        try {
	            User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new UserNotFoundException("User not found!"));
	            Book book = user.getBorrowedBooks().stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(() -> new BookNotFoundException("Book not borrowed by user!"));
	            user.returnBook(book);
	            book.setAvailable(true);
	        } finally {
	            lock.unlock();
	        }
	    }

	    @Override
	    public void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
	        System.out.println("Book reserved: " + ISBN + " for User: " + userID);
	    }

	    public void saveData(String filename) throws IOException {
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
	            oos.writeObject(books);
	            oos.writeObject(users);
	        }
	    }

	    public void loadData(String filename) throws IOException, ClassNotFoundException {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
	            books = (List<Book>) ois.readObject();
	            users = (List<User>) ois.readObject();
	        }
	    }
	}

	public class LibraryManagement {
	    public static void main(String[] args) {
	        LibraryManager libManager = new LibraryManager();
	        libManager.addBook(new Book("Java Programming", "James Gosling", "12345"));
	        libManager.addUser(new User("Alice", "U001"));

	        Thread t1 = new Thread(() -> {
	            try { libManager.borrowBook("12345", "U001"); } 
	            catch (Exception e) { System.out.println(e.getMessage()); }
	        });

	        Thread t2 = new Thread(() -> {
	            try { libManager.returnBook("12345", "U001"); } 
	            catch (Exception e) { System.out.println(e.getMessage()); }
	        });

	        t1.start();
	        t2.start();

	        try {
	            t1.join();
	            t2.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        try {
	            libManager.saveData("library_data.dat");
	            libManager.loadData("library_data.dat");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


