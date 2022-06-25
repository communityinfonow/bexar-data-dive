--liquibase formatted sql

--changeset herronrb:indicators1

create table tbl_filter_types (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null
);

create table tbl_filter_options (
	id_ numeric primary key,
	type_id numeric not null references tbl_filter_types,
	name_en text not null,
	name_es text not null,
	sort_order numeric not null
);

create table tbl_sources (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null,
	url_ text,
	trend_interval numeric
);

create table tbl_indicator_categories (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null,
	sort_order numeric not null unique
);

create table tbl_indicator_types (
	id_ numeric primary key,
	name_ text not null
);

create table tbl_indicators (
	id_ numeric primary key,
	indicator_category_id numeric not null references tbl_indicator_categories,
	indicator_type_id numeric not null references tbl_indicator_types,
	name_en text not null,
	name_es text not null,
	description_en text not null,
	description_es text not null,
	base_filter_type_id numeric references tbl_filter_types,
	source_id numeric references tbl_sources
);

create table tbl_indicator_values (
	indicator_id numeric not null references tbl_indicators,
	year_ text not null,
	location_id text not null,
	location_type_id numeric not null,
	indicator_value numeric,
	moe_low numeric,
	moe_high numeric,
	universe_value numeric,
	race_id numeric references tbl_filter_options,
	age_id numeric references tbl_filter_options,
	sex_id numeric references tbl_filter_options,
	education_id numeric references tbl_filter_options,
	income_id numeric references tbl_filter_options,
	foreign key(location_id, location_type_id) references tbl_locations(id_, location_type_id)
);
create unique index idx_indicator_values on tbl_indicator_values (indicator_id, year_, location_id, location_type_id, race_id, age_id, sex_id, education_id, income_id);

insert into tbl_filter_types(id_, name_en, name_es)
values (1, 'Race or Ethnicity', 'Race or Ethnicity (es)'),
(2, 'Age', 'Age (es)'),
(3, 'Sex', 'Sex (es)'),
(4, 'Education Level', 'Education Level (es)'),
(5, 'Income Level', 'Income Level (es)');

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order)
values (1, 1, 'All', 'All (es)', 1),
(2, 1, 'AIAN', 'AIAN (es)', 2),
(3, 1, 'Asian', 'Asian (es)', 3),
(4, 2, 'All', 'All (es)', 1),
(5, 2, 'Under 18', 'Under 18 (es)', 2),
(6, 2, '65 and Over', '65 and Over (es)', 3),
(7, 3, 'All', 'All (es)', 1),
(8, 3, 'Female', 'Female (es)', 2),
(9, 3, 'Male', 'Male (es)', 3),
(10, 4, 'High School Diploma', 'High School Diploma (es)', 1),
(11, 4, 'Two-year Degree', 'Two-year Degree (es)', 2),
(12, 5, 'Below Poverty Level', 'Below Poverty Level (es)', 1),
(13, 5, 'At or Above Poverty Level', 'At or Above Poverty Level (es)', 2);

insert into tbl_sources (id_, name_en, name_es, url_, trend_interval) 
values (1, 'ACS 5-year', ' (es)', null, 5),
	(2, 'BCAD', 'BCAD (es)', null, null),
	(3, 'Bexar County Elections', 'Bexar County Elections (es)', null, null),
	(4, 'Bexar County Juvenile Probation Dept.', 'Bexar County Juvenile Probation Dept. (es)', null, null),
	(5, 'CDC', 'CDC (es)', null, null),
	(6, 'COSA', 'COSA (es)', null, null),
	(7, 'COSA NHSD', 'COSA NHSD (es)', null, null),
	(8, 'Decennial Census', 'Decennial Census (es)', null, null),
	(9, 'EDI', 'EDI (es)', null, null),
	(10, 'EPA NATA', 'EPA NATA (es)', null, null),
	(11, 'FBI UCR', 'FBI UCR (es)', null, null),
	(12, 'HASA', 'HASA (es)', null, null),
	(13, 'HHS', 'HHS (es)', null, null),
	(14, 'HUD', 'HUD (es)', null, null),
	(15, 'LEHD', 'LEHD (es)', null, null),
	(16, 'National Vital Statistics Report', 'National Vital Statistics Report (es)', null, null),
	(17, 'Natl Housing Preservation DB', 'Natl Housing Preservation DB (es)', null, null),
	(18, 'PLACES', 'PLACES (es)', null, null),
	(19, 'San Antonio Metro Health', 'San Antonio Metro Health (es)', null, null),
	(20, 'SAPD', 'SAPD (es)', null, null),
	(21, 'TDFPS', 'TDFPS (es)', null, null),
	(22, 'TDPS', 'TDPS (es)', null, null),
	(23, 'TDSHS', 'TDSHS (es)', null, null),
	(24, 'THCIC Hospital Discharge PUDF', 'THCIC Hospital Discharge PUDF (es)', null, null),
	(25, 'United Way of San Antonio and Bexar County', 'United Way of San Antonio and Bexar County (es)', null, null),
	(26, 'US Census', 'US Census (es)', null, null),
	(27, 'USDA Food Atlas', 'USDA Food Atlas (es)', null, null);

insert into tbl_indicator_categories (id_, name_en, name_es, sort_order)
values (1, 'Civic & social', 'Civic & social (es)', 1),
(2, 'Criminal justice', 'Criminal justice (es)', 2),
(3, 'Demographic', 'Demographic (es)', 3),
(4, 'Economic', 'Economic (es)', 4),
(5, 'Education', 'Education (es)', 5),
(6, 'Environment', 'Environment (es)', 6),
(7, 'Health behaviors', 'Health behaviors (es)', 7),
(8, 'Health outcomes', 'Health outcomes', 8),
(9, 'Housing', 'Housing (es)', 9),
(10, 'Transportation', 'Transportation (es)', 10);

insert into tbl_indicator_types (id_, name_)
values (1, 'Count'), (2, 'Rate'), (3, 'Percent'), (4, 'Currency');

insert into tbl_indicators (id_, indicator_category_id, indicator_type_id, source_id, name_en, name_es, description_en, description_es)
values (1, 1, 1, 26, '2020 Census response rate', '2020 Census response rate (es)', '[description]...', '[description]...(es)'),
	(2, 1, 1, 18, 'Adequate social-emotional support', 'Adequate social-emotional support (es)', '[description]...', '[description]...(es)'),
	(3, 1, 1, 3, 'Voter registration', 'Voter registration (es)', '[description]...', '[description]...(es)'),
	(4, 1, 1, 3, 'Voter turnout', 'Voter turnout (es)', '[description]...', '[description]...(es)'),
	(5, 2, 1, 2, 'Absentee property owners with two or more property maintenance violations within a 12-month period', 'Absentee property owners with two or more property maintenance violations within a 12-month period (es)', '[description]...', '[description]...(es)'),
	(6, 2, 1, 22, 'Aggravated assaults per 100K', 'Aggravated assaults per 100K (es)', '[description]...', '[description]...(es)'),
	(7, 2, 1, 22, 'Burglaries per 100K', 'Burglaries per 100K (es)', '[description]...', '[description]...(es)'),
	(8, 2, 1, 21, 'Child abuse prevalence (intakes)', 'Child abuse prevalence (intakes) (es)', '[description]...', '[description]...(es)'),
	(9, 2, 1, 21, 'Child abuse prevalence (confirmed victims)', 'Child abuse prevalence (confirmed victims) (es)', '[description]...', '[description]...(es)'),
	(10, 2, 1, 21, 'Child abuse prevalence (revictimized)', 'Child abuse prevalence (revictimized) (es)', '[description]...', '[description]...(es)'),
	(11, 2, 1, 20, 'Drug-related arrest rate', 'Drug-related arrest rate (es)', '[description]...', '[description]...(es)'),
	(12, 2, 1, 22, 'Family violence rate', 'Family violence rate (es)', '[description]...', '[description]...(es)'),
	(13, 2, 1, 22, 'Homicide and manslaughter rate', 'Homicide and manslaughter rate (es)', '[description]...', '[description]...(es)'),
	(14, 2, 1, 4, 'Juvenile probation rate', 'Juvenile probation rate (es)', '[description]...', '[description]...(es)'),
	(15, 2, 1, 8, 'Incarcerated as of 2020 Census', 'Incarcerated as of 2020 Census (es)', '[description]...', '[description]...(es)'),
	(16, 2, 1, 20, 'Property crime rate', 'Property crime rate (es)', '[description]...', '[description]...(es)'),
	(17, 2, 1, 11, 'Reported hate crime offense rate', 'Reported hate crime offense rate (es)', '[description]...', '[description]...(es)'),
	(18, 2, 1, 20, 'Sexual assault rate', 'Sexual assault rate (es)', '[description]...', '[description]...(es)'),
	(19, 2, 1, 20, 'Arrest rate', 'Arrest rate (es)', '[description]...', '[description]...(es)'),
	(20, 2, 1, 20, 'Violent crime rate', 'Violent crime rate (es)', '[description]...', '[description]...(es)'),
	(21, 3, 1, 1, 'Age Group', 'Age Group (es)', '[description]...', '[description]...(es)'),
	(22, 3, 1, 1, 'Limited English proficiency', 'Limited English proficiency (es)', '[description]...', '[description]...(es)'),
	(23, 3, 1, 1, 'Increase in population in past 10 years', 'Increase in population in past 10 years (es)', '[description]...', '[description]...(es)'),
	(24, 3, 1, 1, 'Households that are multigenerational', 'Households that are multigenerational (es)', '[description]...', '[description]...(es)'),
	(25, 3, 1, 1, 'Race', 'Race (es)', '[description]...', '[description]...(es)'),
	(26, 3, 1, 1, 'Racial/ethnic diversity (Theil index)', 'Racial/ethnic diversity (Theil index) (es)', '[description]...', '[description]...(es)'),
	(27, 3, 1, 1, 'Sex', 'Sex (es)', '[description]...', '[description]...(es)'),
	(28, 3, 1, 1, 'Speak Spanish or Spanish Creole at home', 'Speak Spanish or Spanish Creole at home (es)', '[description]...', '[description]...(es)'),
	(29, 3, 1, 1, 'Veteran population', 'Veteran population (es)', '[description]...', '[description]...(es)'),
	(30, 4, 1, 14, 'Access to loans and capital', 'Access to loans and capital (es)', '[description]...', '[description]...(es)'),
	(31, 4, 1, 25, 'ALICE households', 'ALICE households (es)', '[description]...', '[description]...(es)'),
	(32, 4, 1, 14, 'Assistance (food, rent assistance)', 'Assistance (food, rent assistance) (es)', '[description]...', '[description]...(es)'),
	(33, 4, 1, 13, 'Child care capacity per 1K kids (measure tbd)', 'Child care capacity per 1K kids (measure tbd) (es)', '[description]...', '[description]...(es)'),
	(34, 4, 1, 1, 'Computer and broadband Subscription', 'Computer and broadband Subscription (es)', '[description]...', '[description]...(es)'),
	(35, 4, 1, 1, 'Employment status', 'Employment status (es)', '[description]...', '[description]...(es)'),
	(36, 4, 1, 1, 'Home ownership', 'Home ownership (es)', '[description]...', '[description]...(es)'),
	(37, 4, 1, 1, 'Housing cost burden', 'Housing cost burden (es)', '[description]...', '[description]...(es)'),
	(38, 4, 1, 1, 'Income inequality', 'Income inequality (es)', '[description]...', '[description]...(es)'),
	(39, 4, 1, 15, 'Jobs per population', 'Jobs per population (es)', '[description]...', '[description]...(es)'),
	(40, 4, 1, 1, 'Family Income (median)', 'Family Income (median) (es)', '[description]...', '[description]...(es)'),
	(41, 4, 1, 1, 'Household Income (median)', 'Household Income (median) (es)', '[description]...', '[description]...(es)'),
	(42, 4, 1, 15, 'Monthly Earnings', 'Monthly Earnings (es)', '[description]...', '[description]...(es)'),
	(43, 4, 1, 1, 'Households with income <30% AMI', 'Households with income <30% AMI (es)', '[description]...', '[description]...(es)'),
	(44, 4, 1, 1, 'Households with income <60% AMI', 'Households with income <60% AMI (es)', '[description]...', '[description]...(es)'),
	(45, 4, 1, 1, 'Households with income <80% AMI', 'Households with income <80% AMI (es)', '[description]...', '[description]...(es)'),
	(46, 4, 1, 1, 'Percent not in labor force and not employed', 'Percent not in labor force and not employed (es)', '[description]...', '[description]...(es)'),
	(47, 4, 1, 1, 'Population in Armed Forces', 'Population in Armed Forces (es)', '[description]...', '[description]...(es)'),
	(48, 4, 1, 1, 'Income below poverty', 'Income below poverty (es)', '[description]...', '[description]...(es)'),
	(49, 4, 1, 7, 'Rental assistance', 'Rental assistance (es)', '[description]...', '[description]...(es)'),
	(50, 4, 1, 1, 'Supplemental Security Income (SSI)', 'Supplemental Security Income (SSI) (es)', '[description]...', '[description]...(es)'),
	(51, 4, 1, 1, 'Unemployment', 'Unemployment (es)', '[description]...', '[description]...(es)'),
	(52, 4, 1, 1, 'Uninsured', 'Uninsured (es)', '[description]...', '[description]...(es)'),
	(53, 4, 1, 1, 'Youth not in school or not working', 'Youth not in school or not working (es)', '[description]...', '[description]...(es)'),
	(54, 5, 1, 1, 'Education less than high school diploma/GED', 'Education less than high school diploma/GED (es)', '[description]...', '[description]...(es)'),
	(55, 5, 1, 1, 'Education Bachelor''s degree or higher', 'Education Bachelor''s degree or higher (es)', '[description]...', '[description]...(es)'),
	(56, 5, 1, 1, 'Enrolled in preschool', 'Enrolled in preschool (es)', '[description]...', '[description]...(es)'),
	(57, 5, 1, 13, 'Children in high-quality pre-K or daycare', 'Children in high-quality pre-K or daycare (es)', '[description]...', '[description]...(es)'),
	(58, 5, 1, 9, 'Children scoring "Vulnerable" on kinder-readiness', 'Children scoring "Vulnerable" on kinder-readiness (es)', '[description]...', '[description]...(es)'),
	(59, 6, 1, 6, 'Access to parks and greenspace', 'Access to parks and greenspace (es)', '[description]...', '[description]...(es)'),
	(60, 6, 1, 10, 'Air toxins cancer risk index', 'Air toxins cancer risk index (es)', '[description]...', '[description]...(es)'),
	(61, 6, 1, 14, 'Environmental health hazard index', 'Environmental health hazard index (es)', '[description]...', '[description]...(es)'),
	(62, 6, 1, 27, 'Food insecurity/food deserts', 'Food insecurity/food deserts (es)', '[description]...', '[description]...(es)'),
	(63, 7, 1, 18, 'Sleeping less than seven hours among adults', 'Sleeping less than seven hours among adults (es)', '[description]...', '[description]...(es)'),
	(64, 7, 1, 18, 'Cholesterol screening among adults', 'Cholesterol screening among adults (es)', '[description]...', '[description]...(es)'),
	(65, 7, 1, 19, 'COVID-19 vaccination rates', 'COVID-19 vaccination rates (es)', '[description]...', '[description]...(es)'),
	(66, 7, 1, 18, 'Obesity among adults', 'Obesity among adults (es)', '[description]...', '[description]...(es)'),
	(67, 7, 1, 18, 'Older adults aged 65 years and older who are up to date on a core set of clinical preventive services', 'Older adults aged 65 years and older who are up to date on a core set of clinical preventive services (es)', '[description]...', '[description]...(es)'),
	(68, 7, 1, 23, 'Opioid prescription rate', 'Opioid prescription rate (es)', '[description]...', '[description]...(es)'),
	(69, 7, 1, 18, 'Cervical cancer Pap screening among adult women aged 21-65 years', 'Cervical cancer Pap screening among adult women aged 21-65 years (es)', '[description]...', '[description]...(es)'),
	(70, 7, 1, 18, 'Current smoking among adults', 'Current smoking among adults (es)', '[description]...', '[description]...(es)'),
	(71, 7, 1, 19, 'Prenatal care', 'Prenatal care (es)', '[description]...', '[description]...(es)'),
	(72, 7, 1, 18, 'Visits to dentist or dental clinic among adults', 'Visits to dentist or dental clinic among adults (es)', '[description]...', '[description]...(es)'),
	(73, 7, 1, 18, 'Visits to doctor for routine checkup within the past year among adults', 'Visits to doctor for routine checkup within the past year among adults (es)', '[description]...', '[description]...(es)'),
	(74, 8, 1, 12, 'Asthma hospitalization rate', 'Asthma hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(75, 8, 1, 19, 'Birth rates', 'Birth rates (es)', '[description]...', '[description]...(es)'),
	(76, 8, 1, 18, 'Coronary heart disease among adults', 'Coronary heart disease among adults (es)', '[description]...', '[description]...(es)'),
	(77, 8, 1, 19, 'COVID-19 case rate', 'COVID-19 case rate (es)', '[description]...', '[description]...(es)'),
	(78, 8, 1, 19, 'COVID-19 death rate', 'COVID-19 death rate (es)', '[description]...', '[description]...(es)'),
	(79, 8, 1, 23, 'Death rate by age', 'Death rate by age (es)', '[description]...', '[description]...(es)'),
	(80, 8, 1, 12, 'Diabetes hospitalization rate', 'Diabetes hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(81, 8, 1, 18, 'Diabetes among adults', 'Diabetes among adults (es)', '[description]...', '[description]...(es)'),
	(82, 8, 1, 1, 'Disability status', 'Disability status (es)', '[description]...', '[description]...(es)'),
	(83, 8, 1, 24, 'Hypertension hospitalization rate', 'Hypertension hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(84, 8, 1, 24, 'Injury hospitalization rate', 'Injury hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(85, 8, 1, 5, 'Invasive cancer incidence rate', 'Invasive cancer incidence rate (es)', '[description]...', '[description]...(es)'),
	(86, 8, 1, 23, 'Lead poisoning/elevated blood lead', 'Lead poisoning/elevated blood lead (es)', '[description]...', '[description]...(es)'),
	(87, 8, 1, 16, 'Life expectancy (average)', 'Life expectancy (average) (es)', '[description]...', '[description]...(es)'),
	(88, 8, 1, 24, 'Mental health hospitalization rate', 'Mental health hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(89, 8, 1, 18, 'Mental health not good for >=14 days among adults', 'Mental health not good for >=14 days among adults (es)', '[description]...', '[description]...(es)'),
	(90, 8, 1, 18, 'Physical health not good for >=14 days among adults', 'Physical health not good for >=14 days among adults (es)', '[description]...', '[description]...(es)'),
	(91, 8, 1, 18, 'Stroke among adults', 'Stroke among adults (es)', '[description]...', '[description]...(es)'),
	(92, 9, 1, 1, 'Change in total housing units (10-year change)', 'Change in total housing units (10-year change) (es)', '[description]...', '[description]...(es)'),
	(93, 9, 1, 1, 'Multi-family housing units', 'Multi-family housing units (es)', '[description]...', '[description]...(es)'),
	(94, 9, 1, 17, 'Housing units subsidized', 'Housing units subsidized (es)', '[description]...', '[description]...(es)'),
	(95, 9, 1, 2, 'Residential buildings in fair or poor condition', 'Residential buildings in fair or poor condition (es)', '[description]...', '[description]...(es)'),
	(96, 9, 1, 1, 'Renter housing stability (moved in past year)', 'Renter housing stability (moved in past year) (es)', '[description]...', '[description]...(es)'),
	(97, 9, 1, 1, 'Vacant housing units', 'Vacant housing units (es)', '[description]...', '[description]...(es)'),
	(98, 10, 1, 14, 'Low Transportation Cost Index', 'Low Transportation Cost Index (es)', '[description]...', '[description]...(es)');

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','48029',1,487,4,4,487,1,null,null,null,null),
	(1,'2020','48029',1,18,12,12,487,2,null,null,null,null),
	(1,'2020','48029',1,71,9,9,487,3,null,null,null,null),
	(2,'2020','48029',1,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','48029',1,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','48029',1,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','48029',1,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','48029',1,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','48029',1,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','48029',1,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','48029',1,482,4,4,482,1,null,null,null,null),
	(1,'2016','48029',1,16,12,12,482,2,null,null,null,null),
	(1,'2016','48029',1,70,9,9,482,3,null,null,null,null),
	(3,'2016','48029',1,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','48029',1,18.3,3,7,482,2,null,null,11,null);
