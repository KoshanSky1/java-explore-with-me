DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS compilations (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	title varchar(255) NOT NULL,
	pinned boolean NOT NULL,
	CONSTRAINT pk_compilations PRIMARY KEY (id),
	CONSTRAINT UQ_COMPILATIONS_TITLE UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS categories (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name varchar(255) NOT NULL,
	CONSTRAINT pk_categories PRIMARY KEY (id),
	CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	lat float NOT NULL,
	lon float NOT NULL,
	CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	CONSTRAINT pk_users PRIMARY KEY (id),
	CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS events (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	annotation varchar(2000) NOT NULL,
	category_id INTEGER NOT NULL,
	confirmed_requests INTEGER,
	created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	description varchar(7000) NOT NULL NOT NULL,
	event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	initiator_id INTEGER NOT NULL,
	location_id INTEGER NOT NULL,
	paid boolean NOT NULL,
	participant_limit INTEGER,
	published_on TIMESTAMP WITHOUT TIME ZONE,
	request_moderation boolean,
	state varchar(255),
	title varchar(255),
	views INTEGER,
	CONSTRAINT pk_events PRIMARY KEY (id),
	CONSTRAINT events_categories_fk FOREIGN KEY (category_id) REFERENCES PUBLIC.categories(id),
	CONSTRAINT events_users_fk FOREIGN KEY (initiator_id) REFERENCES PUBLIC.users(id),
	CONSTRAINT events_locations_fk FOREIGN KEY (location_id) REFERENCES PUBLIC.locations(id)
);

CREATE TABLE IF NOT EXISTS requests (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	event_id INTEGER NOT NULL,
	requester_id INTEGER NOT NULL,
	created_on TIMESTAMP WITHOUT TIME ZONE,
	status varchar(255) NOT NULL,
	CONSTRAINT pk_requests PRIMARY KEY (id),
	CONSTRAINT requests_events_fk FOREIGN KEY (event_id) REFERENCES PUBLIC.events(id),
	CONSTRAINT requests_users_fk FOREIGN KEY (requester_id) REFERENCES PUBLIC.users(id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
    compilation_id INTEGER NOT NULL,
    events_id       INTEGER NOT NULL,
    CONSTRAINT compilations_events_compilations_fk FOREIGN KEY (compilation_id) REFERENCES PUBLIC.compilations(id),
    CONSTRAINT compilations_events_events_fk FOREIGN KEY (events_id) REFERENCES PUBLIC.events(id)
);