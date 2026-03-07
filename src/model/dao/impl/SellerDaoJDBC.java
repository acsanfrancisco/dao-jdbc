package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT sel.*, dep.department_name"
					+ " FROM seller sel"
					+ " JOIN department dep"
					+ " ON sel.department_id = dep.department_id"
					+ " WHERE sel.seller_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("department_id"));
				dep.setName(rs.getString("department_name"));
				Seller sel = new Seller();
				sel.setId(rs.getInt("seller_id"));
				sel.setName(rs.getString("seller_name"));
				sel.setEmail(rs.getString("email"));
				sel.setBaseSalary(rs.getBigDecimal("baseSalary").doubleValue());
				sel.setBirthDate(rs.getDate("birthDate").toLocalDate());
				sel.setDepartment(dep);
				return sel;
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
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
