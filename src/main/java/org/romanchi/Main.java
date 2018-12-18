package org.romanchi;
import org.romanchi.google.GoogleParser;
import org.romanchi.google.GoogleResultItem;
import org.romanchi.rozetka.RozetkaGoodTile;
import org.romanchi.rozetka.RozetkaParser;
import org.romanchi.rozetka.RozetkaPriceFilter;

public class Main {

    public static void main(String[] args) throws Exception {
        //Task 1,2
        try(GoogleParser googleParser = new GoogleParser()){
            GoogleResultItem googleResultItem = googleParser.find("євровікна", "львів");
            System.out.println("RESULT: " + googleResultItem);

            GoogleResultItem googleResultItem2 = googleParser.find("євровікна","Grandi");
            System.out.println("RESULT: " + googleResultItem2);

        }
        //Task 3
        try(RozetkaParser rozetkaParser = new RozetkaParser()){
            int minPrice = 20000;
            RozetkaPriceFilter priceFilter = rozetkaParser.getPriceFilter();
            priceFilter.setMinPrice(minPrice);
            priceFilter.submit();
            RozetkaGoodTile toCompare = new RozetkaGoodTile("MinPricedGood", minPrice);
            for(RozetkaGoodTile tile:rozetkaParser.getFirstSetGoods()){
                if(tile.compareTo(toCompare) < 0){
                    throw new Exception("Bad filter");
                }
            }
            System.out.println("Filter is ok");
        }
    }
}