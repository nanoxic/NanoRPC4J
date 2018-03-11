package com.nanoxic.nanorpc4j.examples;

import com.nanoxic.nanorpc4j.NanoRPC4J;
import com.nanoxic.nanorpc4j.model.account.Account;
import com.nanoxic.nanorpc4j.model.account.History;
import com.nanoxic.nanorpc4j.model.account.HistoryItem;

public class Test {

	public static void main(String[] args) {

		NanoRPC4J.init("localhost", 7096);

		Account account = new Account("02B471BC75525CD0EB7781A1297214FEE60D42787B03BC2D8D4E6AD05704592E");
		System.out.println(account.getAccount());
		System.out.println(account.getPublicKey());
		System.out.println(account.getBalance());

		System.out.println(account.getPending());
		System.out.println(account.getBlockCount());
		System.out.println(account.getRepresentative());
		System.out.println();

		account = new Account("xrb_11ong8y9cnkwt5oqh1f377s3bzq83o39iyr5qiprtmmct3diapbg6nuq3ygd");
		System.out.println(account.getAccount());
		System.out.println(account.getPublicKey());
		System.out.println(account.getBalance());
		System.out.println(account.getPending());
		System.out.println(account.getBlockCount());
		System.out.println(account.getRepresentative());
		System.out.println();
		System.out.println(account.getInfo().getBalance());
		System.out.println(account.getInfo().getBlock_count());
		System.out.println(account.getInfo().getFrontier());
		System.out.println(account.getInfo().getModified_timestamp());
		System.out.println(account.getInfo().getOpen_block());
		System.out.println(account.getInfo().getPending());
		System.out.println(account.getInfo().getRepresentative());
		System.out.println(account.getInfo().getRepresentative_block());
		System.out.println(account.getInfo().getWeight());
		System.out.println();
		System.out.println(account.getInfo(true, true, true).getPending());
		System.out.println(account.getInfo(true, true, true).getRepresentative());
		System.out.println(account.getInfo(true, true, true).getRepresentative_block());
		System.out.println(account.getInfo(true, true, true).getWeight());
		System.out.println();
		History history = account.getHistory();
		for (HistoryItem i : history.getHistory()) {
			System.out.println(i.getAccount());
			System.out.println(i.getAmount());
			System.out.println(i.getHash());
			System.out.println(i.getType());
		}
		System.out.println();
		history = account.getHistory(20);
		for (HistoryItem i : history.getHistory()) {
			System.out.println(i.getAccount());
			System.out.println(i.getAmount());
			System.out.println(i.getHash());
			System.out.println(i.getType());
		}
	}

}
