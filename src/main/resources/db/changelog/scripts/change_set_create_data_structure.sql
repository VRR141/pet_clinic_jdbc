create table if not exists owner
(
    id           bigserial primary key,
    name         varchar not null,
    surname      varchar not null,
    address      varchar not null,
    mobile_phone varchar not null unique
);

create table if not exists pet
(
    id         bigserial primary key,
    name       varchar                              not null,
    birth_date date                                 not null,
    owner_id   bigint references owner (id)         not null
);

create table if not exists visit
(
    id              bigserial primary key,
    visit_timestamp timestamp with time zone   not null,
    description     varchar,
    pet_id          bigint references pet (id) not null
);