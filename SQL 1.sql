drop table if exists customorder;
drop table if exists manufacturer;
drop table if exists custombox;
drop table if exists material;
drop table if exists trackinglabel;
drop table if exists company;
drop table if exists contactinfo; 
drop table if exists address; 

create table address(
    id serial primary key,
    street1 text not null,
    street2 text,
    city text not null,
    state text not null,
    zip text not null,
    country text not null
);

create table contactinfo(
    id serial primary key,
    email text not null,
    phone text,
    address integer not null,
    foreign key (address) references address (id)
);

create table company(
    login text primary key,
    pwd text not null,
    name text not null,
    contact_info integer not null,
    foreign key (contact_info) references contactinfo (id)
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
    foreign key (material) references material (id)
);

create table manufacturer(
    id serial primary key,
    name text not null,
    address integer not null,
    foreign key (address) references address (id)
);

create table customorder(
    id serial primary key,
    company text not null,
    date date not null,
    boxid integer not null,
    quantity integer not null,
    manufacturerid integer not null,
    trackingid integer not null,
    totalcost real not null,
    foreign key (company) references company (login),
    foreign key (boxid) references custombox (id),
    foreign key (manufacturerid) references manufacturer (id),
    foreign key (trackingid) references trackinglabel (id)
);
