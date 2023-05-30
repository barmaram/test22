package com.example.test2;

import android.widget.Button;

public class Clothe {
     String name;
     String size;
     String price;
     String description ;
     Button floatingButton;
    public Clothe(){}

    public Clothe(String name, String size, String description, String id , Button floatingButton ) {
        this.description=description;
        this.name = name;
        this.price=id;
        this.size=size;
        this.floatingButton=floatingButton;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String id) {
        this.price = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
