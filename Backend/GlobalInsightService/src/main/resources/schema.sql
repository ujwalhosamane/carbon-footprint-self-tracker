CREATE TABLE GlobalInsight (
    insightId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    description TEXT,
    date DATE
);