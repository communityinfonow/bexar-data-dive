--liquibase formatted sql

--changeset herronrb:about_the_data_pt2

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>An absentee property owner is someone who owns property within San Antonio City limits but lives outside of Bexar County, Texas. This indicator measures absentee property owners who have had more than one property maintenance violation within a 12-month period. Read more here: <a target="_blank" href="https://www.sanantonio.gov/Portals/0/Files/CES/AbsenteeOwnerBrochure.pdf">https://www.sanantonio.gov/Portals/0/Files/CES/AbsenteeOwnerBrochure.pdf</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Bexar County Appraisal District</p>',
	description_es = ''
where id_ = 5; -- "Absentee property owners with two or more property maintenance violations within a 12-month period"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Texas Department of Public Safety defines aggravated assault as "an unlawful attack by one person upon another to inflict severe or aggravated bodily injury. This type of assault is usually accompanied by the use of a weapon or by means likely to produce death or serious bodily harm. Statistics for aggravated assault include attempts to commit aggravated assault." Read more here: <a target="_blank" href="https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf">https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Public Safety: Crime Records</p>',
	description_es = ''
where id_ = 6; -- "Aggravated assaults (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Texas Department of Public Safety defines burglary as "the unlawful entry of a structure with the intent to commit a felony or a theft. While the use of force to gain entry is not required to classify an offense as burglary, attempted forcible entries to commit burglary are counted in these crime statistics." Read more here: <a target="_blank" href="https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf">https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf</a></p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Public Safety: Crime Records</p>',
	description_es = ''
where id_ = 7; -- "Burglaries (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Child abuse has four major types: physical abuse, neglect, sexual abuse, and emotional abuse. The Texas Department of Family and Protective Services goes into detail about each here: <a target="_blank" href="https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp">https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp</a>. Statewide Intake refers to the division responsible for assessing all reports of abuse, neglect, and exploitation. This indicator measures the number of children who have gone through statewide intake for child abuse. Read more about the Statewide Intake Division (SWI) here: <a target="_blank" href="https://www.dfps.state.tx.us/About_DFPS/Statewide_Intake/default.asp">https://www.dfps.state.tx.us/About_DFPS/Statewide_Intake/default.asp</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Family and Protective Services</p>',
	description_es = ''
where id_ = 8; -- "Child abuse prevalence – intakes (per 1k children)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Child abuse has four major types: physical abuse, neglect, sexual abuse, and emotional abuse. The Texas Department of Family and Protective Services goes into detail about each here: <a target="_blank" href="https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp">https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp</a>. This indicator measures the number of children who were confirmed victims of child abuse.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Family and Protective Services</p>',
	description_es = ''
where id_ = 9; -- "Child abuse prevalence – confirmed victims (per 1k children)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Child abuse has four major types: physical abuse, neglect, sexual abuse, and emotional abuse. The Texas Department of Family and Protective Services goes into detail about each here: <a target="_blank" href="https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp">https://www.dfps.state.tx.us/Child_Protection/Child_Safety/recognize_abuse.asp</a>. This indicator measures the number of children who were confirmed victims of child abuse and were revictimized within 5 years.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Family and Protective Services</p>',
	description_es = ''
where id_ = 10; -- "Child abuse – confirmed victims revictimized within 5 years"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The rate of arrests in which illegal drugs were involved.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>San Antonio Police Department</p>',
	description_es = ''
where id_ = 11; -- "Drug-related arrest rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Texas Family Code defines family violence as "an act by a member of a family or household against another member that is intended to result in physical harm, bodily injury, assault, or a threat that reasonably places the member in fear of imminent physical harm." Family includes individuals related by blood or affinity. Read more here: <a target="_blank" href="https://www.dps.texas.gov/sites/default/files/documents/crimereports/08/citch5.pdf">https://www.dps.texas.gov/sites/default/files/documents/crimereports/08/citch5.pdf</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Public Safety</p>',
	description_es = ''
where id_ = 12; -- "Family violence rate (per 1k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Murder and non-negligent manslaughter are defined as the willful killing of one human being by another. Attempted murder, attempted assault, suicides, accidental deaths, and justifiable homicides are excluded from the murder classification. Read more here: <a target="_blank" href="https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf">https://www.dps.texas.gov/sites/default/files/documents/crimereports/20/2020cit.pdf</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of Public Safety</p>',
	description_es = ''
where id_ = 13; -- "Homicide and manslaughter rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The rate of juveniles given a court order for juvenile probation, which allows them to return to their normal activities under the supervision of a probation officer.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Bexar County</p>',
	description_es = ''
where id_ = 14; -- "Juvenile probation rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>People who were confined in prison during the time of the 2020 Census Bureau''s American Community Survey.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B26103 A-I</p>',
	description_es = ''
where id_ = 15; -- "Incarcerated as of 2020 Census"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Property crimes include burglary of residence, burglary of vehicle, burglary of coin operated machine, theft, theft of service, criminal mischief, and criminal trespass.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>San Antonio Police Department</p>',
	description_es = ''
where id_ = 16; -- "Property crime rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Federal Bureau of Investigation''s Uniform Crime Reporting Program (UCR) defines hate crime as "a committed criminal offense which is motivated, in whole or in part, by the offender''s bias(es) against a race, religion, disability, sexual orientation, ethnicity, gender, or gender identity." Even if the offenders are mistaken in their perception that the victim was a member of a certain group, the offense is still a hate crime. Read more here: <a target="_blank" href="https://www.fbi.gov/services/cjis/ucr/hate-crime">https://www.fbi.gov/services/cjis/ucr/hate-crime</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Federal Bureau of Investigation</p>',
	description_es = ''
where id_ = 17; -- "Reported hate crime offense rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The City of San Antonio uses the FBI''s Uniform Crime Report definition of sexual assault, which is "the carnal knowledge of a female forcibly and against her will. Assaults or attempts to commit rape by force or threat of force are also included; however, statutory rape (without force) and other sex offenses are excluded." Read more here: <a target="_blank" href="https://www.sanantonio.gov/SAPD/FAQ">https://www.sanantonio.gov/SAPD/FAQ</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>San Antonio Police Department</p>',
	description_es = ''
where id_ = 18; -- "Sexual assault rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The rate at which people are arrested.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>San Antonio Police Department</p>',
	description_es = ''
where id_ = 19; -- "Arrest rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Violent crimes include homicide, rape, robbery, and assault.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>San Antonio Police Department</p>',
	description_es = ''
where id_ = 20; -- "Violent crime rate (per 100k)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>ALICE is an acronym for Asset Limited, Income Constrained, Employed, "and represents the growing number of families who are working, but unable to afford the basic necessities of housing, child care, food, transportation, health care or technology." ALICE helps capture information about families who may make too much money for social programs, but are having trouble affording an average standard of living. Read more here: <a target="_blank" href="https://www.uwsatx.org/alice/">https://www.uwsatx.org/alice/</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>United Way of San Antonio and Bexar County</p>',
	description_es = ''
where id_ = 43; -- "ALICE households"

update tbl_indicators
set description_en = ''
where id_ = 44;

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>% of Licensed Childcare Capacity with an Accreditation (Licensing: Texas Department of Family and Protective Services; Accreditation: Texas Workforce Commission recognized accrediting bodies)</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Health and Human Services</p>',
	description_es = ''
where id_ = 45; -- "Child care capacity (per 1k kids)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of people 16 years and over in the civilian labor force who are employed.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B23001, C23002</p>',
	description_es = ''
where id_ = 47; -- "Employed"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of people 16 years and over in the civilian labor force who are unemployed.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B23001, C23002</p>',
	description_es = ''
where id_ = 48; -- "Unemployed"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of people 16 years and older who are not working and not actively looking for work.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B23001, C23002</p>',
	description_es = ''
where id_ = 49; -- "Not in labor force"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of occupied housing units that are owner-occupied.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B25106</p>',
	description_es = ''
where id_ = 50; -- "Home ownership"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of occupied housing units where the occupants spend 30% or more of their household income on housing costs or rent.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B25106</p>',
	description_es = ''
where id_ = 51; -- "Housing cost burden"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of owner-occupied housing units where the occupants spend 30% or more of their household income on housing costs.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B25106</p>',
	description_es = ''
where id_ = 52; -- "Owner occupied housing cost burden"


update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of renter-occupied housing units where the occupants spend 30% or more of their household income on rent.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B25106</p>',
	description_es = ''
where id_ = 53; -- "Renter occupied housing cost burden"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The U.S. Census Bureau describes the Gini Index as "a summary measure of income inequality. The Gini coefficient incorporates the detailed shares data into a single statistic, which summarizes the dispersion of income across the entire income distribution." For more information, visit <a target="_blank" href="https://www.census.gov/topics/income-poverty/income-inequality/about/metrics/gini-index.html">https://www.census.gov/topics/income-poverty/income-inequality/about/metrics/gini-index.html</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19083</p>',
	description_es = ''
where id_ = 54; -- "Income inequality (Gini Index)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Jobs per 100,000 people by place where workers are employed.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s Center for Economic Studies: Longitudinal Employer-Household Dynamics</p>',
	description_es = ''
where id_ = 55; -- "Jobs per 100K"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>For households and families, "the median income is based on the distribution of the total number of households and families, including those with no income." To read more about median family income, visit <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/INC110220">https://www.census.gov/quickfacts/fact/note/US/INC110220</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19113</p>',
	description_es = ''
where id_ = 56; -- "Family income (median)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>For households and families, "the median income is based on the distribution of the total number of households and families, including those with no income." To read more about median household income, visit <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/INC110220">https://www.census.gov/quickfacts/fact/note/US/INC110220</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19013, B19049</p>',
	description_es = ''
where id_ = 57; -- "Household income (median)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of jobs by earnings per month by place where workers are employed. </p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s Center for Economic Studies: Longitudinal Employer-Household Dynamics</p>',
	description_es = ''
where id_ = 58; -- "Monthly Earnings"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of households making less than 30% of the area median income (AMI). Having a smaller percentage of AMI is indicative of lower-income households.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19001</p>',
	description_es = ''
where id_ = 59; -- "Households with income <30% AMI"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of households making less than 60% of the area median income (AMI). Having a smaller percentage of AMI is indicative of lower-income households.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19001</p>',
	description_es = ''
where id_ = 60; -- "Households with income <60% AMI"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of households making less than 80% of the area median income (AMI). Having a smaller percentage of AMI is indicative of lower-income households. </p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19001</p>',
	description_es = ''
where id_ = 61; -- "Households with income <80% AMI"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of population currently in the United States armed forces.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B23025, C23002</p>',
	description_es = ''
where id_ = 62; -- "Population in armed forces"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of households with an income below the poverty threshold. The poverty threshold is dependent on the number of people in the family. Read more about the poverty thresholds here <a target="_blank" href="https://www.census.gov/topics/income-poverty/poverty/guidance/poverty-measures.html">https://www.census.gov/topics/income-poverty/poverty/guidance/poverty-measures.html</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B17001, B17003</p>',
	description_es = ''
where id_ = 63; -- "Income below poverty"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>As described by the Census Bureau, "the federal SSI program provides monthly cash assistance to low-income elderly persons and to low-income disabled persons." Read more about how SSI can be used to estimate poverty here <a target="_blank" href="https://www.census.gov/programs-surveys/saipe/guidance/model-input-data/security.html">https://www.census.gov/programs-surveys/saipe/guidance/model-input-data/security.html</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B19056, B17015</p>',
	description_es = ''
where id_ = 65; -- "Supplemental Security Income (SSI)"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who do not have health insurance.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B27001</p>',
	description_es = ''
where id_ = 66; -- "Uninsured"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of adults 18 to 24 years of age who are neither enrolled in school nor working. This indicator measures youth who are detached from these activities for various reasons. Read more about detached youth here <a target="_blank" href="https://nces.ed.gov/programs/coe/indicator/col/not-in-school-not-working-neet">https://nces.ed.gov/programs/coe/indicator/col/not-in-school-not-working-neet</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B14005</p>',
	description_es = ''
where id_ = 67; -- "Youth not in school or not working"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The higher the transportation cost index, the lower the cost of transportation in that neighborhood. As explained by the U.S. Department of Housing and Urban Development, transportation costs may be low for many reasons, including "greater access to public transportation and the density of homes, services, and jobs in the neighborhood and surrounding community." For more information, visit <a target="_blank" href="https://hudgis-hud.opendata.arcgis.com/datasets/HUD::low-transportation-cost-index/about">https://hudgis-hud.opendata.arcgis.com/datasets/HUD::low-transportation-cost-index/about</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>United States Department of Housing and Urban Development</p>',
	description_es = ''
where id_ = 68; -- "Low transportation cost index"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people whose education is less than a high school diploma or GED.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, C15002</p>',
	description_es = ''
where id_ = 69; -- "Education less than high school diploma/GED"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of people who have received a Bachelor''s degree or a higher degree.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, C15002</p>',
	description_es = ''
where id_ = 70; -- "Education Bachelor''s degree or higher"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of 3- and 4- year-olds enrolled in preschool.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B14002, B14006, B14007</p>',
	description_es = ''
where id_ = 71; -- "Enrolled in preschool"

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The Early Development Instrument (EDI) measures children''s development, and they are considered "vulnerable" when their domain score is at or below the 10th percentile of the national EDI dataset.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>ReadyKidSA</p>',
	description_es = ''
where id_ = 73; -- "Children scoring "Vulnerable" on kinder-readiness"


update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>As described by the United States Department of Housing and Urban Development, "the Environmental Health Hazard Exposure Index summarizes potential exposure to harmful toxins at a neighborhood level." For more information, visit <a target="_blank" href="https://hudgis-hud.opendata.arcgis.com/datasets/c7e2c62560bd4a999f0e0b2f4cee2494_0/explore?location=33.379342%2C52.449633%2C3.80">https://hudgis-hud.opendata.arcgis.com/datasets/c7e2c62560bd4a999f0e0b2f4cee2494_0/explore?location=33.379342%2C52.449633%2C3.80</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>United States Department of Housing and Urban Development</p>',
	description_es = ''
where id_ = 76; -- "Environmental health hazard index"


update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Food deserts measure areas with low-access to fresh foods and produce. For more information, visit <a target="_blank" href="https://gisportal.ers.usda.gov/portal/apps/experiencebuilder/experience/?id=a53ebd7396cd4ac3a3ed09137676fd40">https://gisportal.ers.usda.gov/portal/apps/experiencebuilder/experience/?id=a53ebd7396cd4ac3a3ed09137676fd40</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as: <ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>United States Department of Agriculture: Economic Research Service</p>',
	description_es = ''
where id_ = 77; -- "Food insecurity/food deserts"