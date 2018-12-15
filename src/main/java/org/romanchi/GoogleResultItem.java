package org.romanchi;

public class GoogleResultItem {
    String title;
    String  link;
    String describe;
    GoogleResultItem(String title, String link, String describe){
        this.title = title;
        this.link = link;
        this.describe = describe;
    }
    public boolean hasKeyword(String keyword){
        if(title.toLowerCase().contains(keyword.toLowerCase())
                || describe.toLowerCase().contains(keyword.toLowerCase())){
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }
        if(object == null || object.getClass() != getClass()){
            return false;
        }
        GoogleResultItem googleResultItem = (GoogleResultItem) object;
        if(googleResultItem.title.equals(title)
                && googleResultItem.describe.equals(describe)
                && googleResultItem.link.equals(link)){
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        int hash = 0;
        hash += 31 * title.hashCode();
        hash += 31 * describe.hashCode();
        hash += 31 * link.hashCode();
        return hash;
        // or simple Objects.hash(...)
    }

    @Override
    public String toString() {
        return "GoogleResultItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
