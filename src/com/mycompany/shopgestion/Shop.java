/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author mary-annperezroig
 */

// Clase Shop
import java.util.*;

public class Shop {

    private final Amount cash = new Amount(100.00);
    private final List<Product> inventory;
    private final List<Sale> sales;
    final static double TAX_RATE = 1.04;
    public class Employee {
    private final String name;
    private final int employeeNumber;

    public Employee(String name, int employeeNumber) {
        this.name = name;
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public String toString() {
        return "Empleado: " + name + " | Número de empleado: " + employeeNumber;
    }
}

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public double getCash() {
        return cash.getValue();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1. Mostrar dinero en caja");
            System.out.println("2. Añadir producto");
            System.out.println("3. Mostrar inventario");
            System.out.println("4. Realizar venta");
            System.out.println("5. Mostrar ventas");
            System.out.println("6. Mostrar total de ventas");
            System.out.println("9. Eliminar producto");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    shop.showCash();
                    break;
                case 2:
                    shop.addProduct();
                    break;
                case 3:
                    shop.showInventory();
                    break;
                case 4:
                    shop.sale();
                    break;
                case 5:
                    shop.showSales();
                    break;
                case 6:
                    shop.numSales();
                    break;
                case 9:
                    shop.removeProduct();
                    break;
                case 10:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (choice != 10);
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del producto (escriba 'c' para cancelar): ");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("c")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (productExists(name)) {
            System.out.println("El producto ya existe en el inventario.");
            return;
        }

        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Product newProduct = new Product(name, wholesalerPrice, true, stock);
        inventory.add(newProduct);

        System.out.println("Producto añadido: " + name);
    }

    private boolean productExists(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }

        System.out.println("Contenido de la tienda:");
        for (Product product : inventory) {
            System.out.println("Producto: " + product.getName());
            System.out.println("Stock: " + product.getStock());
            System.out.println("Precio público: " + product.getPublicPrice());
            System.out.println("-----------------------------------");
        }
    }

    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Qué producto desea eliminar?: ");
        String name = scanner.nextLine();

        Iterator<Product> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Producto eliminado correctamente.");
            }
          return;
        }

        System.out.println("Producto no encontrado.");   
       
    }
    
    

    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("VENTA: escribir nombre cliente: ");
        String client = scanner.nextLine();

        Map<String, Integer> productsSold = new HashMap<>();
        Amount totalAmount = new Amount(0.0);

        while (true) {
            System.out.print("Introduce el nombre del producto (escribir 0 para terminar): ");
            String name = scanner.nextLine();
            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);
            if (product != null && product.isAvailable()) {
                System.out.print("Cantidad a comprar: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                if (quantity > product.getStock()) {
                    System.out.println("Stock insuficiente. Disponible: " + product.getStock());
                } else {
                    double subtotal = product.getPublicPrice().getValue() * quantity;
                    totalAmount.setValue(totalAmount.getValue() + subtotal);
                    product.setStock(product.getStock() - quantity);
                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }
                    productsSold.put(name, productsSold.getOrDefault(name, 0) + quantity);
                    System.out.println("Producto añadido.");
                }
            } else {
                System.out.println("Producto no encontrado o sin stock.");
            }
        }

        if (totalAmount.getValue() > 0) {
            totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
            cash.setValue(cash.getValue() + totalAmount.getValue());
            sales.add(new Sale(client, productsSold, totalAmount.getValue()));
            System.out.println("Venta realizada. Total: " + totalAmount);
        } else {
            System.out.println("No se realizó ninguna venta.");
        }
    }

    public void showSales() {
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("Lista de ventas:");
        System.out.println("------------------------------");
        for (int i = 0; i < sales.size(); i++) {
            System.out.println("Venta #" + (i + 1) + ":\n" + sales.get(i));
            System.out.println("------------------------------");
        }
    }

    public void numSales() {
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        double totalAmount = 0;
        for (Sale sale : sales) {
            totalAmount += sale.getAmount().getValue();
        }

        System.out.println("Total de dinero conseguido: " + new Amount(totalAmount));
    }

    private Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}



   

