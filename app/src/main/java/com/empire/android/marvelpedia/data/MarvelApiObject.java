package com.empire.android.marvelpedia.data;

import java.util.List;

public class MarvelApiObject {

    private List<Character> characterList;
    private List<Comic> comicList;
    private List<Serie> serieList;

    private MarvelApiObject(Builder builder){
        this.characterList = builder.characterList;
        this.comicList = builder.comicList;
        this.serieList = builder.serieList;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public List<Comic> getComicList() {
        return comicList;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }


    public static class Builder {

        private List<Character> characterList;
        private List<Comic> comicList;
        private List<Serie> serieList;

        public Builder setCharacterList(List<Character> characterList){
            this.characterList = characterList;
            return this;
        }

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
