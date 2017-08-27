--init

create tablespace um
datafile 'E:\app\Pc\oradata\orcl\usermanager.dbf'
size 100m;

create user um identified by um default tablespace um;
grant connect,resource to um;

connect um/um;

--create user table
create table users(
 id number(10) primary key,
 name varchar(50),
 password varchar(100),
 telephone varchar(50)
);
create sequence users_id start with 1 increment by 1 maxvalue 99999999 cache 10;

insert into users values(users_id.nextval,'admin','admin','1231231');
insert into users values(users_id.nextval,'test','test','234843434');
submit;
select * from users;

select count(*) from users where name='admin';

--create area table
create table area(
       id number(10) primary key,
       code varchar(10),
       name varchar(50)  
);
create sequence area_id start with 1 increment by 1 minvalue 1 maxvalue 9990999 cache 10;
insert into area values(area_id.nextval,'37','山东省');
insert into area values(area_id.nextval,'3701','济南市');
insert into area values(area_id.nextval,'3702','青岛市');
insert into area values(area_id.nextval,'01','北京市');
insert into area values(area_id.nextval,'0101','西城区');

insert into users values(users_id.nextval,'test01','test01','234843434');
insert into users values(users_id.nextval,'test02','test02','234843434');
insert into users values(users_id.nextval,'test03','test03','234843434');
insert into users values(users_id.nextval,'test04','test04','234843434');

select * from area;
select * from area where length(code)=2;
select * from area where length(code)=4 and substr(code,0,2)='37';


--Oracle中的分页操作
select * from users
--去掉尾巴
select id,name,password,telephone,rownum as rn from (select id,name,password,telephone from users) where rownum<=(2)*3;
--去掉头部
select * from(select id,name,password,telephone,rownum as rn 
       from (select id,name,password,telephone from users) where rownum<=(3)*3) where rn>(3-1)*3;

--创建权限表

create table permission(
       id number(10) primary key,
       name varchar2(50) not null,
       url varchar2(200),
       remark varchar2(100),
       parentId number(10)
);
create sequence permission_id start with 0 increment by 1 minvalue 0 nomaxvalue cache 10;

insert into permission values(permission_id.nextval,'用户信息管理','views/userManager.html','',4);
insert into permission values(permission_id.nextval,'角色信息管理','views/userManager.html','',4);
insert into permission values(permission_id.nextval,'权限信息管理','views/userManager.html','',4);
insert into permission values(permission_id.nextval,'用户权限管理','','',0);
insert into permission values(permission_id.nextval,'测试权限管理','','',0);
commit;

select * from permission;

update permission set url='userManager.html' where id=1;
update permission set url='permissionManager.html' where id=3;

update permission set name=?,url=?,remark=?,parentid=? where id=?

select rownum,id,Name,url,remark,parentId from permission;

--创建角色表
create table role(
       id number(10) primary key,
       name varchar2(50) not null,
       remark varchar2(200)
);
create sequence role_id start with 0 increment by 1 minvalue 0 nomaxvalue cache 10;
insert into role values(role_id.nextval,'管理员','');
insert into role values(role_id.nextval,'用户','');
insert into role values(role_id.nextval,'测试人员01','注释');
insert into role values(role_id.nextval,'测试人员02','注释');
insert into role values(role_id.nextval,'金卡用户','注释');
insert into role values(role_id.nextval,'银卡用户','注释');
update role set remark='注释' where name='管理员';
update role set remark='注释' where name='用户';
commit;
select * from role;

delete from role where id=5;

select rownum,id,name,remark from role;

select id,rn,name,remark from(select id,name,remark,rownum as rn from (select id,name,remark from role where 1=1 and name like '%管理%') where rownum<=(2)*3) where rn>(1-1)*3;

select id,name,remark from role where 1=1 and name like '%管理%';
select id,name,remark,rownum as rn from (select id,name,remark from role where 1=1 and name like '%管理%') where rownum<=(2)*3;

--创建角色权限表
create table role_permission(
       id number(10) primary key,
       roleId number(10) references role(id),
       permissionId number(10) references permission(id)
);
create sequence role_permission_id start with 0 increment by 1 minvalue 0 nomaxvalue cache 10;

select * from role_permission;
--角色有哪些权限
 select name from permission where id in(select permissionid from role_permission where roleid =1);
--给角色添加权限（给管理员角色添加用户权限管理的全部权限）
insert into role_permission values(role_permission_id.nextval,1,1);
insert into role_permission values(role_permission_id.nextval,1,2);
insert into role_permission values(role_permission_id.nextval,1,3);
insert into role_permission values(role_permission_id.nextval,1,4);
insert into role_permission values(role_permission_id.nextval,1,5);
commit;


select * from role_permission;

--创建用户角色表
create table user_role(
       id number(10) primary key,
       userId number(10) references users(id),
       roleId number(10) references role(id)
);
create sequence user_role_id start with 0 increment by 1 minvalue 0 nomaxvalue cache 10;
insert into user_role values(user_role_id.nextval,2,1);
commit;

select * from user_role;

select u.id,u.name,p.name as permissionName from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid
 inner join permission p on rp.permissionid=p.id where u.id=2;

