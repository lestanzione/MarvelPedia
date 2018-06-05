package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Character {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnail")
    private Image image;

    public Character(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public class JsonResponse {

        @SerializedName("data")
        Data data;

        public Data getData(){
            return data;
        }

    }

    public class Data {

        @SerializedName("total")
        int total;
        @SerializedName("results")
        List<Character> characterList;

        public int getTotal(){
            return total;
        }

        public List<Character> getCharacterList(){
            return characterList;
        }

    }

}
