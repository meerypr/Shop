/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;

public class Product {
    private final int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;
    static final double EXPIRATION_RATE = 0.60;

    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        this.id = ++totalProducts;  // El totalProducts solo se incrementa una vez aquí
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.available = available;
        this.stock = stock;
        this.publicPrice = new Amount(wholesalerPrice * 2); // El precio público se calcula al doble del precio mayorista
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Amount getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(Amount publicPrice) {
        this.publicPrice = publicPrice;
    }

    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + name + ", Precio Público: " + publicPrice.getValue() +
               ", Stock: " + stock + ", Disponible: " + (available ? "Sí" : "No");
    }
}
