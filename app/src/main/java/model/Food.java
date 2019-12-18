package model;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialversionUID=10L;
    private String Title;
    private  int Id;
    private String Desc;
    private String recordDate;

    public Food() {
    }

    public Food(String title, int Id, String desc, String recordDate) {
        Title = title;
        this.Id = Id;
        Desc = desc;
        this.recordDate = recordDate;
    }

    public static long getSerialversionUID() {
        return serialversionUID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }


    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
