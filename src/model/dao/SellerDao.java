package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	void insert(Seller sel);
	void update(Seller sel);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Seller> findAll();
}
