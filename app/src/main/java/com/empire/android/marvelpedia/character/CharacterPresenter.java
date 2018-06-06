package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.data.Character;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CharacterPresenter implements CharacterContract.Presenter {

    private CharacterContract.View view;
    private CharacterContract.Repository repository;

    private Character character;

    public CharacterPresenter(CharacterContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void attachView(CharacterContract.View view) {
        this.view = view;
    }

    @Override
    public void getCharacterInfo(long characterId) {

        view.setProgressBarVisible(true);

        repository.getCharacter(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Character.JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Character.JsonResponse jsonResponse) {
                        System.out.println("onNext: " + jsonResponse);

                        character = jsonResponse.getData().getCharacterList().get(0);

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
                    }
                });

    }
}
