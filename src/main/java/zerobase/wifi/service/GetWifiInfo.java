package zerobase.wifi.service;

import test.OpenApi;
import com.google.gson.Gson;
import zerobase.wifi.dto.OpenApiResponse;
import zerobase.wifi.dto.Row;
import zerobase.wifi.model.HistoryModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetWifiInfo {

    public static int saveDateToSQLite() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        Statement statement = null;
        ResultSet rs = null;

        try {
            // SQLiteConnection 클래스 인스턴스 생성
            SqliteConnection sqliteConnection = new SqliteConnection();

            // SQLite 연결
            connection = sqliteConnection.getConnect();

            // OpenApi의 Json 데이터 가져오기
            String json1 = OpenApi.get(1, 1);

            // Gson을 사용해 JSON 파싱
            Gson gson = new Gson();
            // Json 문자열을 Java 객체로 변환

            OpenApiResponse response1 = gson.fromJson(json1, OpenApiResponse.class);

            int total = response1.getTbPublicWifiInfo().getList_total_count();

            List<Row> allRows = new ArrayList<>();
            for (int page = 0; page <= total / 1000; page++) {
                String json = OpenApi.get((1000 * page) + 1, (1000 * page) + 1000);
                OpenApiResponse response = gson.fromJson(json, OpenApiResponse.class);
                Row[] currentPageRows = response.getTbPublicWifiInfo().getRow();
                allRows.addAll(Arrays.asList(currentPageRows));
            }


            // SQLite에 데이터 삽입
            String insertSQL = "insert into LOCATION " +
                    "(management_id, region, wifi_name, address, address2, floor, type, organ, service, network, year, indoor_yn, access_en, lat, lnt, regist_date) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

            preparedStatement = connection.prepareStatement(insertSQL);


            int insertedRowCount = 0;
            for (Row row : allRows) {
                preparedStatement.setString(1, row.getX_SWIFI_MGR_NO());
                preparedStatement.setString(2, row.getX_SWIFI_WRDOFC());
                preparedStatement.setString(3, row.getX_SWIFI_MAIN_NM());
                preparedStatement.setString(4, row.getX_SWIFI_ADRES1());
                preparedStatement.setString(5, row.getX_SWIFI_ADRES2());
                preparedStatement.setString(6, row.getX_SWIFI_INSTL_FLOOR());
                preparedStatement.setString(7, row.getX_SWIFI_INSTL_TY());
                preparedStatement.setString(8, row.getX_SWIFI_INSTL_MBY());
                preparedStatement.setString(9, row.getX_SWIFI_SVC_SE());
                preparedStatement.setString(10, row.getX_SWIFI_CMCWR());
                preparedStatement.setString(11, row.getX_SWIFI_CNSTC_YEAR());
                preparedStatement.setString(12, row.getX_SWIFI_INOUT_DOOR());
                preparedStatement.setString(13, row.getX_SWIFI_REMARS3());
                preparedStatement.setString(14, row.getLAT());
                preparedStatement.setString(15, row.getLNT());
                preparedStatement.setString(16, row.getWORK_DTTM());

                // 실행
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    insertedRowCount++;
                }
            }

            return insertedRowCount;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static List<Row> getDateFromSQLite(Double lat, Double lnt) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        List<Row> nearbyWifiInfo = new ArrayList<>();

        try {
            // SQLiteConnection 클래스 인스턴스 생성
            SqliteConnection sqliteConnection = new SqliteConnection();

            // SQLite 연결
            connection = sqliteConnection.getConnect();

            String query = "SELECT *, " +
                    "(6371 * acos(cos(radians(?)) * cos(radians(lat)) * cos(radians(lnt) - radians(?)) + " +
                    "sin(radians(?)) * sin(radians(lat)))) AS distance " +
                    "FROM LOCATION " +
                    "ORDER BY distance " +
                    "LIMIT 20";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setDouble(3, lat);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Row row = new Row();
                row.setX_SWIFI_MGR_NO(rs.getString("management_id"));
                row.setX_SWIFI_WRDOFC(rs.getString("region"));
                row.setX_SWIFI_MAIN_NM(rs.getString("wifi_name"));
                row.setX_SWIFI_ADRES1(rs.getString("address"));
                row.setX_SWIFI_ADRES2(rs.getString("address2"));
                row.setX_SWIFI_INSTL_FLOOR(rs.getString("floor"));
                row.setX_SWIFI_INSTL_TY(rs.getString("type"));
                row.setX_SWIFI_INSTL_MBY(rs.getString("organ"));
                row.setX_SWIFI_SVC_SE(rs.getString("service"));
                row.setX_SWIFI_CMCWR(rs.getString("network"));
                row.setX_SWIFI_CNSTC_YEAR(rs.getString("year"));
                row.setX_SWIFI_INOUT_DOOR(rs.getString("indoor_yn"));
                row.setX_SWIFI_REMARS3(rs.getString("access_en"));
                row.setLAT(String.valueOf(rs.getDouble("lat")));
                row.setLNT(String.valueOf(rs.getDouble("lnt")));
                row.setWORK_DTTM(rs.getString("regist_date"));

                double distance = Math.round(rs.getDouble("distance") * 1e5) / 1e5;
                row.setDistance(distance);

                nearbyWifiInfo.add(row);

            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return nearbyWifiInfo;
    }

    public static void saveHistory(HistoryModel history) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        Statement statement = null;
        ResultSet rs = null;

        List<HistoryModel> savedHistory = new ArrayList<>();

        try {
            // SQLiteConnection 클래스 인스턴스 생성
            SqliteConnection sqliteConnection = new SqliteConnection();

            // SQLite 연결
            connection = sqliteConnection.getConnect();

            // SQLite에 데이터 삽입
            String insertSQL = "insert into HISTORY " +
                    "(LAT, LNT, HIS_DT) " +
                    "VALUES " +
                    "(?, ?, datetime()) ";

            preparedStatement = connection.prepareStatement(insertSQL);


            preparedStatement.setDouble(1, history.getLAT());
            preparedStatement.setDouble(2, history.getLNT());

            
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("history saved");

            } else {
                System.out.println("history not saved");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<HistoryModel> getHistoryList() {
        List<HistoryModel> historyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {

            // SQLiteConnection 클래스 인스턴스 생성
            SqliteConnection sqliteConnection = new SqliteConnection();

            // SQLite 연결
            connection = sqliteConnection.getConnect();

            // 저장된 history 데이터를 리스트에 담아서 반환
            String selectSQL = "select * from HISTORY";
            preparedStatement = connection.prepareStatement(selectSQL);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                HistoryModel saved = new HistoryModel();
                saved.setHIS_ID(rs.getInt("HIS_ID"));
                saved.setLAT(rs.getDouble("LAT"));
                saved.setLNT(rs.getDouble("LNT"));
                saved.setHIS_DT(rs.getString("HIS_DT"));

                historyList.add(saved);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return historyList;
    }

    public static void deleteHistory(int HIS_ID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            SqliteConnection sqliteConnection = new SqliteConnection();

            connection = sqliteConnection.getConnect();

            String query = "delete from HISTORY where HIS_ID = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, HIS_ID);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("history delete");
            } else {
                System.out.println("history not deleted");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
