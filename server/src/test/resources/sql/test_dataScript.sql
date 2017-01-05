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

----------------------------------- EMPLOYEE -----------------------------------
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (1, 'EmployeeCity0', 'Schlumpfhausen', '100', '21st Jump Street', DATE '2002-09-09', 'marvin0@hallo.to', 'Marvin', 'MALE', 'Jones', DATE '2016-03-29', '0', TIMESTAMP '2016-03-29 15:57:54.91', 0, '$2a$10$fQW7o2zFjKEXwLSun8flOO6qKzWA3.J4qvq4FnHs3Mj1jlO75tjFy', 'ROLE_USER', 'marvin');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (2, 'EmployeeCity1', 'Schlumpfhausen', '101', '21st Jump Street', DATE '2002-09-08', 'marvin1@hallo.to', 'Marvin1', 'FEMALE', 'Jones1', DATE '2016-03-29', '349830', TIMESTAMP '2016-03-29 15:57:55.317', 0, '$2a$10$sXe1jofsCdEMji0pR.uWGuErtl8PatzIj0inNfONeW8GchkrsFYIG', 'ROLE_USER', 'marvin1');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (3, 'EmployeeCity2', 'Schlumpfhausen', '102', '21st Jump Street', DATE '2002-09-07', 'marvin2@hallo.to', 'Marvin2', 'MALE', 'Jones2', DATE '2016-03-29', '699660', TIMESTAMP '2016-03-29 15:57:55.901', 0, '$2a$10$P9YDSK3k1jr9AAgkojhASeeXb.exK/5B6A7.tU0z/BKg.e1XcnxQy', 'ROLE_USER', 'marvin2');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (4, 'EmployeeCity3', 'Schlumpfhausen', '103', '21st Jump Street', DATE '2002-09-06', 'marvin3@hallo.to', 'Marvin3', 'FEMALE', 'Jones3', DATE '2016-03-29', '1049490', TIMESTAMP '2016-03-29 15:57:56.289', 0, '$2a$10$Wjgz3bj6FjEakZ2NeXpFO.dv1Ww.m//XhppY/x2c.6CSE2OxPuaHu', 'ROLE_USER', 'marvin3');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (5, 'EmployeeCity4', 'Schlumpfhausen', '104', '21st Jump Street', DATE '2002-09-05', 'marvin4@hallo.to', 'Marvin4', 'MALE', 'Jones4', DATE '2016-03-29', '1399320', TIMESTAMP '2016-03-29 15:57:56.64', 0, '$2a$10$O3kqrSPeVkuCW5K7HsmKeuSQwsZoml2mtHLbax6OYlkmg9K4KjjxC', 'ROLE_USER', 'marvin4');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (6, 'EmployeeCity5', 'Schlumpfhausen', '105', '21st Jump Street', DATE '2002-09-04', 'marvin5@hallo.to', 'Marvin5', 'FEMALE', 'Jones5', DATE '2016-03-29', '1749150', TIMESTAMP '2016-03-29 15:57:56.868', 0, '$2a$10$uh6JYSdsBofkRH/TnagAu.LjXqBU6lbv3ZBLKNk2Xc9uJqLICgr3i', 'ROLE_USER', 'marvin5');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (7, 'EmployeeCity6', 'Schlumpfhausen', '106', '21st Jump Street', DATE '2002-09-03', 'marvin6@hallo.to', 'Marvin6', 'MALE', 'Jones6', DATE '2016-03-29', '2098980', TIMESTAMP '2016-03-29 15:57:57.056', 0, '$2a$10$lMgYcHU0BfclyCw5fEtfNu20yk.Va.zhUUeSGkzoZ9YAtVEAu/IDi', 'ROLE_USER', 'marvin6');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (8, 'EmployeeCity7', 'Schlumpfhausen', '107', '21st Jump Street', DATE '2002-09-02', 'marvin7@hallo.to', 'Marvin7', 'FEMALE', 'Jones7', DATE '2016-03-29', '2448810', TIMESTAMP '2016-03-29 15:57:57.272', 0, '$2a$10$02p24SWFQsLWADgcXAhB2.uf6KestTv0I5nXnBZawpPiFdxGOrPY6', 'ROLE_USER', 'marvin7');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (9, 'EmployeeCity8', 'Schlumpfhausen', '108', '21st Jump Street', DATE '2002-09-01', 'marvin8@hallo.to', 'Marvin8', 'MALE', 'Jones8', DATE '2016-03-29', '2798640', TIMESTAMP '2016-03-29 15:57:57.551', 0, '$2a$10$ZmfcpUJTH/DK.GrVnyTM3OjnSqXcg.Zk.W6GzpVN51Gx./RCgYkAy', 'ROLE_USER', 'marvin8');
INSERT INTO PUBLIC.EMPLOYEE(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, EMPLOYEDSINCE, INSURANCENUMBER, LASTLOGIN, LOGINFAILCOUNT, PASSWORDHASH, PERMISSION, USERNAME) VALUES (10, 'EmployeeCity9', 'Schlumpfhausen', '109', '21st Jump Street', DATE '2002-08-31', 'marvin9@hallo.to', 'Marvin9', 'FEMALE', 'Jones9', DATE '2016-03-29', '3148470', TIMESTAMP '2016-03-29 15:57:57.804', 0, '$2a$10$jfYOQ4A/xhapP2MfVSv3OORGx4c6s.p./7Zf8VjmJfMdjzLsQKsmK', 'ROLE_USER', 'marvin9');

----------------------------------- CUSTOMER -----------------------------------
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (1, 'Vienna0', 'Austria', '100', 'JO Street0', DATE '2002-09-04', '0@sample.sa', 'Max0', 'MALE', 'Mustermann0', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (2, 'Vienna1', 'Austria', '101', 'JO Street1', DATE '2002-09-03', '1@sample.sa', 'Max1', 'MALE', 'Mustermann1', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (3, 'Vienna2', 'Austria', '102', 'JO Street2', DATE '2002-09-02', '2@sample.sa', 'Max2', 'MALE', 'Mustermann2', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (4, 'Vienna3', 'Austria', '103', 'JO Street3', DATE '2002-09-01', '3@sample.sa', 'Max3', 'MALE', 'Mustermann3', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (5, 'Vienna4', 'Austria', '104', 'JO Street4', DATE '2002-08-31', '4@sample.sa', 'Max4', 'MALE', 'Mustermann4', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (6, 'Vienna5', 'Austria', '105', 'JO Street5', DATE '2002-08-30', '5@sample.sa', 'Max5', 'MALE', 'Mustermann5', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (7, 'Vienna6', 'Austria', '106', 'JO Street6', DATE '2002-08-29', '6@sample.sa', 'Max6', 'MALE', 'Mustermann6', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (8, 'Vienna7', 'Austria', '107', 'JO Street7', DATE '2002-08-28', '7@sample.sa', 'Max7', 'MALE', 'Mustermann7', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (9, 'Vienna8', 'Austria', '108', 'JO Street8', DATE '2002-08-27', '8@sample.sa', 'Max8', 'MALE', 'Mustermann8', 'VALID');
INSERT INTO CUSTOMER(ID, CITY, COUNTRY, POSTALCODE, STREET, DATEOFBIRTH, EMAIL, FIRSTNAME, GENDER, LASTNAME, CUSTOMERSTATUS) VALUES (10, 'Vienna9', 'Austria', '109', 'JO Street9', DATE '2002-08-26', '9@sample.sa', 'Max9', 'MALE', 'Mustermann9', 'VALID');

----------------------------------- LOCATION -----------------------------------
INSERT INTO LOCATION(ID, CITY, COUNTRY, POSTALCODE, STREET, DESCRIPTION, NAME, OWNER) VALUES (1, 'London', 'England', 'E1 8JB', '1 Graces Alley', 'The oldest surviving Music Hall in the world, now home to original theatre. Productions, hire, mailing list, and friends.', 'Wilton''s Music Hall', 'Wilton''s Music Hall Trust');
INSERT INTO location(id, city, country, postalcode, street, description, name, owner) VALUES (2, 'Vienna', 'Austria', '1110', 'Guglgasse 8', 'They told us, the old PlanetMusic will only temporarily move there... they lied :( ', 'Planet Music Hall', 'Planet Music VerlagsgmbH.');
INSERT INTO location(id, city, country, postalcode, street, description, name, owner) VALUES (3, 'Salzburg', 'Austria', '5020', 'Hofstallgasse 1', ' The stage here is one of the widest in the world', 'Grosses Festspielhaus', 'Salzburg');

----------------------------------- ROOM -----------------------------------
INSERT INTO ROOM(ID, DESCRIPTION, NAME, SEATCHOICE, LOCATION_ID) VALUES (1, 'Beautiful hall with 2 areas', 'Hall_1', FALSE, 1);
INSERT INTO ROOM(ID, DESCRIPTION, NAME, SEATCHOICE, LOCATION_ID) VALUES (2, 'Beautiful hall with 4 areas', 'Hall_2', FALSE, 1);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (3, 'Beautiful Room', 'Prince Charles', TRUE, 1);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (4, 'Gorgeous Hall', 'Queen Victoria', TRUE, 1);

INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (5, 'Big concert Hall', 'Hall A', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (6, 'Huge concert Hall', 'Hall B', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (7, 'Middle concert Hall', 'Hall C', FALSE, 2);
INSERT INTO room(id, description, name, seatchoice, location_id) VALUES (8, 'Small concert Hall', 'Hall D', TRUE, 2);

----------------------------------- AREA -----------------------------------
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (1, 'STANCE', 34.0, 40, 1);
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (2, 'VIP', 50.0, 20, 1);
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (3, 'VIP', 66.0, 40, 2);
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (4, 'BARRIER_FREE', 60.0, 20, 2);
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (5, 'STANCE', 38.0, 400, 2);
INSERT INTO AREA(AREA_ID, AREATYPE, PRICE, TICKETAMOUNT, ROOM_ID) VALUES (6, 'FREE_SEATING_CHOICE', 42.0, 200, 2);

----------------------------------- Performance -----------------------------------
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (1, 'Great performance of the red hot chili peppers', 120, 'RHCP', 'CONCERT');
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (2, 'Great performance of anonymous', 200, 'Anonymous', 'CONCERT');
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (3, 'Great movie about very important stuff', 90, 'Very Important', 'MOVIE');

----------------------------------- Show -----------------------------------
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (1, FALSE, '2016-07-09 00:00:00.000', 1, 1);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (2, FALSE, '2016-07-10 00:00:00.000', 1, 2);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (3, FALSE, '2016-07-12 00:00:00.000', 2, 4);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (4, TRUE, '2016-07-13 00:00:00.000', 3, 4);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (5, FALSE, '2016-09-28 00:00:00.000', 3, 4);


----------------------------------- Artist -----------------------------------
INSERT INTO artist(id, description, firstname, lastname) VALUES (1, 'An American singer and songwriter best known as the lead vocalist of the band Red Hot Chili Peppers', 'Anthony', 'Kiedis');
INSERT INTO artist(id, description, firstname, lastname) VALUES (2, 'About this person is very less known', 'Ano', 'Nymous');
INSERT INTO artist(id, description, firstname, lastname) VALUES (3, 'Very informative description', 'Very', 'Important');

----------------------------------- Row -----------------------------------
INSERT INTO row(id, description, name, price, room_id) VALUES (1, 'row description1', 'a', 20.0, 3);
INSERT INTO row(id, description, name, price, room_id) VALUES (2, 'row description2', 'b', 22.0, 3);
INSERT INTO row(id, description, name, price, room_id) VALUES (3, 'row description3', 'c', 24.0, 3);
INSERT INTO row(id, description, name, price, room_id) VALUES (4, 'row description4', 'd', 26.0, 3);

INSERT INTO row(id, description, name, price, room_id) VALUES (5, 'row description1', 'a', 120.0, 4);
INSERT INTO row(id, description, name, price, room_id) VALUES (6, 'row description2', 'b', 140.0, 4);
INSERT INTO row(id, description, name, price, room_id) VALUES (7, 'row description3', 'c', 160.0, 4);
INSERT INTO row(id, description, name, price, room_id) VALUES (8, 'row description4', 'd', 200.0, 4);

----------------------------------- Seat -----------------------------------
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (1, 'comfy seat', 'a', 1, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (2, 'comfy seat', 'a', 2, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (3, 'comfy seat', 'a', 3, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (4, 'comfy seat', 'a', 4, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (5, 'comfy seat', 'a', 5, 5);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (6, 'comfy seat', 'a', 6, 5);

INSERT INTO seat(id, description, name, sequence, row_id) VALUES (7, 'comfy seat', 'b', 1, 6);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (8, 'comfy seat', 'b', 2, 6);
INSERT INTO seat(id, description, name, sequence, row_id) VALUES (9, 'comfy seat', 'b', 3, 6);

----------------------------------- Reservation -----------------------------------
INSERT INTO reservation(id, reservationnumber, customer_id, employee_id) VALUES (1, 12345, 1, 1);

----------------------------------- Ticket -----------------------------------
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (1, null, null, 34.0, null, true, 1, null, null, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (2, null, null, 34.0, null, true, 1, null, null, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (3, NULL, null, 34.0, null, TRUE, 1, NULL, NULL, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (4, null, null, 50.0, null, true, 2, null, null, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (5, null, null, 50.0, null, true, 2, null, null, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (6, NULL, null, 50.0, null, TRUE, 2, NULL, NULL, 1);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (7, NULL, null, 50.0, null, TRUE, 2, NULL, NULL, 1);

INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (8, NULL, null, 120.0, null, TRUE, NULL, NULL, 1, 3);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (9, NULL, null, 120.0, null, TRUE, NULL, NULL, 2, 3);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (10, NULL, null, 120.0, null, TRUE, NULL, NULL, 3, 3);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (11, NULL, null, 120.0, null, TRUE, NULL, NULL, 4, 3);
INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (12, NULL, null, 120.0, null, TRUE, NULL, NULL, 5, 3);

INSERT INTO ticket(id, cancellationreason, description, price, uuid, valid, area_id, reservation_id, seat_id, show_id) VALUES (13, NULL, null, 120.0, null, TRUE, NULL, 1, 6, 3);

----------------------------------- Method Of Payment -----------------------------------
INSERT INTO methodofpayment(id, methodofpaymenttype) VALUES (1, 'CASH');
INSERT INTO CASH(ID) VALUES (1);

----------------------------------- Receipt -----------------------------------
INSERT INTO receipt(id, performancename, transactiondate, transactionstate, customer_id, employee_id, methofpay_id) VALUES (1, 'RHCP', '2016-05-09 00:00:00.000', 'PAID', 1, 1, 1);
INSERT INTO receipt(id, performancename, transactiondate, transactionstate, customer_id, employee_id, methofpay_id) VALUES (2, 'Anonymous', '2016-04-06 00:00:00.000', 'PAID', 2, 1, 1);

----------------------------------- Receipt entries -----------------------------------
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (1, 1, 1, 34.0, 1, 1);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (2, 1, 1, 34.0, 1, 2);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (3, 1, 1, 34.0, 1, 3);

INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (4, 1, 1, 120.0, 2, 8);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (5, 1, 1, 120.0, 2, 9);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (6, 1, 1, 120.0, 2, 10);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (7, 1, 1, 120.0, 2, 11);
INSERT INTO RECEIPTENTRY(ID, AMOUNT, POSITION, UNITPRICE, RECEIPT_ID, TICKET_ID) VALUES (8, 1, 1, 120.0, 2, 12);



COMMIT