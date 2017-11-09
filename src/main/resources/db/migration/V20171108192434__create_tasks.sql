CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID REFERENCES users(id) NOT NULL,
  content TEXT,
  status TEXT NOT NULL,
  due_date timestamp without time zone,
  created_at timestamp without time zone,
  updated_at timestamp without time zone
);
