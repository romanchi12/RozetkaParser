package org.romanchi.google;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleParser implements Closeable {
    private WebDriver driver;
    public GoogleParser(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
        driver.get("http://google.com");
    }
    private WebElement getNav(){
        return driver.findElement(By.id("nav"));
    }
    public void goToNextPage(){
        List<WebElement> pages =  getNav().findElements(By.tagName("td"));
        pages.get(pages.size() - 1).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void find(String query){
        WebElement qElement = driver.findElement(By.name("q"));
        qElement.clear();
        qElement.sendKeys(query);
        qElement.submit();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public GoogleResultItem find(String query, String keyword){
        find(query);
        while(true){
            System.out.println("Page: " + getCurrentPage());
            for(GoogleResultItem googleResultItem: getResults()){
                System.out.println(googleResultItem);
                if(googleResultItem.hasKeyword(keyword)){
                    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(10)).takeScreenshot(driver);
                    try {
                        ImageIO.write(myScreenshot.getImage(),
                                "PNG",new File(keyword+"_" + googleResultItem.getPage() + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return googleResultItem;
                }
            }
            goToNextPage();
        }
    }

    public int getCurrentPage(){
        return Integer.valueOf(getNav().findElement(By.className("cur")).getText());
    }

    public List<GoogleResultItem> getResults(){
        List<WebElement> results = driver.findElements(By.className("g"));
        System.out.println(results.size());
        List<GoogleResultItem> toReturn = new ArrayList<>(results.size());
        int currentPage = getCurrentPage();
        for (WebElement result : results) {
            try{
                WebElement r = result.findElement(By.className("r"));
                String title = r.findElement(By.tagName("h3")).getText();
                String link = r.findElement(By.tagName("cite")).getText();
                WebElement s = result.findElement(By.className("s"));
                String describe = s.findElement(By.className("st")).getText();
                GoogleResultItem googleResultItem =
                        new GoogleResultItem(title, link, describe, getCurrentPage());
                toReturn.add(googleResultItem);
            }catch (org.openqa.selenium.NoSuchElementException ex){
            }
        }

        return toReturn;
    }

    public void close() throws IOException {
        driver.quit();
    }
}
