package meditrack.utils;

import meditrack.exceptions.InvalidDataException;

public class Validator {
    public static void name(String n){
        if(n==null || n.isBlank()){
            throw new InvalidDataException("Invalid name");
        }
    }

    public static void age(int age){
        if(age<0){
            throw new InvalidDataException("Invalid age");
        }
    }
}
