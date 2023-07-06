package org.cinow.omh.data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.cinow.omh.indicators.IndicatorType;

public abstract class DataItemMultiple {
	
	/**
	 * The indicator values.
	 */
	private Map<String, BigDecimal> values = new TreeMap<>();

	/**
	 * The indicator universe value.
	 */
	private Map<String, BigDecimal> universeValues = new TreeMap<>();

	/**
	 * Whether the data value is suppressed.
	 */
	private Map<String, Boolean> suppresseds = new TreeMap<>();

	/**
	 * The indicator count value.
	 */
	private Map<String, BigDecimal> countValues = new TreeMap<>();

	/**
	 * The indicator universe margin of error.
	 */
	private Map<String, BigDecimal> universeMoes = new TreeMap<>();

	/**
	 * The indicator count margin of error.
	 */
	private Map<String, BigDecimal> countMoes = new TreeMap<>();

	private IndicatorType indicatorType;

	private int ratePer;

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		BigDecimal value = null;
		switch (this.indicatorType.getId()) {
			case IndicatorType.COUNT:
				try {
					value = this.getCountValue().setScale(0, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.PERCENTAGE:
				try {
					value = this.getCountValue().divide(this.getUniverseValue(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.RATE:
				try {
					value = this.getCountValue().divide(this.getUniverseValue(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(this.ratePer)).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.CURRENCY:
				try {
					value = this.getValues().values().stream().reduce(BigDecimal.ZERO, (a, b) -> {
						if (a == null) {
							return b;
						}
						if (b == null) {
							return a;
						}
						return a.add(b);
					}).divide(BigDecimal.valueOf(this.getValues().values().size()), 4, RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
		}

		return value;
	}

	/**
	 * @return the moeHigh
	 */
	public BigDecimal getMoeHigh() {
		try {
			BigDecimal moeHigh = BigDecimal.ZERO.max(this.getValue().add(this.getMoe())).setScale(1, RoundingMode.HALF_UP);
			if (IndicatorType.PERCENTAGE.equals(this.indicatorType.getId())) {
				moeHigh = BigDecimal.valueOf(100).min(moeHigh);
			}
			return moeHigh;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * @return the moeLow
	 */
	public BigDecimal getMoeLow() {
		try {
			return BigDecimal.ZERO.max(this.getValue().subtract(this.getMoe())).setScale(1, RoundingMode.HALF_UP);
		} catch (NullPointerException e) {
			return null;
		}
	}

	private BigDecimal getMoe() {
		List<BigDecimal> moes = new ArrayList<>();
		BigDecimal moe = null;
		for (Entry<String, BigDecimal> countMoe : countMoes.entrySet()) {
			switch (this.indicatorType.getId()) {
				case IndicatorType.COUNT:
					try {
						moe = this.countMoes.get(countMoe.getKey());
					} catch (NumberFormatException e) {
						moe = null;
					}
					break;
				case IndicatorType.PERCENTAGE:
					try {
						moe = countMoe.getValue().pow(2)
							.subtract(this.values.get(countMoe.getKey()).multiply(BigDecimal.valueOf(.01)).pow(2)
								.multiply(universeMoes.get(countMoe.getKey()).pow(2)))
							.sqrt(new MathContext(4, RoundingMode.HALF_UP))
							.divide(this.universeValues.get(countMoe.getKey()), 4, RoundingMode.HALF_UP)
							.multiply(BigDecimal.valueOf(100));
					} catch (ArithmeticException | NumberFormatException e) {
						moe = null;
					}
					break;
				case IndicatorType.RATE:
					try {
						moe = countMoe.getValue().pow(2)
							.add(this.values.get(countMoe.getKey()).pow(2)
								.multiply(universeMoes.get(countMoe.getKey()).pow(2)))
							.sqrt(new MathContext(4, RoundingMode.HALF_UP))
							.divide(this.universeValues.get(countMoe.getKey()), 4, RoundingMode.HALF_UP);
					} catch (ArithmeticException | NumberFormatException e) {
						moe = null;
					}
					break;
				case IndicatorType.CURRENCY:
					try {
						moe = null; //TODO: need currency MOE calculation
					} catch (ArithmeticException | NumberFormatException e) {
						moe = null;
					}
					break;
			}

			moes.add(moe);
		}

		moe = moes
			.stream()
			.map(m -> m.pow(2))
			.reduce(BigDecimal.ZERO, (a, b) -> {
				if (a == null) {
					return b;
				}
				if (b == null) {
					return a;
				}
				return a.add(b);
			});

		return moe.sqrt(new MathContext(1, RoundingMode.HALF_UP));
	}

	/**
	 * @return the suppressed
	 */
	public boolean isSuppressed() {
		return this.suppresseds.values().stream().anyMatch(s -> s);
	}

	/**
	 * @return the universeValue
	 */
	public BigDecimal getUniverseValue() {
		return this.universeValues.values().stream().reduce(BigDecimal.ZERO, (a, b) -> {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
			return a.add(b);
		});
	}

	public BigDecimal getCountValue() {
		return this.countValues.values().stream().reduce(BigDecimal.ZERO, (a, b) -> {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
			return a.add(b);
		});
	}


	public Map<String, BigDecimal> getValues() {
		return values;
	}

	public void setValues(Map<String, BigDecimal> values) {
		this.values = values;
	}

	public Map<String, BigDecimal> getUniverseValues() {
		return universeValues;
	}

	public void setUniverseValues(Map<String, BigDecimal> universeValues) {
		this.universeValues = universeValues;
	}

	public Map<String, Boolean> getSuppresseds() {
		return suppresseds;
	}

	public void setSuppresseds(Map<String, Boolean> suppresseds) {
		this.suppresseds = suppresseds;
	}

	public Map<String, BigDecimal> getCountValues() {
		return countValues;
	}

	public void setCountValues(Map<String, BigDecimal> countValues) {
		this.countValues = countValues;
	}

	public Map<String, BigDecimal> getUniverseMoes() {
		return universeMoes;
	}

	public void setUniverseMoes(Map<String, BigDecimal> universeMoes) {
		this.universeMoes = universeMoes;
	}

	public Map<String, BigDecimal> getCountMoes() {
		return countMoes;
	}

	public void setCountMoes(Map<String, BigDecimal> countMoes) {
		this.countMoes = countMoes;
	}

	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	public int getRatePer() {
		return ratePer;
	}

	public void setRatePer(int ratePer) {
		this.ratePer = ratePer;
	}

}
