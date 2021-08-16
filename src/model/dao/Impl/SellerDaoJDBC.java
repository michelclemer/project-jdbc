package model.dao.Impl;

import java.sql.Connection;
import java.sql.Date;
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					statement.RETURN_GENERATED_KEYS);
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getEmail());
			statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			statement.setDouble(4, obj.getBaseSalary());
			statement.setInt(5, obj.getDepartment().getId());
			int rows = statement.executeUpdate();
			if(rows > 0) {
				ResultSet rSet= statement.getGeneratedKeys();
				if(rSet.next()) {
					int id  = rSet.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rSet);
			}
			else {
				throw new DbException("Nenhuma linha foi afetada");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
		
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId  = department.Id "
					+ "ORDER BY Name "
					+ "");
	
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















