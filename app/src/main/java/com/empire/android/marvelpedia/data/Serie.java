package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Serie {

    @SerializedName("id")
    private long id;
    @SerializedName(value="title", alternate={"name"})
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("resourceURI")
    private String uri;
    @SerializedName("thumbnail")
    private Image image;

    public Serie(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
        List<Serie> serieList;

        public int getTotal(){
            return total;
        }

        public List<Serie> getSerieList(){
            return serieList;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setSerieList(List<Serie> serieList) {
            this.serieList = serieList;
        }
    }
}
