/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopgestion;

/**
 *
 * @author mary-annperezroig
 */
public class Worker extends Person {
   
    private final String CodeW;
    private String Passwd;
    
    public Worker(String Name, String CodeW, String Passwd) {
        this.CodeW = CodeW;
        this.Passwd = Passwd;
        super(Name);
    }
   
}

