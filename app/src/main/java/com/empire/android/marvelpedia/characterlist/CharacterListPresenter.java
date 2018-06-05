package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.data.Character;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CharacterListPresenter implements CharacterListContract.Presenter {

    private CharacterListContract.View view;
    private CharacterListContract.Repository repository;

    private List<Character> characterList;
    private int totalPages;
    private int dataPerPage = 10;
    private int currentPage = 1;
    private boolean isRunning = false;

    public CharacterListPresenter(CharacterListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void attachView(CharacterListContract.View view) {
        this.view = view;
    }

    @Override
    public void getCharacters() {

        isRunning = true;
        view.setProgressBarVisible(true);

        characterList = new ArrayList<>();

        int offset = getOffset(currentPage);
        Observable<Character.JsonResponse> characters = repository.getCharacters(offset);

        characters
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Character.JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Character.JsonResponse s) {
                        System.out.println("onNext: " + s);

                        totalPages = (int) Math.ceil(s.getData().getTotal() / dataPerPage);
                        characterList = s.getData().getCharacterList();

                        System.out.println("s.getTotal: " + s.getData().getTotal());
                        System.out.println("dataPerPage: " + dataPerPage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                        isRunning = false;
                        view.setProgressBarVisible(false);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");

                        isRunning = false;
                        view.showList(characterList);
                        view.setPagesText(currentPage, totalPages);
                        view.setProgressBarVisible(false);
                        handleButtonsState();
                    }
                });

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
    public void characterClicked() {

    }

    private int getOffset(int page){
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

}
