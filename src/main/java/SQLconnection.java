import java.sql.*;

public class SQLconnection {

    private final String databaseName = "your_database_name";
    private final String serverName = "your_server_name";
    private final String username = "sa";
    private final String password = "123456";
    private final String connectionUrl = "jdbc:sqlserver://NAMIDI-LAB\\MSSQLSERVER;databaseName=SrananFowru";
    private final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private Statement statement;

    public SQLconnection () throws Exception {

            Class.forName(driver);
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            Statement statement = connection.createStatement();
            this.statement = statement;
            System.out.println("Connection successful!");
            // Perform database operations here...
           // connection.close();
    }

    public void insertRow (String writeString) throws Exception{

        statement.executeQuery ("INSERT INTO dbo.tblWeegschaal(Weegschaaldata) VALUES ('"+writeString+"');" );
        System.out.println("Written to SrananFowru DB =" + writeString);
    }

    public void readRow () throws Exception {

        ResultSet rs = statement.executeQuery("SELECT TOP 1 * FROM dbo.tblWeegschaal");
        while (rs.next()){
            String weegschaaldata = rs.getString("Weegschaaldata");
            System.out.println("Connection established with SrananFowru DB: " + weegschaaldata);
        }
    }


}
