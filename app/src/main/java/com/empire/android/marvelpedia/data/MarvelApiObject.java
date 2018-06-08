package com.empire.android.marvelpedia.data;

import java.util.List;

public class MarvelApiObject {

    private List<Comic> comicList;
    private List<Serie> serieList;

    private MarvelApiObject(Builder builder){
        this.comicList = builder.comicList;
        this.serieList = builder.serieList;
    }

    public List<Comic> getComicList() {
        return comicList;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }


    public static class Builder {

        private List<Comic> comicList;
        private List<Serie> serieList;

        public Builder setComicList(List<Comic> comicList){
            this.comicList = comicList;
            return this;
        }

        public Builder setSerieList(List<Serie> serieList){
            this.serieList = serieList;
            return this;
        }

        public MarvelApiObject build(){
            return new MarvelApiObject(this);
        }

    }

}
