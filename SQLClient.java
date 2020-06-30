import java.sql.*;

public class SQLClient {
    public static void main(String[] args) throws Exception {

        Connection conn = null;
        String mysql_connection = System.getenv("MYSQL_CONNECTION");
        String mysql_username = System.getenv("MYSQL_USER");
        String mysql_password = System.getenv("MYSQL_PASSWORD");
        {
          if(mysql_password == null || mysql_username == null || mysql_password == null || args.length != 2)
          {
              System.out.println("Missing parameters or connection info \n");
              System.out.println("Usage: java SQLClient.java <database> '<SQL QUERY>'");
              System.out.println("Example command: java SQLClient.java test_db 'show tables;'\n");
              System.out.println("Following environment variables with MYSQL connection info may be missing:");
              System.out.println("export MYSQL_CONNECTION='jdbc:mariadb://127.0.0.1:3306'");
              System.out.println("export MYSQL_USER='root'");
              System.out.println("export MYSQL_PASSWORD='secret'");
              System.exit(0);
          }
        }
        // Class.forName("org.mariadb.jdbc.Driver");
        String url = mysql_connection + "/" + args[0];
        conn = DriverManager.getConnection(url, mysql_username, mysql_password);

        Statement stmt = null;
        ResultSet resultSet = null;
        try{
              stmt = (Statement) conn.createStatement();
              resultSet = stmt.executeQuery(args[1]); 
              ResultSetMetaData rsmd = resultSet.getMetaData();
              int columnsNumber = rsmd.getColumnCount();
              while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print(",  ");
                                  String columnValue = resultSet.getString(i);
                                  System.out.print("\nColumn : " + rsmd.getColumnName(i) + "\nValue: " + columnValue);
                    }
                    System.out.println("");
              }
        } catch(SQLException e){
              e.printStackTrace();
            }
            finally{
              try{
                if(stmt != null) stmt.close();
                if(resultSet != null) resultSet.close();
                if(conn != null) conn.close();
              } catch(SQLException e2){
                e2.printStackTrace();
              }
            }
    }
}
