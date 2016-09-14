-- *** Usage: Script to be run after a clean import. Use for all environments. ***

-- Setup SA Codes
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA1", "SA Code 1", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA2", "SA Code 2", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA3", "SA Code 3", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA4", "SA Code 4", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA5", "SA Code 5", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA6", "SA Code 6", true);
INSERT INTO sa_code (sa_code, description, active) VALUES ("SA7", "SA Code 7", true);

-- Setup Traveler Relationship
INSERT INTO travelerrelationship (value, description, active) VALUES ('PA', 'Parent', 1);
INSERT INTO travelerrelationship (value, description, active) VALUES ('CH', 'Child', 1);
INSERT INTO travelerrelationship (value, description, active) VALUES ('SP', 'Spouse', 1);
INSERT INTO travelerrelationship (value, description, active) VALUES ('DP', 'Domestic Partner', 1);
INSERT INTO travelerrelationship (value, description, active) VALUES ('TC', 'Travel Companion', 1);

-- Setup Traveler Types
INSERT INTO traveltypes (value, description, active) VALUES ('EMPL', 'Employee', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('EMET', 'Employee and eligible traveler', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('ELTR', 'Eligible traveler', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('RETI', 'Retiree', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('REET', 'Retiree and eligible traveler', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('REEL', 'Retiree eligible', 1);
INSERT INTO traveltypes (value, description, active) VALUES ('GUTR', 'Guest pass traveler', 1);

-- Setup Suspension Types
INSERT INTO suspensiontype (value, description, active) VALUES ('LOA', 'Leave of Absence', 1);
INSERT INTO suspensiontype (value, description, active) VALUES ('NWH', 'New Hire', 1);
INSERT INTO suspensiontype (value, description, active) VALUES ('MIS', 'Misconduct/Abuse', 1);
INSERT INTO suspensiontype (value, description, active) VALUES ('OTH', 'Other', 1);

-- Setup configuration initial VALUES
INSERT INTO configuration (category, identity, value, description, year, reg_ex) VALUES ('PASS', 'DEFAULT_GUEST_PASS_ALLOTMENT', '24', 'Default guess pass allotment.', 2016, '^[0-9]{1,3}$');
INSERT INTO configuration (category, identity, value, description, year, reg_ex) VALUES ('PASS', 'DEFAULT_VACATION_PASS_ALLOTMENT', '1', 'Default vacation pass allotment.', 2016, '^[0-9]{1,3}$');
INSERT INTO configuration (category, identity, value, description, year, reg_ex) VALUES ('PASSENGER_LIMIT', 'MAX_ALLOWED_CHILDREN', '8', 'Max allowed children.', 2016, '^[0-9]{1,3}$');
INSERT INTO configuration (category, identity, value, description, year, reg_ex) VALUES ('PASSENGER_LIMIT', 'MAX_ALLOWED_PARENTS', '2', 'Max allowed parents.', 2016, '^[0-9]{1,3}$');
INSERT INTO configuration (category, identity, value, description, year, reg_ex) VALUES ('PASSENGER_LIMIT', 'MAX_ALLOWED_SPDPTC', '1', 'Max allowed spouse, domestic partners and travel companions.', 2016, '^[0-9]{1,3}$');
-- Update employee records with default VALUES FROM config above.
UPDATE employee SET sa_code_id = (SELECT id FROM sa_code WHERE sa_code LIKE '%SA3%') WHERE job_code != '2035';
UPDATE employee SET sa_code_id = (SELECT id FROM sa_code WHERE sa_code LIKE '%SA5%') WHERE job_code = '2035';

UPDATE employee SET guest_passes_alloted = 24, guest_passes_booked = 0, vacation_passes_alloted = 1, vacation_passes_used = 0;
