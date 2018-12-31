package com.nanoxic.nanorpc4j.listeners;

import com.nanoxic.nanorpc4j.Callback;

abstract class AbstractCallbackListener {
	abstract protected void newCallback(Callback callback);
}
