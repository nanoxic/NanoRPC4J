package com.nanoxic.nanorpc4j;

class RequestPassword extends RequestWallet {

	private static final long serialVersionUID = 3653867039357232888L;

	private String password;

	public RequestPassword(String action, String wallet) {
		super(action, wallet);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
