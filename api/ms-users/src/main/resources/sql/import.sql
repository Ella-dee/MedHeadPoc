INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER') on conflict (name) do nothing;
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MODERATOR') on conflict (name) do nothing;
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN') on conflict (name) do nothing;
