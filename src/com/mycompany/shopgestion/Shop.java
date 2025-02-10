/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;



import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import model.Product;
import model.Sale;
import model.Amount;
/**
 *
 * @author mary-annperezroig
 */

// Clase Shop
public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private int numberSales;
    private static final double TAX_RATE = 1.04;
    private ArrayList<Sale> sales;

    public Shop() {
        this.inventory = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public double getCash() {
        return cash.getValue();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("1. Mostrar dinero en caja");
            System.out.println("2. Añadir producto");
            System.out.println("3. Mostrar inventario");
            System.out.println("4. Realizar venta");
            System.out.println("5. Mostrar ventas");
            System.out.println("6. Mostrar total de ventas");
            System.out.println("7. Marcar caducidad");
            System.out.println("0. Salir");
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
                case 7:
                    shop.setExpired();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (choice != 0);
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto: ");
        String name = scanner.nextLine();

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
        numberProducts++;

        System.out.println("Producto añadido exitosamente: " + name);
    }

    private boolean productExists(String name) {
        for (Product product : inventory) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void showInventory() {
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del cliente: ");
        String client = scanner.nextLine();
        
        Map<String, Integer> productsSold = new HashMap<>();
        Amount totalAmount = new Amount(0.0);

        while (true) {
            System.out.print("Nombre del producto (0 para terminar): ");
            String name = scanner.nextLine();
            if (name.equals("0")) break;

            Product product = findProduct(name);
            if (product != null && product.isAvailable()) {
                System.out.print("Cantidad: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                
                if (quantity > product.getStock()) {
                    System.out.println("Stock insuficiente.");
                } else {
                    double subtotal = product.getPublicPrice().getValue() * quantity;
                    totalAmount.setValue(totalAmount.getValue() + subtotal);
                    product.setStock(product.getStock() - quantity);
                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }
                    productsSold.put(name, productsSold.getOrDefault(name, 0) + quantity);
                }
            } else {
                System.out.println("Producto no encontrado o sin stock.");
            }
        }

        if (totalAmount.getValue() > 0) {
            totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
            cash.setValue(cash.getValue() + totalAmount.getValue());
            sales.add(new Sale(client, productsSold, totalAmount.getValue()));
            numberSales++;
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
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    public void numSales() {
        double totalAmount = sales.stream().mapToDouble(s -> s.getAmount().getValue()).sum();
        System.out.println("Total de ventas: " + new Amount(totalAmount));
    }
    
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto a caducar: ");
        String name = scanner.nextLine();
        
        Product product = findProduct(name);
        if (product != null) {
            product.setPublicPrice(new Amount(product.getPublicPrice().getValue() * 0.6));
            System.out.println("El precio de " + name + " se ha reducido por caducidad.");
        }
    }

    private Product findProduct(String name) {
        return inventory.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
}



   

