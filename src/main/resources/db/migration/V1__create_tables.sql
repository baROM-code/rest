CREATE TABLE products
(
    id          serial primary key,
    title       text not null,
    description text,
    imagelink   text,
    price       int  not null
);

CREATE TABLE goods
(
    id    serial primary key,
    title text not null,
    price int  not null
);

CREATE TABLE buyers
(
    id       serial primary key,
    name     text not null,
    goods_id int,
    foreign key (goods_id) references goods (id)
);
