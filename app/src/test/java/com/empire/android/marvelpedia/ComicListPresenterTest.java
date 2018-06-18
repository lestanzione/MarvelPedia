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

    ComicListContract.View mockView;
    ComicListContract.Repository mockRepository;
    ComicListPresenter presenter;

    Comic.JsonResponse defaultJsonResponse;
    String defaultQuery = "defaultQuery";
    Long defaultComicId = 1L;

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
    public void noQueryShouldShowComics(){

        when(mockRepository.getComics(anyLong(), anyInt(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withQueryShouldShowComics(){

        when(mockRepository.getComics(anyLong(), anyInt(), anyString())).thenReturn(Observable.just(defaultJsonResponse));

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

        when(mockRepository.getComics(anyLong(), anyInt(), anyString())).thenReturn(Observable.error(new Exception()));

        presenter.setSearchQuery(defaultQuery);
        presenter.getComics();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);

    }

}
