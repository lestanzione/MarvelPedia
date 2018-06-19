package com.empire.android.marvelpedia.comic;

import android.support.annotation.VisibleForTesting;

import com.empire.android.marvelpedia.character.CharacterContract;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.MarvelApiObject;
import com.empire.android.marvelpedia.data.Serie;
import com.empire.android.marvelpedia.serie.SerieContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
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
                .flatMap(new Function<Comic.JsonResponse, ObservableSource<Character.JsonResponse>>() {
                    @Override
                    public ObservableSource<Character.JsonResponse> apply(Comic.JsonResponse jsonResponse) throws Exception {

                        comic = jsonResponse.getData().getComicList().get(0);

                        return characterRepository.getCharactersByComicId(comic.getId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Character.JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Character.JsonResponse jsonResponse) {
                        System.out.println("onNext: " + jsonResponse);
                        characterList = jsonResponse.getData().getCharacterList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setProgressBarVisible(false);
                    }

                    @Override
                    public void onComplete() {
                        view.setProgressBarVisible(false);
                        view.setComicName(comic.getTitle());
                        view.setComicDescription(comic.getDescription());

                        String imagePath = comic.getImage().getPath() + "." + comic.getImage().getExtension();
                        view.setComicImage(imagePath);

                        view.showCharacters(characterList);
                        view.setSeeAllCharactersVisible(true);
                    }
                });

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
