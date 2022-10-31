package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WifiService {

	private static WifiService wifiService = new WifiService();

	public static WifiService getWifiService() {
		return wifiService;
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	public int insertWifiAPI() {
		int total_cnt = 0;
		String key = "6a7949777068323337396f654d6143";

		String dbUrl = "jdbc:mariadb://172.20.10.4:3306/missiondb";
		String dbUserID = "uni";
		String dbPassword = "uni";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(dbUrl, dbUserID, dbPassword);
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document f_doc = dBuilder
					.parse("http://openapi.seoul.go.kr:8088/6a7949777068323337396f654d6143/xml/TbPublicWifiInfo/1/1/");

			// 제일 첫번째(first) 태그
			f_doc.getDocumentElement().normalize();

			// 총 WIFI 갯수를 가져오기 위해(첫 번째 태그에 존재)
			NodeList f_nList = f_doc.getElementsByTagName("TbPublicWifiInfo");
			Node f_nNode = f_nList.item(0);
			Element f_eElement = (Element) f_nNode;

			total_cnt = Integer.parseInt(getTagValue("list_total_count", f_eElement));

			int first_p = 0;
			int last_p = 0;

			for (int k = 0; k <= total_cnt / 1000; k++) {
				first_p = k * 1000 + 1;
				last_p = (k + 1) * 1000;

				String url = "http://openapi.seoul.go.kr:8088/" + key + "/xml/TbPublicWifiInfo/" + first_p + "/"
						+ last_p + "/";
				Document doc = dBuilder.parse(url);

				// 파싱할 tag
				NodeList nList = doc.getElementsByTagName("row");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					Element eElement = (Element) nNode;
					String sql = " INSERT INTO WIFI_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

					preparedStatement = connection.prepareStatement(sql);

					preparedStatement.setString(1, getTagValue("X_SWIFI_MGR_NO", eElement));
					preparedStatement.setString(2, getTagValue("X_SWIFI_WRDOFC", eElement));
					preparedStatement.setString(3, getTagValue("X_SWIFI_MAIN_NM", eElement));
					if (null != getTagValue("X_SWIFI_ADRES1", eElement)) {
						preparedStatement.setString(4, getTagValue("X_SWIFI_ADRES1", eElement));	
					} else {
						preparedStatement.setString(4, " ");
					}
					if (null != getTagValue("X_SWIFI_ADRES2", eElement)) {
						preparedStatement.setString(5, getTagValue("X_SWIFI_ADRES2", eElement));
					} else {
						preparedStatement.setString(5, " ");
					}
					if (null != getTagValue("X_SWIFI_INSTL_FLOOR", eElement)) {
						preparedStatement.setString(6, getTagValue("X_SWIFI_INSTL_FLOOR", eElement));
					} else {
						preparedStatement.setString(6, " ");
					}
					preparedStatement.setString(7, getTagValue("X_SWIFI_INSTL_TY", eElement));
					preparedStatement.setString(8, getTagValue("X_SWIFI_INSTL_MBY", eElement));
					preparedStatement.setString(9, getTagValue("X_SWIFI_SVC_SE", eElement));
					if (null != getTagValue("X_SWIFI_CMCWR", eElement)) {
						preparedStatement.setString(10, getTagValue("X_SWIFI_CMCWR", eElement));
					} else {
						preparedStatement.setString(10, " ");
					}
					if (null != getTagValue("X_SWIFI_CNSTC_YEAR", eElement)) {
						preparedStatement.setString(11, getTagValue("X_SWIFI_CNSTC_YEAR", eElement));
					} else {
						preparedStatement.setString(11, " ");
					}
					preparedStatement.setString(12, getTagValue("X_SWIFI_INOUT_DOOR", eElement));
					if (null != getTagValue("X_SWIFI_REMARS3", eElement)) {
						preparedStatement.setString(13, getTagValue("X_SWIFI_REMARS3", eElement));
					} else {
						preparedStatement.setString(13, " ");
					}
					preparedStatement.setString(14, getTagValue("LAT", eElement));
					preparedStatement.setString(15, getTagValue("LNT", eElement));
					preparedStatement.setString(16, getTagValue("WORK_DTTM", eElement));

					rs = preparedStatement.executeQuery();
				}
			}
		} catch (Exception e) {

		}

		return total_cnt;
	}

	public List<Wifi> selectWifiList(String LAT, String LNT) {
		List<Wifi> result = new ArrayList<Wifi>();
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

			String sql = " SELECT *, ROUND((6371 * acos(cos(radians(?))* cos(radians(LAT))* cos(radians(LNT)-radians(?))+sin(radians(?))*sin(radians(LAT)))), 4) AS DISTANCE "
					+ " FROM wifi_info " 
					+ " ORDER BY DISTANCE ASC " 
					+ " LIMIT 20 ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, Double.parseDouble(LAT));
			preparedStatement.setDouble(2, Double.parseDouble(LNT));
			preparedStatement.setDouble(3, Double.parseDouble(LAT));
			System.out.println(LAT);
			System.out.println(LNT);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Wifi wifi = new Wifi();

				wifi.setDISTANCE(rs.getString("DISTANCE"));
				wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
				wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
				wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
				wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
				wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
				wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
				wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
				wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
				wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
				wifi.setLAT(rs.getString("LAT"));
				wifi.setLNT(rs.getString("LNT"));
				wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));
				result.add(wifi);
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

}
