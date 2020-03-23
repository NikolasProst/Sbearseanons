package com;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");
        if (args.length > 0) {
            MyCompany company = MyCompany.getInstance(args[0]);
            System.out.println(company.toString());
        } else {
            System.out.println("Not enough parameters");
        }
    }
}
