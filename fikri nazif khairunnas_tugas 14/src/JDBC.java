import java.sql.*;

public interface JDBC {

    public void insert() throws SQLException;
    public void display() throws SQLException;
    public void update() throws SQLException;
    public void search() throws SQLException;
    public void delete();

    
}

