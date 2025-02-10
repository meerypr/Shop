/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import model.Product;
import model.Sale;
import model.Amount;
import model.Worker;

/**
 *
 * @author mary-annperezroig
 */

// Clase Shop




public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private int numberSales; // Contador de ventas
    final static double TAX_RATE = 1.04;
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
        // shop.loadInventory();
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        do {
            System.out.println("1. Mostrar dinero en caja");
            System.out.println("2. Añadir producto");
            System.out.println("3. Mostrar inventario");
            System.out.println("4. Realizar venta");
            System.out.println("5. Mostrar ventas");
            System.out.println("6. Mostrar total de ventas");
            System.out.println("7. Marcar caducidad");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    shop.showCash();
                    break;
                case 2:
                    shop.addProduct(null);
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
                    shop.Marcad();
                    break;    
                case 9:
                    shop.Delprod();
                    break;    
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opci�n no v�lida. Intente de nuevo.");
                    break;
            }
        } while (choice != 0);
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void loadInventory() {
        addProduct(new Product("Manzana", 10.00, true, 10));
        addProduct(new Product("Pera", 20.00, true, 20));
        addProduct(new Product("Hamburguesa", 30.00, true, 30));
        addProduct(new Product("Fresa", 5.00, true, 20));
    }

    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden a�adir m�s productos.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del producto (escriba 'salir' para cancelar): ");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("salir")) {
            System.out.println("Operaci�n cancelada.");
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

        // Crear y agregar el producto al inventario
        Product newProduct = new Product(name, wholesalerPrice, true, stock);
        inventory.set(numberProducts, newProduct);
        numberProducts++;

        System.out.println("Producto a�adido exitosamente: " + name);
    }

    private boolean productExists(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println("Producto: " + product.getName());
                System.out.println("Stock: " + product.getStock());
                System.out.println("Precio p�blico: " + product.getPublicPrice());
                System.out.println("-----------------------------------");
            }
        }
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a a�adir: ");
            int stock = scanner.nextInt();
            product.setStock(stock + product.getStock());
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            product.setPublicPrice(new Amount(product.getPublicPrice().getValue() * 0.6));
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Realizar venta, escribir nombre cliente: ");
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
                    System.out.println("Producto a�adido con �xito.");
                }
            } else {
                System.out.println("Producto no encontrado o sin stock.");
            }
        }

        if (totalAmount.getValue() > 0) {
            totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
            cash.setValue(cash.getValue() + totalAmount.getValue());
            registerSale(new Sale(client, productsSold, totalAmount.getValue()));
            System.out.println("Venta realizada con �xito. Total: " + totalAmount);
        } else {
            System.out.println("No se realiz� ninguna venta.");
        }
    }

    private void registerSale(Sale sale) {
            sales.set(numberSales, sale);
            numberSales++;
        
    }

    public void showSales() {
        if (numberSales == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("Lista de ventas:");
        System.out.println("------------------------------");
        for (int i = 0; i < numberSales; i++) {
            System.out.println("Venta #" + (i + 1) + ":\n" + sales.get(i));
            System.out.println("------------------------------");
        }
    }

    public void numSales() {
        if (numberSales == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        double totalAmount = 0;
        for (int i = 0; i < numberSales; i++) {
            totalAmount += sales.get(i).getAmount().getValue();
        }

        System.out.println("Total de dinero conseguido con todas las compras: " + new Amount(totalAmount));
    }
    
    public void Marcad() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
 
            

        }
    }
    
    public void Delprod() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
           inventory.remove(name);

        }
    }
    
    private boolean isInventoryFull() {
        return numberProducts >= inventory.size();
    }

    private Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}


   

