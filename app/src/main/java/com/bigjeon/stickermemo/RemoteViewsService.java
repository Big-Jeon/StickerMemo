package com.bigjeon.stickermemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class RemoteViewsService extends android.widget.RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.bigjeon.stickermemo.RemoteViewsFactory(this.getApplicationContext());
    }
}