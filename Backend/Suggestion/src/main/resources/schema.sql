CREATE TABLE SUGGESTIONMODEL (
    suggestionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    description TEXT,
    creationDate DATE
);