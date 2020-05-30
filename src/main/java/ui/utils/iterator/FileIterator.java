package ui.utils.iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileIterator implements Iterator {
    private Scanner scanner;

    public FileIterator(String path){
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.err.println("File not found. Abort execution.");
            System.exit(-1);
        }
    }
    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        return scanner.nextLine();
    }

    @Override
    public void onTermination(){
        scanner.close();
    }
}
