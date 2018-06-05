-- company
insert into company (login, pwd, companyname, contactname, email, phone,
    street1, city, state, zip, country) values ('arka', 'password', 'Arka', 
    'amanda', 'email@email.com', '3331112222', '111 arka st', 'slo', 'ca', 
    '93405', 'USA');
insert into company (login, pwd, companyname, contactname, email, phone,
    street1, city, state, zip, country) values ('walgreens', 'walgreens', 
    'walgreens', 'amy', 'amy@walgreens.com', '23223223232', 'blah', 'slo', 'ca', 
    '93405', 'USA');

-- manufacturer
insert into manufacturer values (1, 'San Luis Paper Co', '625 Tank Farm Rd');
insert into manufacturer values (2, 'Ball Corporation', '10 Longs Peak Drive');
insert into manufacturer values (3, 'International Paper', '640 Poplar Ave');

-- material
insert into material values (1, 'cardboard', 0.5, 0.03);
insert into material values (2, 'plastic', 0.7, 0.01);
insert into material values (3, 'wood', 1.0, 0.5);
