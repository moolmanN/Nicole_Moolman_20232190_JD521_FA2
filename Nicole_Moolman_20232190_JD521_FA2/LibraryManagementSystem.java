import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Represents a Book in the library system
class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;

    // Constructor for creating a new Book
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true; // Book is initially available
    }

    // Getter methods for book attributes
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to toggle the availability status of the book
    public void toggleAvailability() {
        isAvailable = !isAvailable;
    }
}

// Represents a Member in the library system
class Member {
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    // Constructor for creating a new Member
    public Member(String name, String email) {
        this.name = name;
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.borrowedBooks = new ArrayList<>();
    }

    // Validate email format using regular expression
    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Getter methods for member attributes
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Method to borrow a book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Method to return a book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}

// Represents the Library system
class Library {
    private List<Book> books;
    private List<Member> members;

    // Constructor for creating a new Library
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Method to add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to add a member to the library
    public void addMember(Member member) {
        members.add(member);
    }

    // Method to search for books based on a query
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    // Method to checkout a book for a member
    public void checkoutBook(Member member, Book book) {
        assert book.isAvailable() : "Book is not available for checkout"; // Assertion for book availability
        member.borrowBook(book);
        book.toggleAvailability();
        System.out.println("Book checked out successfully.");
    }

    // Method to return a book by a member
    public void returnBook(Member member, Book book) {
        member.returnBook(book);
        book.toggleAvailability();
        System.out.println("Book returned successfully.");
    }

    // Method to get all checked out books
    public List<Book> getCheckedOutBooks() {
        List<Book> checkedOutBooks = new ArrayList<>();
        for (Member member : members) {
            checkedOutBooks.addAll(member.getBorrowedBooks());
        }
        return checkedOutBooks;
    }

    // Getter methods for library data
    public List<Member> getMembers() {
        return members;
    }

    public List<Book> getBooks() {
        return books;
    }
}

// Main class for running the Library Management System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Main menu loop
        while (true) {
            System.out.println("Welcome to the Library Management System");
            System.out.println("1. Login as Librarian");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    librarianMenu(library, scanner);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Menu for librarian actions
    public static void librarianMenu(Library library, Scanner scanner) {
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Sample password, you should use a more secure method for real applications
        if (!password.equals("admin")) {
            System.out.println("Incorrect password. Access denied.");
            return;
        }

        // Librarian menu loop
        while (true) {
            System.out.println("\nLibrarian Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Display Books");
            System.out.println("4. Display Members");
            System.out.println("5. Search Books");
            System.out.println("6. Check Out Book");
            System.out.println("7. Display Checked Out Books");
            System.out.println("8. Return Book");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(library, scanner);
                    break;
                case 2:
                    addMember(library, scanner);
                    break;
                case 3:
                    displayBooks(library);
                    break;
                case 4:
                    displayMembers(library);
                    break;
                case 5:
                    searchBooks(library, scanner);
                    break;
                case 6:
                    checkOutBook(library, scanner);
                    break;
                case 7:
                    displayCheckedOutBooks(library);
                    break;
                case 8:
                    returnBook(library, scanner);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a book to the library
    public static void addBook(Library library, Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String ISBN = scanner.nextLine();

        Book book = new Book(title, author, ISBN);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    // Method to add a member to the library
    public static void addMember(Library library, Scanner scanner) {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();

        Member member = new Member(name, email);
        library.addMember(member);
        System.out.println("Member added successfully.");
    }

    // Method to display all books in the library
    public static void displayBooks(Library library) {
        List<Book> books = library.getBooks();
        System.out.println("\nBooks:");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", ISBN: " + book.getISBN());
        }
    }

    // Method to display all members in the library
    public static void displayMembers(Library library) {
        List<Member> members = library.getMembers();
        System.out.println("\nMembers:");
        for (Member member : members) {
            System.out.println("Name: " + member.getName() + ", Email: " + member.getEmail());
        }
    }

    // Method to search for books based on a query
    public static void searchBooks(Library library, Scanner scanner) {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        List<Book> searchResults = library.searchBooks(query);
        if (searchResults.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : searchResults) {
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", ISBN: " + book.getISBN());
            }
        }
    }

    // Method to check out a book
    public static void checkOutBook(Library library, Scanner scanner) {
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        Member member = findMemberByEmail(library, email);
        assert member != null : "Member not found"; // Assertion for member existence
        System.out.print("Enter book ISBN: ");
        String ISBN = scanner.nextLine();
        Book book = findBookByISBN(library, ISBN);
        assert book != null : "Book not found"; // Assertion for book existence
        try {
            library.checkoutBook(member, book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to return a book
    public static void returnBook(Library library, Scanner scanner) {
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        Member member = findMemberByEmail(library, email);
        assert member != null : "Member not found"; // Assertion for member existence
        System.out.print("Enter book ISBN: ");
        String ISBN = scanner.nextLine();
        Book book = findBookByISBN(library, ISBN);
        assert book != null : "Book not found"; // Assertion for book existence
        library.returnBook(member, book);
    }

    // Method to find a member by email
    public static Member findMemberByEmail(Library library, String email) {
        for (Member member : library.getMembers()) {
            if (member.getEmail().equals(email)) {
                return member;
            }
        }
        return null;
    }

    // Method to find a book by ISBN
    public static Book findBookByISBN(Library library, String ISBN) {
        for (Book book : library.getBooks()) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    // Method to display all checked out books
    public static void displayCheckedOutBooks(Library library) {
        List<Book> checkedOutBooks = library.getCheckedOutBooks();
        System.out.println("\nChecked Out Books:");
        for (Book book : checkedOutBooks) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }
}
