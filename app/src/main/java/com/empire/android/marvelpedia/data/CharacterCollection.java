package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterCollection {

    @SerializedName("available")
    private int available;
    @SerializedName("collectionURI")
    private String uri;
    @SerializedName("items")
    private List<Character> characterList;

    public CharacterCollection(){}

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }
}
