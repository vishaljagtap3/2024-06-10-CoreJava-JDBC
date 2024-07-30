import java.sql.*;
import java.util.ArrayList;

public class DBUtil {

    private Connection con;

    public DBUtil(String host, String  username, String password) throws SQLException{
        try {
            con = DriverManager.getConnection(host, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addEmp(
            int id,
            String name,
            String dob,
            int mgr,
            int deptNo,
            int sal,
            String location
    ) {
        try {
            String query = "insert into emp(id, name, dob, mgr, deptno, sal, location) " +
                    "values(" +
                    id + "," +
                    "'" + name + "'," +
                    "'" + dob + "'," +
                    mgr + "," +
                    deptNo + "," +
                    sal + "," +
                    "'" + location + "'" +
                    ")"
                    ;

            System.out.println(query);
            Statement statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            System.out.println("RA: " + rowsAffected);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Emp> getEmps() {
        Statement statement = null;
        try {
            statement = con.createStatement();
            ResultSet result = statement.executeQuery("select id, name, dob, mgr, deptno, sal, location from emp");

            ArrayList<Emp> emps = new ArrayList<>();

            while(result.next()){
                emps.add(
                        new Emp(
                                result.getInt(1),
                                result.getString(2),
                                result.getString(3),
                                result.getInt(4),
                                result.getInt(5),
                                result.getInt(6),
                                result.getString(7)
                        )
                );
            }
            return emps;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;



    }

    public boolean deleteEmp(int id) {
        try {
            Statement statement = con.createStatement();
            String query = "delete from emp where id = " +  id;
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException e) {

        }

        return false;
    }

    public boolean validateCredentials(String username, String password) {
        String query = "select * from users where username = '" + username +"' and password = '" + password + "'";
        System.out.println(query);
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;k
    }

    public boolean validateCredentialsNew(String username, String password) {
        String query = "select * from users where username = ? and password = ?";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            System.out.println(statement);
            ResultSet rs = statement.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addEmpNew(
            int id,
            String name,
            String dob,
            int mgr,
            int deptNo,
            int sal,
            String location
    ) {
        try {
            String query = "insert into emp(id, name, dob, mgr, deptno, sal, location) " +
                    "values(?,?,?,?,?,?,?)";

            System.out.println(query);
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, dob);
            pstmt.setInt(4, mgr);
            pstmt.setInt(5, deptNo);
            pstmt.setInt(6, sal);
            pstmt.setString(7, location);

            int rowsAffected = pstmt.executeUpdate(query);

            System.out.println("RA: " + rowsAffected);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
