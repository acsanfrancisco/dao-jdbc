package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				Department dep = instantiateDepartment(rs);
				Seller sel = instantiateSeller(rs, dep);
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller sel = new Seller();
		sel.setId(rs.getInt("seller_id"));
		sel.setName(rs.getString("seller_name"));
		sel.setEmail(rs.getString("email"));
		sel.setBaseSalary(rs.getBigDecimal("baseSalary").doubleValue());
		sel.setBirthDate(rs.getDate("birthDate").toLocalDate());
		sel.setDepartment(dep);
		return sel;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
		dep.setId(rs.getInt("department_id"));
		dep.setName(rs.getString("department_name"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Seller> sellerList = new ArrayList<Seller>();
		Map<Integer, Department> map = new HashMap<>();
		
		try {
			ps = conn.prepareStatement("SELECT sel.*, dep.department_name"
					+ "	FROM seller sel"
					+ " JOIN department dep"
					+ "	ON sel.department_id = dep.department_id"
					+ " ORDER BY sel.seller_name DESC");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Department department = map.get(rs.getInt("department_id"));
				
				if(department == null) {
					department = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), department);
				}
				
				Seller sel = instantiateSeller(rs, department);
				sellerList.add(sel);
			}
			return sellerList;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Seller> findByDeparment(Department dep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT sel.*, dep.department_name"
					+ " FROM seller sel"
					+ " JOIN department dep"
					+ " ON sel.department_id = dep.department_id"
					+ " WHERE dep.department_id = ?"
					+ " ORDER BY sel.seller_name DESC;");
			ps.setInt(1, dep.getId());
			rs = ps.executeQuery();
			List<Seller> sellerList = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department department = map.get(rs.getInt("department_id"));
				
				if(department == null) {
					department = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), department);
				}
				
				Seller sel = instantiateSeller(rs, department);
				sellerList.add(sel);
			}
			return sellerList;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
