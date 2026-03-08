package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("Teste: seller findById: ");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("=====================================");
		System.out.println("Teste: seller findByDepartment: ");
		Department dep = new Department(2, null);
		List<Seller> sellerList = sellerDao.findByDeparment(dep);
		for(Seller x : sellerList) {
			System.out.println(x);
		}
		
		System.out.println("=====================================");
		System.out.println("Teste: seller findAll: ");
		sellerList = sellerDao.findAll();
		for(Seller x : sellerList) {
			System.out.println(x);
		}
		
		System.out.println("=====================================");
		System.out.println("Teste: seller insert: ");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", LocalDate.parse("19/02/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")), 4000.00, dep);
		sellerDao.insert(newSeller);
		System.out.println("Id do novo vendedor: " + newSeller.getId());
		
		System.out.println("=====================================");
		System.out.println("Teste: seller update: ");
		seller = sellerDao.findById(1);
		seller.setName("Martha Wayne");
		seller.setEmail("martha@gmail.com");
		sellerDao.update(seller);
		
		System.out.println("=====================================");
		System.out.println("Teste: seller delete: ");
		System.out.println("Informe o ID do vendedor para exclusão: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		sc.close();
		
	}
}
