create table if not exists orders
(
    id          bigserial primary key,
    user_id     bigint      not null,
    book_id     bigint      not null,
    status      varchar(20) not null,
    created_at  timestamp default current_timestamp,
    return_date timestamp
);