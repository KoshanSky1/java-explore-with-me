DROP TABLE IF EXISTS hits CASCADE;

CREATE TABLE hits (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	app varchar(255) NOT NULL,
	uri varchar(255) NOT NULL,
	ip varchar(255) NOT NULL,
	created timestamp NULL,
	CONSTRAINT pk_hits PRIMARY KEY (id)
);