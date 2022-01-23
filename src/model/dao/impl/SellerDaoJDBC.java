package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
