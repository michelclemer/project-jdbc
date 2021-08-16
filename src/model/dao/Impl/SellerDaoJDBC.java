package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;



import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class SellerDaoJDBC implements SellerDao{
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id \r\n"
					+ "WHERE seller.Id = ?");
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				
				Seller objSeller = instatiateSeller(rs, department);
				
				return objSeller;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// TODO: handle exception
		}
		finally {
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
	
	}

	private Seller instatiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller objSeller = new Seller();
		objSeller.setId(rs.getInt("Id"));
		objSeller.setName(rs.getString("Name"));
		objSeller.setEmail(rs.getString("Email"));
		objSeller.setBaseSalary(rs.getDouble("BaseSalary"));
		objSeller.setBirthDate(rs.getDate("BirthDate"));
		objSeller.setDepartment(department);
		return objSeller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new  Department();
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepName"));
		return department;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department  "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			statement.setInt(1, department.getId());
			rs = statement.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller objSeller = instatiateSeller(rs, dep);	
				list.add(objSeller);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// TODO: handle exception
		}
		finally {
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		
	}
	
}















