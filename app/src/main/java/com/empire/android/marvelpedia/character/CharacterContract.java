package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Serie;

import java.util.List;

import io.reactivex.Observable;

public interface CharacterContract {

    interface View {
        void setProgressBarVisible(boolean visible);
        void setCharacterName(String name);
        void setCharacterDescription(String description);
        void setCharacterImage(String imageUrl);
        void showComics(List<Comic> comicList);
        void setSeeAllComicsVisible(boolean visible);
        void showSeries(List<Serie> serieList);
        void navigateToComicList(long characterId);
    }

    interface Presenter{
        void attachView(CharacterContract.View view);
        void getCharacterInfo(long characterId);
        void seeAllComicsClicked();
    }

    interface Repository {
        Observable<Character.JsonResponse> getCharacter(long characterId);
    }

}
