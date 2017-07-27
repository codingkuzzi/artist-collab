--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: notes; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE notes (
    id integer NOT NULL,
    authorid integer,
    author character varying,
    description character varying,
    occurrence timestamp without time zone,
    projectid integer
);


ALTER TABLE notes OWNER TO "Guest";

--
-- Name: notes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE notes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notes_id_seq OWNER TO "Guest";

--
-- Name: notes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE notes_id_seq OWNED BY notes.id;


--
-- Name: projects; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE projects (
    id integer NOT NULL,
    name character varying,
    host_id integer,
    description character varying,
    location character varying,
    deadline date,
    time_requirements character varying,
    project_picture character varying,
    open_closed boolean
);


ALTER TABLE projects OWNER TO "Guest";

--
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE projects_id_seq OWNER TO "Guest";

--
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE projects_id_seq OWNED BY projects.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying,
    skills character varying,
    location character varying,
    email character varying,
    time_available character varying,
    picture_link character varying,
    past_works character varying,
    past_projects character varying,
    recommendations character varying,
    password character varying
);


ALTER TABLE users OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: users_projects; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE users_projects (
    id integer NOT NULL,
    user_id integer,
    project_id integer
);


ALTER TABLE users_projects OWNER TO "Guest";

--
-- Name: users_projects_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_projects_id_seq OWNER TO "Guest";

--
-- Name: users_projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_projects_id_seq OWNED BY users_projects.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY notes ALTER COLUMN id SET DEFAULT nextval('notes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY projects ALTER COLUMN id SET DEFAULT nextval('projects_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users_projects ALTER COLUMN id SET DEFAULT nextval('users_projects_id_seq'::regclass);


--
-- Data for Name: notes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY notes (id, authorid, author, description, occurrence, projectid) FROM stdin;
1	4	Anna	sample note 3	2017-07-26 10:58:30.644522	12
2	4	Anna	sample note 4	2017-07-26 10:58:38.863504	12
3	4	Anna	Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.	2017-07-26 11:00:50.343914	12
4	4	Anna	Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.	2017-07-26 11:01:34.622343	12
5	4	Anna	sample note 5	2017-07-26 11:43:54.771585	12
6	4	Anna	fkadsfdkjf	2017-07-26 11:46:51.224838	12
7	5	Mick	sample note 1	2017-07-26 11:47:38.297718	13
8	5	Mick	sample note 2	2017-07-26 12:03:11.778252	13
9	4	Anna	vdf	2017-07-26 14:00:33.402905	12
10	5	Mick	sample note 3	2017-07-27 08:19:24.447928	13
11	5	Mick	sample note 4	2017-07-27 08:19:28.966273	13
12	5	Mick	sample note 5	2017-07-27 09:40:08.503396	13
13	5	Mick	sample note 6	2017-07-27 09:52:30.958158	13
14	7	AnnaK	edsfsdfds	2017-07-27 10:50:55.691425	12
15	9	Mick	Great jam the other night everyone! Who left their glockenspiel at my house?	2017-07-27 14:32:13.23178	14
16	13	Saulamander	Sorry, that was MY glockenspiel! You can hold on to it.	2017-07-27 14:33:29.897477	14
17	14	Joel3000	Looks our time table got pushed back. Shooting will begin in November!	2017-07-27 15:07:02.409279	15
18	9	Mick	Lame Sauce!!	2017-07-27 15:08:40.16887	15
19	13	Saulamander	Okay, I only need 16 years of experience for the nose flute player. Sorry about the initial confusion!	2017-07-27 15:14:36.647389	16
20	10	Alena	Dang! I only have 15 years of experience, oh well....	2017-07-27 15:15:00.998612	16
21	9	Mick	note	2017-07-27 16:13:51.808527	14
\.


--
-- Name: notes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('notes_id_seq', 21, true);


--
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY projects (id, name, host_id, description, location, deadline, time_requirements, project_picture, open_closed) FROM stdin;
1	Saturday Jam	1	playing music	portland	\N	No time requirements specified.	No project picture yet.	t
2	Sunday Jam	1	music and fun	seattle	\N	No time requirements specified.	No project picture yet.	t
3	Monday Paint	1	painting on monday	spokane	\N	No time requirements specified.	No project picture yet.	t
4	Tuesday Jam	1	music stuff	epicodus	\N	No time requirements specified.	No project picture yet.	t
5	Wed Jam	1	music	epicodus	\N	No time requirements specified.	No project picture yet.	t
6	Movie Making	1	movies	seattle	\N	No time requirements specified.	No project picture yet.	t
7	Film Making	1	make a documentary film	seattle	\N	No time requirements specified.	No project picture yet.	t
8	dddd	1	dddd	ddddd	\N	No time requirements specified.	No project picture yet.	t
9	Anna's Movie	2	its a movie	seattle	\N	No time requirements specified.	No project picture yet.	t
10	Anna's Band	2	rock music	seattle	\N	No time requirements specified.	No project picture yet.	t
11	uuuu	1	uuuu	uuuu	\N	No time requirements specified.	No project picture yet.	t
13	Zork Remake	5	Code a remake of a game	Mick's house	\N	No time requirements specified.	No project picture yet.	t
12	Painting Murals	4	this is some description	Seattle	\N	Everyday	https://www.muralarts.org/wp-content/uploads/2016/01/pph.jpg	t
15	Secret Agent Film	14	Making a film about a secret agent in Brazil. We will be shooting locally in Seattle in October. I need lighting guys, film editor, actors, stuntmen, and caterer.	seattle	\N	Everyday, rain or shine!	http://www.pagairsgsd.com/james-bond_thumbnail.jpg	t
14	Tuesday Night Jam	9	Anyone is welcome to the tuesday night jazz band jam. Bring your instrument and some songs. We can practice soloing or even jam on some original stuff. Would be great to have horns - trumpet, saxophone, trombone.	seattle - mick's house	\N	Tuesday 7-10pm	http://www.mgthomas.co.uk/dancebands/American%20Visitors/Pictures/Southern%20Rag-a-Jazz%20Band.jpg	t
16	Musicians for Experimental Band	13	I need 80 tuba players, a sitar player, 6 balalayka players, one hula dancer, a guitar, piano, drums, and a nose flute player with 18 years of experience. We will record at Sub Pop studios starting in September. All original compositions for this experimental music band.	seattle	\N	No time requirements specified.	data:image/jpeg;base64,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	t
\.


--
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('projects_id_seq', 16, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, name, skills, location, email, time_available, picture_link, past_works, past_projects, recommendations, password) FROM stdin;
13	Saulamander	guitar, coding, recording	seattle	saul@saulio.com	No Time Availability Specified	https://www.bls.gov/ooh/images/15621.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
14	Joel3000	coding, drawing	seattle	joel@aol.com	No Time Availability Specified	https://2.bp.blogspot.com/-5vGKaJb1mmw/VuGGPDuCSUI/AAAAAAAAOzU/jFJ6NvCSb3M/s1600/Bobby%2BDriscoll.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
15	Maramonster	cooking, coding, remodeling	seattle	mara@earthlink.net	No Time Availability Specified	https://fthmb.tqn.com/rv4DDL2S2AQ0j7dVlqq_haWVL9I=/2050x1464/filters:fill(auto,1)/about/149615332-56a2148a3df78cf77271c3f5.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
9	Mick	guitar, composing, coding	seattle	mick@hotmail.com	Available on Weekends and Thurs	https://www.bleedingcool.com/wp-content/uploads/2017/05/Sid-Vicious.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
10	Alena	singing, coding, drawing	seattle	alena@hotmail.com	No Time Availability Specified	http://media.npr.org/assets/img/2014/10/02/ada-lovelace_custom-36235be92c6df0badbde6aed459cadf598131dee-s300-c85.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
11	AnnaT	painting, drawing	seattle	annat@yahoo.com	No Time Availability Specified	http://preview.www.allaboutyou.com/cm/allaboutyou/images/2T/woman-painting-on-lake300.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
12	AnnaK	piano, knitting, coding	seattle	annak@gmail.com	No Time Availability Specified	https://i.ytimg.com/vi/tvUrxH5_VhY/maxresdefault.jpg	No Past Works Listed	No Past Artist-Collab Projects Listed	No Recommendations Yet	123
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 15, true);


--
-- Data for Name: users_projects; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users_projects (id, user_id, project_id) FROM stdin;
22	9	14
23	13	14
24	12	14
25	15	14
26	14	15
27	12	15
28	15	15
29	9	15
30	13	16
31	10	16
32	11	16
33	9	16
34	14	14
\.


--
-- Name: users_projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_projects_id_seq', 34, true);


--
-- Name: notes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY notes
    ADD CONSTRAINT notes_pkey PRIMARY KEY (id);


--
-- Name: projects_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_projects_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users_projects
    ADD CONSTRAINT users_projects_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

