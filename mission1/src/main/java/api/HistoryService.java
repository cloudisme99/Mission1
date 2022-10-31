package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

	private static HistoryService historyService = new HistoryService();

	public static HistoryService getHistoryService() {
		return historyService;
	}

	public void insertHistory(String LAT, String LNT) {

		String url = "jdbc:mariadb://172.20.10.4:3306/missiondb";
		String dbUserID = "uni";
		String dbPassword = "uni";

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connection = DriverManager.getConnection(url, dbUserID, dbPassword);

			String sql = " INSERT INTO SEARCH_HISTORY (LAT, LNT, SEARCH_DT) " 
						+ " VALUES  (?, ?, now()) ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, Double.parseDouble(LAT));
			preparedStatement.setDouble(2, Double.parseDouble(LNT));

			rs = preparedStatement.executeQuery();

		} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if(rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

	public List<History> selectHistoryList() {
		List<History> result = new ArrayList<History>();
		String url = "jdbc:mariadb://172.20.10.4:3306/missiondb";
		String dbUserID = "uni";
		String dbPassword = "uni";

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connection = DriverManager.getConnection(url, dbUserID, dbPassword);

			String sql = " SELECT HISTORY_ID, LAT, LNT, DATE_FORMAT(SEARCH_DT, '%Y-%m-%dT%T') as TIME "
					+ " FROM SEARCH_HISTORY ORDER BY HISTORY_ID DESC ";

			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				History history = new History();

				history.setHISTORY_ID(rs.getInt("HISTORY_ID"));
				history.setLAT(rs.getDouble("LAT"));
				history.setLNT(rs.getDouble("LNT"));
				history.setTime(rs.getString("TIME"));
				result.add(history);
			}

		} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if(rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

		return result;

	}

	public void deleteHistory(String HISTORY_ID) {

		String url = "jdbc:mariadb://172.20.10.4:3306/missiondb";
		String dbUserID = "uni";
		String dbPassword = "uni";

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connection = DriverManager.getConnection(url, dbUserID, dbPassword);

			String sql = " DELETE FROM SEARCH_HISTORY WHERE HISTORY_ID = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(HISTORY_ID));

			rs = preparedStatement.executeQuery();

		} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if(rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}