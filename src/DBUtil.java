import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DBUtil {

    private Connection con;

    public DBUtil(String host, String username, String password) throws SQLException {
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

    public ArrayList<Emp> getEmpsWithImages() {
        Statement statement = null;
        try {
            statement = con.createStatement();
            ResultSet result = statement.executeQuery("select id, name, dob, mgr, deptno, sal, location, image from emp");

            ArrayList<Emp> emps = new ArrayList<>();

            while (result.next()) {
                String filePath = null;
                Blob imageBlob = result.getBlob(8);
                if(imageBlob != null) {
                    InputStream in = imageBlob.getBinaryStream();
                    String fileName = result.getInt(1) + " " + result.getString(2) + "png";
                    filePath = "/home/vishal/kotlin/myworkspace/JDBCJuly2024/src/" + fileName;
                    FileOutputStream fout =
                            new FileOutputStream(
                                    filePath
                            );

                    byte[] data = new byte[1024 * 2];
                    int count;
                    while ((count = in.read(data)) != -1) {
                        fout.write(data, 0, count);
                    }
                    in.close();
                    fout.close();
                }

                emps.add(
                        new Emp(
                                result.getInt(1),
                                result.getString(2),
                                result.getString(3),
                                result.getInt(4),
                                result.getInt(5),
                                result.getInt(6),
                                result.getString(7),
                                filePath
                        )
                );
            }
            return emps;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public boolean addEmpWithImage(
            int id,
            String name,
            String dob,
            int mgr,
            int deptNo,
            int sal,
            String location,
            String imagePath
    ) {
        try {
            String query = "insert into emp(id, name, dob, mgr, deptno, sal, location, image) " +
                    "values(?,?,?,?,?,?,?,?)";

            System.out.println(query);
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, dob);
            pstmt.setInt(4, mgr);
            pstmt.setInt(5, deptNo);
            pstmt.setInt(6, sal);
            pstmt.setString(7, location);
            pstmt.setBinaryStream(8, new FileInputStream(imagePath));

            System.out.println(pstmt);
            int rowsAffected = pstmt.executeUpdate(query);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
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
                    ")";

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

            while (result.next()) {
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
            String query = "delete from emp where id = " + id;
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {

        }

        return false;
    }

    public boolean validateCredentials(String username, String password) {
        String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
        System.out.println(query);
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    public void callableDemo(int deptno) {
        String sqlStatement = "call empcount(?, ?)";
        try {
            CallableStatement callableStatement = con.prepareCall(sqlStatement);
            callableStatement.setInt(1, deptno);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();

            int count = callableStatement.getInt(2);
            System.out.println("Emps in dept: " + count);
            callableStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
