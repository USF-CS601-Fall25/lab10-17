package solution.concurrentlibrary;

import java.util.concurrent.CountDownLatch;

/**
 * Demonstrates basic thread coordination using CountDownLatch.
 * Customers (threads) must wait until the main thread "opens" the library.
 *
 * Fill in code and discuss what happens at each step.
 */
public class LibraryDemo {

    public static void runLibrary() throws InterruptedException {
        System.out.println("Main thread: preparing to open the library...");

        ConcurrentLibraryCatalog catalog = new ConcurrentLibraryCatalog();
        System.out.println("Main thread: stocking shelves with new books...");
        Thread.sleep(400);
        catalog.setVerbose(false); // print less, since multithreading has not started yet
        catalog.addBook("Dune", "Herbert");
        catalog.addBook("1984", "Orwell");
        catalog.addBook("Foundation", "Asimov");
        catalog.setVerbose(true);  // prepare to print more now that we will create other threads

        // Initially, count = 1 for CountDownLatch because the library is closed.
        // Once the main thread calls countDown(), the count reaches 0, and patrons can enter.
        CountDownLatch openLatch = new CountDownLatch(1);
        int numCustomers = 3;

        // Create customers (threads) that wait for the latch signal (to say that the library is open).
        for (int i = 1; i <= numCustomers; i++) {
            // TODO: Create a new Thread  (give it a name: Customer + i) that:
            //   - Prints a message that it is waiting for the library to open.
            //   - Calls openLatch.await() to wait until the main thread opens the library.
            //   - Once unblocked, prints that it has entered the library.
            //   - Prints available books and attempts to checkout "1984".
            //   - Handle InterruptedException properly.
            // TODO: start the thread
            Thread t = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting outside for library to open...");
                    openLatch.await(); // each customer thread waits until the library opens (until openLatch is 0)
                    System.out.println(Thread.currentThread().getName() + " entered the library.");
                    catalog.printAvailableBooks();
                    catalog.checkoutBook("1984");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Customer-" + i);
            t.start();
        }

        // Main thread prepares to open the library
        System.out.println("Main thread: turning on lights...");
        Thread.sleep(400);
        System.out.println("Main thread: unlocking doors...");
        Thread.sleep(400);
        System.out.println("Main thread: placing 'OPEN' sign on the door...");
        Thread.sleep(400);

        System.out.println(System.lineSeparator() + "Library is now open! Customers may enter." + System.lineSeparator());
        openLatch.countDown(); // Open the library (signal all threads who were waiting)
    }


    public static void main(String[] args) {
        try {
            runLibrary();
        }
        catch(InterruptedException e) {
            System.out.println(e);
        }
    }

}
