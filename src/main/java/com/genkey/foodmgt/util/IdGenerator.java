/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.util;

public class IdGenerator {
    public static String createId() {
        return java.util.UUID.randomUUID().toString();
    }
}