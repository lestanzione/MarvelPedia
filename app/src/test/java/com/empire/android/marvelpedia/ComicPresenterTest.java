package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.character.CharacterContract;
import com.empire.android.marvelpedia.character.CharacterPresenter;
import com.empire.android.marvelpedia.comic.ComicContract;
import com.empire.android.marvelpedia.comic.ComicPresenter;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Image;
import com.empire.android.marvelpedia.serie.SerieContract;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComicPresenterTest {

    ComicContract.View mockView;
    ComicContract.Repository mockComicRepository;
    CharacterContract.Repository mockCharacterRepository;
    ComicPresenter presenter;

    Comic.JsonResponse defaultComicJsonResponse;
    Character.JsonResponse defaultCharacterJsonResponse;

    @BeforeClass
    public static void setupRxSchedulers() {

        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }

    @Before
    public void setup() {

        mockView = mock(ComicContract.View.class);
        mockCharacterRepository = mock(CharacterContract.Repository.class);
        mockComicRepository = mock(ComicContract.Repository.class);

        presenter = new ComicPresenter(mockComicRepository, mockCharacterRepository);
        presenter.attachView(mockView);

        createCharacterJsonResponse();
        createComicJsonResponse();
    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    private void createCharacterJsonResponse(){

        Character topLevel = new Character();

        List<Character> characterList = new ArrayList<>();
        characterList.add(new Character());
        characterList.add(new Character());

        Character.Data data = topLevel.new Data();
        defaultCharacterJsonResponse = topLevel.new JsonResponse();

        data.setCharacterList(characterList);
        data.setTotal(characterList.size());
        defaultCharacterJsonResponse.setData(data);

    }

    private void createComicJsonResponse(){

        Comic comic = new Comic();
        Image image = new Image();

        image.setPath("path");
        image.setExtension("extension");

        comic.setTitle("Title");
        comic.setDescription("Description");
        comic.setImage(image);

        Comic topLevel = new Comic();

        List<Comic> comicList = new ArrayList<>();
        comicList.add(comic);

        Comic.Data data = topLevel.new Data();
        defaultComicJsonResponse = topLevel.new JsonResponse();

        data.setComicList(comicList);
        data.setTotal(comicList.size());
        defaultComicJsonResponse.setData(data);

    }

    @Test
    public void withComicIdShouldShowComicDetail(){

        when(mockComicRepository.getComicById(anyLong())).thenReturn(Observable.just(defaultComicJsonResponse));
        when(mockCharacterRepository.getCharactersByComicId(anyLong())).thenReturn(Observable.just(defaultCharacterJsonResponse));

        presenter.getComicInfo(anyLong());

        verify(mockView, times(1)).setSeeAllCharactersVisible(false);
        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setComicName(anyString());
        verify(mockView, times(1)).setComicDescription(anyString());
        verify(mockView, times(1)).setComicImage(anyString());
        verify(mockView, times(1)).showCharacters(anyList());
        verify(mockView, times(1)).setSeeAllCharactersVisible(true);

    }

    @Test
    public void withErrorShouldHandle(){

        when(mockComicRepository.getComicById(anyLong())).thenReturn(Observable.error(new Exception()));

        presenter.getComicInfo(anyLong());

        verify(mockView, times(1)).setSeeAllCharactersVisible(false);
        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);

    }

}
