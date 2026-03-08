package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import db.DBIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Department dep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("INSERT department (department_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, dep.getName());
			
			int rows = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			
			if(rows > 0) {
				if(rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
			}
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
	}

	@Override
	public void update(Department dep) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE department SET department_name = ? WHERE department_id = ?");
			ps.setString(1, dep.getName());
			ps.setInt(2, dep.getId());
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closePreparedStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM department WHERE department_id = ?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBIntegrityException(e.getMessage());
		}
		finally {
			DB.closePreparedStatement(ps);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM department WHERE department_id = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			Department dep = new Department();
			if(rs.next()) {
				dep.setId(rs.getInt("department_id"));
				dep.setName(rs.getString("department_name"));
				return dep;
			}
			else {
				return null;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> departmentList = new ArrayList<Department>();
		try {
			ps = conn.prepareStatement("SELECT * FROM department");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("department_id"));
				dep.setName(rs.getString("department_name"));
				departmentList.add(dep);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
		return departmentList;
	}

}
