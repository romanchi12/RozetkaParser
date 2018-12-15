package org.romanchi;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        try(GoogleParser googleParser = new GoogleParser()){
            /*googleParser.find("євровікна");
            googleParser.goToNextPage();
            List<GoogleResultItem> googleResultItems = googleParser.getResults();
            for (GoogleResultItem googleResultItem:googleResultItems) {
                System.out.println(googleResultItem);
            }*/
            googleParser.find("євровікна", "львів");
        }




    }
}