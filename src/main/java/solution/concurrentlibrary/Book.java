package solution.concurrentlibrary;

/** A class representing a book */
public class Book {
    private String title;
    private String author;
    private boolean isCheckedOut;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isCheckedOut = false;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", isAvailable? " + !isCheckedOut;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** If the book is available (not checked out), then
     *  set isCheckout to true and return true,
     *  otherwise return false
     * @return true if we could check out the book, and false otherwise
     */
    public boolean checkout() {
        // FILL IN CODE
        if (!isCheckedOut) {
            isCheckedOut = true;
            return true;
        }
        return false;
    }

    /**
     * If the book is checked out, return it by setting isCheckedOut to false
     * and return true;
     * Otherwise, return false, because the book could not be returned
     * @return true if the book could be returned, and false otherwise
     */
    public boolean returnToLibrary() {
        // FILL IN CODE
        if (isCheckedOut) {
            isCheckedOut = false;
            return true;
        }
        return false;
    }
}

