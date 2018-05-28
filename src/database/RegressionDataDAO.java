package database;

import models.RegressionDataDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.RegressionDataDAO.Contract.*;

public class RegressionDataDAO extends SQLiteDB{

    private static RegressionDataDAO regressionDataDAO;

    public static RegressionDataDAO getInstance(){
        if(regressionDataDAO == null)
            regressionDataDAO = new RegressionDataDAO();

        return regressionDataDAO;
    }

    private RegressionDataDAO(){
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n"
                + Contract.COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT,\n"
                + Contract.COLUMN_DATA_ID + " text NOT NULL,\n"
                + Contract.COLUMN_WEIGHT + " double NOT NULL,\n"
                + Contract.COLUMN_DAILY_GAIN + " double NOT NULL,\n"
                + Contract.COLUMN_FEED_CON + " double NOT NULL,\n"
                + Contract.COLUMN_WATER_CON + " double NOT NULL,\n"
                + Contract.COLUMN_AGE + " int NOT NULL,\n"
                + Contract.COLUMN_USER_ID + " text NOT NULL\n"
                + ");";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement()){
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public void addData(String user, RegressionDataDTO data){
        String sql = "INSERT INTO " +
                TABLE_NAME + "(" +
                COLUMN_DATA_ID + "," +
                COLUMN_WEIGHT + "," +
                COLUMN_DAILY_GAIN + "," +
                COLUMN_FEED_CON + "," +
                COLUMN_WATER_CON + ", " +
                COLUMN_AGE + ", " +
                COLUMN_USER_ID + ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

//        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data.getId());
            pstmt.setDouble(2, data.getWeight());
            pstmt.setDouble(3, data.getDailyGain());
            pstmt.setDouble(4, data.getDailyFeed());
            pstmt.setDouble(5, data.getWater());
            pstmt.setInt(6, data.getAge());
            pstmt.setString(7, user);

            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<RegressionDataDTO> getAllData(String user){

        String sql = "SELECT " +
                COLUMN_DATA_ID + ", " +
                COLUMN_WEIGHT + ", " +
                COLUMN_DAILY_GAIN + ", " +
                COLUMN_FEED_CON + ", " +
                COLUMN_WATER_CON + ", " +
                COLUMN_AGE +
                " FROM " + TABLE_NAME +
                " WHERE '" + user + "'=" + COLUMN_USER_ID;

        System.out.println(sql);

        List<RegressionDataDTO> dataList = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            RegressionDataDTO data = null;
            // loop through the result set
            while (rs.next()) {
                data = new RegressionDataDTO();

                data.setId(rs.getString(COLUMN_DATA_ID));
                data.setWeight(rs.getDouble(COLUMN_WEIGHT));
                data.setDailyGain(rs.getDouble(COLUMN_DAILY_GAIN));
                data.setDailyFeed(rs.getDouble(COLUMN_FEED_CON));
                data.setWater(rs.getDouble(COLUMN_WATER_CON));
                data.setAge(rs.getInt(COLUMN_AGE));

                dataList.add(data);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return dataList;
    }

    public void deleteData(String user, String dataId){
         String sql = "DELETE FROM " + TABLE_NAME +
                 " WHERE " + COLUMN_USER_ID + " = ?" +
                 " AND " + COLUMN_DATA_ID + " = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, user);
            pstmt.setString(2, dataId);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    class Contract{
        public static final String TABLE_NAME = "regression_data_table";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATA_ID = "data_id";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_DAILY_GAIN = "daily_gain";
        public static final String COLUMN_FEED_CON = "feed_consumption";
        public static final String COLUMN_WATER_CON = "water_consumption";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_USER_ID = "user_id";
    }
}
