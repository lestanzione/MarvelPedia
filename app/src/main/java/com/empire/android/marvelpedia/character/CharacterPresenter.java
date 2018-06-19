package com.empire.android.marvelpedia.character;

import android.support.annotation.VisibleForTesting;

import com.empire.android.marvelpedia.comic.ComicContract;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.MarvelApiObject;
import com.empire.android.marvelpedia.data.Serie;
import com.empire.android.marvelpedia.serie.SerieContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharacterPresenter implements CharacterContract.Presenter {

    private CharacterContract.View view;
    private CharacterContract.Repository characterRepository;
    private ComicContract.Repository comicRepository;
    private SerieContract.Repository serieRepository;

    private Character character;
    private List<Comic> comicList;
    private List<Serie> serieList;

    public CharacterPresenter(CharacterContract.Repository characterRepository, ComicContract.Repository comicRepository, SerieContract.Repository serieRepository){
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
        this.serieRepository = serieRepository;
    }

    @Override
    public void attachView(CharacterContract.View view) {
        this.view = view;
    }

    @Override
    public void getCharacterInfo(long characterId) {

        view.setSeeAllComicsVisible(false);
        view.setProgressBarVisible(true);

        characterRepository.getCharacter(characterId)
                .flatMap(this::mapToMarvelApiObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onReceiveMarvelApiObject,
                        this::onError
                );

    }

    private ObservableSource<MarvelApiObject> mapToMarvelApiObject(Character.JsonResponse jsonResponse){

        character = jsonResponse.getData().getCharacterList().get(0);

        return Observable.zip(comicRepository.getComicsByCharacterId(character.getId()),
                                serieRepository.getStoriesByCharacterId(character.getId()),
                                this::createMarvelApiObject
                            );

    }

    private MarvelApiObject createMarvelApiObject(Comic.JsonResponse comicJsonResponse, Serie.JsonResponse serieJsonResponse){

        System.out.println("comic jsonresponse: " + comicJsonResponse);
        System.out.println("serie jsonresponse: " + serieJsonResponse);

        return new MarvelApiObject.Builder()
                .setComicList(comicJsonResponse.getData().getComicList())
                .setSerieList(serieJsonResponse.getData().getSerieList())
                .build();

    }

    private void onReceiveMarvelApiObject(MarvelApiObject marvelApiObject) {

        System.out.println("onReceiveMarvelApiObject: " + marvelApiObject);
        comicList = marvelApiObject.getComicList();
        serieList = marvelApiObject.getSerieList();

        view.setProgressBarVisible(false);
        view.setCharacterName(character.getName());
        view.setCharacterDescription(character.getDescription());

        String imagePath = character.getImage().getPath() + "." + character.getImage().getExtension();
        view.setCharacterImage(imagePath);

        view.showComics(comicList);
        view.showSeries(serieList);
        view.setSeeAllComicsVisible(true);

    }

    private void onError(Throwable error) {
        System.out.println(error.getMessage());
        view.setProgressBarVisible(false);
    }

    @Override
    public void seeAllComicsClicked() {
        view.navigateToComicList(character.getId());
    }

    @VisibleForTesting
    public void setCharacter(Character character){
        this.character = character;
    }

}
