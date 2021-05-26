INSERT INTO user_roles(role)
values ('ADMIN');

INSERT INTO user_roles(role)
values ('USER');

INSERT INTO public.users(name, phone, email, login, password, user_role_id)
VALUES ('admin', '616', 'admin@admin.com', 'admin', '616', 1);


