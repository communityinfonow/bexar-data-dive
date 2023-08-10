package org.cinow.omh.data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

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

	/**
	 * The value moes.
	 */
	private Map<String, BigDecimal> valueMoes = new TreeMap<>();

	/**
	 * The indicator type.
	 */
	private IndicatorType indicatorType;

	/**
	 * The indicator rate per.
	 */
	private int ratePer;

	/**
	 * Whether the indicator is aggregable.
	 */
	private boolean aggregable;

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		if (this.returnNullValues()) {
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
					value = this.getCountSum().divide(this.getUniverseSum(), 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP);
				} catch (ArithmeticException | NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.RATE:
				try {
					value = this.getCountSum().divide(this.getUniverseSum(), 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(this.ratePer)).setScale(1, RoundingMode.HALF_UP);
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
		if (this.returnNullValues()) {
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
		if (this.returnNullValues()) {
			return null;
		}

		try {
			return BigDecimal.ZERO.max(this.getValue().subtract(this.getMoe())).setScale(1, RoundingMode.HALF_UP);
		} catch (NullPointerException e) {
			return null;
		}
	}

	private BigDecimal getMoe() {
		BigDecimal moe = null;
		try {
			BigDecimal valueMoe = this.valueMoes.values().stream()
				.map(m -> m.pow(2))
				.reduce(BigDecimal.ZERO, (a, b) -> {
					if (a == null) {
						return b;
					}
					if (b == null) {
						return a;
					}
					return a.add(b);
				})
				.sqrt(new MathContext(8, RoundingMode.HALF_UP)).setScale(1, RoundingMode.HALF_UP);
			BigDecimal numeratorMoe = this.countMoes.values().stream()
				.map(m -> m.pow(2))
				.reduce(BigDecimal.ZERO, (a, b) -> {
					if (a == null) {
						return b;
					}
					if (b == null) {
						return a;
					}
					return a.add(b);
				})
				.sqrt(new MathContext(8, RoundingMode.HALF_UP)).setScale(1, RoundingMode.HALF_UP);
			BigDecimal denominatorMoe = this.universeMoes.values().stream()
				.map(m -> m.pow(2))
				.reduce(BigDecimal.ZERO, (a, b) -> {
					if (a == null) {
						return b;
					}
					if (b == null) {
						return a;
					}
					return a.add(b);
				})
				.sqrt(new MathContext(8, RoundingMode.HALF_UP)).setScale(1, RoundingMode.HALF_UP);
			switch (this.indicatorType.getId()) {
				case IndicatorType.COUNT:
					moe = valueMoe;
					break;
				case IndicatorType.PERCENTAGE:
					moe = numeratorMoe.pow(2)
						.subtract(this.getValue().multiply(BigDecimal.valueOf(.01)).pow(2)
							.multiply(denominatorMoe.pow(2)))
						.sqrt(new MathContext(8, RoundingMode.HALF_UP))
						.divide(this.getUniverseSum(), 8, RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100))
						.setScale(1, RoundingMode.HALF_UP);
					break;
				case IndicatorType.RATE:
					moe = numeratorMoe.pow(2)
						.add(this.getValue().pow(2)
							.multiply(denominatorMoe.pow(2)))
						.sqrt(new MathContext(8, RoundingMode.HALF_UP))
						.divide(this.getUniverseSum(), 8, RoundingMode.HALF_UP)
						.setScale(1, RoundingMode.HALF_UP);
					break;
				case IndicatorType.CURRENCY:
					moe = null; // no aggregation for currency indicators for now
					break;
			}
		} catch (ArithmeticException | NumberFormatException e) {
			moe = null;
		}
		
		return moe;
	}

	private boolean returnNullValues() {
		if (this.isSuppressed() 
			|| !this.aggregable 
			|| (BigDecimal.ZERO.equals(this.getUniverseSum()) && !this.indicatorType.getId().equals(IndicatorType.COUNT))
			|| this.values.entrySet().stream().filter(v -> v.getValue() == null).map(v -> v.getKey()).anyMatch(k -> this.countValues.get(k) == null)
		) {
			return true;
		}
		return false;
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
			if (BigDecimal.valueOf(5).compareTo(this.getCountSum()) == 1) {
				suppressed = true;
			} else {
				suppressed = this.suppresseds.entrySet().stream().filter(e -> e.getValue()).allMatch(e -> {
					return this.countValues.get(e.getKey()) != null && BigDecimal.ONE.compareTo(this.countValues.get(e.getKey())) == 0;
				});
			}
		}

		return suppressed;
	}

	/**
	 * @return the universeValue
	 */
	public BigDecimal getUniverseValue() {
		if (this.returnNullValues()) {
			return null;
		}

		return this.getUniverseSum();
	}

	public BigDecimal getCountValue() {
		if (this.returnNullValues()) {
			return null;
		}
		
		return this.getCountSum();
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

	private BigDecimal getCountSum() {
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

	private BigDecimal getUniverseSum() {
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
	public Map<String, BigDecimal> getValueMoes() {
		return valueMoes;
	}

	public void setValueMoes(Map<String, BigDecimal> valueMoes) {
		this.valueMoes = valueMoes;
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
