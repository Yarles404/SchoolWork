USE project1;

SELECT name, snum, ssn FROM students
	WHERE name = 'Becky';
    
SELECT m.name, m.level, s.ssn FROM students s
    JOIN major m ON m.snum = s.snum
    WHERE s.ssn = 123097834;
    
SELECT c.name, d.name FROM courses c
	JOIN departments d ON d.code = c.department_code
    WHERE d.name = 'Computer Science';
    
SELECT dg.name, dg.level, d.name FROM degrees dg
	JOIN departments d ON d.code = dg.department_code
    WHERE d.name = 'Computer Science';
    \n
SELECT s.name, m.name FROM students s
	JOIN minor m ON m.snum = s.snum;
    
SELECT COUNT(*) FROM students s
	JOIN minor m ON m.snum = s.snum;
    
SELECT s.name, s.snum FROM students s
	JOIN register r ON r.snum = s.snum
    JOIN courses c ON r.course_number = c.number
    WHERE c.name = 'Algorithm';
    
SELECT name, snum, dob FROM students
	WHERE dob = (SELECT MIN(dob) FROM students);

SELECT name, snum, dob FROM students
	WHERE dob = (SELECT MAX(dob) FROM students);
    
SELECT name, snum, ssn FROM students
	WHERE name LIKE '%n%' or name LIKE '%N%';    

SELECT name, snum, ssn FROM students
	WHERE name NOT LIKE '%n%' or name NOT LIKE '%N%';

SELECT c.number, c.name, COUNT(r.snum)
	FROM courses c
	LEFT OUTER JOIN register r on c.number = r.course_number
	group by c.number;

SELECT s.name, r.regtime FROM students s
	JOIN register r ON r.snum = s.snum
    WHERE r.regtime = 'Fall2020';
    
SELECT c.number, c.name, d.name FROM courses c
	JOIN departments d ON d.code = c.department_code
    WHERE d.name = 'Computer Science';
    
SELECT c.number, c.name, d.name FROM courses c
	JOIN departments d ON d.code = c.department_code
    WHERE d.name = 'Computer Science' or d.name = 'Landscape Architect';
    
