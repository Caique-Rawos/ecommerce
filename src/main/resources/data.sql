INSERT INTO permissions (id, descricao)
VALUES (1, 'ADMIN'), (2, 'BASIC')
    ON CONFLICT (id) DO NOTHING;