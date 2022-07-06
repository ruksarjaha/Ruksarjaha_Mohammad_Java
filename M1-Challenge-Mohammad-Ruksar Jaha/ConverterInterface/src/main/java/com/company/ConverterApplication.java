package com.company;

import com.company.ConverterIf;

import com.company.ConverterSwitch;

import java.util.Scanner;

public class ConverterApplication {

    public static void main(String[] args) {

        ConverterIf converter1 = new ConverterIf();
        ConverterSwitch converter2 = new ConverterSwitch();
        Scanner scan = new Scanner(System.in);

        // Month

        System.out.println("Enter the number 1 - 12: ");
        int monthNum = Integer.parseInt(scan.nextLine());

        // Month for ConverterIf
        //converter1.convertMonth(monthNum);
        System.out.println("The result of ConverterIf is: " + converter1.convertMonth(monthNum));

        //Month for ConverterSwitch
        //converter2.convertMonth(monthNum);
        System.out.println("The result of ConverterSwitch is: " + converter1.convertMonth(monthNum));

        // Day

        System.out.println("Enter the number between 1 - 7: ");
        int dayNum = Integer.parseInt(scan.nextLine());

        // Day for ConverterIf
        //converter1.convertDay(dayNum);
        System.out.println("The result of ConverterIf is: " + converter1.convertDay(dayNum));

        // Day for ConverterSwitch
       // converter2.convertDay(dayNum);
        System.out.println("The result of ConverterSwitch is: " + converter2.convertDay(dayNum));
    }
}
