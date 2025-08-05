create table if not exists books
(
    id               bigserial primary key,
    title            varchar(255) not null unique,
    author           varchar(255) not null,
    publication_year date,
    genre            varchar(100),
    publisher        varchar(255),
    total_copies     integer,
    available_copies integer,
    added_at         timestamp default current_timestamp
);
