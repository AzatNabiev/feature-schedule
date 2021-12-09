insert into role(user_roles)
values ('MANAGER'),
       ('DEVELOPER'),
       ('TESTER');

insert into account (login, name, password, role_id)
values ('test@gmail.com', 'Schmidt', '$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6', 1);

insert into account (login, name, password, role_id)
values ('developer@gmail.com', 'Azat', '$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6', 2);

insert into account (login, name, password, role_id)
values ('tester@gmail.com', 'Mark', '$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6', 3);

insert into feature (description, feature_roles, name, user_id)
values ('TEST', 'OPEN', 'TEST', 2);

insert into task (description, name, task_role, feature_id, user_id)
values ('test', 'test', 'OPEN', 1, 2);
