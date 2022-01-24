package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public  SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller objSeller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+"(name, Email, BirthDate, BaseSalary, DepartmentId) "
					+" VALUES "
					+ " (? , ? , ? , ? , ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, objSeller.getName());
			st.setString(2, objSeller.getEmail());
			st.setDate(3, new java.sql.Date(objSeller.getBirthDate().getTime()));
			st.setDouble(4, objSeller.getBaseSalary());
			st.setInt(5, objSeller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					objSeller.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected");
			}
			
		}
		catch(SQLException exception) {
			throw new DbException("\"Unexpected error! No rows affected!\"");
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void update(Seller objSeller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department objDep = instatiateDepartment(rs);
				Seller objSeller = instatiateSeller(rs,objDep);
				
				return objSeller;
			}
			return null;
		}
		catch(SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instatiateSeller(ResultSet rs ,Department objDep) throws SQLException {
		
		Seller objSeller = new Seller();
		
		objSeller = new Seller();
		objSeller.setId(rs.getInt("Id"));
		objSeller.setName(rs.getString("Name"));
		objSeller.setEmail(rs.getString("Email"));
		objSeller.setBaseSalary(rs.getDouble("BaseSalary"));
		objSeller.setBirthDate(rs.getDate("BirthDate"));
		objSeller.setDepartment(objDep);
		
		return objSeller;
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department objdep = new Department();
		objdep.setId(rs.getInt("DepartmentId"));
		objdep.setName(rs.getString("DepName"));
		
		return objdep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName "
					+"from seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name; ");
			
			
			rs = st.executeQuery();
			
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer , Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department objDep = map.get(rs.getInt("DepartmentId"));
				
				if(objDep == null) {
					objDep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), objDep);
				}
				
				Seller objSeller = instatiateSeller(rs,objDep);
				listSeller.add(objSeller);
			}
			return listSeller;
		}
		catch(SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName "
					+"from seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE DepartmentId = ? "
					+"ORDER BY Name; ");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer , Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department objDep = map.get(rs.getInt("DepartmentId"));
				
				if(objDep == null) {
					objDep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), objDep);
				}
				
				Seller objSeller = instatiateSeller(rs,objDep);
				listSeller.add(objSeller);
			}
			return listSeller;
		}
		catch(SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

}
