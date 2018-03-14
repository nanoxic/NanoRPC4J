package com.nanoxic.nanorpc4j.model.wallet;

class ContainsRequest extends WalletRequest {

	private static final long serialVersionUID = -5776942498343561492L;

	private String account;

	public ContainsRequest(String action, String wallet) {
		super(action, wallet);
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
