--liquibase formatted sql

--changeset herronrb:indicators9

update tbl_indicators
set description_en = '',
	description_es = '';

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The number of people who answered the survey divided by the number of people in the sample. Also known as a completion rate.</p><p><span class="font-weight-bold">Data Notes: </span>As of October, 2020, 99.8% of all housing units and addresses nationwide were accounted for in the 2020 Census, according to the U.S. Census Bureau. The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>National, State, and County – U.S. Census Bureau, 2020</p>',
	description_es = ''
where id_ = 1; -- "2020 Census response rate"


update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of citizens who have completed the process of registering with election officials in order to become eligible to vote.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Bexar County Elections</p>',
	description_es = ''
where id_ = 3; -- "Voter registration"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of registered voters who have cast a ballot in an election.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Bexar County Elections</p>',
	description_es = ''
where id_ = 4; -- "Voter turnout"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who are under 5 years of age.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 21; -- "Under 5"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who are between 5 and 17 years of age. This age group includes those who are 5 or 17.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 22; -- "5 to 17"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who are between 18 and 34 years of age. This age group includes those who are 18 or 34.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 23; -- "18 to 34"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who are between 35 and 64 years of age. This age group includes those who are 35 or 64.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 24; -- "35 to 64"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who are 65 years of age or older. This age group includes those who are 65.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 25; -- "65 and Older"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Census Bureau identifies multigenerational households through relationship to the householder. The presence of one of the following relationship combinations caused the household to be coded as multigenerational:<ol><li>householder, householder''s child, and householder''s grandchild</li><li>householder''s parent, householder, and householder''s child</li><li>householder''s parent-in-law, householder, and householder''s child</li></ol></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B11017</p>',
	description_es = ''
where id_ = 28; -- "Households that are multigenerational"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of U.S. counties that gained population between the 2010 and 2020 Decennial Censes.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau Decennial Census</p>',
	description_es = ''
where id_ = 27; -- "Increase in population in past 10 years"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of households in which no member 14 years and over speaks only English or speaks a non-English language and speaks English “very well.” In other words, all members 14 years and over have at least some difficulty with English. For more information: <a target="_blank" href="https://www.census.gov/library/visualizations/2017/comm/english-speaking.html">https://www.census.gov/library/visualizations/2017/comm/english-speaking.html</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B16002</p>',
	description_es = ''
where id_ = 26; -- "Limited English proficiency"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as American Indian or Alaska Native (AIAN), but not of Hispanic ethnicity. AIAN refers to a person having origins in any of the original peoples of North and South America (including Central America) and who maintains tribal affiliation or community attachment. This category includes people who indicate their race as "American Indian or Alaska Native" or report entries such as Navajo, Blackfeet, Inupiat, Yup''ik, or Central American Indian groups or South American Indian groups. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 29; -- "American Indian or Alaska Native (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as Asian, but not of Hispanic ethnicity. Asian refers to a person having origins in any of the original peoples of the Far East, Southeast Asia, or the Indian subcontinent including, for example, Cambodia, China, India, Japan, Korea, Malaysia, Pakistan, the Philippine Islands, Thailand, and Vietnam. This includes people who reported detailed Asian responses such as: "Asian Indian," "Chinese," "Filipino," "Korean," "Japanese," "Vietnamese," and "Other Asian" or provide other detailed Asian responses. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 30; -- "Asian (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as Black or African American, but not of Hispanic ethnicity. Black or African American refers to a person having origins in any of the Black racial groups of Africa. It includes people who indicate their race as "Black or African American," or report entries such as African American, Kenyan, Nigerian, or Haitian. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 31; -- "Black or African American (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as of Hispanic or Latino ethnicity, regardless of race. Hispanic or Latino refers to a person of Cuban, Mexican, Puerto Rican, South or Central American, or other Spanish culture or origin regardless of race. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 32; -- "Hispanic or Latino"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as Native Hawaiian and Other Pacific Islander (NHPI), but not of Hispanic ethnicity. NHPI refers to a person having origins in any of the original peoples of Hawaii, Guam, Samoa, or other Pacific Islands. It includes people who reported their race as "Fijian," "Guamanian or Chamorro," "Marshallese," "Native Hawaiian," "Samoan," "Tongan," and "Other Pacific Islander" or provide other detailed Pacific Islander responses. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 33; -- "Native Hawaiian or Pacific Islander (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as Other Race, but not of Hispanic ethnicity. The White House Office of Management and Budget (OMB) requires that race data be collected for a minimum of five groups: White, Black or African American, American Indian or Alaska Native, Asian, and Native Hawaiian or Other Pacific Islander. OMB permits the Census Bureau to also use a sixth category - Some Other Race. The Other Race category is for people who feel their racial/ethnic identity does not align with the other options. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 34; -- "Other Race (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as Two or More Races, but not of Hispanic ethnicity. People may choose to provide two or more races either by checking two or more race response check boxes, by providing multiple responses, or by some combination of check boxes and other responses. For data product purposes, "Two or More Races" refers to combinations of two or more of the following race categories: "White," "Black or African American," American Indian or Alaska Native," "Asian," Native Hawaiian or Other Pacific Islander," or "Some Other Race.” For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 35; -- "Two or More Races (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report as White, but not of Hispanic ethnicity. White refers to a person having origins in any of the original peoples of Europe, the Middle East, or North Africa. It includes people who indicate their race as "White" or report entries such as Irish, German, Italian, Lebanese, Arab, Moroccan, or Caucasian. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/RHI625221">https://www.census.gov/quickfacts/fact/note/US/RHI625221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2010-2020 Census Bureau''s American Community Survey 5-Year Estimates, Table B03002</p>',
	description_es = ''
where id_ = 36; -- "White (non-Hispanic)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report their biological sex as male rather than female, the only two response options offered. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/SEX255221">https://www.census.gov/quickfacts/fact/note/US/SEX255221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 38; -- "Male"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who self-report their biological sex as female rather than male, the only two response options offered. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/SEX255221">https://www.census.gov/quickfacts/fact/note/US/SEX255221</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table B01001</p>',
	description_es = ''
where id_ = 39; -- "Female"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people 5 years and older who self-report as speaking Spanish or Spanish Creole at home. For more information: <a target="_blank" href="https://www.census.gov/acs/www/about/why-we-ask-each-question/language/">https://www.census.gov/acs/www/about/why-we-ask-each-question/language/</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table S1603</p>',
	description_es = ''
where id_ = 40; -- "Speak Spanish or Spanish Creole at home"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of civilians 18 and older who self-report as a veteran. Veterans are men and women who have served (even for a short time), but are not currently serving, on active duty in the U.S. Army, Navy, Air Force, Marine Corps, or the Coast Guard, or who served in the U.S. Merchant Marine during World War II. For more information: <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/VET605220">https://www.census.gov/quickfacts/fact/note/US/VET605220</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Census Bureau''s American Community Survey 5-Year Estimates, Table S2101</p>',
	description_es = ''
where id_ = 41; -- "Veteran population"

update tbl_indicators set display = false where id_ = 2; -- "Adequate social-emotional support"

update tbl_indicators set name_en = 'Speak Spanish at home' where id_ = 40; -- "Speak Spanish or Spanish Creole at home"