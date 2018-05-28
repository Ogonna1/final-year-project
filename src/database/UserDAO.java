package database;

import models.UserDTO;

import java.sql.*;

import static database.UserDAO.CONTRACT.*;

public class UserDAO extends SQLiteDB{

    private static UserDAO userDAO;

    private Connection conn = null;

    public static UserDAO getInstance(){
        if(null == userDAO)
            userDAO = new UserDAO();

        return userDAO;
    }

    private UserDAO(){
        createTable();
    }

    private void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + CONTRACT.COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
                + CONTRACT.COLUMN_USER_ID + " text NOT NULL, "
                + CONTRACT.COLUMN_FULLNAME + " text NOT NULL, "
                + CONTRACT.COLUMN_LOGIN_NAME + " text NOT NULL, "
                + CONTRACT.COLUMN_PHONE + " text NOT NULL, "
                + CONTRACT.COLUMN_PASSWORD + " text NOT NULL "
                + ");";

//        System.out.println(sql);

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement()){
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public void addUser(UserDTO user, String password){

        String sql = "INSERT INTO " +
                TABLE_NAME + "(" +
                COLUMN_USER_ID + "," +
                COLUMN_FULLNAME + "," +
                COLUMN_LOGIN_NAME + "," +
                COLUMN_PHONE + "," +
                COLUMN_PASSWORD + ") " +
                "VALUES(?, ?, ?, ?, ?)";

//        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getLoginName());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, password);

            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    public UserDTO getUser(String loginName, String password){

        String sql = "SELECT " +
                CONTRACT.COLUMN_USER_ID + ", " +
                CONTRACT.COLUMN_FULLNAME + ", " +
                CONTRACT.COLUMN_LOGIN_NAME + ", " +
                CONTRACT.COLUMN_PHONE +
                " FROM " + TABLE_NAME +
                " WHERE '" + loginName + "'=" + CONTRACT.COLUMN_LOGIN_NAME +
                " AND '" + password + "'=" + CONTRACT.COLUMN_PASSWORD;

//        System.out.println(sql);

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            if (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getString(CONTRACT.COLUMN_USER_ID),
                        rs.getString(CONTRACT.COLUMN_FULLNAME),
                        rs.getString(CONTRACT.COLUMN_LOGIN_NAME),
                        rs.getString(CONTRACT.COLUMN_PHONE)
                );

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void updateUser(){
        /*
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/
    }


    class CONTRACT{
        public static final String TABLE_NAME = "user_table";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_FULLNAME = "fullname";
        public static final String COLUMN_LOGIN_NAME = "loginname";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PASSWORD = "password";
//        public static final String
    }
}
