package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.characterlist.CharacterListContract;
import com.empire.android.marvelpedia.characterlist.CharacterListPresenter;
import com.empire.android.marvelpedia.data.Character;

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

public class CharacterListPresenterTest {

    private CharacterListContract.View mockView;
    private CharacterListContract.Repository mockRepository;
    private CharacterListPresenter presenter;

    private Character.JsonResponse defaultJsonResponse;
    private String defaultQuery = "defaultQuery";
    private Long defaultComicId = 1L;
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

        mockView = mock(CharacterListContract.View.class);
        mockRepository = mock(CharacterListContract.Repository.class);

        presenter = new CharacterListPresenter(mockRepository);
        presenter.attachView(mockView);

        createCharacterJsonResponse();
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
        defaultJsonResponse = topLevel.new JsonResponse();

        data.setCharacterList(characterList);
        data.setTotal(characterList.size());
        defaultJsonResponse.setData(data);

    }

    @Test
    public void noQueryAndNoComicIdShouldShowCharacters(){

        when(mockRepository.getCharacters(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.getCharacters();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withQueryAndNoComicIdShouldShowCharacters(){

        when(mockRepository.getCharacters(anyInt(), anyString(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setSearchQuery(defaultQuery);
        presenter.getCharacters();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void noQueryAndWithComicIdShouldShowCharacters(){

        when(mockRepository.getCharacters(anyInt(), isNull(), anyLong())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setComicId(defaultComicId);
        presenter.getCharacters();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withQueryAndWithComicIdShouldShowCharacters(){

        when(mockRepository.getCharacters(anyInt(), anyString(), anyLong())).thenReturn(Observable.just(defaultJsonResponse));

        presenter.setComicId(defaultComicId);
        presenter.setSearchQuery(defaultQuery);
        presenter.getCharacters();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showList(anyList());
        verify(mockView, times(1)).setPagesText(anyInt(), anyInt());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setPreviousButtonEnable(anyBoolean());
        verify(mockView, times(1)).setNextButtonEnable(anyBoolean());

    }

    @Test
    public void withErrorShouldHandle(){

        when(mockRepository.getCharacters(anyInt(), anyString(), anyLong())).thenReturn(Observable.error(new Exception()));

        presenter.setComicId(defaultComicId);
        presenter.setSearchQuery(defaultQuery);
        presenter.getCharacters();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);

    }

    @Test
    public void nextPageClickedShouldAddPageAndGetCharacters(){

        when(mockRepository.getCharacters(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

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

        when(mockRepository.getCharacters(anyInt(), isNull(), isNull())).thenReturn(Observable.just(defaultJsonResponse));

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
