INSERT INTO question_range (category, lower, upper, score) VALUES ('AGE_RANGE', 0, 25, 1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('AGE_RANGE', 26, 35, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('AGE_RANGE', 36, 45, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('AGE_RANGE', 46, 55, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('AGE_RANGE', 56, 125, 5);

INSERT INTO question_range (category, lower, upper, score) VALUES ('SLEEP', 0, 6, 1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('SLEEP', 7, 7, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('SLEEP', 8, 8, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('SLEEP', 9, 9, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('SLEEP', 10, 100, 5);

INSERT INTO question_range (category, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 0, 1, 1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 2, 2, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 3, 3, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 4, 4, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('CAFFEINE_DRINKS', 5, 100, 5);

INSERT INTO question_range (category, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 0, 0, -2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 1, 1, -1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 2, 2, .5);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 3, 3, 1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BOWEL_MOVEMENTS', 4, 100, 2);

INSERT INTO question_range (category, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 0, 0, 1);
INSERT INTO question_range (category, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 1, 1, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 2, 2, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 3, 3, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('ACTIVITY_LEVEL', 4, 4, 5);

INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_MALE', 0, 10, 5);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_MALE', 11, 14, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_MALE', 15, 20, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_MALE', 21, 24, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_MALE', 25, 100, 1);

INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 0, 15, 5);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 16, 23, 4);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 24, 30, 3);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 31, 36, 2);
INSERT INTO question_range (category, lower, upper, score) VALUES ('BODY_FAT_FEMALE', 37, 100, 1);

INSERT INTO question (text) VALUES ('What time did you take the medicine? (Start Time)');
INSERT INTO question (text) VALUES ('What time is it when you first start feeling the effects?');
INSERT INTO question (text) VALUES ('What time is it when you feel the peak?');
INSERT INTO question (text) VALUES ('How long does the peak last?');
INSERT INTO question (text) VALUES ('What time is it when you started to feel the medicinal effects wear off?');
INSERT INTO question (text) VALUES ('What was the potency of the peak for you? 1=none/slight effect, 2=moderate effect, 3=strong effect, 4=very strong effect, 5=too strong)');
INSERT INTO question (text) VALUES ('What was the potency strength average for the overall medicinal effects?  (scale 1-5)');
INSERT INTO question (text) VALUES ('Did you eat during the effects?');

INSERT INTO question (text) VALUES ('How old are you?');
INSERT INTO question (text) VALUES ('How many hours of sleep do you get?');
INSERT INTO question (text) VALUES ('How many caffeine drink a day do you consume?');
INSERT INTO question (text) VALUES ('How ofen do you have bowel movements a day?');
INSERT INTO question (text) VALUES ('What is your daily activity level?');
INSERT INTO question (text) VALUES ('What is you body fat %?');
INSERT INTO question (text) VALUES ('What is you body fat %?');

INSERT INTO profile (amount_nicotine_day, avg_hours_sweat_week, avg_ounce_meat_day, avg_ounce_pot_thc_week, avg_sex_week, children, dob, email, first_name, gender, generic_items, had_menopause, hot_cold_normal, last_name, middle_name, on_prescription_meds, phone, prefer_salivas, use_nicotine, vegetarian, weight, activityLevel_id, ageRange_id, bodyfat_id, bowelMovement_id, caffeineDrinks_id, sleep_id) VALUES (1, 5, 5, 5, 5, 0, '1970-01-01', 'john.doe@mmj.com', 'John', 'M', 0, 0, 3, 'Doe', 'Y', 1, '702-555-5555', 0, 1, 0, 180, (SELECT id from question_range where category='ACTIVITY_LEVEL' and lower = 2), (SELECT id from question_range where category='AGE_RANGE' and lower = 36), (SELECT id from question_range where category='BODY_FAT_MALE' and lower = 15), (SELECT id from question_range where category='BOWEL_MOVEMENTS' and lower = 2), (SELECT id from question_range where category='CAFFEINE_DRINKS' and lower = 2), (SELECT id from question_range where category='SLEEP' and lower = 8));

INSERT INTO survey (name) VALUES ('My awesome survey');

INSERT INTO profile_survey (profile_id, survey_id) VALUES ((SELECT MAX(id) from profile), (SELECT MAX(id) FROM survey));

INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id) from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+1 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+3 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+4 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+5 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+6 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+7 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+8 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+9 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+10 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+11 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+12 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+13 from question));
INSERT INTO survey_question (survey_id, question_id) VALUES ((SELECT MAX(id) from survey), (SELECT MIN(id)+14 from question));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)   from question_range where category = 'AGE_RANGE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+1 from question_range where category = 'AGE_RANGE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+2 from question_range where category = 'AGE_RANGE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+3 from question_range where category = 'AGE_RANGE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+8 from question), (SELECT MIN(id)+4 from question_range where category = 'AGE_RANGE'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)   from question_range where category = 'SLEEP'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+1 from question_range where category = 'SLEEP'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+2 from question_range where category = 'SLEEP'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+3 from question_range where category = 'SLEEP'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+9 from question), (SELECT MIN(id)+4 from question_range where category = 'SLEEP'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)   from question_range where category = 'CAFFEINE_DRINKS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+1 from question_range where category = 'CAFFEINE_DRINKS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+2 from question_range where category = 'CAFFEINE_DRINKS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+3 from question_range where category = 'CAFFEINE_DRINKS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+10 from question), (SELECT MIN(id)+4 from question_range where category = 'CAFFEINE_DRINKS'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)   from question_range where category = 'BOWEL_MOVEMENTS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+1 from question_range where category = 'BOWEL_MOVEMENTS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+2 from question_range where category = 'BOWEL_MOVEMENTS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+3 from question_range where category = 'BOWEL_MOVEMENTS'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+11 from question), (SELECT MIN(id)+4 from question_range where category = 'BOWEL_MOVEMENTS'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)   from question_range where category = 'ACTIVITY_LEVEL'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+1 from question_range where category = 'ACTIVITY_LEVEL'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+2 from question_range where category = 'ACTIVITY_LEVEL'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+3 from question_range where category = 'ACTIVITY_LEVEL'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+12 from question), (SELECT MIN(id)+4 from question_range where category = 'ACTIVITY_LEVEL'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)   from question_range where category = 'BODY_FAT_MALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+1 from question_range where category = 'BODY_FAT_MALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_MALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+3 from question_range where category = 'BODY_FAT_MALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+13 from question), (SELECT MIN(id)+4 from question_range where category = 'BODY_FAT_MALE'));

INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)   from question_range where category = 'BODY_FAT_FEMALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+1 from question_range where category = 'BODY_FAT_FEMALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_FEMALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+3 from question_range where category = 'BODY_FAT_FEMALE'));
INSERT INTO question_question_ranges (question_id, question_range_id) VALUES ((SELECT MIN(id)+14 from question), (SELECT MIN(id)+4 from question_range where category = 'BODY_FAT_FEMALE'));

INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('1pm', (SELECT MAX(id) from profile), (SELECT MIN(id) from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('1:30pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+1 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('2:30pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+2 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('45 min', (SELECT MAX(id) from profile), (SELECT MIN(id)+3 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('3:30 pm', (SELECT MAX(id) from profile), (SELECT MIN(id)+4 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('3', (SELECT MAX(id) from profile), (SELECT MIN(id)+5 from question), (SELECT MAX(id) from survey));
INSERT INTO answer (answer, profile_id, question_id, survey_id) VALUES ('2', (SELECT MAX(id) from profile), (SELECT MIN(id)+6 from question), (SELECT MAX(id) from survey));

INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+8 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'AGE_RANGE'));
INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+9 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'SLEEP'));
INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+10 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'CAFFEINE_DRINKS'));
INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+11 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'BOWEL_MOVEMENTS'));
INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+12 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'ACTIVITY_LEVEL'));
INSERT INTO answer (profile_id, question_id, survey_id, questionRange_id) VALUES ((SELECT MAX(id) from profile), (SELECT MIN(id)+13 from question), (SELECT MAX(id) from survey), (SELECT MIN(id)+2 from question_range where category = 'BODY_FAT_MALE'));











