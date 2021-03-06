package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.comiclist.ComicListContract;
import com.empire.android.marvelpedia.comiclist.ComicListPresenter;
import com.empire.android.marvelpedia.data.Comic;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComicListPresenterTest {

    private ComicListContract.View mockView;
    private ComicListContract.Repository mockRepository;
    private ComicListPresenter presenter;

    private Comic.JsonResponse defaultJsonResponse;
    private String defaultQuery = "defaultQuery";
    private Long defaultCharacterId = 1L;
    private int defaultCurrentPage = 3;

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

        mockView = mock(ComicListContract.View.class);
        mockRepository = mock(ComicListContract.Repository.class);

        presenter = new ComicListPresenter(mockRepository);
        presenter.attachView(mockView);

        createComicJsonResponse();
    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    private void createComicJsonResponse(){

        Comic topLevel = new Comic();

        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic());
        comicList.add(new Comic());

        Comic.Data data = topLevel.new Data();
        defaultJsonResponse = topLevel.new JsonResponse();

        data.setComicList(comicList);
        data.setTotal(comicList.size());
        defaultJsonResponse.setData(data);

    }

    @Test
    public void noQueryAndNoCharacterIdShouldShowComics(){

        when(mockRepository.getComics(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withQueryAndNoCharacterIdShouldShowComics(){

        when(mockRepository.getComics(anyInt(), anyString(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setSearchQuery(defaultQuery);
        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void noQueryAndWithCharacterIdShouldShowComics(){

        when(mockRepository.getComics(anyInt(), isNull(), anyLong())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setCharacterId(defaultCharacterId);
        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withQueryAndWithCharacterIdShouldShowComics(){

        when(mockRepository.getComics(anyInt(), anyString(), anyLong())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setCharacterId(defaultCharacterId);
        presenter.setSearchQuery(defaultQuery);
        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withErrorShouldHandle(){

        when(mockRepository.getComics(anyInt(), anyString(), anyLong())).thenReturn(Observable.error(new Exception()));

        presenter.setCharacterId(defaultCharacterId);
        presenter.setSearchQuery(defaultQuery);
        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);

    }

    @Test
    public void nextPageClickedShouldAddPageAndGetComics(){

        when(mockRepository.getComics(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setCurrentPage(defaultCurrentPage);
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

        presenter.nextPageButtonClicked();
        assertEquals(defaultCurrentPage+1, presenter.getCurrentPage());

    }

    @Test
    public void nextPageClickedWhenRunningShouldIgnore(){

        presenter.setIsRunning(true);

        presenter.setCurrentPage(defaultCurrentPage);
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

        presenter.nextPageButtonClicked();
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

    }

    @Test
    public void previousPageClickedShouldSubtractPageAndGetCharacters(){

        when(mockRepository.getComics(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setCurrentPage(defaultCurrentPage);
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

        presenter.previousPageButtonClicked();
        assertEquals(defaultCurrentPage-1, presenter.getCurrentPage());

    }

    @Test
    public void previousPageClickedWhenRunningShouldIgnore(){

        presenter.setIsRunning(true);

        presenter.setCurrentPage(defaultCurrentPage);
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

        presenter.previousPageButtonClicked();
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

    }

    @Test
    public void resetPageNumberShouldReset(){

        presenter.setCurrentPage(defaultCurrentPage);
        assertEquals(defaultCurrentPage, presenter.getCurrentPage());

        presenter.resetPageNumber();
        assertEquals(1, presenter.getCurrentPage());

    }

    @Test
    public void getOffsetShouldCalculateOffset(){

        presenter.setCurrentPage(defaultCurrentPage);
        presenter.setDataPerPage(20);

        assertEquals(40, presenter.getOffset(presenter.getCurrentPage()));

        presenter.resetPageNumber();
        assertEquals(0, presenter.getOffset(presenter.getCurrentPage()));

    }

}
