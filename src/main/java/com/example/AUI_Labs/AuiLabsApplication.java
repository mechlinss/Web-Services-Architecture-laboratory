package com.example.AUI_Labs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@SpringBootApplication
public class AuiLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuiLabsApplication.class, args);

		EmployeeRole salesman = EmployeeRole.builder()
				.name("Salesman")
				.build();
		EmployeeRole serviceTechnician = EmployeeRole.builder()
				.name("Service Technician")
				.build();
		EmployeeRole cleaner = EmployeeRole.builder()
				.name("Cleaner")
				.build();

		Collection<EmployeeRole> roles = Arrays.asList(salesman, serviceTechnician, cleaner);

		Employee.builder()
				.id("07311810434")
				.name("Wojciech")
				.surname("Nieszczęsny")
				.role(cleaner)
				.build();

		Employee.builder()
				.id("77020230113")
				.name("Andrzej")
				.surname("Lipny")
				.role(serviceTechnician)
				.build();

		Employee.builder()
				.id("81110738320")
				.name("Agata")
				.surname("Herbata")
				.role(salesman)
				.build();

		Employee.builder()
				.id("67060725719")
				.name("Sławomir")
				.surname("Braun")
				.role(cleaner)
				.build();

		Employee.builder()
				.id("53022165884")
				.name("Aniela")
				.surname("Adamczewska")
				.role(serviceTechnician)
				.build();

		Employee.builder()
				.id("61100961709")
				.name("Bożena")
				.surname("Kowalska")
				.role(salesman)
				.build();

		roles.forEach(role -> {
			System.out.println(role);
			role.getEmployees().forEach(System.out::println);
		});

		System.out.println("===3===");
		Set<Employee> allItems = roles.stream()
				.flatMap(role -> role.getEmployees().stream())
				.collect(Collectors.toSet());

		allItems.stream().forEach(System.out::println);

		System.out.println("===4===");
		allItems.stream()
				.filter(e -> !e.getName().contains("Sławomir"))                               // filtruj tylko dostępne
				.sorted(Comparator.comparing(Employee::getSurname)) // sortuj po cenie
				.forEach(System.out::println);

		System.out.println("===5===");
		List<EmployeeDto> dtoList = allItems.stream()
				.map(Employee::toDto)
				.sorted()
				.collect(Collectors.toList());

		dtoList.stream().forEach(System.out::println);

		System.out.println("===6===");
		Path filePath = Path.of("roles.ser");

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
			oos.writeObject(roles);
		} catch (IOException e) {
			System.err.println("Error occurred while saving to file: " + e.getMessage());
		}

		List<EmployeeRole> loadedCategories = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
			Object obj = ois.readObject();
			if (obj instanceof List<?>) {
				loadedCategories = (List<EmployeeRole>) obj;
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error occurred while uploading file " + e.getMessage());
		}

		if (loadedCategories != null) {
			loadedCategories.forEach(cat -> {
				System.out.println(cat);
				cat.getEmployees().forEach(item -> System.out.println("   " + item));
			});
		} else {
			System.err.println("Failed to load employees");
		}

		System.out.println("===7===");
		for (int poolSize : new int[]{1, 2, 4}) {
			System.out.println("\n➡️  PoolSize: " + poolSize);

			ForkJoinPool pool = new ForkJoinPool(poolSize);

			try {
				pool.submit(() -> {
					roles.parallelStream().forEach(role -> {
						String threadName = Thread.currentThread().getName();
						System.out.println("[" + threadName + "] Role: " + role.getName());

						role.getEmployees().forEach(employee -> {
							try {
								Thread.sleep(150);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							System.out.println("[" + threadName + "]   " + employee.getName() + " " + employee.getSurname());
						});
					});
				}).get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			pool.shutdown();
		}
	}
}
