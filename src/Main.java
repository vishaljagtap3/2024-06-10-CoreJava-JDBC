import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emp_dept?useSSL=false",
                    "bitcode",
                    "Bitcode@123"
            );
            System.out.println(con.getClass().getName());

            Statement statement = con.createStatement();
            System.out.println(statement.getClass().getName());

            ResultSet result = statement.executeQuery("select id, name, sal, location from emp");

            while(result.next()){
                System.out.println(result.getInt(1) + " " + result.getString(2) + " " + result.getInt(6) + " " + result.getString(7));
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            DBUtil dbUtil = new DBUtil(
                    "jdbc:mysql://localhost:3306/emp_dept?useSSL=false",
                    "bitcode",
                    "Bitcode@123"
            );

            /*boolean isAdded =dbUtil.addEmp(
                    690690,
                    "BitCodeNew",
                    "2008-08-06",
                    333,
                    20,
                    3400,
                    "Jaipur"
            );
            System.out.println(isAdded);*/

            /*dbUtil.deleteEmp(690690);

            ArrayList<Emp> emps = dbUtil.getEmps();
            System.out.println(emps.size());
            for (Emp emp : emps) {
                System.out.println(emp);
            }*/

            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            String password = scanner.nextLine();

            boolean isLoggedIn = dbUtil.validateCredentialsNew(username, password);
            System.out.println("Is logged in: " + isLoggedIn);

            dbUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
