package org.cinow.omh.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cinow.omh.filters.FilterType;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.locations.LocationType;
import org.cinow.omh.sources.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataCorrectionRepositoryPostgresql implements DataCorrectionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<DataCorrection> findDisplayed() {
		String sql = ""
			+ " select dc.id_, dc.indicator_id, dc.date_corrected, dc.years, dc.location_type_ids, dc.filter_type_ids, dc.note, dc.source_id,"
			+ "   i.name_en as indicator_name_en, i.name_es as indicator_name_es, "
			+ "   s.name_en as source_name_en, s.name_es as source_name_es, "
			+ "   lt.id_ as location_type_id, lt.name_en as location_type_name_en, lt.name_es as location_type_name_es, "
			+ "   ft.id_ as filter_type_id, ft.name_en as filter_type_name_en, ft.name_es as filter_type_name_es, "
			+ "   case when exists (select 1 from (select unnest(filter_type_ids) as fti) tmp where fti is null) then true else false end as unfiltered_data_corrected "
			+ " from tbl_data_corrections dc "
			+ "   left join tbl_indicators i on i.id_ = dc.indicator_id "
			+ "   left join tbl_sources s on s.id_ = dc.source_id "
			+ "   left join tbl_location_types lt on location_type_ids @> array[lt.id_] "
			+ "   left join tbl_filter_types ft on filter_type_ids @> array[ft.id_] "
			+ " where dc.display = true "
			+ " order by dc.date_corrected desc, lt.sort_order, ft.id_ ";

		return this.jdbcTemplate.query(sql, extractor);
	}
	
	@Override
	public List<DataCorrection> findAll() {
		String sql = ""
			+ " select dc.id_, dc.indicator_id, dc.date_corrected, dc.years, dc.location_type_ids, dc.filter_type_ids, dc.note, dc.source_id, "
			+ "   i.name_en as indicator_name_en, i.name_es as indicator_name_es, "
			+ "   s.name_en as source_name_en, s.name_es as source_name_es, "
			+ "   lt.id_ as location_type_id, lt.name_en as location_type_name_en, lt.name_es as location_type_name_es, "
			+ "   ft.id_ as filter_type_id, ft.name_en as filter_type_name_en, ft.name_es as filter_type_name_es, "
			+ "   case when exists (select 1 from (select unnest(filter_type_ids) as fti) tmp where fti is null) then true else false end as unfiltered_data_corrected "
			+ " from tbl_data_corrections dc "
			+ "   left join tbl_indicators i on i.id_ = dc.indicator_id "
			+ "   left join tbl_sources s on s.id_ = dc.source_id "
			+ "   left join tbl_location_types lt on location_type_ids @> array[lt.id_] "
			+ "   left join tbl_filter_types ft on filter_type_ids @> array[ft.id_] "
			+ " order by dc.date_corrected desc, lt.sort_order, ft.id_ ";

		return this.jdbcTemplate.query(sql, extractor);
	}

	@Override
	public void add(DataCorrection dataCorrection) {
		String sql = ""
			+ " insert into tbl_data_corrections (indicator_id, date_corrected, years, location_type_ids, filter_type_ids, note, display, source_id) "
			+ " values (:indicatorId::numeric, :dateCorrected::date, :years, :locationTypeIds::numeric[], :filterTypeIds::numeric[], :note, :display, :source_id::numeric) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicatorId", dataCorrection.getIndicator() != null ? dataCorrection.getIndicator().getId() : null);
		paramMap.addValue("dateCorrected", dataCorrection.getDateCorrected());
		if (dataCorrection.getYears() != null) {
			paramMap.addValue("years", dataCorrection.getYears().toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("years", new String[0], Types.ARRAY);
		}
		if (dataCorrection.getLocationTypes() != null) {
			paramMap.addValue("locationTypeIds", dataCorrection.getLocationTypes().stream().map(LocationType::getId).collect(Collectors.toList()).toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("locationTypeIds", new String[0], Types.ARRAY);
		}
		if (dataCorrection.getFilterTypes() != null) {
			paramMap.addValue("filterTypeIds", dataCorrection.getFilterTypes().stream().map(FilterType::getId).collect(Collectors.toList()).toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("filterTypeIds", new String[0], Types.ARRAY);
		}
		paramMap.addValue("note", dataCorrection.getNote());
		paramMap.addValue("display", true);
		paramMap.addValue("source_id", dataCorrection.getSource() != null ? dataCorrection.getSource().getId() : null);

		this.namedParameterJdbcTemplate.update(sql, paramMap);
		
	}

	@Override
	public void update(DataCorrection dataCorrection) {
		String sql = ""
			+ " update tbl_data_corrections "
			+ " set indicator_id = :indicatorId::numeric, date_corrected = :dateCorrected::date, years = :years::numeric[], location_type_ids = :locationTypeIds::numeric[], filter_type_ids = :filterTypeIds::numeric[], note = :note, display = :display, source_id = :source_id::numeric "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", dataCorrection.getId());
		paramMap.addValue("indicatorId", dataCorrection.getIndicator().getId());
		paramMap.addValue("dateCorrected", dataCorrection.getDateCorrected());
		if (dataCorrection.getYears() != null) {
			paramMap.addValue("years", dataCorrection.getYears().toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("years", new String[0], Types.ARRAY);
		}
		if (dataCorrection.getLocationTypes() != null) {
			paramMap.addValue("locationTypeIds", dataCorrection.getLocationTypes().stream().map(LocationType::getId).collect(Collectors.toList()).toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("locationTypeIds", new String[0], Types.ARRAY);
		}
		if (dataCorrection.getFilterTypes() != null) {
			paramMap.addValue("filterTypeIds", dataCorrection.getFilterTypes().stream().map(FilterType::getId).collect(Collectors.toList()).toArray(new String[0]), Types.ARRAY);
		} else {
			paramMap.addValue("filterTypeIds", new String[0], Types.ARRAY);
		}
		paramMap.addValue("note", dataCorrection.getNote());
		paramMap.addValue("display", dataCorrection.isDisplay());
		paramMap.addValue("source_id", dataCorrection.getSource() != null ? dataCorrection.getSource().getId() : null);

		this.namedParameterJdbcTemplate.update(sql, paramMap);
		
	}

	@Override
	public boolean hasRecent(String indicatorId, String year, String locationTypeId, Collection<String> filterTypes) {
		String sql = ""
			+ " select 1 "
			+ " from tbl_data_corrections "
			+ " where (indicator_id = :indicator_id::numeric or source_id = (select source_id from tbl_indicators where id_ = :indicator_id::numeric)) "
			+ "   and display = true "
			+ "   and date_corrected > current_date - interval '90 days' "
			+ "   and ( "
			+ "     years @> array[ :year::text ] "
			+ "     or years = '{}' "
			+ "     or exists ( "
			+ "       with years as (select unnest(years) as year_ from tbl_data_corrections where indicator_id = :indicator_id::numeric or source_id = (select source_id from tbl_indicators where id_ = :indicator_id::numeric)) "
			+ "       select 1 "
			+ "       from years"
			+ "       where (year_::numeric - :year::numeric) % (select coalesce(trend_interval, 1) from tbl_sources where id_ = (select source_id from tbl_indicators where id_ = :indicator_id::numeric)) = 0"
			+ "     )"
			+ "   ) "
			+ "   and (location_type_ids @> array[ :location_type_id::numeric ] or location_type_ids = '{}') "
			+ "   and (filter_type_ids && :filter_type_ids::numeric[] or filter_type_ids = '{}' or (:filter_type_ids::numeric[] = '{}' and exists (select 1 from (select unnest(filter_type_ids) as fti) tmp where fti is null))) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);
		paramMap.addValue("year", year);
		paramMap.addValue("location_type_id", locationTypeId);
		paramMap.addValue("filter_type_ids", filterTypes.toArray(new String[0]), Types.ARRAY);

		List<Integer> results = this.namedParameterJdbcTemplate.queryForList(sql, paramMap, Integer.class);
		
		return !results.isEmpty();
	}

	@Override
	public boolean hasRecent(String indicatorId, Collection<String> years, Collection<String> locationTypeIds, Collection<String> filterTypes) {
		String sql = ""
			+ " select 1 "
			+ " from tbl_data_corrections "
			+ " where (indicator_id = :indicator_id::numeric or source_id = (select source_id from tbl_indicators where id_ = :indicator_id::numeric)) "
			+ "   and display = true "
			+ "   and date_corrected > current_date - interval '90 days' ";
		if (years != null && !years.isEmpty()) {
			sql += "   and (years && :years::text[] or years = '{}') ";
		}
		if (locationTypeIds != null && !locationTypeIds.isEmpty()) {
			sql += "   and (location_type_ids && :location_type_ids::numeric[] or location_type_ids = '{}') ";
		}
		if (filterTypes != null && !filterTypes.isEmpty()) {
			sql += "   and (filter_type_ids && :filter_type_ids::numeric[] or filter_type_ids = '{}') ";
		}

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);
		paramMap.addValue("years", years.toArray(new String[0]), Types.ARRAY);
		paramMap.addValue("location_type_ids", locationTypeIds.toArray(new String[0]), Types.ARRAY);
		paramMap.addValue("filter_type_ids", filterTypes.toArray(new String[0]), Types.ARRAY);

		List<Integer> results = this.namedParameterJdbcTemplate.queryForList(sql, paramMap, Integer.class);
		
		return !results.isEmpty();
	}

	private ResultSetExtractor<List<DataCorrection>> extractor = new ResultSetExtractor<List<DataCorrection>>() {
		@Override
		public List<DataCorrection> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<DataCorrection> dataCorrections = new ArrayList<>();
			DataCorrection dataCorrection = null;
			while (rs.next()) {
				if (dataCorrection == null || dataCorrection.getId() != rs.getLong("id_")) {
					dataCorrection = new DataCorrection();
					dataCorrection.setId(rs.getInt("id_"));
					Indicator indicator = new Indicator();
					indicator.setId(rs.getString("indicator_id"));
					indicator.setName_en(rs.getString("indicator_name_en"));
					indicator.setName_es(rs.getString("indicator_name_es"));
					dataCorrection.setIndicator(indicator);
					dataCorrection.setDateCorrected(rs.getString("date_corrected"));
					dataCorrection.setYears(Arrays.asList((String[]) rs.getArray("years").getArray()));
					dataCorrection.setLocationTypes(new ArrayList<>());
					dataCorrection.setFilterTypes(new ArrayList<>());
					dataCorrection.setNote(rs.getString("note"));
					dataCorrection.setDisplay(true);
					Source source = new Source();
					source.setId(rs.getString("source_id"));
					source.setName_en(rs.getString("source_name_en"));
					source.setName_es(rs.getString("source_name_es"));
					dataCorrection.setSource(source);
					dataCorrection.setCorrectionType(dataCorrection.getIndicator().getId() != null ? "indicator" : "source");
					dataCorrections.add(dataCorrection);
				}
				String locationTypeId = rs.getString("location_type_id");
				String filterTypeId = rs.getString("filter_type_id");
				if (locationTypeId != null && !dataCorrection.getLocationTypes().stream().anyMatch(lt -> lt.getId().equals(locationTypeId))) {
					LocationType locationType = new LocationType();
					locationType.setId(rs.getString("location_type_id"));
					locationType.setName_en(rs.getString("location_type_name_en"));
					locationType.setName_es(rs.getString("location_type_name_es"));
					dataCorrection.getLocationTypes().add(locationType);
				}
				if (rs.getBoolean("unfiltered_data_corrected") && !dataCorrection.getFilterTypes().stream().anyMatch(ft -> ft.getId() == null)) {
					FilterType filterType = new FilterType();
					filterType.setId(null);
					filterType.setName_en("No filter");
					filterType.setName_es("Sin filtro");
					dataCorrection.getFilterTypes().add(filterType);
				}
				if (filterTypeId != null && !dataCorrection.getFilterTypes().stream().filter(ft -> ft.getId() != null).anyMatch(ft -> ft.getId().equals(filterTypeId))) {
					FilterType filterType = new FilterType();
					filterType.setId(rs.getString("filter_type_id"));
					filterType.setName_en(rs.getString("filter_type_name_en"));
					filterType.setName_es(rs.getString("filter_type_name_es"));
					dataCorrection.getFilterTypes().add(filterType);
				}
			}

			return dataCorrections;
		}
	};
}
