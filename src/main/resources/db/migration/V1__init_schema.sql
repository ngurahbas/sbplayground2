CREATE TYPE id_type AS ENUM ('EMAIL', 'PHONE');
CREATE TYPE id_source AS ENUM ('GOOGLE', 'GITHUB', 'WA', 'SMS');

CREATE CAST (varchar AS id_type) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS id_source) WITH INOUT AS IMPLICIT;

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE  user_auth
(
    id          BIGSERIAL PRIMARY KEY,
    type        id_type     NOT NULL,
    value       TEXT        NOT NULL,
    source      id_source   NOT NULL,
    data        JSONB,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT user_auth_type_value_source_key UNIQUE (type, value, source)
);

CREATE TRIGGER update_user_auth_updated_at
BEFORE UPDATE ON user_auth
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
