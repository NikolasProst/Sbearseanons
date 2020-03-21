package com;

public class Main {
    public static void main(String[] args) {
        MyCompany company = MyCompany.getInstance(args[0]);
        System.out.println(company.toString());
    }
}
