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
    @SerializedName("comics")
    private ComicCollection comicCollection;
    @SerializedName("stories")
    private SerieCollection serieCollection;

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

    public ComicCollection getComicCollection() {
        return comicCollection;
    }

    public void setComicCollection(ComicCollection comicCollection) {
        this.comicCollection = comicCollection;
    }

    public SerieCollection getSerieCollection() {
        return serieCollection;
    }

    public void setSerieCollection(SerieCollection serieCollection) {
        this.serieCollection = serieCollection;
    }


    public class JsonResponse {

        @SerializedName("data")
        private
        Data data;

        public Data getData(){
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public class Data {

        @SerializedName("total")
        private
        int total;
        @SerializedName("results")
        private
        List<Character> characterList;

        public int getTotal(){
            return total;
        }

        public List<Character> getCharacterList(){
            return characterList;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setCharacterList(List<Character> characterList) {
            this.characterList = characterList;
        }
    }

}
