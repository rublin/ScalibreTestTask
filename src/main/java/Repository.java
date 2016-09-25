import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ruslan Sheremet (rublin) on 25.09.2016.
 */
public class Repository {
    private static Repository ourInstance = new Repository();
    public static Repository getInstance() {
        return ourInstance;
    }
    private static final String LOGIN = "puser";
    private static final String PASSWORD = "P@ssw0rd";
    private static final String URL = "jdbc:postgresql://localhost:5432/topjava";
    private Connection connection;
    private Statement statement;

    private Repository() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL ,LOGIN , PASSWORD);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Map<Integer, String> readTable(String query, String pk) {
        Map<Integer, String> result = new HashMap<>();

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(pk);
                String word = resultSet.getString("word");
                result.put(id, word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void writeTable(String table, List<Integer> list) {
        try {
            statement.executeUpdate(String.format("DROP TABLE IF EXISTS %s;", table));
            statement.executeUpdate(String.format("CREATE TABLE %s (  id INTEGER NOT NULL );", table));
            for (int id:list) {
                statement.addBatch(String.format("INSERT INTO %s (id) VALUES (%d);", table, id));
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getUnique(String tableA, String idA, String tableB, String idB) {
        //      read table A
        Map<Integer, String> mapA = readTable(String.format("SELECT * FROM %s;", tableA), idA);
        Set<String> setA = new HashSet<>(mapA.values());
        System.out.println("Table A was read. Size: " + mapA.size());
        //      read table B
        Map<Integer, String> mapB = readTable(String.format("SELECT * FROM %s;", tableB), idB);
        Set<String> setB = new HashSet<>(mapB.values());
        System.out.println("Table B was read. Size: " + mapB.size());
        //      create list with unique id
        return Stream.concat(
                filter(mapA, setB).stream(),
                filter(mapB, setA).stream())
                .sorted()
                .collect(Collectors.toList());
    }
    private static List<Integer> filter(Map<Integer, String> map, Set<String> set) {
        return map.entrySet().stream()
                .filter(entry -> !set.contains(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
