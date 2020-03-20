package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MyCompany company = MyCompany.getInstance(args[0]);
        System.out.println(company.toString());
    }
}
