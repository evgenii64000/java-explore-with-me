DROP TABLE IF EXISTS users, events, categories, compilations, compilations_events, participation_requests, locations, likes;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    rating BIGINT,
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    pinned boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    description VARCHAR(7000) NOT NULL,
    category_id BIGINT,
    event_date timestamp NOT NULL,
    location_id BIGINT,
    initiator_id BIGINT,
    paid boolean,
    participant_limit INTEGER,
    request_moderation boolean,
    created_on timestamp,
    published_on timestamp,
    confirmed_requests INTEGER,
    views INTEGER,
    state VARCHAR(10),
    rating BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS compilations_events (
    compilation_id BIGINT,
    event_id BIGINT,
    FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS participation_requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id BIGINT,
    requester_id BIGINT,
    created timestamp,
    status VARCHAR(10),
    FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT,
    event_id BIGINT,
    rate boolean,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
)