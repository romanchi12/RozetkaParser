package org.romanchi;
import org.romanchi.google.GoogleParser;
import org.romanchi.google.GoogleResultItem;
import org.romanchi.rozetka.RozetkaGoodTile;
import org.romanchi.rozetka.RozetkaParser;
import org.romanchi.rozetka.RozetkaPriceFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //Task 1,2
        /*try(GoogleParser googleParser = new GoogleParser()){
            GoogleResultItem googleResultItem = googleParser.find("євровікна", "львів");
            System.out.println("RESULT: " + googleResultItem);

            GoogleResultItem googleResultItem2 = googleParser.find("євровікна","Grandi");
            System.out.println("RESULT: " + googleResultItem2);

        }*/
        //Task 3

        Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object


        try(RozetkaParser rozetkaParser = new RozetkaParser()){
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            rozetkaParser.nextSet();
            List<RozetkaGoodTile> tiles = rozetkaParser.getSetOfGoods(0,480);
            tiles.forEach(System.out::println);

        }
    }
}