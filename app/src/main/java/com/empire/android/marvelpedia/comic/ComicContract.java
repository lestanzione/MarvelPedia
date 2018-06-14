package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Serie;

import java.util.List;

import io.reactivex.Observable;

public interface ComicContract {

    interface View {
        void setProgressBarVisible(boolean visible);
        void setComicName(String name);
        void setComicDescription(String description);
        void setComicImage(String imageUrl);
        void showCharacters(List<Character> characterList);
        void setSeeAllCharactersVisible(boolean visible);
        void navigateToCharacterList(long comicId);
    }

    interface Presenter{
        void attachView(ComicContract.View view);
        void getComicInfo(long comicId);
        void seeAllCharactersClicked();
    }

    interface Repository{
        Observable<Comic.JsonResponse> getComicById(long comicId);
        Observable<Comic.JsonResponse> getComicsByCharacterId(long characterId);
    }

}
