/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;

/**
 *
 * @author mary-annperezroig
 */

 
public class Client extends Person{
    private String CodeC;
    private double Money;

    public Client(String Name, String CodeC, double Money) { 
        super(Name);
        this.CodeC = CodeC;
        this.Money = Money;
       
    }
    
}   

