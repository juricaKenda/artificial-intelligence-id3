package ui.utils.iterator;

public interface Iterator {
    boolean hasNext();
    String next();
    void onTermination();
}
