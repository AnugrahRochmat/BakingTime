package com.example.android.bakingtime.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Anugrah on 8/23/17.
 */

public class WidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
