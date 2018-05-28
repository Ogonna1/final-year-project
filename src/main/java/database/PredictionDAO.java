package database;

import models.PredictionDTO;
import models.UserDTO;
import table.PredictionTableData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.PredictionDAO.Contract.*;
import static database.PredictionDAO.Contract.TABLE_NAME;

public class PredictionDAO extends SQLiteDB{

    public static PredictionDAO predictionDAO;

    public static PredictionDAO getInstance(){
        if(predictionDAO == null)
            predictionDAO = new PredictionDAO();

        return predictionDAO;
    }

    private PredictionDAO(){
        createTable();
    }

    private void createTable(){
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n"
                    + Contract.COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT,\n"
                    + COLUMN_PREDICTION_ID + " text NOT NULL,\n"
                    + Contract.COLUMN_AGE_OF_PIG + " integer NOT NULL,\n"
                    + Contract.COLUMN_DAYS + " integer NOT NULL,\n"
                    + Contract.COLUMN_USER_ID + " text NOT NULL,\n"
                    + Contract.COLUMN_HEALTH + " text NOT NULL,\n"
                    + Contract.COLUMN_CARBOHYDRATE + " integer NOT NULL,\n"
                    + Contract.COLUMN_PROTEIN + " double NOT NULL,\n"
                    + Contract.COLUMN_FAT_AND_OIL + " double NOT NULL,\n"
                    + Contract.COLUMN_VITAMIN_AND_MINERAL + " integer NOT NULL,\n"
                    + Contract.COLUMN_WATER + " double NOT NULL,\n"
                    + Contract.COULMN_DATE_ADDED + " text NOT NULL,\n"
                    + Contract.COLUMN_DATE_MODIFIED + " text NOT NULL,\n"
                    + Contract.COLUMN_PREDICTION + " double NOT NULL\n"
                    + ");";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement()){
            // create a new table
            stmt.execute(sql);


        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public void addPrediction(String userId, PredictionDTO pred){

        String sql = "INSERT INTO " +
                TABLE_NAME + "(" +
                COLUMN_PREDICTION_ID + "," +
                COLUMN_USER_ID + "," +
                COLUMN_AGE_OF_PIG + "," +
                COLUMN_DAYS + "," +
                COLUMN_HEALTH + "," +
                COLUMN_CARBOHYDRATE + "," +
                COLUMN_PROTEIN + "," +
                COLUMN_FAT_AND_OIL + "," +
                COLUMN_VITAMIN_AND_MINERAL + "," +
                COLUMN_WATER + "," +
                COULMN_DATE_ADDED + "," +
                COLUMN_DATE_MODIFIED + "," +
                COLUMN_PREDICTION + ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pred.getId());
            pstmt.setString(2, userId);
            pstmt.setInt(3, pred.getAge());
            pstmt.setInt(4, pred.getDays());
            pstmt.setString(5, pred.getHealth());
            pstmt.setDouble(6, pred.getCarbo());
            pstmt.setDouble(7, pred.getProtein());
            pstmt.setDouble(8, pred.getFat());
            pstmt.setDouble(9, pred.getVitamin());
            pstmt.setDouble(10, pred.getWater());
            pstmt.setString(11, pred.getDateAdded());
            pstmt.setString(12, pred.getDateModified());
            pstmt.setDouble(13, pred.getPrediction());

            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<PredictionDTO> getAllPredictions(String userId){

        String sql = "SELECT " +
                COLUMN_PREDICTION_ID + ", " +
                COLUMN_AGE_OF_PIG + ", " +
                COLUMN_DAYS + ", " +
                COLUMN_HEALTH + ", " +
                COLUMN_CARBOHYDRATE + ", " +
                COLUMN_PROTEIN + ", " +
                COLUMN_FAT_AND_OIL + ", " +
                COLUMN_VITAMIN_AND_MINERAL + ", " +
                COLUMN_WATER + ", " +
                COULMN_DATE_ADDED + ", " +
                COLUMN_DATE_MODIFIED + ", " +
                COLUMN_PREDICTION +
                " FROM " + TABLE_NAME +
                " WHERE '" + userId + "'=" + COLUMN_USER_ID;

//        System.out.println(sql);


        List<PredictionDTO> predictions = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            PredictionDTO pred = null;
            // loop through the result set
            while (rs.next()) {
                pred = new PredictionDTO();

                pred.setId(rs.getString(COLUMN_PREDICTION_ID));
                pred.setAge(rs.getInt(COLUMN_AGE_OF_PIG));
                pred.setDays(rs.getInt(COLUMN_DAYS));
                pred.setHealth(rs.getString(COLUMN_HEALTH));
                pred.setCarbo(rs.getDouble(COLUMN_CARBOHYDRATE));
                pred.setProtein(rs.getDouble(COLUMN_PROTEIN));
                pred.setFat(rs.getDouble(COLUMN_FAT_AND_OIL));
                pred.setVitamin(rs.getDouble(COLUMN_VITAMIN_AND_MINERAL));
                pred.setWater(rs.getDouble(COLUMN_WATER));
                pred.setDateAdded(rs.getString(COULMN_DATE_ADDED));
                pred.setDateModified(rs.getString(COLUMN_DATE_MODIFIED));
                pred.setPrediction(rs.getDouble(COLUMN_PREDICTION));

                predictions.add(pred);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return predictions;
    }

    public PredictionDTO getPrediction(String userId, String predictionId){

        String sql = "SELECT " +
                COLUMN_PREDICTION_ID + ", " +
                COLUMN_AGE_OF_PIG + ", " +
                COLUMN_DAYS + ", " +
                COLUMN_HEALTH + ", " +
                COLUMN_CARBOHYDRATE + ", " +
                COLUMN_PROTEIN + ", " +
                COLUMN_FAT_AND_OIL + ", " +
                COLUMN_VITAMIN_AND_MINERAL + ", " +
                COLUMN_WATER + ", " +
                COULMN_DATE_ADDED + ", " +
                COLUMN_DATE_MODIFIED + ", " +
                COLUMN_PREDICTION +
                " FROM " + TABLE_NAME +
                " WHERE '" + userId + "'=" + COLUMN_USER_ID;

//        System.out.println(sql);


        PredictionDTO pred = null;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            if (rs.next()) {
                pred = new PredictionDTO();

                pred.setId(rs.getString(COLUMN_PREDICTION_ID));
                pred.setAge(rs.getInt(COLUMN_AGE_OF_PIG));
                pred.setDays(rs.getInt(COLUMN_DAYS));
                pred.setHealth(rs.getString(COLUMN_HEALTH));
                pred.setCarbo(rs.getDouble(COLUMN_CARBOHYDRATE));
                pred.setProtein(rs.getDouble(COLUMN_PROTEIN));
                pred.setFat(rs.getDouble(COLUMN_FAT_AND_OIL));
                pred.setVitamin(rs.getDouble(COLUMN_VITAMIN_AND_MINERAL));
                pred.setWater(rs.getDouble(COLUMN_WATER));
                pred.setDateAdded(rs.getString(COULMN_DATE_ADDED));
                pred.setDateModified(rs.getString(COLUMN_DATE_MODIFIED));
                pred.setPrediction(rs.getDouble(COLUMN_PREDICTION));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return pred;
    }

    public void updateCalculation(String userId, PredictionDTO pred){

        /**
         *  String sql = "UPDATE warehouses SET name = ? , "
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

        String sql = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_AGE_OF_PIG + " = ? ," +
                COLUMN_DAYS + " = ? ," +
                COLUMN_HEALTH + " = ? ," +
                COLUMN_CARBOHYDRATE + " = ? ," +
                COLUMN_PROTEIN + " = ? ," +
                COLUMN_FAT_AND_OIL + " = ? ," +
                COLUMN_VITAMIN_AND_MINERAL + " = ? ," +
                COLUMN_WATER + " = ? ," +
                COLUMN_DATE_MODIFIED + " = ? ," +
                COLUMN_PREDICTION + " = ? " +
                " WHERE '" + userId + "'=" + COLUMN_USER_ID +
                " AND '" + pred.getId() + "'=" + COLUMN_PREDICTION_ID;

//        System.out.println(sql);


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pred.getAge());
            pstmt.setInt(2, pred.getDays());
            pstmt.setString(3, pred.getHealth());
            pstmt.setDouble(4, pred.getCarbo());
            pstmt.setDouble(5, pred.getProtein());
            pstmt.setDouble(6, pred.getFat());
            pstmt.setDouble(7, pred.getVitamin());
            pstmt.setDouble(8, pred.getWater());
            pstmt.setString(9, pred.getDateModified());
            pstmt.setDouble(10, pred.getPrediction());

            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeCalculation(String userId, PredictionDTO pred){

        String sql = "DELETE FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_ID + " = ?" +
                " AND " + COLUMN_PREDICTION_ID + " = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, userId);
            pstmt.setString(2, pred.getId());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    class Contract{
        public static final String TABLE_NAME = "predictions";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PREDICTION_ID = "prediction_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_AGE_OF_PIG = "age_of_pig";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_HEALTH = "health";
        public static final String COLUMN_CARBOHYDRATE = "carbohydrate";
        public static final String COLUMN_PROTEIN = "protein";
        public static final String COLUMN_FAT_AND_OIL = "fat_and_oil";
        public static final String COLUMN_VITAMIN_AND_MINERAL = "vitamin_and_mineral";
        public static final String COLUMN_WATER = "water";
        public static final String COLUMN_PREDICTION = "prediction";
        public static final String COULMN_DATE_ADDED = "date_added";
        public static final String COLUMN_DATE_MODIFIED = "date_modified";
    }
}
