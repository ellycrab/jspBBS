package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	//Mysql 접속부분
	public UserDAO() {
		try {
			
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPW = "1234";
			
			//드라이브에 접속할수있도록 도와주는 라이브러리
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPW);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
	}
	
	//로그인부분  ->preparedstatement 사용이유는 sqlinjection에서 해킹방지목적!
	//하나의 계정에대한 로그인시도하는 함수
	public int login(String userID,String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); 
			if(rs.next()) {//sql결과 즉,id가 있을때
				if(rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				}
				else
					return 0; //비밀번호가 틀림
			}
			return -1; //id가 없을때	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES ( ?, ?, ?, ?, ?) ";

		
		try {
			pstmt = conn.prepareStatement(SQL);//sql 구문의 instance넣어주기
			pstmt.setString(1, user.getUserID());//각각의 물음표에 들어가는것들 설정함
			pstmt.setString(2, user.getUserPassword());//각각의 물음표에 들어가는것들 설정함
			pstmt.setString(3, user.getUserName());//각각의 물음표에 들어가는것들 설정함
			pstmt.setString(4, user.getUserGender());//각각의 물음표에 들어가는것들 설정함
			pstmt.setString(5, user.getUserEmail());//각각의 물음표에 들어가는것들 설정함
			return pstmt.executeUpdate(); //해당 statement를 실행한 결과를 넣는다.
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return -1; //데이터 베이스 오류
	}
}
