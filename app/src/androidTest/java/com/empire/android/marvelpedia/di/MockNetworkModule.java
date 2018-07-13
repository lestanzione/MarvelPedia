package com.empire.android.marvelpedia.di;

import okhttp3.mockwebserver.MockWebServer;

public class MockNetworkModule extends NetworkModule {

    private final MockWebServer server;

    public MockNetworkModule(MockWebServer server) {
        this.server = server;
    }

    @Override
    public String providesBaseUrl() {
        return server.url("/").toString();
    }

}
