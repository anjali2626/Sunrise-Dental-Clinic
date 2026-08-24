package com.sunrisedental.util;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.service.DentistService;

import java.util.List;

public class DentistServiceTest {

    public static void main(String[] args) {

        try {

            DentistService service =
                    new DentistService();

            System.out.println(
                    "Testing Dentist Service...");

            List<Dentist> dentists =
                    service.getActiveDentists();

            System.out.println(
                    "Active dentists: "
                    + dentists.size());

            System.out.println(
                    "Dentist service test completed successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Dentist service test failed!");

            e.printStackTrace();
        }
    }
}