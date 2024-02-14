package model;


import org.junit.jupiter.api.BeforeEach;

class LibraryTest {
    private Library newLibrary;
    private Show s1;
    private Show s2;
    private Show s3;
    private Show s4;

    @BeforeEach
    void runBefore() {
        newLibrary = new Library();
        s1 = new Show("Naturo", "shounen", 4,230, 500);
        s2 = new Show("Fruits Basket", "shoujo", 5, 30, 63);
        s3 = new Show("Promised Neverland", "thriller",4, 0, 25);

    }


}