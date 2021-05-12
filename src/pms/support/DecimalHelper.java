package pms.support;

public class DecimalHelper {

	private DecimalHelper() {
	}

	/**
	 * 根据传入精度进行四舍五入
	 *
	 * @param d
	 * @param precision
	 * @return
	 */
	public static double doubleRound(double d, int precision) {
		double newDouble = d * Math.pow(10, precision);
		long integerPart = (long) newDouble;
		double decimalPart = newDouble - integerPart;
		if (doubleCompare(Math.abs(decimalPart), 0.5D, 1) >= 0) {
			if (decimalPart > 0) {
				integerPart += 1;
			} else {
				integerPart -= 1;
			}
		}
		return integerPart / Math.pow(10, precision);
	}

	/**
	 * 根据传入精度进行舍入，小于5时进行舍，大于5时进行入，等于5时，前一位是偶数进行舍，前一位是奇数进行入
	 *
	 * @param d
	 * @param precision
	 * @return
	 */
	public static double doubleCurrencyRound(double d, int precision) {
		double newDouble = d * Math.pow(10, precision);
		long integerPart = (long) newDouble;
		double decimalPart = newDouble - integerPart;
		int compareResult = doubleCompare(Math.abs(decimalPart), 0.5D, 1);
		if ((compareResult > 0) || (compareResult == 0 && integerPart % 2 != 0)) {
			if (decimalPart > 0) {
				integerPart += 1;
			} else {
				integerPart -= 1;
			}
		}
		return integerPart / Math.pow(10, precision);
	}

	/**
	 * 根据传入的有效位数，进行两个double数之间的比较
	 *
	 * @param d1
	 * @param d2
	 * @param comparePrecision
	 * @return
	 */
	public static int doubleCompare(double d1, double d2, int comparePrecision) {
		if ((d1 - d2) > 1.0D * Math.pow(10, -(comparePrecision + 1))) {
			return 1;
		}
		if ((d2 - d1) > 1.0D * Math.pow(10, -(comparePrecision + 1))) {
			return -1;
		} else {
			return 0;
		}
	}

}
