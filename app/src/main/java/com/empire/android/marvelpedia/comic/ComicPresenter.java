package com.empire.android.marvelpedia.comic;

import android.support.annotation.VisibleForTesting;

import com.empire.android.marvelpedia.character.CharacterContract;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComicPresenter implements ComicContract.Presenter {

    private ComicContract.View view;
    private ComicContract.Repository comicRepository;
    private CharacterContract.Repository characterRepository;

    private Comic comic;
    private List<Character> characterList;

    public ComicPresenter(ComicContract.Repository comicRepository, CharacterContract.Repository characterRepository){
        this.comicRepository = comicRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public void attachView(ComicContract.View view) {
        this.view = view;
    }

    @Override
    public void getComicInfo(long comicId) {

        view.setSeeAllCharactersVisible(false);
        view.setProgressBarVisible(true);

        comicRepository.getComicById(comicId)
                .flatMap(this::mapToCharacterList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onReceiveCharacterResponse,
                        this::onError
                );

    }

    private ObservableSource<Character.JsonResponse> mapToCharacterList(Comic.JsonResponse jsonResponse) {

        comic = jsonResponse.getData().getComicList().get(0);

        return characterRepository.getCharactersByComicId(comic.getId());
    }

    private void onReceiveCharacterResponse(Character.JsonResponse jsonResponse) {

        System.out.println("onReceiveCharacterResponse: " + jsonResponse);
        characterList = jsonResponse.getData().getCharacterList();

        view.setProgressBarVisible(false);
        view.setComicName(comic.getTitle());
        view.setComicDescription(comic.getDescription());

        String imagePath = comic.getImage().getPath() + "." + comic.getImage().getExtension();
        view.setComicImage(imagePath);

        view.showCharacters(characterList);
        view.setSeeAllCharactersVisible(true);
    }

    private void onError(Throwable error) {
        System.out.println(error.getMessage());
        view.setProgressBarVisible(false);
    }

    @Override
    public void seeAllCharactersClicked() {
        view.navigateToCharacterList(comic.getId());
    }

    @VisibleForTesting
    public void setComic(Comic comic){
        this.comic = comic;
    }

}
