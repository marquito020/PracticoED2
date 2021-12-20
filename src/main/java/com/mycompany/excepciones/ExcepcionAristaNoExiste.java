/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.excepciones;

/**
 *
 * @author Ricky
 */
public class ExcepcionAristaNoExiste extends Exception {

    /*
     * Creates a new instance of <code>ExcepcionClaveNoExiste</code> without
     * detail message.
     */
    public ExcepcionAristaNoExiste() {
        super("La arista no existe en el grafo");
    }

    /*
     * Constructs an instance of <code>ExcepcionClaveNoExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaNoExiste(String msg) {
        super(msg);
    }
}
