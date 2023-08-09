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

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	private boolean aggregable;

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		if (this.isSuppressed() || !this.aggregable || BigDecimal.ZERO.equals(this.getUniverseValue())) {
			return null;
		}

		BigDecimal value = null;
		switch (this.indicatorType.getId()) {
			case IndicatorType.COUNT:
				try {
					value = this.getValueSum().setScale(0, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.PERCENTAGE:
				try {
					value = this.getCountValue().divide(this.getUniverseValue(), 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.RATE:
				try {
					value = this.getCountValue().divide(this.getUniverseValue(), 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(this.ratePer)).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.CURRENCY:
				value = null; // no aggregation for currency indicators
				break;
		}

		return value;
	}

	/**
	 * @return the moeHigh
	 */
	public BigDecimal getMoeHigh() {
		if (this.isSuppressed() || !this.aggregable  || BigDecimal.ZERO.equals(this.getUniverseValue())) {
			return null;
		}

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
		if (this.isSuppressed() || !this.aggregable  || BigDecimal.ZERO.equals(this.getUniverseValue())) {
			return null;
		}

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
							.sqrt(new MathContext(8, RoundingMode.HALF_UP))
							.divide(this.universeValues.get(countMoe.getKey()), 8, RoundingMode.HALF_UP)
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
							.sqrt(new MathContext(8, RoundingMode.HALF_UP))
							.divide(this.universeValues.get(countMoe.getKey()), 8, RoundingMode.HALF_UP);
					} catch (ArithmeticException | NumberFormatException e) {
						moe = null;
					}
					break;
				case IndicatorType.CURRENCY:
					moe = null; // no aggregation for currency indicators
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

		return moe.sqrt(new MathContext(8, RoundingMode.HALF_UP)).setScale(1, RoundingMode.HALF_UP);
	}

	/**
	 * @return the suppressed
	 */
	public boolean isSuppressed() {
		boolean suppressed = false;
		// suppress if any suppressed count value is null
		if (this.suppresseds.entrySet().stream().filter(s -> s.getValue()).map(s -> s.getKey()).anyMatch(k -> this.countValues.get(k) == null)) {
			suppressed = true;
		// suppress if only one value is suppressed
		} else if (this.suppresseds.values().stream().filter(s -> s).count() == 1) {
			suppressed = true;
		// or suppress if more than one value is suppressed and all suppressed counts are equal to 1
		} else if (this.suppresseds.values().stream().filter(s -> s).count() > 1) {
			suppressed = this.suppresseds.entrySet().stream().filter(e -> e.getValue()).allMatch(e -> {
				return this.countValues.get(e.getKey()) != null && BigDecimal.ONE.compareTo(this.countValues.get(e.getKey())) == 0;
			});
		}

		return suppressed;
	}

	/**
	 * @return the universeValue
	 */
	public BigDecimal getUniverseValue() {
		if (this.isSuppressed() || !this.aggregable) {
			return null;
		}

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
		if (this.isSuppressed() || !this.aggregable) {
			return null;
		}
		
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

	@JsonIgnore
	public BigDecimal getValueSum() {
		return this.values.values().stream().reduce(BigDecimal.ZERO, (a, b) -> {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
			return a.add(b);
		});
	}

	@JsonIgnore
	public Map<String, BigDecimal> getValues() {
		return values;
	}

	public void setValues(Map<String, BigDecimal> values) {
		this.values = values;
	}

	@JsonIgnore
	public Map<String, BigDecimal> getUniverseValues() {
		return universeValues;
	}

	public void setUniverseValues(Map<String, BigDecimal> universeValues) {
		this.universeValues = universeValues;
	}

	@JsonIgnore
	public Map<String, Boolean> getSuppresseds() {
		return suppresseds;
	}

	public void setSuppresseds(Map<String, Boolean> suppresseds) {
		this.suppresseds = suppresseds;
	}

	@JsonIgnore
	public Map<String, BigDecimal> getCountValues() {
		return countValues;
	}

	public void setCountValues(Map<String, BigDecimal> countValues) {
		this.countValues = countValues;
	}

	@JsonIgnore
	public Map<String, BigDecimal> getUniverseMoes() {
		return universeMoes;
	}

	public void setUniverseMoes(Map<String, BigDecimal> universeMoes) {
		this.universeMoes = universeMoes;
	}

	@JsonIgnore
	public Map<String, BigDecimal> getCountMoes() {
		return countMoes;
	}

	public void setCountMoes(Map<String, BigDecimal> countMoes) {
		this.countMoes = countMoes;
	}

	@JsonIgnore
	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	@JsonIgnore
	public int getRatePer() {
		return ratePer;
	}

	public void setRatePer(int ratePer) {
		this.ratePer = ratePer;
	}

	@JsonIgnore
	public boolean isAggregable() {
		return aggregable;
	}

	public void setAggregable(boolean aggregable) {
		this.aggregable = aggregable;
	}
}
