package com.thisastergroup.controller;
import java.util.*;
public class pruebita {
    public static void main(String[] args) {
        
        printNumbers("12&32&45");
    }


    public static void printNumbers(String input) {
        String[] numbers = input.split("&");
        for (String number : numbers) {
            System.out.println(number);
        }
    }
}
