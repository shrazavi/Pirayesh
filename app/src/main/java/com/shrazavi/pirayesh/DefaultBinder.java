package com.shrazavi.pirayesh;

import android.os.Binder;

public class DefaultBinder extends Binder {
    ClosingService s;

    public DefaultBinder(ClosingService s) {
        this.s = s;
    }

    public ClosingService getService() {
        return s;
    }
}
