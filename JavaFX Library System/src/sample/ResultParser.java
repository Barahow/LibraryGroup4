package sample;


import java.sql.ResultSet;
import java.util.ArrayList;

@FunctionalInterface
public interface ResultParser {

    /*
     * Takes a MySQL resultSet, creates objects and puts then into an ArrayList.
     * The one who implements "ResultParser" knows the object class and how to parse columns into fields.
     */
    void parse(ResultSet resultSet, ArrayList collection);
}
