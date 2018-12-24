package org.romanchi.rozetka;

import java.util.Arrays;
//додавай процесор, обсяг оперативної памяті, об'єм накопичувача, покоління процесора, операційна система,  , графічний адаптер ,вага, середня оцінка і кількість оцінок
public class RozetkaGoodTile implements Comparable{
    private String goodName;
    private int price;
    private String screen;
    private String vendor;
    private String processor;
    private Integer ram;
    private String vinchester;
    private String os;
    private String gpu;
    private Double weight;
    private Double mark;
    private Integer markAmount;
    private Integer wishlistCount;

    public RozetkaGoodTile(String goodName, int price, String screen, String vendor, String processor, Integer ram, String vinchester, String os, String gpu, Double weight, Double mark, Integer markAmount, Integer wishlistCount) {
        this.goodName = goodName;
        this.price = price;
        this.screen = screen;
        this.vendor = vendor;
        this.processor = processor;
        this.ram = ram;
        this.vinchester = vinchester;
        this.os = os;
        this.gpu = gpu;
        this.weight = weight;
        this.mark = mark;
        this.markAmount = markAmount;
        this.wishlistCount = wishlistCount;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public String getVinchester() {
        return vinchester;
    }

    public void setVinchester(String vinchester) {
        this.vinchester = vinchester;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Integer getMarkAmount() {
        return markAmount;
    }

    public void setMarkAmount(Integer markAmount) {
        this.markAmount = markAmount;
    }

    public Integer getWishlistCount() {
        return wishlistCount;
    }

    public void setWishlistCount(Integer wishlistCount) {
        this.wishlistCount = wishlistCount;
    }

    @Override
    public String toString() {
        return "RozetkaGoodTile{" +
                "goodName='" + goodName + '\'' +
                ", price=" + price +
                ", screen='" + screen + '\'' +
                ", vendor='" + vendor + '\'' +
                ", processor='" + processor + '\'' +
                ", ram=" + ram +
                ", vinchester='" + vinchester + '\'' +
                ", os='" + os + '\'' +
                ", gpu='" + gpu + '\'' +
                ", weight=" + weight +
                ", mark=" + mark +
                ", markAmount=" + markAmount +
                ", wishlistCount=" + wishlistCount +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o == null){
            throw new NullPointerException();
        }
        if(o.getClass() != getClass()){
            throw new ClassCastException();
        }
        RozetkaGoodTile leftTile = (RozetkaGoodTile) o;

        if(price < leftTile.price){
            return -1;
        }else if(price > leftTile.price){
            return 1;
        }else{
            return 0;
        }
    }
}
