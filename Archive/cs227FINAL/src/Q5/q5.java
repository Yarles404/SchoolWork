package Q5;

public class Book {
    private int pages;

    @Override
    public int compareTo(Book other)
    {
        // returns negative if other has more pages than this.
        return this.pages - other.getNumPages();
    }
}
