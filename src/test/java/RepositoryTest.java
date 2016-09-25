import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ruslan Sheremet (rublin) on 25.09.2016.
 */
public class RepositoryTest {

    private Repository repository = Repository.getInstance();
    private static final String PREPARE_TABLES = "DROP TABLE IF EXISTS a;\n" +
            "DROP TABLE IF EXISTS b;\n" +
            "DROP TABLE IF EXISTS c;\n" +
            "CREATE TABLE a\n" +
            "(\n" +
            "  word    VARCHAR NOT NULL ,\n" +
            "  id      INTEGER NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE b\n" +
            "(\n" +
            "  word    VARCHAR NOT NULL ,\n" +
            "  id2      INTEGER NOT NULL\n" +
            ");\n" +
            "-- insert initial data\n" +
            "INSERT INTO a (word, id) VALUES\n" +
            "  ('word1', 1000 ),\n" +
            "  ('word2', 7824),\n" +
            "  ('word3', 3874),\n" +
            "  ('word4', 3482);\n" +
            "INSERT INTO b (word, id2) VALUES\n" +
            "  ('word7', 4327 ),\n" +
            "  ('word3', 2478),\n" +
            "  ('word8', 3974),\n" +
            "  ('word4', 8234);";
    private static Integer[] result = {1000, 3974, 4327, 7824};

    @Before
    public void setUp() throws Exception {
        repository.executeQuery(PREPARE_TABLES);
    }

    @Test
    public void testGetUnique() throws Exception {
        assertArrayEquals(repository.getUnique("a", "id", "b", "id2").toArray(), result);
    }
}