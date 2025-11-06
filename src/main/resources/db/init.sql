
CREATE TABLE IF NOT EXISTS task_info (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  notes TEXT,
  revenue DECIMAL(19,4),
  time_taken DECIMAL(19,4),
  roi DECIMAL(19,4),
  priority VARCHAR(32),
  status VARCHAR(32),
  created_at TIMESTAMP NULL
);