drop database expenniesdb;
drop user expennies;

create user expennies with password 'password';
create database expenniesdb with template=template0 owner=expennies;
\connect expenniesdb;

alter default privileges grant all on tables to expennies;
alter default privileges grant all on sequences to expennies;

create table ep_users (
    user_id integer primary key not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    email varchar(30) not null,
    password text not null
);

create table ep_categories (
    category_id integer primary key not null,
    user_id integer not null,
    title varchar(20) not null
);
alter table ep_categories add constraint cat_users_fk
foreign key (user_id) references ep_users(user_id) on delete cascade;

create table ep_transactions (
    transaction_id integer primary key not null,
    category_id integer not null,
    user_id integer not null,
    amount numeric(10, 2) not null,
    note varchar(50) not null,
    -- epoch time
    transaction_date bigint not null,
    is_recurring boolean not null default false,
    -- daily / weekly / monthly / yearly
    recurrence_type varchar(50)
);
alter table ep_transactions add constraint trans_cat_fk
foreign key (category_id) references ep_categories(category_id) on delete cascade;
alter table ep_transactions add constraint trans_users_fk
foreign key (user_id) references ep_users(user_id) on delete cascade;

create sequence ep_users_seq increment 1 start 1;
create sequence ep_categories_seq increment 1 start 1;
create sequence ep_transactions_seq increment 1 start 1000;

INSERT INTO ep_users (user_id, first_name, last_name, email, password)
VALUES (NEXTVAL('ep_users_seq'), 'Test', 'Dev', 'test@dev.com', 'password');