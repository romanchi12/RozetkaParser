package org.romanchi.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.html.HTMLDocument;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RozetkaParser implements Closeable {
    private WebDriver driver;
    private Connection con;
    public RozetkaParser(){
        try {
            this.con= DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","ROZETKA_DB","OLAP");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
        driver.get("https://rozetka.com.ua/notebooks/c80004/filter/preset=workteaching/");
        /*try {
            Thread.sleep(16000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("exponea-close-cross")));
        WebElement element = driver.findElement(By.className("exponea-close-cross"));
        element.click();

    }

    public RozetkaPriceFilter getPriceFilter(){
        return new RozetkaPriceFilter(driver.findElement(
                By.ByCssSelector.cssSelector("#sort_price .price-input")));
    }

    public List<RozetkaGoodTile> getSetOfGoods(int offset, int length){
        //data-view_type="catalog_with_hover"
        String base = driver.getWindowHandle();

        List<WebElement> allTiles = driver.
                findElements(By.ByCssSelector.cssSelector("[data-view_type=catalog_with_hover]"));
        Iterator<WebElement> iter = allTiles.stream().skip(offset).limit(length).iterator();
        List<WebElement> webElementList = new ArrayList<>();
        while(iter.hasNext()){
            webElementList.add(iter.next());
        }
        List<RozetkaGoodTile> result = new ArrayList<>();
        int i = 0;
        for(WebElement tile:webElementList){


            Actions actions = new Actions(driver);
            actions.moveToElement(tile).build().perform();
            String goodName = tile.findElement(By.className("g-i-tile-i-title")).getText();
            String vendor = goodName.split(" ")[1];
            String priceRaw = tile.findElement(By.className("g-price-uah")).getText();
            Double mark = null;
            try{
                String starsbarWidth = tile.
                        findElement(By.className("g-rating-stars-i")).getAttribute("style");
                String ratestring = starsbarWidth.substring(starsbarWidth.lastIndexOf(":")+1,starsbarWidth.length()-2);
                mark = Double.valueOf(ratestring)*0.05;


            }catch (org.openqa.selenium.NoSuchElementException e){}
            String params = tile.findElement(By.ByCssSelector.cssSelector(".g-i-tile-desc-wrap .short-description")).getText();

            System.out.println("RATE: " + mark);
            System.out.println("Params " +  params);
            try{
                String[] paramsArray = params.split("/");
                String screen = paramsArray[0].trim();
                String processor = paramsArray[1].trim();
                Integer ram = Integer.valueOf(paramsArray[2].trim().split(" ")[1]);
                String vinchester = paramsArray[3].trim();
                String os = paramsArray[paramsArray.length-3].trim();
                String gpu = paramsArray[4].trim();
                Double weight = Double.valueOf(paramsArray[paramsArray.length-2].trim().split(" ")[0]);
                Integer markAmount = Integer.valueOf(tile.
                        findElement(By.className("g-rating-reviews")).
                        getText().split(" ")[0]);

                int price = Integer.valueOf(priceRaw
                        .substring(0,priceRaw.length()-3)
                        .replace("\u2009","")
                        .replace(" ",""));

                WebElement link = tile.findElement(By.ByCssSelector.cssSelector(".g-i-tile-i-title a"));

                new Actions(driver)
                        .keyDown(Keys.CONTROL)
                        .click(link)
                        .keyUp(Keys.CONTROL)
                        .build()
                        .perform();
                ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(windows.get(1));
                WebDriverWait wait = new WebDriverWait(driver,10);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.ByCssSelector.cssSelector(".wishlist-count-t-i")));
                Integer wishListCount = Integer.valueOf(driver.findElement(By.ByCssSelector.cssSelector(".wishlist-count-t-i")).getText().split(" ")[0]);
                driver.close();
                driver.switchTo().window(base);
                RozetkaGoodTile rozetkaGoodTile = new RozetkaGoodTile(goodName,  price,screen,  vendor,  processor,  ram,  vinchester,  os,  gpu,  weight,  mark,  markAmount, wishListCount);
                result.add(rozetkaGoodTile);
                PreparedStatement preparedStatement = null;
                i++;
                try {
                    preparedStatement = con.prepareStatement("insert into NOTEBOOK VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setLong(1,i);
                    preparedStatement.setString(2, rozetkaGoodTile.getGoodName());
                    preparedStatement.setString(3,rozetkaGoodTile.getProcessor());
                    preparedStatement.setString(4,rozetkaGoodTile.getVendor());
                    preparedStatement.setString(5,rozetkaGoodTile.getGpu());
                    preparedStatement.setInt(6,rozetkaGoodTile.getRam());
                    preparedStatement.setString(7,rozetkaGoodTile.getVinchester());
                    preparedStatement.setString(8,rozetkaGoodTile.getScreen());
                    preparedStatement.setString(9,rozetkaGoodTile.getOs());
                    preparedStatement.setDouble(10,rozetkaGoodTile.getWeight());
                    preparedStatement.setInt(11,rozetkaGoodTile.getPrice());
                    preparedStatement.setDouble(12, rozetkaGoodTile.getMark());
                    preparedStatement.setInt(13,rozetkaGoodTile.getWishlistCount());
                    boolean status = preparedStatement.execute();
                    System.out.println(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }catch (ArrayIndexOutOfBoundsException e){continue;}
            catch (NumberFormatException e){continue;}
            catch (Exception e){continue;}



            /*Set<String> set = driver.getWindowHandles();
            System.out.println(set.size());
            Iterator<String> windowIterator = set.iterator();
            windowIterator.next();
            driver.switchTo().window(windowIterator.next());*/

            /*WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.ByCssSelector.cssSelector(".wishlist-count-t-i")));
            String wishlistCountElement = driver.findElement(By.ByCssSelector.cssSelector(".wishlist-count-t-i")).getText();
            System.out.println(wishlistCountElement);*/

            //driver.close();
            //driver.switchTo().window(base);


        }



        return result;
    }
    public void nextSet(){
        WebElement moreGoods = driver.findElement(By.name("more_goods"));
        moreGoods.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(moreGoods,"class","run-animation")));
    }

    @Override
    public void close() throws IOException {
        driver.close();
    }
}
