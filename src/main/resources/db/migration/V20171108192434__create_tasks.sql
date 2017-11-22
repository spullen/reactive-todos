CREATE TABLE tasks (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID REFERENCES users(id) NOT NULL,
  content TEXT,
  status TEXT NOT NULL,
  priority INT NOT NULL DEFAULT 0,
  due_date timestamp without time zone,
  completed_at timestamp without time zone,
  created_at timestamp without time zone,
  updated_at timestamp without time zone
);
