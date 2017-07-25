--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: notes; Type: TABLE; Schema: public; Owner: lacrookedbeat
--

CREATE TABLE notes (
    id integer NOT NULL,
    authorid integer,
    author character varying,
    description character varying,
    occurrence timestamp without time zone,
    projectid integer
);


ALTER TABLE notes OWNER TO lacrookedbeat;

--
-- Name: notes_id_seq; Type: SEQUENCE; Schema: public; Owner: lacrookedbeat
--

CREATE SEQUENCE notes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notes_id_seq OWNER TO lacrookedbeat;

--
-- Name: notes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lacrookedbeat
--

ALTER SEQUENCE notes_id_seq OWNED BY notes.id;


--
-- Name: projects; Type: TABLE; Schema: public; Owner: lacrookedbeat
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


ALTER TABLE projects OWNER TO lacrookedbeat;

--
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: lacrookedbeat
--

CREATE SEQUENCE projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE projects_id_seq OWNER TO lacrookedbeat;

--
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lacrookedbeat
--

ALTER SEQUENCE projects_id_seq OWNED BY projects.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: lacrookedbeat
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
    recommendations character varying
);


ALTER TABLE users OWNER TO lacrookedbeat;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: lacrookedbeat
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO lacrookedbeat;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lacrookedbeat
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: users_projects; Type: TABLE; Schema: public; Owner: lacrookedbeat
--

CREATE TABLE users_projects (
    id integer NOT NULL,
    user_id integer,
    project_id integer
);


ALTER TABLE users_projects OWNER TO lacrookedbeat;

--
-- Name: users_projects_id_seq; Type: SEQUENCE; Schema: public; Owner: lacrookedbeat
--

CREATE SEQUENCE users_projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_projects_id_seq OWNER TO lacrookedbeat;

--
-- Name: users_projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lacrookedbeat
--

ALTER SEQUENCE users_projects_id_seq OWNED BY users_projects.id;


--
-- Name: notes id; Type: DEFAULT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY notes ALTER COLUMN id SET DEFAULT nextval('notes_id_seq'::regclass);


--
-- Name: projects id; Type: DEFAULT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY projects ALTER COLUMN id SET DEFAULT nextval('projects_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: users_projects id; Type: DEFAULT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY users_projects ALTER COLUMN id SET DEFAULT nextval('users_projects_id_seq'::regclass);


--
-- Data for Name: notes; Type: TABLE DATA; Schema: public; Owner: lacrookedbeat
--

COPY notes (id, authorid, author, description, occurrence, projectid) FROM stdin;
\.


--
-- Name: notes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: lacrookedbeat
--

SELECT pg_catalog.setval('notes_id_seq', 1, false);


--
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: lacrookedbeat
--

COPY projects (id, name, host_id, description, location, deadline, time_requirements, project_picture, open_closed) FROM stdin;
\.


--
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: lacrookedbeat
--

SELECT pg_catalog.setval('projects_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: lacrookedbeat
--

COPY users (id, name, skills, location, email, time_available, picture_link, past_works, past_projects, recommendations) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: lacrookedbeat
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Data for Name: users_projects; Type: TABLE DATA; Schema: public; Owner: lacrookedbeat
--

COPY users_projects (id, user_id, project_id) FROM stdin;
\.


--
-- Name: users_projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: lacrookedbeat
--

SELECT pg_catalog.setval('users_projects_id_seq', 1, false);


--
-- Name: notes notes_pkey; Type: CONSTRAINT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY notes
    ADD CONSTRAINT notes_pkey PRIMARY KEY (id);


--
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_projects users_projects_pkey; Type: CONSTRAINT; Schema: public; Owner: lacrookedbeat
--

ALTER TABLE ONLY users_projects
    ADD CONSTRAINT users_projects_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

