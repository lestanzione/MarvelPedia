package com.empire.android.marvelpedia.characterlist;

import android.support.annotation.VisibleForTesting;

import com.empire.android.marvelpedia.data.Character;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharacterListPresenter implements CharacterListContract.Presenter {

    private CharacterListContract.View view;
    private CharacterListContract.Repository repository;

    private List<Character> characterList;
    private int totalPages;
    private int dataPerPage = 10;
    private int currentPage = 1;
    private String searchQuery;
    private boolean isRunning = false;
    private Long comicId;

    public CharacterListPresenter(CharacterListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void attachView(CharacterListContract.View view) {
        this.view = view;
    }

    @Override
    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    @Override
    public void getCharacters() {

        isRunning = true;
        view.setProgressBarVisible(true);

        characterList = new ArrayList<>();

        int offset = getOffset(currentPage);
        Observable<Character.JsonResponse> characters = repository.getCharacters(offset, searchQuery, comicId);

        characters
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onReceiveCharacterResponse,
                        this::onError);

    }

    private void onReceiveCharacterResponse(Character.JsonResponse jsonResponse) {
        System.out.println("onReceiveCharacterResponse: " + jsonResponse);

        totalPages = (int) Math.ceil(jsonResponse.getData().getTotal() / Float.valueOf(dataPerPage));
        characterList = jsonResponse.getData().getCharacterList();

        System.out.println("jsonResponse.getTotal: " + jsonResponse.getData().getTotal());
        System.out.println("dataPerPage: " + dataPerPage);

        isRunning = false;
        view.showList(characterList);
        view.setPagesText(currentPage, totalPages);
        view.setProgressBarVisible(false);
        handleButtonsState();

    }

    private void onError(Throwable error){
        System.out.println(error.getMessage());
        isRunning = false;
        view.setProgressBarVisible(false);
    }

    @Override
    public void nextPageButtonClicked() {
        if(!isRunning) {
            currentPage++;
            getCharacters();
        }
    }

    @Override
    public void previousPageButtonClicked() {
        if(!isRunning) {
            currentPage--;
            getCharacters();
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
