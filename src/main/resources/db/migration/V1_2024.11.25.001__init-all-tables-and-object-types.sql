CREATE TABLE company (
    event_id                varchar(36)     NOT NULL,
    id                      varchar(36)     NOT NULL,
    name                    text            NOT NULL,
    status                  text            NOT NULL,
    contact_information     text,
    industry                text,
    event_action            text            NOT NULL,
    event_time              timestamp       NOT NULL,
    PRIMARY KEY (event_id, id)
);

CREATE TABLE company_address (
    event_id                varchar(36)     NOT NULL,
    id                      varchar(36)     NOT NULL,
    company_id              varchar(36)     NOT NULL,
    country                 text,
    city                    text,
    street                  text,
    zip                     text,
    category                text,
    event_time              timestamp       NOT NULL,
    PRIMARY KEY (event_id, id),
    CONSTRAINT fk_company_address_company FOREIGN KEY (event_id, company_id) REFERENCES company (event_id, id)
);
