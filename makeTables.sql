drop table if exists customorder;
drop table if exists manufacturer;
drop table if exists custombox;
drop table if exists material;
drop table if exists trackinglabel;
drop table if exists employee;
drop table if exists company;

create table company(
    login text primary key,
    pwd text not null,
    companyname text not null,
    contactname text not null,
    email text not null,
    phone text,
    street1 text not null,
    street2 text,
    city text not null,
    state text not null,
    zip text not null,
    country text not null
);

create table employee (
    login text primary key,
    pwd text not null,
    name text not null,
    email text not null,
    isadmin boolean default false
);

create table trackinglabel(
    id serial primary key,
    code text not null,
    shippingcompany text not null,
    url text
);

create table material(
    id serial primary key,
    name text not null,
    thickness real not null,
    weight real not null,
    check (thickness > 0),
    check (weight > 0)
);

create table custombox(
    id serial primary key,
    length real not null,
    width real not null,
    height real not null,
    color text,
    material integer not null,
    customtext text,
    unitprice real not null,
    orderID integer not null,
    foreign key (material) references material (id),
    foreign key (orderID) references customerorder (id)
);

create table manufacturer(
    id serial primary key,
    name text not null,
    address text not null
);

create table customorder(
    id serial primary key,
    company text not null,
    date date not null,
    quantity integer not null,
    manufacturerid integer not null,
    trackingid integer not null,
    totalcost real not null,
    foreign key (company) references company (login),
    foreign key (manufacturerid) references manufacturer (id),
    foreign key (trackingid) references trackinglabel (id)
);
