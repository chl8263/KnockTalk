package com.example.choi.knocktalk.AdapterItem;

/**
 * Created by choi on 17. 9. 18.
 */

public class DrawerItem {
    private int resource;
    private String name;

    public DrawerItem(int resource, String name) {
        this.resource = resource;
        this.name = name;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
