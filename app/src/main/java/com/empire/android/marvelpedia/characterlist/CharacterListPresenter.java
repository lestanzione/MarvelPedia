package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.MarvelApi;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CharacterListPresenter implements CharacterListContract.Presenter {

    private CharacterListContract.View view;
    private MarvelApi marvelApi;

    List<Character> characterList;

    public CharacterListPresenter(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public void attachView(CharacterListContract.View view) {
        this.view = view;
    }

    @Override
    public void getCharacters() {

        characterList = new ArrayList<>();

        Date now = new Date();
        String timestamp = String.valueOf(now.getTime());
        String hash = Utils.generateHash(timestamp);

        Observable<Character.JsonResponse> characters = marvelApi.getCharacters(MarvelApi.PUBLIC_KEY, timestamp, hash);

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
                        System.out.println("getCharacterList: " + s.getData().getCharacterList());
                        System.out.println("getCharacterList size: " + s.getData().getCharacterList().size());
                        System.out.println("getCharacterList - 0 - name: " + s.getData().getCharacterList().get(0).getName());

                        characterList = s.getData().getCharacterList();

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                        view.showList(characterList);
                    }
                });

    }

}
