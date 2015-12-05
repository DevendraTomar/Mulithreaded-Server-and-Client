package co.in.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.in.exception.ServerException;

public class DbManager {

	Connection connection = null;

	public DbManager(String dbLocation) {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);
		} catch (SQLException | ClassNotFoundException ex) {
			throw new ServerException(ex.getMessage());
		}

	}

	public void closeConnection() {

		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException ex) {
				throw new ServerException(ex.getMessage());
			}
		}

	}

	public List<StudentDao> getAllUsers() {

		if (connection == null) {
			throw new ServerException("Database is not connected ! , Please reconnect and then try again");
		}
		List<StudentDao> studentList = null;

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select name,rollNo,location from Student order by rollNo");
			studentList = new ArrayList<>();

			while (resultSet.next()) {

				String name = resultSet.getString(1);
				Integer rollNo = resultSet.getInt(2);
				String location = resultSet.getString(3);
				StudentDao dao = new StudentDao();

				dao.setName(name);
				dao.setRollNo(rollNo);
				dao.setLocation(location);

				studentList.add(dao);

			}

		} catch (SQLException ex) {
			throw new ServerException(ex.getMessage());
		}

		return studentList;
	}

	public void registerStudent(StudentDao studentDao) {

		if (connection == null) {
			throw new ServerException("Database is not connected ! , Please reconnect and then try again");
		}

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into Student(name,rollNo,location)" + "values (?,?,?)");

			preparedStatement.setString(1, studentDao.getName());
			preparedStatement.setInt(2, studentDao.getRollNo());
			preparedStatement.setString(3, studentDao.getLocation());
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			throw new ServerException(ex.getMessage());
		}

	}

	public StudentDao getStudenDetailsFromRollNo(int rollNo) {

		StudentDao dao = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("Select name,rollNo,location from Student where rollNo =" + rollNo);

			if (resultSet.next()) {

				String name = resultSet.getString(1);
				Integer rollNo1 = resultSet.getInt(2);
				String location = resultSet.getString(3);
				dao = new StudentDao();

				dao.setName(name);
				dao.setRollNo(rollNo1);
				dao.setLocation(location);

			}
		} catch (SQLException ex) {
			throw new ServerException(ex.getMessage());
		}

		return dao;
	}

}
