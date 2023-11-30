--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

--
-- TOC entry 3359 (class 0 OID 26125)
-- Dependencies: 221
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users VALUES ('jackson.michell@test.com', '$2a$10$mmEBQaI.2Om2JoQHxKACaO6.44he5wnk4Ko9nolvDpkegC3ekGS9C');
INSERT INTO users VALUES ('mandy.lloyd@test.com', '$2a$10$cvT3FWEzH0wgfi1xgnpmsebUbXo435txY0N2ml2.9ka1IqAM7Ux5m');
INSERT INTO users VALUES ('jayden.reid@test.com', '$2a$10$j8WGw7toEPuxZWjtKb4uMucAu2MdUsliheBJqeSmwFsdE4pr7mxPe');

--
-- TOC entry 3357 (class 0 OID 26119)
-- Dependencies: 219
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (2, 1);
INSERT INTO user_roles VALUES (3, 1);

--
-- TOC entry 3354 (class 0 OID 26104)
-- Dependencies: 216
-- Data for Name: patients; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO patients (id, address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES (1, 'Margam Country Park', '1973-04-30', 'Port Talbot', 'jackson.michell@test.com', 'Jackson', 'Mitchell', 51.562731, -3.725558, '0845 643 9215', 'SA13 2TJ', 'ZZ361112T', 1);
INSERT INTO patients (id, address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES (2, 'Crown Passage, 20 King''s St', '1974-07-24', 'St James''s', 'mandy.lloyd@test.com', 'Mandy', 'Lloyd', 51.506083, -0.138165, '020 7839 8831', 'SW1Y 6QY', 'ZZ576137T', 2);
INSERT INTO patients (id, address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES (3, '23 Smith St', '1982-03-28', 'London', 'jayden.reid@test.com', 'Jayden', 'Reid', 51.488954, -0.163229, '020 7730 9182', 'SW3 4EW', 'ZZ204368T', 3);




