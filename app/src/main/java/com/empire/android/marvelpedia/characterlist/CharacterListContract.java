package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.data.Character;

import java.util.List;

import io.reactivex.Observable;

public interface CharacterListContract {

    interface View {
        void setPagesText(int currentPage, int totalPageNumber);
        void setProgressBarVisible(boolean visible);
        void setPreviousButtonEnable(boolean enabled);
        void setNextButtonEnable(boolean enabled);
        void showList(List<Character> characterList);
    }

    interface Presenter {
        void attachView(CharacterListContract.View view);
        void setComicId(Long comicId);
        void getCharacters();
        void nextPageButtonClicked();
        void previousPageButtonClicked();
        void resetPageNumber();
        void setSearchQuery(String searchQuery);
    }

    interface Repository{
        Observable<Character.JsonResponse> getCharacters(int offset, String searchQuery, Long comicId);
    }

}
