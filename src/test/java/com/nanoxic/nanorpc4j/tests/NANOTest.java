package com.nanoxic.nanorpc4j.tests;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;

import com.nanoxic.nanorpc4j.NANO;

public class NANOTest {

	NANO NANO1 = NANO.ONE;
	NANO NANO2 = new NANO("2");
	NANO NANO3 = new NANO("3");
	NANO NANO4 = new NANO("4");

	@Test
	public void NANOtoRAWoneRAWtest() {
		NANO amount = new NANO(new BigDecimal("0.000000000000000000000000000001"));
		assertEquals(BigInteger.ONE, amount.getRAW());
	}

	@Test
	public void NANOtoRAWoneNANOtest() {
		NANO amount = new NANO(BigDecimal.ONE);
		assertEquals(new BigInteger("1000000000000000000000000000000"), amount.getRAW());
	}

	@Test
	public void RAWtoNANOoneRAWtest() {
		NANO amount = NANO.fromRAW(BigInteger.ONE);
		assertEquals(new NANO("0.000000000000000000000000000001"), amount);
	}

	@Test
	public void RAWtoNANOoneNANOtest() {
		NANO amount = NANO.fromRAW(new BigInteger("1000000000000000000000000000000"));
		assertEquals(NANO.ONE, amount);
	}

	// addition
	@Test
	public void additionTest() {
		NANO sum = NANO2.add(NANO4);
		assertEquals(new NANO("6"), sum);
	}

	// subtraction
	@Test
	public void subtractionTest() {
		NANO subtraction = NANO4.subtract(NANO2);
		assertEquals(new NANO("2"), subtraction);
	}

	// multiplication
	@Test
	public void multiplicationTest() {
		NANO multiplication = NANO2.multiply(NANO4);
		assertEquals(new NANO("8"), multiplication);
	}

	// division
	@Test
	public void divisionTest() {
		NANO division = NANO4.divide(NANO2);
		assertEquals(new NANO("2"), division);
	}

	@Test
	public void nonTerminatingDivisionTest() {
		NANO division = NANO1.divide(NANO3);
		assertEquals(new NANO("0.333333333333333333333333333333"), division);
	}

	@Test
	public void loopTest() {
		for (int x = 0; x < 500; x++) { // 0000
			BigDecimal randomBD1 = new BigDecimal(Math.random()).movePointRight(7);
			BigDecimal randomBD2 = new BigDecimal(Math.random()).movePointRight(7);
			NANO randomN1 = new NANO(randomBD1);
			NANO randomN2 = new NANO(randomBD2);

			randomBD1 = randomBD1.setScale(30, RoundingMode.HALF_UP);
			randomBD2 = randomBD2.setScale(30, RoundingMode.HALF_UP);

			BigDecimal divideDB = randomBD1.divide(randomBD2, 30, RoundingMode.HALF_UP);
			BigDecimal multiplyDB = randomBD1.multiply(randomBD2).setScale(30, RoundingMode.HALF_UP);
			BigDecimal addDB = randomBD1.add(randomBD2);
			BigDecimal subDB = randomBD1.subtract(randomBD2);
			NANO divideN = randomN1.divide(randomN2);
			NANO multiplyN = randomN1.multiply(randomN2);
			NANO addN = randomN1.add(randomN2);
			NANO subN = randomN1.subtract(randomN2);

			if (!divideDB.equals(divideN.toBigDecimal())) {
				System.out.println(randomBD1);
				System.out.println(randomN1.toBigDecimal());
				System.out.println(randomBD2);
				System.out.println(randomN2.toBigDecimal());
				System.out.println(divideDB);
				System.out.println(divideN.toBigDecimal());
				System.out.println("D=========================================================");
			}
			assertEquals(divideDB, divideN.toBigDecimal());
			if (!multiplyDB.equals(multiplyN.toBigDecimal())) {
				System.out.println(randomBD1);
				System.out.println(randomN1.toBigDecimal());
				System.out.println(randomBD2);
				System.out.println(randomN2.toBigDecimal());
				System.out.println(multiplyDB);
				System.out.println(multiplyN.toBigDecimal());
				System.out.println("M=========================================================");
			}
			assertEquals(multiplyDB, multiplyN.toBigDecimal());
			if (!addDB.equals(addN.toBigDecimal())) {
				System.out.println(randomBD1);
				System.out.println(randomN1.toBigDecimal());
				System.out.println(randomBD2);
				System.out.println(randomN2.toBigDecimal());
				System.out.println(addDB);
				System.out.println(addN.toBigDecimal());
				System.out.println("M=========================================================");
			}
			assertEquals(addDB, addN.toBigDecimal());
			if (!subDB.equals(subN.toBigDecimal())) {
				System.out.println(randomBD1);
				System.out.println(randomN1.toBigDecimal());
				System.out.println(randomBD2);
				System.out.println(randomN2.toBigDecimal());
				System.out.println(subDB);
				System.out.println(subN.toBigDecimal());
				System.out.println("M=========================================================");
			}
			assertEquals(subDB, subN.toBigDecimal());
		}
	}

}
