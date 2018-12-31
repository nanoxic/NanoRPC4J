package com.nanoxic.nanorpc4j.listeners;

import com.nanoxic.nanorpc4j.Callback;

// TODO document
public abstract class CallbackListener extends AbstractCallbackListener {

	public abstract void onNewCallback(Callback callback);

	@Override
	protected final void newCallback(Callback callback) {
		onNewCallback(callback);
	}

}
