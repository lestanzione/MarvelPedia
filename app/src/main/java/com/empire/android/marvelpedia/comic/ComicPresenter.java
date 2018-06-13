package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.data.Comic;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ComicPresenter implements ComicContract.Presenter {

    private ComicContract.View view;
    private ComicContract.Repository repository;

    private Comic comic;

    public ComicPresenter(ComicContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void attachView(ComicContract.View view) {
        this.view = view;
    }

    @Override
    public void getComicInfo(long comicId) {

        view.setProgressBarVisible(true);

        repository.getComicById(comicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Comic.JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Comic.JsonResponse jsonResponse) {
                        System.out.println("onNext: " + jsonResponse);
                        comic = jsonResponse.getData().getComicList().get(0);
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
                    }
                });

    }

}
