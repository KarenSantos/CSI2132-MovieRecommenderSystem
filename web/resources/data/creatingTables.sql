set search_path = "movie_recommender_system";

-- Cannot create table User
CREATE table User_Account(
	user_id CHAR(5) NOT NULL,
	password VARCHAR(30) NOT NULL,
	last_name VARCHAR(20),
	first_name VARCHAR(20) NOT NULL,
	email VARCHAR(30) NOT NULL,
	city VARCHAR(30),
	province CHAR(2),
	country VARCHAR(20),
	PRIMARY KEY (user_id));

CREATE table User_Profile(
	user_id CHAR(5) NOT NULL,
	DBO date,
	gender CHAR(1),
	occupation VARCHAR(20),
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) references User_Account 
		ON DELETE CASCADE ON UPDATE CASCADE);

CREATE table Director(
	director_id CHAR(3) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	country VARCHAR(20),
	PRIMARY KEY (director_id));

CREATE table Movie(
	movie_id CHAR(5) NOT NULL,
	name VARCHAR(40) NOT NULL,
	date_released date,
	language VARCHAR(10),
	subtitles boolean,
	country VARCHAR(20),
	age_restriction int,
	director_id CHAR(3),
	PRIMARY KEY (movie_id),
	FOREIGN KEY (director_id) references Director
		ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT age_restrictionCheck 
		CHECK (age_restriction <= 18));
	
CREATE table Genre(
	genre_id CHAR(3) NOT NULL,
	name VARCHAR(20),
	PRIMARY KEY (genre_id));

CREATE table Star(
	star_id CHAR(5) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	DBO date,
	country VARCHAR(20),
	PRIMARY KEY (star_id));

CREATE table Role(
	role_id CHAR(3) NOT NULL,
	name VARCHAR(30) NOT NULL,
	PRIMARY KEY (role_id));

CREATE table Studio(
	studio_id CHAR(3) NOT NULL,
	name VARCHAR(40) NOT NULL,
	country VARCHAR(20),
	PRIMARY KEY (studio_id));

CREATE table Movie_Genre(
	movie_id CHAR(5) NOT NULL,
	genre_id CHAR(3) NOT NULL,
	PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) references Movie 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (genre_id) references Genre
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table Movie_Star(
	movie_id CHAR(5) NOT NULL,
	star_id CHAR(5) NOT NULL,
	PRIMARY KEY (movie_id, star_id),
	FOREIGN KEY (movie_id) references Movie 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (star_id) references Star
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table Movie_Role(
	movie_id CHAR(5) NOT NULL,
	role_id CHAR(5) NOT NULL,
	PRIMARY KEY (movie_id, role_id),
	FOREIGN KEY (movie_id) references Movie 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (role_id) references Role
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table Movie_Studio(
	movie_id CHAR(5) NOT NULL,
	studio_id CHAR(3) NOT NULL,
	PRIMARY KEY (movie_id, studio_id),
	FOREIGN KEY (movie_id) references Movie 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (studio_id) references Studio
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table User_Watched_Movie(
	user_id CHAR(5) NOT NULL,
	movie_id CHAR(5) NOT NULL,
	date_watched date NOT NULL,
	PRIMARY KEY (user_id, movie_id, date_watched),
	FOREIGN KEY (user_id) references User_Account 
		ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY (movie_id) references Movie
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table User_Rates_Movie(
	user_id CHAR(5) NOT NULL,
	movie_id CHAR(5) NOT NULL,
	rating int NOT NULL,
	PRIMARY KEY (user_id, movie_id),
	FOREIGN KEY (user_id) references User_Account 
		ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY (movie_id) references Movie
		ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT movieRatingCheck CHECK (rating >= 1 AND rating <= 5));

CREATE table User_Likes_Genre(
	user_id CHAR(5) NOT NULL,
	genre_id CHAR(3) NOT NULL,
	PRIMARY KEY (user_id, genre_id),
	FOREIGN KEY (user_id) references User_Account 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (genre_id) references Genre
		ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE table Star_Role(
	star_id CHAR(5) NOT NULL,
	role_id CHAR(5) NOT NULL,
	PRIMARY KEY (star_id, role_id),
	FOREIGN KEY (star_id) references Star 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (role_id) references Role
		ON DELETE CASCADE ON UPDATE CASCADE);
