CREATE TABLE IF NOT EXISTS items
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    link            VARCHAR(200),
    title           TEXT,
    description     TEXT,
    chatId          BIGINT,
    templateId      VARCHAR(200),
    customizationId VARCHAR(200),
    isActive        BOOLEAN
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;