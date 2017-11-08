CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email TEXT NOT NULL,
  full_name TEXT NOT NULL,
  password_digest TEXT NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone
);

create unique index idx_users_on_email on users(email);