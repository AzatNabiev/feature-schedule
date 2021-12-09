insert into role(user_roles)
values ('MANAGER'),
       ('DEVELOPER'),
       ('TESTER');

insert into account (login, name, password, role_id)
values ('test@gmail.com', 'Schmidt', '$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6', 1);