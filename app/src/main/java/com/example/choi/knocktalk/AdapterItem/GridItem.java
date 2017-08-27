package com.example.choi.knocktalk.AdapterItem;

/**
 * Created by choi on 17. 8. 20.
 */

public class GridItem {
    private String image;
    private String text;
    private int kingImage;

    public GridItem(String a,String b,int c){
        this.image=a;
        this.text=b;
        this.kingImage=c;
    }
    public String getGridimage(){return image;}
    public String getGridText(){
        return text;
    }
    public int getGridKingImage(){
        return kingImage;
    }

    public void setGridimage(String path){this.image = path;}
}
