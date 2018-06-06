package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.comic.ComicContract;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CharacterPresenter implements CharacterContract.Presenter {

    private CharacterContract.View view;
    private CharacterContract.Repository characterRepository;
    private ComicContract.Repository comicRepository;

    private Character character;
    private List<Comic> comicList;

    public CharacterPresenter(CharacterContract.Repository characterRepository, ComicContract.Repository comicRepository){
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
    }

    @Override
    public void attachView(CharacterContract.View view) {
        this.view = view;
    }

    @Override
    public void getCharacterInfo(long characterId) {

        view.setProgressBarVisible(true);

        characterRepository.getCharacter(characterId)
                .flatMap(new Function<Character.JsonResponse, ObservableSource<Comic.JsonResponse>>() {
                    @Override
                    public ObservableSource<Comic.JsonResponse> apply(Character.JsonResponse jsonResponse) throws Exception {

                        character = jsonResponse.getData().getCharacterList().get(0);

                        return comicRepository.getComicsByCharacterId(character.getId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Comic.JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Comic.JsonResponse jsonResponse) {
                        System.out.println("onNext: " + jsonResponse);
                        comicList = jsonResponse.getData().getComicList();
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
                    }
                });

    }
}
