CREATE TABLE IF NOT EXISTS procedure_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    log_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    log_message TEXT
);

CREATE TABLE IF NOT EXISTS gpa_log (
                                             log_id INT AUTO_INCREMENT PRIMARY KEY,
                                             log_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                                             log_message TEXT
);
