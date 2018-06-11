package com.empire.android.marvelpedia.comiclist;

import com.empire.android.marvelpedia.data.Comic;

import java.util.List;

import io.reactivex.Observable;

public interface ComicListContract {

    interface View {
        void setPagesText(int currentPage, int totalPageNumber);
        void setProgressBarVisible(boolean visible);
        void setPreviousButtonEnable(boolean enabled);
        void setNextButtonEnable(boolean enabled);
        void showList(List<Comic> comicList);
    }

    interface Presenter {
        void attachView(ComicListContract.View view);
        void setCharacterId(long characterId);
        void getComics();
        void nextPageButtonClicked();
        void previousPageButtonClicked();
        void resetPageNumber();
        void setSearchQuery(String searchQuery);
    }

    interface Repository{
        Observable<Comic.JsonResponse> getComics(long characterId, int offset, String searchQuery);
    }

}
