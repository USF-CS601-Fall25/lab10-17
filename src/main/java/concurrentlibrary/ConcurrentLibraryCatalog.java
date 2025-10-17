package concurrentlibrary;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe library catalog that supports concurrent reading and writing.
 * Uses a ReentrantReadWriteLock to allow multiple readers but only one writer at a time.
 *
 * Students: Fill in missing code.
 */
public class ConcurrentLibraryCatalog {
    private final List<Book> books = new ArrayList<>();
    private final List<String> auditLog = new ArrayList<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    private boolean verbose = true;

    /** Adds a book to the catalog (requires WRITE lock).
     *
     * @param title title of the book
     * @param author author of the book
     */
    public void addBook(String title, String author) {
        // TODO: Acquire WRITE lock before modifying the list of books
        // TODO: Add the new book
        // TODO: Call logChange("Added: " + title) to log the fact that we added a book
        // TODO: Release the lock in a finally block
    }

    /** Checks out a book if available (requires WRITE lock).
     *
     * @param title title of the book
     * @return true, if was able to check out, false otherwise
     */
    public boolean checkoutBook(String title) {
        // TODO: Acquire WRITE lock
        // TODO: Find the book with the given title
        // TODO: If available, mark it as checked out and log the change
        // TODO: Return true if successful, false otherwise
        // TODO: Release lock in finally block
        return false;
    }

    /** Returns a checked-out book (requires WRITE lock).
     *
     * @param title title of the book
     * @return true, if was able to return, false otherwise
     */
    public boolean returnBook(String title) {
        // TODO: Acquire WRITE lock
        // TODO: Find the book by title, if it's checked out, return it and log the change
        // TODO: Return true if successful, false otherwise
        // TODO: Release lock in finally block
        return false;
    }

    /** Prints all books currently available for checkout (requires READ lock). */
    public void printAvailableBooks() {
        // TODO: Acquire READ lock
        // TODO: Print all books that are not checked out
        // TODO: Release lock in finally block
    }

    /** Logs changes to the audit log (demonstrates why reentrant lock is useful). */
    private void logChange(String message) {
        // TODO: Acquire WRITE lock
        // TODO: Add message to audit log
        // TODO: Release WRITE lock in the finally block
        // Discuss why this method, the way it is currently invoked, will reacquire the write lock again


    }

    /** Prints all audit log entries (requires READ lock). */
    public void printLog() {
        // TODO: Acquire READ lock
        // TODO: Print all log messages
        // TODO: Release lock in the finally block
    }

    /** Enable or disable lock tracing messages. */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}