package edu.newdawn;

import java.util.Arrays;
import java.util.List;

public class ApplicationJPA {
    
	private static final JpaService jpaService = JpaService.getInstance();

	public static void main(String[] args){
        try {
			createProducts();
			printProducts();

		} finally {
			jpaService.shutdown();
		}
	}

	private static void createProducts() {
		jpaService.runInTransaction(entityManager -> {
			Arrays.stream("Laptop,Mouse,Printer,Scanner,USB,Keyboard,Python,Pencil".split(","))
			.map(name -> new Products(name, (int) (Math.random() * 10), (int) (Math.random() * 100)))
			.forEach(entityManager::persist);
			return null;
		});
	}

	private static void printProducts() {
		List<Products> products = jpaService
				.runInTransaction(entityManager -> entityManager.createQuery(
						"SELECT p FROM Products p WHERE p.price > 5",
						Products.class).getResultList());

		products.stream()
				.map(pl -> pl.getName() + ": " + pl.getPrice())
				.forEach(System.out::println);
	}
}
