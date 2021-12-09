create table if not exists account
(
    id       int8 generated by default as identity,
    login    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null,
    role_id  int4,
    primary key (id)
);

create table if not exists bug
(
    id          int8 generated by default as identity,
    bug_name    varchar(255),
    description varchar(255),
    user_id     int8,
    primary key (id)
);

create table if not exists feature
(
    id            int8 generated by default as identity,
    description   varchar(255),
    feature_roles varchar(255),
    name          varchar(255),
    user_id       int8,
    primary key (id)
);

create table if not exists role
(
    id         int4 generated by default as identity,
    user_roles varchar(255),
    primary key (id)
);

create table if not exists task
(
    id          int8 generated by default as identity,
    description varchar(255),
    name        varchar(255),
    task_role   varchar(255),
    feature_id  int8,
    user_id     int8,
    primary key (id)
);

alter table if exists account
    add constraint account_role_fk foreign key (role_id) references role;
alter table if exists bug
    add constraint bug_task_fk foreign key (user_id) references task;
alter table if exists feature
    add constraint feature_account_fk foreign key (user_id) references account;
alter table if exists task
    add constraint task_feature_fk foreign key (feature_id) references feature;
alter table if exists task
    add constraint task_account_fk foreign key (user_id) references account;
