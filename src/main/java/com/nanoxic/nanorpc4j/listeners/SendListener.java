package com.nanoxic.nanorpc4j.listeners;

import com.nanoxic.nanorpc4j.Callback;

public abstract class SendListener extends AbstractCallbackListener {

	public abstract void onNewSendCallback(Callback callback);

	@Override
	protected final void newCallback(Callback callback) {
		if (callback.isIs_send())
			onNewSendCallback(callback);
	}
}
