import java.sql.Connection;
import java.sql.DriverManager;

public class OracleConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "C##outven";
        String password = "m1234";

        long startTime = System.currentTimeMillis();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            long endTime = System.currentTimeMillis();
            System.out.println("✅ Oracle DB 연결 성공! 연결 시간: " + (endTime - startTime) + "ms");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
