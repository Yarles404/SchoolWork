USE project1;

UPDATE students
	SET name = 'Scott'
    WHERE ssn = 746897816;
    
UPDATE major m
	JOIN students s ON s.snum = m.snum
    SET m.name = 'Computer Science', m.level = 'MS'
    WHERE s.ssn = 746897816;

SET SQL_SAFE_UPDATES = 0;
DELETE FROM register
	WHERE regtime = 'Spring2021';
SET SQL_SAFE_UPDATES = 1;