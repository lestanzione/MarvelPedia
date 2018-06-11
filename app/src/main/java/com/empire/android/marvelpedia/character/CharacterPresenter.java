package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.comic.ComicContract;
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
                .flatMap(new Function<Character.JsonResponse, ObservableSource<MarvelApiObject>>() {
                    @Override
                    public ObservableSource<MarvelApiObject> apply(Character.JsonResponse jsonResponse) throws Exception {

                        character = jsonResponse.getData().getCharacterList().get(0);

                        return Observable.zip(comicRepository.getComicsByCharacterId(character.getId()), serieRepository.getStoriesByCharacterId(character.getId()), new BiFunction<Comic.JsonResponse, Serie.JsonResponse, MarvelApiObject>() {
                            @Override
                            public MarvelApiObject apply(Comic.JsonResponse comicJsonResponse, Serie.JsonResponse storyJsonResponse) throws Exception {

                                System.out.println("comic jsonresponse: " + comicJsonResponse);
                                System.out.println("story jsonresponse: " + storyJsonResponse);

                                return new MarvelApiObject.Builder()
                                                            .setComicList(comicJsonResponse.getData().getComicList())
                                                            .setSerieList(storyJsonResponse.getData().getSerieList())
                                                            .build();
                            }
                        });

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MarvelApiObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MarvelApiObject marvelApiObject) {
                        System.out.println("onNext: " + marvelApiObject);
                        comicList = marvelApiObject.getComicList();
                        serieList = marvelApiObject.getSerieList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setProgressBarVisible(false);
                    }

                    @Override
                    public void onComplete() {
                        view.setProgressBarVisible(false);
                        view.setCharacterName(character.getName());
                        view.setCharacterDescription(character.getDescription());

                        String imagePath = character.getImage().getPath() + "." + character.getImage().getExtension();
                        view.setCharacterImage(imagePath);

                        view.showComics(comicList);
                        view.showSeries(serieList);
                        view.setSeeAllComicsVisible(true);
                    }
                });

    }

    @Override
    public void seeAllComicsClicked() {
        view.navigateToComicList(character.getId());
    }

}
