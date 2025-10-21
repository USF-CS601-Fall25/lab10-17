package solution.concurrentlibrary;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLibraryCatalog {
    private final List<Book> books = new ArrayList<>();
    private final List<String> auditLog = new ArrayList<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true); // fair mode

    private boolean verbose = true;

    /** Enable or disable lock tracing messages. */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void addBook(String title, String author) {
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " trying to acquire WRITE lock to add " + title);
        rwLock.writeLock().lock();
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " acquired WRITE lock (adding " + title + ")");
        try {
            books.add(new Book(title, author));
            logChange("Added: " + title);
        } finally {
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " releasing WRITE lock after adding " + title);
            rwLock.writeLock().unlock();
        }
    }

    public boolean checkoutBook(String title) {
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " trying to acquire WRITE lock to checkout " + title);
        rwLock.writeLock().lock();
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " acquired WRITE lock (checking out " + title + ")");
        try {
            for (Book b : books) {
                if (b.getTitle().equals(title) && !b.isCheckedOut()) {
                    b.checkout();
                    logChange("Checked out: " + title);
                    return true;
                }
            }
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " could NOT checkout " + title);
            return false;
        } finally {
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " releasing WRITE lock (done checkout)");
            rwLock.writeLock().unlock();
        }
    }

    public boolean returnBook(String title) {
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " trying to acquire WRITE lock to return " + title);
        rwLock.writeLock().lock();
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " acquired WRITE lock (returning " + title + ")");
        try {
            for (Book b : books) {
                if (b.getTitle().equals(title) && b.isCheckedOut()) {
                    b.returnToLibrary();
                    logChange("Returned: " + title);
                    return true;
                }
            }
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " could NOT return " + title);
            return false;
        } finally {
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " releasing WRITE lock (done return)");
            rwLock.writeLock().unlock();
        }
    }

    public void printAvailableBooks() {
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " trying to acquire READ lock to view available books...");
        rwLock.readLock().lock();
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " acquired READ lock (printing available books)");
        try {
            if (books.isEmpty()) {
                System.out.println("No books currently in catalog.");
            } else {
                for (Book b : books) {
                    if (!b.isCheckedOut()) {
                        System.out.println("  " + b);
                    }
                }
            }
        } finally {
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " releasing READ lock (done viewing books)");
            rwLock.readLock().unlock();
        }
    }

    /** Demonstrates reentrancy: same thread re-acquires write lock safely. */
    private void logChange(String message) {
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " re-acquiring WRITE lock (inside logChange)");
        rwLock.writeLock().lock();
        if (verbose)
            System.out.println(Thread.currentThread().getName() + " re-acquired WRITE lock (inside logChange)");
        try {
            auditLog.add(message);
        } finally {
            if (verbose)
                System.out.println(Thread.currentThread().getName() + " releasing WRITE lock (inside logChange)");
            rwLock.writeLock().unlock();
        }
    }

    public void printLog() {
        rwLock.readLock().lock();
        try {
            System.out.println("Audit log:");
            for (String msg : auditLog) {
                System.out.println("  " + msg);
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
