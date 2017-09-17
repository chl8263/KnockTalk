package com.example.choi.knocktalk.AdapterItem;

import android.graphics.Bitmap;

/**
 * Created by choi on 17. 9. 17.
 */

public class SecondGriditem {
    private Bitmap bitmap;
    private String name;

    public SecondGriditem(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
