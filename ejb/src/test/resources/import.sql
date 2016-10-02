INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('AGE_RANGE', 1, 0, 25, 1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('AGE_RANGE', 2, 26, 35, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('AGE_RANGE', 3, 36, 45, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('AGE_RANGE', 4, 46, 55, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('AGE_RANGE', 5, 56, 125, 5);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('SLEEP', 1, 0, 6, 1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('SLEEP', 2, 7, 7, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('SLEEP', 3, 8, 8, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('SLEEP', 4, 9, 9, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('SLEEP', 5, 10, 100, 5);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 1, 0, 1, 1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 2, 2, 2, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 3, 3, 3, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 4, 4, 4, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 5, 5, 100, 5);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 1, 0, 0, -2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 2, 1, 1, -1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 3, 2, 2, .5);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 4, 3, 3, 1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 5, 4, 100, 2);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 1, 0, 0, 1);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 2, 1, 1, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 3, 2, 2, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 4, 3, 3, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 5, 4, 4, 5);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_MALE', 1, 0, 10, 5);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_MALE', 2, 11, 14, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_MALE', 3, 15, 20, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_MALE', 4, 21, 24, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_MALE', 5, 25, 100, 1);

INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 1, 0, 15, 5);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 2, 16, 23, 4);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 3, 24, 30, 3);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 4, 31, 36, 2);
INSERT INTO question_range (category, mark, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 5, 37, 100, 1);

INSERT INTO question (text) VALUES ('startTime');
INSERT INTO question (text) VALUES ('startTimeEffect');
INSERT INTO question (text) VALUES ('peakTime');
INSERT INTO question (text) VALUES ('peakLast');
INSERT INTO question (text) VALUES ('timeEffectEnd');
INSERT INTO question (text) VALUES ('potency');
INSERT INTO question (text) VALUES ('potencyStrength');
INSERT INTO question (text) VALUES ('eat');

INSERT INTO profile (amount_nicotine_day, avg_hours_sweat_week, avg_ounce_meat_day, avg_ounce_pot_thc_week, avg_sex_week, children, email, first_name, gender, generic_items, had_menopause, hot_cold_normal, last_name, middle_name, on_prescription_meds, phone, prefer_salivas, vegetarian, weight, activityLevel, ageRange, bodyfat, bowelMovement, caffeineDrinks, sleep) VALUES (1, 5, 5, 5, 5, 0, 'john.doe@mmj.com', 'John', 'M', 0, 0, 'N', 'Doe', 'Y', 1, '702-555-5555', 0, 0, 180, 3, 3, 3, 3, 3, 3);

INSERT INTO survey (name) VALUES ('My awesome survey');

INSERT INTO profile_survey (profile_id, survey_id) VALUES ((SELECT MAX(id) from profile), (SELECT MAX(id) FROM survey));

-- Survey
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id) from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+1 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+3 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+4 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+5 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+6 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+7 from question));

-- Profile
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+8 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+9 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+10 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+11 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+12 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+13 from question));
--INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+14 from question));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)   from question_range where category = 'AGE_RANGE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+1 from question_range where category = 'AGE_RANGE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+2 from question_range where category = 'AGE_RANGE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+3 from question_range where category = 'AGE_RANGE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+4 from question_range where category = 'AGE_RANGE'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)   from question_range where category = 'SLEEP'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+1 from question_range where category = 'SLEEP'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+2 from question_range where category = 'SLEEP'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+3 from question_range where category = 'SLEEP'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+4 from question_range where category = 'SLEEP'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)   from question_range where category = 'CAFFEINE_DRINKS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+1 from question_range where category = 'CAFFEINE_DRINKS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+2 from question_range where category = 'CAFFEINE_DRINKS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+3 from question_range where category = 'CAFFEINE_DRINKS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+4 from question_range where category = 'CAFFEINE_DRINKS'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)   from question_range where category = 'BOWEL_MOVEMENTS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+1 from question_range where category = 'BOWEL_MOVEMENTS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+2 from question_range where category = 'BOWEL_MOVEMENTS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+3 from question_range where category = 'BOWEL_MOVEMENTS'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+4 from question_range where category = 'BOWEL_MOVEMENTS'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)   from question_range where category = 'ACTIVITY_LEVEL'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+1 from question_range where category = 'ACTIVITY_LEVEL'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+2 from question_range where category = 'ACTIVITY_LEVEL'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+3 from question_range where category = 'ACTIVITY_LEVEL'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+4 from question_range where category = 'ACTIVITY_LEVEL'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)   from question_range where category = 'BODY_FAT_MALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+1 from question_range where category = 'BODY_FAT_MALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_MALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+3 from question_range where category = 'BODY_FAT_MALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+4 from question_range where category = 'BODY_FAT_MALE'));

--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)   from question_range where category = 'BODY_FAT_FEMALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+1 from question_range where category = 'BODY_FAT_FEMALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_FEMALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+3 from question_range where category = 'BODY_FAT_FEMALE'));
--INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+4 from question_range where category = 'BODY_FAT_FEMALE'));

INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('1pm', (SELECT MAX(id) from profile), (SELECT MIN(id) from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('1:30pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+1 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('2:30pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+2 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('45 min', (SELECT MAX(id) from profile), (SELECT MIN(id)+3 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('3:30 pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+4 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('3', (SELECT MAX(id) from profile), (SELECT MIN(id)+5 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('2', (SELECT MAX(id) from profile), (SELECT MIN(id)+6 from question), (SELECT MAX(id) from survey));

--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+8 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'AGE_RANGE'));
--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+9 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'SLEEP'));
--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+10 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'CAFFEINE_DRINKS'));
--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+11 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'BOWEL_MOVEMENTS'));
--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+12 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'ACTIVITY_LEVEL'));
--INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+13 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_MALE'));











