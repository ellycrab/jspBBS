package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	//Mysql ���Ӻκ�
	public UserDAO() {
		try {
			
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPW = "1234";
			
			//����̺꿡 �����Ҽ��ֵ��� �����ִ� ���̺귯��
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPW);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
	}
	
	//�α��κκ�  ->preparedstatement ��������� sqlinjection���� ��ŷ��������!
	//�ϳ��� ���������� �α��νõ��ϴ� �Լ�
	public int login(String userID,String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); 
			if(rs.next()) {//sql��� ��,id�� ������
				if(rs.getString(1).equals(userPassword)) {
					return 1; //�α��� ����
				}
				else
					return 0; //��й�ȣ�� Ʋ��
			}
			return -1; //id�� ������	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES ( ?, ?, ?, ?, ?) ";

		
		try {
			pstmt = conn.prepareStatement(SQL);//sql ������ instance�־��ֱ�
			pstmt.setString(1, user.getUserID());//������ ����ǥ�� ���°͵� ������
			pstmt.setString(2, user.getUserPassword());//������ ����ǥ�� ���°͵� ������
			pstmt.setString(3, user.getUserName());//������ ����ǥ�� ���°͵� ������
			pstmt.setString(4, user.getUserGender());//������ ����ǥ�� ���°͵� ������
			pstmt.setString(5, user.getUserEmail());//������ ����ǥ�� ���°͵� ������
			return pstmt.executeUpdate(); //�ش� statement�� ������ ����� �ִ´�.
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return -1; //������ ���̽� ����
	}
}
