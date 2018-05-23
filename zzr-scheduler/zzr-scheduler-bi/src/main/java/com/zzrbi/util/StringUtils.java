package com.zzrbi.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StringUtils {
	
	public static String objectToString(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj);
	}
	
	public static boolean objectToBoolean(Object obj) {
		if (obj == null) {
			return false;
		}
		return Boolean.valueOf(objectToString(obj));
	}

	public static Integer objectToInteger(Object obj) {
		if (obj == null) {
			return Integer.valueOf(0);
		}
		return Integer.valueOf(objectToString(obj));
	}

	public static BigInteger objectToBigInteger(Object obj) {
		if (obj == null) {
			return BigInteger.valueOf(0L);
		}
		return BigInteger.valueOf(objectToLong(obj).longValue());
	}

	public static Long objectToLong(Object obj) {
		if (obj == null) {
			return Long.valueOf(0L);
		}
		return Long.valueOf(objectToString(obj));
	}

	public static Double objectToDouble(Object obj) {
		if (obj == null) {
			return Double.valueOf(0.0D);
		}
		return Double.valueOf(objectToString(obj));
	}

	public static BigDecimal objectToBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(objectToDouble(obj).doubleValue());
	}

	public static boolean isEmpty(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return false;
		}
		return true;
	}
}
