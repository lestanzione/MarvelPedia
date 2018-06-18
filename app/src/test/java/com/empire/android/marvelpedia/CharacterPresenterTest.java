package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.character.CharacterContract;
import com.empire.android.marvelpedia.character.CharacterPresenter;
import com.empire.android.marvelpedia.comic.ComicContract;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Image;
import com.empire.android.marvelpedia.data.Serie;
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

public class CharacterPresenterTest {

    CharacterContract.View mockView;
    CharacterContract.Repository mockCharacterRepository;
    ComicContract.Repository mockComicRepository;
    SerieContract.Repository mockSerieRepository;
    CharacterPresenter presenter;

    Character.JsonResponse defaultJsonResponse;
    Comic.JsonResponse defaultComicJsonResponse;
    Serie.JsonResponse defaultSerieJsonResponse;

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

        mockView = mock(CharacterContract.View.class);
        mockCharacterRepository = mock(CharacterContract.Repository.class);
        mockComicRepository = mock(ComicContract.Repository.class);
        mockSerieRepository = mock(SerieContract.Repository.class);

        presenter = new CharacterPresenter(mockCharacterRepository, mockComicRepository, mockSerieRepository);
        presenter.attachView(mockView);

        createCharacterJsonResponse();
        createComicJsonResponse();
        createSerieJsonResponse();
    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    private void createCharacterJsonResponse(){

        Character character = new Character();
        Image image = new Image();

        image.setPath("path");
        image.setExtension("extension");

        character.setName("Name");
        character.setDescription("Description");
        character.setImage(image);

        Character topLevel = new Character();

        List<Character> characterList = new ArrayList<>();
        characterList.add(character);

        Character.Data data = topLevel.new Data();
        defaultJsonResponse = topLevel.new JsonResponse();

        data.setCharacterList(characterList);
        data.setTotal(characterList.size());
        defaultJsonResponse.setData(data);

    }

    private void createComicJsonResponse(){

        Comic topLevel = new Comic();

        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic());
        comicList.add(new Comic());

        Comic.Data data = topLevel.new Data();
        defaultComicJsonResponse = topLevel.new JsonResponse();

        data.setComicList(comicList);
        data.setTotal(comicList.size());
        defaultComicJsonResponse.setData(data);

    }

    private void createSerieJsonResponse(){

        Serie topLevel = new Serie();

        List<Serie> serieList = new ArrayList<>();
        serieList.add(new Serie());
        serieList.add(new Serie());

        Serie.Data data = topLevel.new Data();
        defaultSerieJsonResponse = topLevel.new JsonResponse();

        data.setSerieList(serieList);
        data.setTotal(serieList.size());
        defaultSerieJsonResponse.setData(data);

    }

    @Test
    public void withCharacterIdShouldShowCharacterDetail(){

        when(mockCharacterRepository.getCharacter(anyLong())).thenReturn(Observable.just(defaultJsonResponse));
        when(mockComicRepository.getComicsByCharacterId(anyLong())).thenReturn(Observable.just(defaultComicJsonResponse));
        when(mockSerieRepository.getStoriesByCharacterId(anyLong())).thenReturn(Observable.just(defaultSerieJsonResponse));

        presenter.getCharacterInfo(anyLong());

        verify(mockView, times(1)).setSeeAllComicsVisible(false);
        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setCharacterName(anyString());
        verify(mockView, times(1)).setCharacterDescription(anyString());
        verify(mockView, times(1)).setCharacterImage(anyString());
        verify(mockView, times(1)).showComics(anyList());
        verify(mockView, times(1)).showSeries(anyList());
        verify(mockView, times(1)).setSeeAllComicsVisible(true);

    }

    @Test
    public void withErrorShouldHandle(){

        when(mockCharacterRepository.getCharacter(anyLong())).thenReturn(Observable.error(new Exception()));

        presenter.getCharacterInfo(anyLong());

        verify(mockView, times(1)).setSeeAllComicsVisible(false);
        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);

    }

}
