package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.data.Character;

import java.util.List;

public interface CharacterListContract {

    interface View {
        void showList(List<Character> characterList);
    }

    interface Presenter {
        void attachView(CharacterListContract.View view);
        void getCharacters();
    }

}
