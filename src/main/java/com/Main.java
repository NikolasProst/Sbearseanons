package com;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            MyCompany company = MyCompany.getInstance(args[0]);
            System.out.println(company.toString());
        } else {
            System.out.println("Not enough parameters");
        }
    }
}
