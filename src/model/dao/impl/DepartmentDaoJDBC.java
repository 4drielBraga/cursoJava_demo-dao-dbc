package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	//@constructor
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	
	@Override
	public void insert(Department objDepartment) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name) "
					+"VALUES "		
					+"(?) ",
			Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, objDepartment.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					objDepartment.setId(id);
				}
				
			}
			else {
				throw new DbException("\"Unexpected error! No rows affected!\"");
			}
		}
		catch(SQLException exception){
			throw new DbException(exception.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department objDepartment) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+"SET Name = ? "
					+"WHERE Id = ?");
			
			st.setString(1, objDepartment.getName());
			st.setInt(2, objDepartment.getId());
			
			st.executeUpdate();		
			}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try{
			
			st = conn.prepareStatement(
					"DELETE FROM department WHERE Id = ?");
					
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.*,department.Name as DepName "
					+ "FROM department "
					+ "WHERE department.Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department objDepartment = new Department();
				objDepartment.setId(rs.getInt("Id"));
				objDepartment.setName(rs.getString("Name"));
				return objDepartment;
			}
			
			
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	
		
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.*,department.Name as DepName "
					+"FROM department "
					+"ORDER BY Name");
					
			rs = st.executeQuery();	
			
			List<Department> listDepartment = new ArrayList();
			
			while(rs.next()) {
				Department objDepartment = new Department();
				objDepartment.setId(rs.getInt("Id"));
				objDepartment.setName(rs.getNString("Name"));
				listDepartment.add(objDepartment);
			}
			
			return listDepartment;
		}
		catch(SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

}
