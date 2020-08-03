create table student.bankdetails(acc_id numeric(13) primary key,username varchar(30),balance numeric(10));
insert into student.bankdetails(acc_id,username,balance) values(567890,"P.Hareesha",100000);
select *from student.bankdetails;
create table student.translist(serialno numeric(10),acc_id numeric(13),amount numeric(10),foreign key(acc_id) references demo.bankdetails(acc_id)); 
select *from student.translist;
DELIMITER $$
DROP PROCEDURE IF EXISTS `student`.`get_transcation_list`$$

CREATE PROCEDURE `student`.`get_transcation_list`(IN accountid numeric(13))
BEGIN
	
	select amount from student.translist where serialno>((select count(*) from demo.translist)-5) and acc_id=accountid;

END$$
DELIMITER ;