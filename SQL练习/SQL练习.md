# SQL练习

> **学院：  省级示范性软件学院**
>
> **课程：  JavaWeb后端开发技术**
>
> **题目：** 《SQL练习》
>
> **姓名：**  郭研棋
>
> **学号：**  2200770285
>
> **班级：**  软工2202
>
> **日期：**  2024-10-20
>
> **实验环境：**  MySQL Workbench

## 一、员工信息练习

1. 查询所有员工的姓名、邮箱和工作岗位。
   
   ```
   select concat(first_name," ",last_name) 姓名,email 邮箱,job_title 工作岗位
   from employees;
   ```
   
2. 查询所有部门的名称和位置。
   
   ```
   select dept_name,location
   from departments;
   ```
   
3. 查询工资超过70000的员工姓名和工资。
   
   ```
   select concat(first_name," ",last_name) 姓名,salary
   from employees
   where salary > 70000;
   ```
   
4. 查询IT部门的所有员工。
   
   ```
   select emp_id,first_name,last_name,email,phone_number,hire_date,job_title,salary
   from employees
   where dept_id = 2;
   ```
   
5. 查询入职日期在2020年之后的员工信息。
   
   ```
   select emp_id,first_name,last_name,email,phone_number,hire_date,job_title,salary
   from employees
   where hire_date >= "2021-01-01";
   ```
   
6. 计算每个部门的平均工资。
   
   ```
   select dept_name,avg(salary)
   from employees,departments
   where employees.dept_id = departments.dept_id
   group by employees.dept_id;
   ```
   
7. 查询工资最高的前3名员工信息。
   
   ```
   select emp_id,first_name,last_name,email,phone_number,hire_date,job_title,salary
   from employees
   order by salary desc
   limit 3;
   ```
   
8. 查询每个部门员工数量。
   
   ```
   select dept_name,count(emp_id)
   from employees,departments
   where employees.dept_id = departments.dept_id
   group by employees.dept_id;
   ```
   
9. 查询没有分配部门的员工。
   
   ```
   select emp_id,first_name,last_name,email,phone_number,hire_date,job_title,salary
   from employees
   where dept_id is null;
   ```
10. 询参与项目数量最多的员工。
    
    ```
    with total_emp_proj as(
    select e.*,count(*) total_proj
    from employees e
    join employee_projects ep on e.emp_id = ep.emp_id
    group by e.emp_id
    ),
    rank_table as (
    select *,
    rank() over(order by total_proj desc) rank_proj_num
    from total_emp_proj
    )
    select *
    from rank_table
    where rank_table.rank_proj_num = 1;
    ```
    
11. 计算所有员工的工资总和。
    
    ```
    select sum(salary) 工资总和
    from employees;
    ```
    
12. 查询姓"Smith"的员工信息。
    
    ```
    select emp_id,first_name,last_name,email,phone_number,hire_date,job_title,salary
    from employees
    where last_name = "Smith";
    ```
    
13. 查询即将在半年内到期的项目。

    ```
    select *
    from projects
    where end_date > now() and timestampdiff(day,now(),end_date) <= 180;
    ```

14. 查询至少参与了两个项目的员工。
    
    ```
    select employees.*
    from employees
    where emp_id in (
    	select emp_id
        from employee_projects
        group by emp_id
        having count(project_id) >= 2
    );
    ```
    
 15. 查询没有参与任何项目的员工。

     ```
     select employees.*
     from employees
     where emp_id not in (
     select emp_id
        from employee_projects
     );
     ```

16. 计算每个项目参与的员工数量。
    
    ```
    select project_id,count(emp_id) 员工数量
    from employee_projects
    group by project_id;
    ```
    
17. 查询工资第二高的员工信息。
    
    ```
    WITH ranked_employees AS (
    	 SELECT e.*, d.dept_name,
    	 RANK() OVER(ORDER BY e.salary DESC) AS salary_rank
    	 FROM employees e
    	 JOIN departments d ON e.dept_id = d.dept_id
    )
    SELECT *
    FROM ranked_employees
    WHERE salary_rank = 2;
    ```
    
18. 查询每个部门工资最高的员工。
    
    ```
    WITH ranked_employees AS (
    	 SELECT e.*, d.dept_name,
    	 RANK() OVER(PARTITION BY e.dept_id ORDER BY e.salary DESC) AS salary_rank
    	 FROM employees e
    	 JOIN departments d ON e.dept_id = d.dept_id
    )
    SELECT *
    FROM ranked_employees
    WHERE salary_rank <= 1;
    ```
    
19. 计算每个部门的工资总和,并按照工资总和降序排列。
    
    ```
    select departments.*,sum(salary)
    from departments,employees
    where departments.dept_id = employees.dept_id
    group by employees.dept_id
    order by sum(salary) desc;
    ```
    
20. 查询员工姓名、部门名称和工资。
    
    ```
    select concat(first_name," ",last_name) 姓名,dept_name 部门名称,salary 工资
    from departments,employees
    where departments.dept_id = employees.dept_id;
    ```
    
21. 查询每个员工的上级主管(假设emp_id小的是上级)。

    ```
    select x.emp_id,
           concat(x.first_name, ' ', x.last_name) as 姓名,
           (select y.emp_id
            from employees y
            where y.dept_id = x.dept_id and y.emp_id < x.emp_id
            order by y.emp_id desc
            limit 1) as 上级主管
    from employees x
    order by x.emp_id;
    ```

22. 查询所有员工的工作岗位,不要重复。
    
    ```
    select distinct job_title
    from employees;
    ```
    
23. 查询平均工资最高的部门。
    
    ```
    select dept_name,avg(salary)
    from employees,departments
    where employees.dept_id = departments.dept_id
    group by employees.dept_id
    order by avg(salary) desc
    limit 1;
    ```
    
24. 查询工资高于其所在部门平均工资的员工。
    
    ```
    SELECT e.*
    FROM employees e
    WHERE e.salary > (
        SELECT AVG(e2.salary)
        FROM employees e2
        WHERE e2.dept_id = e.dept_id
        group by e2.dept_id
    );
    ```
    
25. 查询每个部门工资前两名的员工。
    
    ```
    WITH ranked_employees AS (
        SELECT e.*,
               d.dept_name,
               DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS salary_rank
        FROM employees e
        JOIN departments d ON e.dept_id = d.dept_id
    )
    SELECT emp_id, first_name, last_name, dept_name, salary
    FROM ranked_employees
    WHERE salary_rank <= 2
    ORDER BY dept_name, salary DESC;
    ```
    
26. 查询跨部门的项目(参与员工来自不同部门)。

    ```
    select project_id
    from employee_projects ep,employees e
    where ep.emp_id = e.emp_id
    group by project_id
    having count(distinct dept_id) > 1;
    ```

27. 查询每个员工的工作年限,并按工作年限降序排序。

    ```
    select e.first_name,e.last_name,
    	timestampdiff(year,hire_date,now()) 工作年限
    from employees e
    order by timestampdiff(year,hire_date,now()) desc;
    ```

28. 查询本月过生日的员工(假设hire_date是生日)。

    ```
    select e.first_name,e.last_name
    from employees e
    where month(hire_date) = month(now());
    ```

29. 查询即将在90天内到期的项目和负责该项目的员工。

    ```
    select p.project_id,e.first_name,e.last_name
    from projects p,employee_projects ep,employees e
    where end_date > now() and ep.emp_id = e.emp_id and
    	p.project_id = ep.project_id and timestampdiff(day,now(),end_date) <= 90;
    ```

30. 计算每个项目的持续时间(天数)。

    ```
    select project_name,timestampdiff(day,start_date,end_date) 持续时间
    from projects;
    ```

31. 查询没有进行中项目的部门。

    ```
    select *
    from projects
    where end_date < now() or start_date > now();
    ```

32. 查询员工数量最多的部门。

    ```
    select dept_id,count(emp_id) 员工人数
    from employees
    group by dept_id
    order by count(emp_id) desc
    limit 1;
    ```

33. 查询参与项目最多的部门。

    ```
    select dept_id,count(distinct project_id) 参与项目数
    from employees e,employee_projects ep
    where e.emp_id = ep.emp_id
    group by dept_id
    order by count(distinct project_id) desc
    limit 1;
    ```

34. 计算每个员工的薪资涨幅(假设每年涨5%)。

    ```
    select emp_id,
    	concat(5 * timestampdiff(year,hire_date,now()) ,"%") 薪资涨幅
    from employees;
    ```

35. 查询入职时间最长的3名员工。

    ```
    select emp_id,
    	timestampdiff(day,hire_date,now()) 入职时间
    from employees
    order by timestampdiff(day,hire_date,now()) desc
    limit 3;
    ```

36. 查询名字和姓氏相同的员工。

    ```
    select employees.*
    from employees
    where first_name = last_name;
    ```

37. 查询每个部门薪资最低的员工。

    ```
    WITH ranked_employees AS (
        SELECT e.*,
               d.dept_name,
               DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary ASC) AS salary_rank
        FROM employees e
        JOIN departments d ON e.dept_id = d.dept_id
    )
    SELECT emp_id, first_name, last_name, dept_name, salary
    FROM ranked_employees
    WHERE salary_rank <= 1;
    ```

38. 查询哪些部门的平均工资高于公司的平均工资。

    ```
    select dept_id
    from employees
    group by dept_id
    having avg(salary) > (
    	select avg(salary)
        from employees
    );
    ```

39. 查询姓名包含"son"的员工信息。

    ```
    select *
    from employees
    where last_name like "%son%" or first_name like "%son%";
    ```

40. 查询所有员工的工资级别(可以自定义工资级别)。

    ```
    select concat(first_name," ",last_name) 姓名,
    	if(salary >= 80000,'1',
    		if(salary >= 70000,'2',
    			if(salary >= 60000,'3',
    				if(salary >= 50000,'4','5')))) as 工资级别
    from employees;
    ```

41. 查询每个项目的完成进度(根据当前日期和项目的开始及结束日期)。

    ```
    select project_id,
        case
            when start_date > now() then '0%'
            when end_date < now() then '100%'
            else concat(round(
                    (timestampdiff(day, start_date, now()) / 
                    timestampdiff(day, start_date, end_date)) * 100,
                    2),'%')
        end as 完成进度
    from projects;
    ```

42. 查询每个经理(假设job_title包含'Manager'的都是经理)管理的员工数量。

    ```
    select x.emp_id,count(y.emp_id) 员工数
    from employees x,employees y
    where x.job_title like "%Manager%" and x.dept_id = y.dept_id
    group by x.emp_id;
    ```

43. 查询工作岗位名称里包含"Manager"但不在管理岗位(salary<70000)的员工。

    ```
    select *
    from employees
    where job_title like "%Manager%" and salary < 70000;
    ```

44. 计算每个部门的男女比例(假设以名字首字母A-M为女性,N-Z为男性)。

    ```
    select dept_id,
        concat(
            sum(case when substr(first_name, 1, 1) between 'N' and 'Z' then 1 else 0 end), 
            ':', 
            sum(case when substr(first_name, 1, 1) between 'A' and 'M' then 1 else 0 end)
        ) as male_female
    from employees
    group by dept_id;
    ```

45. 查询每个部门年龄最大和最小的员工(假设hire_date反应了年龄)。

    ```
    with max_min_age as(
    	select dept_id,concat(first_name," ",last_name),
    		rank() over(partition by dept_id order by hire_date asc) as min_rank,
            rank() over(partition by dept_id order by hire_date desc) as max_rank
    	from employees
    )
    select *
    from max_min_age
    where max_rank = 1 or min_rank = 1;
    ```

46. 查询连续3天都有员工入职的日期。

    ```
    with consecutive_days as (
        select hire_date,
               lag(hire_date, 1) over (order by hire_date) as prev_day,
               lag(hire_date, 2) over (order by hire_date) as prev_prev_day
        from employees
    )
    select hire_date
    from consecutive_days
    where datediff(hire_date,prev_day) = 1 and datediff(prev_day,prev_prev_day) = 1;
    ```

47. 查询员工姓名和他参与的项目数量。

    ```
    select concat(first_name," ",last_name) 姓名,count(distinct project_id) 项目数
    from employees e
    join employee_projects ep on ep.emp_id = e.emp_id
    group by e.emp_id;
    ```

48. 查询每个部门工资最高的3名员工。
    
    ```
    WITH ranked_employees AS (
        SELECT e.*,
               d.dept_name,
               DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS salary_rank
        FROM employees e
        JOIN departments d ON e.dept_id = d.dept_id
    )
    SELECT emp_id, first_name, last_name, dept_name, salary
    FROM ranked_employees
    WHERE salary_rank <= 3
    ORDER BY dept_name, salary DESC;
    ```
    
49. 计算每个员工的工资与其所在部门平均工资的差值。

    ```
    with dept_avg_salary as (
        select dept_id, avg(salary) as avg_salary
        from employees
        group by dept_id
    )
    select e.emp_id,e.salary,d.avg_salary,
           (e.salary - d.avg_salary) as salary_minus
    from employees e,dept_avg_salary d
    where e.dept_id = d.dept_id;
    ```

50. 查询所有项目的信息,包括项目名称、负责人姓名(假设工资最高的为负责人)、开始日期和结束日期。

    ```
    with projects_detail as(
    	select project_name,concat(e.first_name," ",e.last_name) name,
        rank() over(partition by ep.project_id order by salary desc) as manager_rank
        ,start_date,end_date
        from projects p,employees e,employee_projects ep
        where e.emp_id = ep.emp_id and p.project_id = ep.project_id
    )
    select project_name,name,start_date,end_date
    from projects_detail
    where manager_rank = 1;
    ```

## 二、学生选课练习

1. 查询所有学生的信息。
   
   ```
   select *
   from student;
   ```
   
2. 查询所有课程的信息。
   
   ```
   select *
   from course;
   ```
   
3. 查询所有学生的姓名、学号和班级。
   
   ```
   select name,student_id,my_class
   from student;
   ```
   
4. 查询所有教师的姓名和职称。
   
   ```
   select name,title
   from teacher;
   ```
   
5. 查询不同课程的平均分数。
   
   ```
   select course_name,avg(score)
   from course,score
   where course.course_id = score.course_id
   group by course.course_id;
   ```
   
6. 查询每个学生的平均分数。
   
   ```
   select student.name,avg(score)
   from student,score
   where student.student_id = score.student_id
   group by student.student_id;
   ```
   
7. 查询分数大于85分的学生学号和课程号。

   ```
   select student_id,course_id
   from score
   where score > 85;
   ```

8. 查询每门课程的选课人数。
   
   ```
   select course_id,count(student_id) 选课人数
   from score
   group by course_id;
   ```
   
9. 查询选修了"高等数学"课程的学生姓名和分数。
   
   ```
   select st.name,score
   from student st,score sc,course c
   where st.student_id = sc.student_id and 
   	sc.course_id = c.course_id and course_name = "高等数学";
   ```
10. 查询没有选修"大学物理"课程的学生姓名。
    
    ```
    select st.name
    from student st
    where st.name not in(
    	select st.name
    	from student st,score sc,course c
    	where st.student_id = sc.student_id and 
    		sc.course_id = c.course_id and 
    		course_name = "大学物理"
        );
    ```
    
11. 查询C001比C002课程成绩高的学生信息及课程分数。

    ```
    select st.*, x.score C001,y.score C002
    from student st
    join score x on st.student_id = x.student_id and x.course_id = 'C001'
    join score y on st.student_id = y.student_id and y.course_id = 'C002'
    where x.score > y.score;
    ```

12. 统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比

    ```
    select c.course_id,course_name,
        count(case when score between 85 and 100 then 1 end) as score_85_100,
        concat((count(case when score between 85 and 100 then 1 end) * 100 / count(*)),"%") as percent_85_100,
        count(case when score between 70 and 85 then 1 end) as score_70_85,
        concat((count(case when score between 70 and 85 then 1 end) * 100 / count(*)),"%") as percent_70_85,
        count(case when score between 60 and 70 then 1 end) as score_60_70,
        concat((count(case when score between 60 and 70 then 1 end) * 100 / count(*)),"%") as percent_60_70,
        count(case when score < 60 then 1 end) as score_below_60,
        concat((count(case when score < 60 then 1 end) * 100 / count(*)),"%") as percent_below_60
    from score sc,course c 
    where sc.course_id = c.course_id
    group by c.course_id, c.course_name;
    ```

13. 查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。

    ```
    select student_id,score
    from score
    where course_id = "C002" and student_id not in (
    	select student_id
        from score
        where course_id = "C004"
    );
    ```

14. 查询平均分数最高的学生姓名和平均分数。

    ```
    select st.name,avg(score)
    from student st,score sc
    where st.student_id = sc.student_id
    group by sc.student_id
    order by avg(score) desc
    limit 1;
    ```

15. 查询总分最高的前三名学生的姓名和总分。

    ```
    select st.name,sum(score)
    from student st,score sc
    where st.student_id = sc.student_id
    group by sc.student_id
    order by sum(score) desc
    limit 3;
    ```

16. 查询各科成绩最高分、最低分和平均分。要求如下：以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率。及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
    要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列

    ```
    select c.course_id,course_name,
        max(score) 最高分, min(score) 最低分,avg(score) 平均分,
        (sum(case when score >= 60 then 1 else 0 end) * 100 / count(*)) as 及格率,
        (sum(case when score between 70 and 80 then 1 else 0 end) * 100 / count(*)) as 中等率,
        (sum(case when score between 80 and 90 then 1 else 0 end) * 100 / count(*)) as 优良率,
        (sum(case when score >= 90 then 1 else 0 END) * 100 / count(*)) as 优秀率,
        count(*) as 选修人数
    from score sc,course c 
    where sc.course_id = c.course_id
    group by c.course_id, c.course_name
    order by 选修人数 DESC, c.course_id asc;
    ```
    
17. 查询男生和女生的人数。

    ```
    select gender,count(gender)
    from student st
    group by gender;
    ```

18. 查询年龄最大的学生姓名。

    ```
    select name
    from student
    where birth_date = (
    	select min(birth_date) 
        from student
    );
    ```

19. 查询年龄最小的教师姓名。

    ```
    select name
    from teacher
    where birth_date = (
    	select max(birth_date) 
        from teacher
    );
    ```

20. 查询学过「张教授」授课的同学的信息。

    ```
    select st.*
    from student st,score sc,teacher t,course c
    where t.name = "张教授" and st.student_id = sc.student_id
    	and sc.course_id = c.course_id and c.teacher_id = t.teacher_id;
    ```

21. 查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。

    ```
    select distinct st.*
    from student st,score sc
    where st.student_id = sc.student_id and course_id in(
    	select course_id
        from score
    	where student_id = "2021001"
    );
    ```

22. 查询每门课程的平均分数，并按平均分数降序排列。

    ```
    select course_id,avg(score)
    from score
    group by course_id
    order by avg(score) desc;
    ```

23. 查询学号为"2021001"的学生所有课程的分数。

    ```
    select course_id,score
    from score
    where student_id = "2021001";
    ```

24. 查询所有学生的姓名、选修的课程名称和分数。

    ```
    select st.name,course_name,score
    from student st,score sc,course c
    where st.student_id = sc.student_id and sc.course_id = c.course_id;
    ```

25. 查询每个教师所教授课程的平均分数。

    ```
    select t.name,avg(score)
    from teacher t,course c,score sc
    where t.teacher_id = c.teacher_id and sc.course_id = c.course_id
    group by t.teacher_id;
    ```

26. 查询分数在80到90之间的学生姓名和课程名称。

    ```
    select st.name,course_name
    from student st,course c,score sc
    where score between 80 and 90 and st.student_id = sc.student_id
    	and sc.course_id = c.course_id;
    ```

27. 查询每个班级的平均分数。

    ```
    select my_class,avg(score)
    from student st,score sc
    where st.student_id = sc.student_id
    group by my_class;
    ```

28. 查询没学过"王讲师"老师讲授的任一门课程的学生姓名。

    ```
    select distinct st.name
    from student st
    where st.student_id not in (
        select sc.student_id
        from score sc,course c,teacher t
        where sc.course_id = c.course_id 
    		and c.teacher_id = t.teacher_id and t.name = '王讲师'
    );
    ```

29. 查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。

    ```
    select st.student_id,st.name,avg(score)
    from student st,score sc
    where st.student_id = sc.student_id and score < 85
    group by st.student_id
    having count(course_id) >= 2;
    ```

30. 查询所有学生的总分并按降序排列。

    ```
    select student_id,sum(score)
    from score
    group by student_id
    order by sum(score) desc;
    ```

31. 查询平均分数超过85分的课程名称。

    ```
    select course_name,avg(score)
    from course c,score sc
    where c.course_id = sc.course_id
    group by c.course_id
    having avg(score) > 85;
    ```

32. 查询每个学生的平均成绩排名

    ```
    with ranked_score as(
    	select st.name,
        rank() over(order by avg(score) desc) as score_rank
    	from student st,score sc
        where st.student_id = sc.student_id
        group by st.student_id
    )
    select *
    from ranked_score;
    ```

33. 查询每门课程分数最高的学生姓名和分数。

    ```
    with ranked_course as(
    	select course_id,st.name,score,
    		rank() over(partition by course_id order by score desc) 
            as course_rank
    	from student st,score sc
        where st.student_id = sc.student_id
    )
    select *
    from ranked_course
    where course_rank = 1;
    ```

34. 查询选修了"高等数学"和"大学物理"的学生姓名。

    ```
    select st.name
    from student st,score sc,course c
    where sc.course_id = c.course_id and 
    	st.student_id = sc.student_id and course_name = "大学物理"
        and st.student_id in(
    	select student_id
        from score sc,course c
        where sc.course_id = c.course_id and course_name = "高等数学"
    );
    ```

35. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。

    ```
    select *,avg(score) over(partition by student_id) as avg_score
    from score
    order by avg_score desc;
    ```

36. 查询分数最高和最低的学生姓名及其分数。

    ```
    with max_min_score as(
    	select st.name,score,
    		rank() over(order by score) as score_rank
    	from student st,score sc
        where st.student_id = sc.student_id
    )
    select *
    from max_min_score
    where score_rank = 1 or score_rank = (
    	select max(score_rank)
        from max_min_score
    );
    ```

37. 查询每个班级的最高分和最低分。

    ```
    with class_score as(
    	select st.name,score,
    		rank() over(partition by my_class order by score) as score_rank
    	from student st,score sc
        where st.student_id = sc.student_id
    )
    select *
    from class_score
    where score_rank = 1 or score_rank = (
    	select max(score_rank)
        from class_score
    );
    ```

38. 查询每门课程的优秀率（优秀为90分）。

    ```
    select c.course_id,course_name,
        (count(case when score >= 90 then 1 end) * 100 / count(*)) as excellence_rate
    from score sc,course c 
    where sc.course_id = c.course_id
    group by c.course_id;
    ```

39. 查询平均分数超过班级平均分数的学生。

    ```
    with class_avg as(
    	select my_class,avg(score) as class_score
    	from student st,score sc
    	where st.student_id = sc.student_id
    	group by my_class
    ),
    student_avg as(
    	select student_id,avg(score) as student_score
    	from score
    	group by student_id
    )
    select st.name
    from class_avg ca,student_avg sa,student st
    where ca.class_score > sa.student_score and 
    	ca.my_class = st.my_class and
        sa.student_id = st.student_id;
    ```

40. 查询每个学生的分数及其与课程平均分的差值。

    ```
    with student_avg_score as (
        select course_id, avg(score) as avg_score
        from score
        group by course_id
    )
    select sc.student_id,sc.score,sa.avg_score,
           (sc.score - sa.avg_score) as score_minus
    from score sc,student_avg_score sa
    where sc.course_id = sa.course_id;
    ```

41. 查询至少有一门课程分数低于80分的学生姓名。

    ```
    select distinct st.name
    from student st,score sc
    where sc.student_id = st.student_id and score < 80;
    ```

42. 查询所有课程分数都高于85分的学生姓名。

    ```
    select distinct st.name
    from student st,score sc
    where sc.student_id = st.student_id and st.student_id not in(
    	select student_id
        from score
        where score < 85
    );
    ```

43. 查询查询平均成绩大于等于90分的同学的学生编号和学生姓名和平均成绩。

    ```
    select st.student_id,st.name,avg(score)
    from student st,score sc
    where sc.student_id = st.student_id
    group by student_id
    having avg(score) > 90;
    ```

44. 查询选修课程数量最少的学生姓名。

    ```
    with min_course as(
    	select st.name,
    		rank() over(order by count(course_id) asc) as min_rank
    	from student st,score sc
    	where sc.student_id = st.student_id
        group by st.student_id
    )
    select *
    from min_course
    where min_rank = 1;
    ```

45. 查询每个班级的第2名学生（按平均分数排名）。

    ```
    with ranked_avg as(
    	select st.name,my_class,
    		rank() over(partition by my_class order by avg(score) desc) avg_rank
    	from student st,score sc
    	where sc.student_id = st.student_id
        group by my_class,st.student_id
    )
    select *
    from ranked_avg
    where avg_rank = 2;
    ```

46. 查询每门课程分数前三名的学生姓名和分数。

    ```
    with top3_score as(
    	select course_id,st.name,score,
    		rank() over(partition by course_id order by score desc)
            as top3_rank
    	from student st,score sc
        where st.student_id = sc.student_id
    )
    select *
    from top3_score
    where top3_rank <=3;
    ```

47. 查询平均分数最高和最低的班级。

    ```
    with class_avg_score as(
    	select my_class,
    		rank() over(order by avg(score) desc) as score_rank
    	from student st,score sc
        where st.student_id = sc.student_id
        group by st.my_class
    )
    select *
    from class_avg_score
    where score_rank = 1 or score_rank = (
    	select max(score_rank)
        from class_avg_score
    );
    ```

48. 查询每个学生的总分和他所在班级的平均分数。

    ```
    with class_avg as(
    	select my_class,avg(score) as class_score
    	from student st,score sc
    	where st.student_id = sc.student_id
    	group by my_class
    ),
    student_sum as(
    	select student_id,sum(score) as student_score
    	from score
    	group by student_id
    )
    select st.name,ss.student_score,ca.class_score
    from class_avg ca,student_sum ss,student st
    where ca.my_class = st.my_class and
        ss.student_id = st.student_id;
    ```

49. 查询每个学生的最高分的课程名称, 学生名称，成绩。

    ```
    with max_score as(
    	select course_name,st.name,score,
    		rank() over(partition by st.student_id order by score) 
            as max_score_rank
    	from student st,score sc,course c
        where st.student_id = sc.student_id and 
    		c.course_id = sc.course_id
    )
    select *
    from max_score
    where max_score_rank = 1;
    ```

50. 查询每个班级的学生人数和平均年龄。

    ```
    select my_class,count(student_id) 学生人数,
    avg(timestampdiff(year,birth_date,now())) 平均年龄
    from student
    group by my_class;
    ```
