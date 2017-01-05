BEGIN TRANSACTION

----------------------------------- News -----------------------------------

INSERT INTO news(id, submittedon, title, newstext) VALUES (1, '2013-06-18 00:00:00.000', 'News 1', 'Text 1');
INSERT INTO news(id, submittedon, title, newstext) VALUES (2, '2013-06-18 00:00:00.000', 'News 2', 'Text 2');
INSERT INTO news(id, submittedon, title, newstext) VALUES (3, '2013-06-30 00:00:00.000', 'News 3', 'Text 3');
INSERT INTO news(id, submittedon, title, newstext) VALUES (4, '2013-06-30 00:00:00.000', 'News 4', 'Text 4');
INSERT INTO news(id, submittedon, title, newstext) VALUES (5, '2013-07-05 00:00:00.000', 'News 5', 'Text 5');
INSERT INTO news(id, submittedon, title, newstext) VALUES (6, '2013-07-08 00:00:00.000', 'News 6', 'Text 6');
INSERT INTO news(id, submittedon, title, newstext) VALUES (7, '2013-07-08 00:00:00.000', 'News 7', 'Text 7');
INSERT INTO news(id, submittedon, title, newstext) VALUES (8, '2013-07-08 00:00:00.000', 'News 8', 'Text 8');
INSERT INTO news(id, submittedon, title, newstext) VALUES (9, '2013-07-08 00:00:00.000', 'News 9', 'Text 9');
INSERT INTO news(id, submittedon, title, newstext) VALUES (10, '2013-07-09 00:00:00.000', 'News 10', 'Text 10');

----------------------------------- Employee -----------------------------------
INSERT INTO employee(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, employedsince, insurancenumber, loginfailcount, passwordhash, permission, username, lastlogin) VALUES (1, 'vienna', 'country', '1210', 'street', '1980-06-06', 'a@a', 'herbert', 'MALE', 'huber', '2009-07-07', '3409', 0, 'ka', 'ROLE_ADMINISTRATOR', 'admin2', '2013-06-18 00:00:00.000');
INSERT INTO employee(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, employedsince, insurancenumber, loginfailcount, passwordhash, permission, username, lastlogin) VALUES (2, 'vienna', 'country', '1210', 'street', '1980-06-06', 'a@a', 'herbert', 'MALE', 'huber', '2009-07-07', '3409', 0, 'ka', 'ROLE_USER', 'user1', '2013-06-18 00:00:00.000');
INSERT INTO employee(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, employedsince, insurancenumber, loginfailcount, passwordhash, permission, username, lastlogin) VALUES (3, 'vienna', 'country', '1210', 'street', '1980-06-06', 'a@a', 'herbert', 'MALE', 'huber', '2009-07-07', '3409', 0, 'ka', 'ROLE_USER', 'user2', '2013-06-18 00:00:00.000');
INSERT INTO employee(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, employedsince, insurancenumber, loginfailcount, passwordhash, permission, username, lastlogin) VALUES (4, 'vienna', 'country', '1210', 'street', '1980-06-06', 'a@a', 'herbert', 'MALE', 'huber', '2009-07-07', '3409', 0, 'ka', 'ROLE_USER', 'user3', '2013-06-18 00:00:00.000');
INSERT INTO employee(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, employedsince, insurancenumber, loginfailcount, passwordhash, permission, username, lastlogin) VALUES (5, 'vienna', 'country', '1210', 'street', '1980-06-06', 'a@a', 'herbert', 'MALE', 'huber', '2009-07-07', '3409', 0, 'ka', 'ROLE_USER', 'user4', '2013-06-18 00:00:00.000');

----------------------------------- Artist -----------------------------------
INSERT INTO artist(id, description, firstname, lastname) VALUES (1, 'An American singer and songwriter best known as the lead vocalist of the band Red Hot Chili Peppers', 'Anthony', 'Kiedis');
INSERT INTO artist(id, description, firstname, lastname) VALUES (2, 'About this person is very less known', 'Ano', 'Nymous');
INSERT INTO artist(id, description, firstname, lastname) VALUES (3, 'Very informative description', 'Very', 'Important');


----------------------------------- Customer -----------------------------------
INSERT INTO customer(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, customerstatus) VALUES (1, 'Vienna0', 'Austria', '100', 'JO Street0', DATE '2002-09-04', '0@sample.sa', 'Max0', 'MALE', 'Mustermann0', 'VALID');
INSERT INTO customer(id, city, country, postalcode, street, dateofbirth, email, firstname, gender, lastname, customerstatus) VALUES (2, 'Vienna1', 'Austria', '101', 'JO Street1', DATE '2002-09-03', '1@sample.sa', 'Max1', 'MALE', 'Mustermann1', 'VALID');

----------------------------------- Performance -----------------------------------
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (1, 'Great performance of the red hot chili peppers', 120, 'RHCP', 'CONCERT');
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (2, 'Great performance of anonymous', 200, 'Anonymous', 'CONCERT');
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (3, 'Great movie about very important stuff', 90, 'Very Important', 'MOVIE');


----------------------------------- Location -----------------------------------
INSERT INTO location(id, city, country, postalcode, street, description, name, owner) VALUES (1, 'London', 'England', 'E1 8JB', '1 Graces Alley', 'The oldest surviving Music Hall in the world, now home to original theatre. Productions, hire, mailing list, and friends.', 'Wilton''s Music Hall', 'Wilton''s Music Hall Trust');
INSERT INTO location(id, city, country, postalcode, street, description, name, owner) VALUES (2, 'Vienna', 'Austria', '1110', 'Guglgasse 8', 'They told us, the old PlanetMusic will only temporarily move there... they lied :( ', 'Planet Music Hall', 'Planet Music VerlagsgmbH.');

----------------------------------- Room -----------------------------------
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (1, 'Beautiful Hall', 'Queen Elizabeth', TRUE, 1);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (2, 'Beautiful Room', 'Prince Charles', TRUE, 1);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (3, 'Gorgeous Hall', 'Queen Victoria', TRUE, 1);

INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (4, 'Big concert Hall', 'Hall A', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (5, 'Huge concert Hall', 'Hall B', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (6, 'Middle concert Hall', 'Hall C', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (7, 'Small concert Hall', 'Hall D', TRUE, 2);

----------------------------------- Participation -----------------------------------
INSERT INTO participation(id, artistrole, description, artist_id, performance_id) VALUES (1, 'Lead vocalist', 'Sings in the band red hot chili peppers', 1, 1);
INSERT INTO participation(id, artistrole, description, artist_id, performance_id) VALUES (2, 'Stealth master', 'Is secretive in the band anonymous', 2, 2);
INSERT INTO participation(id, artistrole, description, artist_id, performance_id) VALUES (3, 'Importance master', 'Is important in the movie very important', 3, 3);

----------------------------------- Show -----------------------------------
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (1, FALSE, '2016-07-09 00:00:00.000', 1, 1);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (2, FALSE, '2016-07-10 00:00:00.000', 1, 2);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (3, FALSE, '2016-07-12 00:00:00.000', 2, 4);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (4, TRUE, '2016-07-13 00:00:00.000', 3, 4);

----------------------------------- Row -----------------------------------
INSERT INTO row(id, description, name, price, room_id) VALUES (1, 'front row', 'A', 85.9, 1);
INSERT INTO row(id, description, name, price, room_id) VALUES (2, 'second row', 'B', 83.9, 1);
INSERT INTO row(id, description, name, price, room_id) VALUES (3, 'third row', 'C', 81.9, 1);
INSERT INTO row(id, description, name, price, room_id) VALUES (4, 'last row', 'W', 40.0, 1);

INSERT INTO row(id, description, name, price, room_id) VALUES (5, 'front row', 'A', 200.9, 2);
INSERT INTO row(id, description, name, price, room_id) VALUES (6, 'second row', 'B', 205.9, 2);
INSERT INTO row(id, description, name, price, room_id) VALUES (7, 'third row', 'C', 100.2, 2);
INSERT INTO row(id, description, name, price, room_id) VALUES (8, 'last row', 'W', 40.0, 2);

----------------------------------- Area -----------------------------------
INSERT INTO area(area_id, areatype, price, ticketamount, room_id) VALUES (1, 'VIP', 250.0, 40, 4);
INSERT INTO area(area_id, areatype, price, ticketamount, room_id) VALUES (2, 'STANCE', 120.0, 200, 4);
INSERT INTO area(area_id, areatype, price, ticketamount, room_id) VALUES (3, 'BARRIER_FREE', 80.0, 15, 4);

----------------------------------- Seat -----------------------------------
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (1, 'comfy seat', '1A', 1, 1);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (2, 'comfy seat', '2A', 2, 1);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (3, 'comfy seat', '3A', 3, 1);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (4, 'comfy seat', '4A', 4, 1);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (5, 'comfy seat', '5A', 5, 1);

INSERT INTO seat(id, description, name, sequence, row_id) VALUES (6, 'comfy seat', '1B', 1, 2);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (7, 'comfy seat', '2B', 2, 2);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (8, 'comfy seat', '3B', 3, 2);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (9, 'comfy seat', '4B', 4, 2);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (10, 'comfy seat', '5B', 5, 2);

INSERT INTO seat(id, description, name, sequence, row_id) VALUES (11, 'comfy seat', '1A', 1, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (12, 'comfy seat', '2A', 2, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (13, 'comfy seat', '3A', 3, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (14, 'comfy seat', '4A', 4, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (15, 'comfy seat', '5A', 5, 5);

----------------------------------- Reservation -----------------------------------
INSERT INTO RESERVATION(ID, RESERVATIONNUMBER, CUSTOMER_ID, EMPLOYEE_ID) VALUES (1, '1', 1, 1);

----------------------------------- Ticket -----------------------------------
INSERT INTO TICKET(ID, CANCELLATIONREASON, DESCRIPTION, PRICE, UUID, VALID, RESERVATION_ID, SEAT_ID, SHOW_ID) VALUES (1, NULL, 'Ticket1 description', 24.5, NULL, TRUE, 1, 1, 1);
INSERT INTO TICKET(ID, CANCELLATIONREASON, DESCRIPTION, PRICE, UUID, VALID, RESERVATION_ID, SEAT_ID, SHOW_ID) VALUES (2, NULL, 'Ticket2 description', 99.0, NULL, TRUE, NULL, 2, 1);

----------------------------------- MethodOfPayment -----------------------------------
INSERT INTO METHODOFPAYMENT(ID, methodOfPaymentType) VALUES (1, 'CASH');
INSERT INTO CASH(ID) VALUES (1);

----------------------------------- Receipt -----------------------------------
INSERT INTO RECEIPT(ID, PERFORMANCENAME, TRANSACTIONDATE, TRANSACTIONSTATE, CUSTOMER_ID, EMPLOYEE_ID, METHOFPAY_ID) VALUES (1, 'hallo', TIMESTAMP '2016-06-07 00:00:00.0', 'IN_PROCESS', 1, 1, 1);

----------------------------------- Receipt entries -----------------------------------
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (1, 20, 1, 23, 1, 1);


COMMIT