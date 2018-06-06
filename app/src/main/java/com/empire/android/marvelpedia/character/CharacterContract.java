package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;

import java.util.List;

import io.reactivex.Observable;

public interface CharacterContract {

    interface View {
        void setProgressBarVisible(boolean visible);
        void setCharacterName(String name);
        void setCharacterDescription(String description);
        void setCharacterImage(String imageUrl);
        void showComics(List<Comic> comicList);
    }

    interface Presenter{
        void attachView(CharacterContract.View view);
        void getCharacterInfo(long characterId);
    }

    interface Repository {
        Observable<Character.JsonResponse> getCharacter(long characterId);
    }

}
