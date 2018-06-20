package com.empire.android.marvelpedia.comiclist;

import android.support.annotation.VisibleForTesting;

import com.empire.android.marvelpedia.data.Comic;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComicListPresenter implements ComicListContract.Presenter {

    private ComicListContract.View view;
    private ComicListContract.Repository repository;

    private Long characterId;
    private List<Comic> comicList;
    private int totalPages;
    private int dataPerPage = 20;
    private int currentPage = 1;
    private String searchQuery;
    private boolean isRunning = false;

    public ComicListPresenter(ComicListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void attachView(ComicListContract.View view) {
        this.view = view;
    }

    @Override
    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    @Override
    public void getComics() {

        isRunning = true;
        view.setProgressBarVisible(true);

        comicList = new ArrayList<>();

        int offset = getOffset(currentPage);
        Observable<Comic.JsonResponse> comics = repository.getComics(offset, searchQuery, characterId);

        comics
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onReceiveComicResponse,
                        this::onError
                );

    }

    private void onReceiveComicResponse(Comic.JsonResponse jsonResponse){
        System.out.println("onReceiveComicResponse: " + jsonResponse);

        totalPages = (int) Math.ceil(jsonResponse.getData().getTotal() / Float.valueOf(dataPerPage));
        comicList = jsonResponse.getData().getComicList();

        System.out.println("jsonResponse.getTotal: " + jsonResponse.getData().getTotal());
        System.out.println("dataPerPage: " + dataPerPage);

        isRunning = false;
        view.showList(comicList);
        view.setPagesText(currentPage, totalPages);
        view.setProgressBarVisible(false);
        handleButtonsState();

    }

    private void onError(Throwable error) {
        System.out.println(error.getMessage());
        isRunning = false;
        view.setProgressBarVisible(false);
    }

    @Override
    public void nextPageButtonClicked() {
        if(!isRunning) {
            currentPage++;
            getComics();
        }
    }

    @Override
    public void previousPageButtonClicked() {
        if(!isRunning) {
            currentPage--;
            getComics();
        }
    }

    @Override
    public void resetPageNumber() {
        currentPage = 1;
    }

    @Override
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getOffset(int page){
        return page * dataPerPage - dataPerPage;
    }

    private void handleButtonsState(){
        if(currentPage == 1){
            view.setPreviousButtonEnable(false);
        }
        else{
            view.setPreviousButtonEnable(true);
        }

        if(currentPage == totalPages){
            view.setNextButtonEnable(false);
        }
        else{
            view.setNextButtonEnable(true);
        }
    }

    @VisibleForTesting
    public int getCurrentPage(){
        return currentPage;
    }

    @VisibleForTesting
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    @VisibleForTesting
    public void setDataPerPage(int dataPerPage){
        this.dataPerPage = dataPerPage;
    }

    @VisibleForTesting
    public void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

}
