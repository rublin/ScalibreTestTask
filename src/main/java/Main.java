import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ruslan Sheremet (rublin) on 23.09.2016.
 */
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Repository repository = Repository.getInstance();
        List<Integer> listC = repository.getUnique("a", "id", "b", "id2");
        System.out.println("listC size is " + listC.size());
        //      write to table
        repository.writeTable("tableC", listC);
        long stop = System.currentTimeMillis();
        System.out.println("Table C was created. Size: " + listC.size() + ". Time: " + (stop - start) + " ms");
        repository.stopConnection();
    }
}
