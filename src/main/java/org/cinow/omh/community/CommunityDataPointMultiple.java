package org.cinow.omh.community;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.indicators.IndicatorType;

/**
 * This object represents a data point in the Commmunity view for multiple communities.
 * 
 * @author brian
 */
public class CommunityDataPointMultiple implements CommunityDataPoint {

	/**
	 * The race filter.
	 */
	private FilterOption raceFilter;

	/**
	 * The indicator values.
	 */
	private Map<String, BigDecimal> values = new TreeMap<>();

	/**
	 * The indicator universe value.
	 */
	private Map<String, Double> universeValues = new TreeMap<>();

	/**
	 * Whether the data value is suppressed.
	 */
	private Map<String, Boolean> suppresseds = new TreeMap<>();

	/**
	 * The indicator count value.
	 */
	private Map<String, Double> countValues = new TreeMap<>();

	/**
	 * The indicator universe margin of error.
	 */
	private Map<String, Double> universeMoes = new TreeMap<>();

	/**
	 * The indicator count margin of error.
	 */
	private Map<String, Double> countMoes = new TreeMap<>();

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
					value = new BigDecimal(this.getCountValue()).setScale(0, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.PERCENTAGE:
				try {
					value = new BigDecimal(this.getCountValue() / this.getUniverseValue() * 100d).setScale(1, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.RATE:
				try {
					value = new BigDecimal(this.getCountValue() / this.getUniverseValue() * this.ratePer).setScale(1, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
			case IndicatorType.CURRENCY:
				try {
					value = new BigDecimal(this.getValues().values().stream().reduce(BigDecimal.ZERO, (a, b) -> {
						if (a == null) {
							return b;
						}
						if (b == null) {
							return a;
						}
						return a.add(b);
					}).doubleValue() / this.getValues().values().size()).setScale(1, RoundingMode.HALF_UP);
				} catch (NumberFormatException e) {
					value = null;
				}
				break;
		}

		return value;
	}

	/**
	 * @return the moeHigh
	 */
	public Double getMoeHigh() {
		//TODO: needs calculated
		return null;
	}

	/**
	 * @return the moeLow
	 */
	public Double getMoeLow() {
		//TODO: needs calculated
		return null;
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
	public Double getUniverseValue() {
		return this.universeValues.values().stream().reduce(0d, (a, b) -> {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
			return a + b;
		});
	}

	public Double getCountValue() {
		return this.countValues.values().stream().reduce(0d, (a, b) -> {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
			return a + b;
		});
	}

	public Double getCountMoe() {
		//TODO: needs calculated
		return null;
	}

	public Double getUniverseMoe() {
		//TODO: needs calculated
		return null;
	}

	/**
	 * @return the raceFilter
	 */
	public FilterOption getRaceFilter() {
		return raceFilter;
	}

	/**
	 * @param raceFilter the raceFilter to set
	 */
	public void setRaceFilter(FilterOption raceFilter) {
		this.raceFilter = raceFilter;
	}

	public Map<String, BigDecimal> getValues() {
		return values;
	}

	public void setValues(Map<String, BigDecimal> values) {
		this.values = values;
	}

	public Map<String, Double> getUniverseValues() {
		return universeValues;
	}

	public void setUniverseValues(Map<String, Double> universeValues) {
		this.universeValues = universeValues;
	}

	public Map<String, Boolean> getSuppresseds() {
		return suppresseds;
	}

	public void setSuppresseds(Map<String, Boolean> suppresseds) {
		this.suppresseds = suppresseds;
	}

	public Map<String, Double> getCountValues() {
		return countValues;
	}

	public void setCountValues(Map<String, Double> countValues) {
		this.countValues = countValues;
	}

	public Map<String, Double> getUniverseMoes() {
		return universeMoes;
	}

	public void setUniverseMoes(Map<String, Double> universeMoes) {
		this.universeMoes = universeMoes;
	}

	public Map<String, Double> getCountMoes() {
		return countMoes;
	}

	public void setCountMoes(Map<String, Double> countMoes) {
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
