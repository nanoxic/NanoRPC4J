package com.nanoxic.nanorpc4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Object to easily represent an amount of NANO.
 * 
 * @author Koen De Voegt
 *
 */
public class NANO implements Comparable<NANO> {

	private static final BigDecimal factor = new BigDecimal("1000000000000000000000000000000");

	private static final int scale = 30;
	private static final RoundingMode roundingMode = RoundingMode.HALF_UP;

	/** The value 0 NANO */
	public static final NANO ZERO = new NANO("0");
	/** The value 1 NANO */
	public static final NANO ONE = new NANO("1");
	/** The value 10 NANO */
	public static final NANO TEN = new NANO("10");

	private BigInteger RAWamount;

	// Public constructors based on NANO value
	/**
	 * Translates the string representation of a NANO into a NANO. The string
	 * representation consists of an optional sign, '+' ( '\u002B') or '-'
	 * ('\u002D'), followed by a sequence of zero or more decimal digits ("the
	 * integer"), optionally followed by a fraction, optionally followed by an
	 * exponent.
	 * 
	 * The fraction consists of a decimal point followed by zero or more decimal
	 * digits. The string must contain at least one digit in either the integer or
	 * the fraction. The number formed by the sign, the integer and the fraction is
	 * referred to as the significand.
	 * 
	 * The exponent consists of the character 'e' ('\u0065') or 'E' ('\u0045')
	 * followed by one or more decimal digits.
	 * 
	 * @param NANOamount
	 *            String representation of NANO.
	 */
	public NANO(String NANOamount) {
		this(new BigDecimal(NANOamount));
	}

	/**
	 * Translates a BigDecimal into a NANO.
	 * 
	 * @param NANOamount
	 *            BigDecimal value to be converted to NANO.
	 */
	public NANO(BigDecimal NANOamount) {
		RAWamount = NANOamount.setScale(scale, roundingMode).multiply(factor).toBigIntegerExact();
	}

	// Static method to create a NANO object from RAW
	/**
	 * Creates a NANO object from a String representing an amount of RAW. The String
	 * representation consists of an optional minus sign followed by a sequence of
	 * one or more decimal digits. The character-to-digit mapping is provided by
	 * Character.digit. The String may not contain any extraneous characters
	 * (whitespace, for example).
	 * 
	 * @param RAWamount
	 *            Decimal String representation of an amount of RAW.
	 * @return The new NANO object
	 */
	public static NANO fromRAW(String RAWamount) {
		return new NANO(new BigInteger(RAWamount));
	}

	/**
	 * Creates a NANO object from a BigInteger representing an amount of RAW.
	 * 
	 * @param RAWamount
	 *            BigInteger representing an amount of RAW.
	 * @return The new NANO object
	 */
	public static NANO fromRAW(BigInteger RAWamount) {
		return new NANO(RAWamount);
	}

	// Kept private to avoid confusion
	private NANO(BigInteger RAWamount) {
		this.RAWamount = RAWamount;
	}

	// Getter
	/**
	 * Returns a BigInteger holding the value of this NANO in RAW.
	 * 
	 * @return The BigInteger holding the value of this NANO in RAW.
	 */
	public BigInteger getRAW() {
		return RAWamount;
	}

	// Methods
	/**
	 * Returns a BigDecimal holding the value of this NANO.
	 * 
	 * @return The BigDecimal holding the value of this NANO.
	 */
	public BigDecimal toBigDecimal() {
		BigDecimal RAWamountDecimal = new BigDecimal(RAWamount);
		return RAWamountDecimal.movePointLeft(30);
	}

	/**
	 * Returns the String representation of this NANO. The digit-to-character
	 * mapping provided by Character.forDigit is used, and a minus sign is prepended
	 * if appropriate. The returned string has a fixed default of 6 fractional
	 * Digits.
	 * 
	 * @return String representation of this NANO with 6 fraction digits.
	 */
	@Override
	public String toString() {
		return toString(6);
	}

	/**
	 * Returns the String representation of this NANO with given fraction digits.
	 * The digit-to-character mapping provided by Character.forDigit is used, and a
	 * minus sign is prepended if appropriate.
	 * 
	 * @param fractions
	 *            The amount of fraction digits required.
	 * @return String representation of this NANO with given fraction digits.
	 */
	public String toString(int fractions) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(fractions);
		df.setMinimumFractionDigits(fractions);
		return df.format(toBigDecimal());
	}

	// Mathematical functions
	/**
	 * Returns a NANO whose value is (this + augend).
	 * 
	 * @param augend
	 *            Value to be added to this NANO.
	 * @return this + augend
	 */
	public NANO add(NANO augend) {
		return NANO.fromRAW(this.RAWamount.add(augend.getRAW()));
	}

	/**
	 * Returns a NANO whose value is (this - subtrahend).
	 * 
	 * @param subtrahend
	 *            Value to be subtracted from this NANO.
	 * @return this - subtrahend
	 */
	public NANO subtract(NANO subtrahend) {
		return NANO.fromRAW(this.RAWamount.subtract(subtrahend.getRAW()));
	}

	/**
	 * Returns a NANO whose value is (this × multiplicand).
	 * 
	 * @param multiplicand
	 *            Value to be multiplied by this NANO.
	 * @return this * multiplicand
	 */
	public NANO multiply(NANO multiplicand) {
		return new NANO(this.toBigDecimal().multiply(multiplicand.toBigDecimal()));
	}

	/**
	 * Returns a BigDecimal whose value is (this / divisor).
	 * 
	 * @param divisor
	 *            Value by which this BigDecimal is to be divided.
	 * @return this / divisor
	 */
	public NANO divide(NANO divisor) {
		return new NANO(this.toBigDecimal().divide(divisor.toBigDecimal(), scale, roundingMode));
	}

	// Equals and Comparison methods
	/**
	 * Returns the hash code for this NANO.
	 * 
	 * @return Hash code for this NANO.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RAWamount == null) ? 0 : RAWamount.hashCode());
		return result;
	}

	/**
	 * Compares this NANO with the specified Object for equality.
	 * 
	 * @return True if and only if the specified Object is a NANO whose value is
	 *         numerically equal to this NANO.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NANO))
			return false;
		NANO other = (NANO) obj;
		if (RAWamount == null) {
			if (other.RAWamount != null)
				return false;
		} else if (!RAWamount.equals(other.RAWamount))
			return false;
		return true;
	}

	/**
	 * Compares this NANO with the specified NANO. This method is provided in
	 * preference to individual methods for each of the six boolean comparison
	 * operators ({@literal <}, ==, {@literal >}, {@literal >=}, !=, {@literal <=}).
	 * The suggested idiom for performing these comparisons is:
	 * {@code (x.compareTo(y)} &lt;<i>op</i>&gt; {@code 0)}, where &lt;<i>op</i>&gt;
	 * is one of the six comparison operators.
	 * 
	 * @param other
	 *            NANO to which this NANO is to be compared.
	 * @return -1, 0, or 1 as this NANO is numerically less than, equal to, or
	 *         greater than val.
	 */
	@Override
	public int compareTo(NANO other) {
		return this.getRAW().compareTo(other.getRAW());
	}

}
