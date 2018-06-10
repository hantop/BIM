package com.zzrbi.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class UtilValidate {

    public static boolean areEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
	}

	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)) {
			return ((String) value).length() == 0;
		}
		if ((value instanceof Collection)) {
			return ((Collection) value).size() == 0;
		}
		if ((value instanceof Map)) {
			return ((Map) value).size() == 0;
		}
		if ((value instanceof CharSequence)) {
			return ((CharSequence) value).length() == 0;
		}
		if ((value instanceof Boolean)) {
			return false;
		}
		if ((value instanceof Number)) {
			return false;
		}
		if ((value instanceof Character)) {
			return false;
		}
		if ((value instanceof Date)) {
			return false;
		}
		return false;
	}

	public static boolean permissionListStr(Object value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(String s) {

        return (s == null) || (s.length() == 0);
    }

	public static boolean isEmpty(Collection<?> c) {
		return (c == null) || (c.size() == 0);
	}

	public static boolean isNotEmpty(String s) {
		return (s != null) && (s.length() > 0);
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return (c != null) && (c.size() > 0);
	}

	public static boolean isString(Object obj) {
		return (obj != null) && ((obj instanceof String));
	}
	
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
}
