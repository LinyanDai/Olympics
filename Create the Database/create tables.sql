CREATE TABLE Medallists(
       City VARCHAR(50),
       Olympic_edition INT,
       Sport VARCHAR(50),
       Discipline VARCHAR(50),
       Athlete VARCHAR(255),
       NOC VARCHAR(7),
       Gender VARCHAR(7) CHECK (Gender IN (’Men’,’Women’)),
       Event VARCHAR(255),
       Event_gender VARCHAR(7) CHECK (Event_Gender IN (’M’,’W’)),
       Medal VARCHAR(50) CHECK (Medal IN (’Gold’,’Silver’,'Bronze')),
       PRIMARY KEY (Olympic_edition, Athlete, NOC, Event, Event_gender, Medal)
);

CREATE TABLE Games(
       HostYear INT,
       PRIMARY KEY (HostYear)
);

CREATE TABLE Countries(
	country VARCHAR(50),
  IOC VARCHAR(5),
  ISO VARCHAR(4),
	PRIMARY KEY(country)
);

CREATE TABLE Athletes(
	id INT,
	firstName VARCHAR(30),
	lastName VARCHAR(30),
	gender VARCHAR(15),
	birthYear INT,
	Primary Key(id),
	CHECK (gender IN ('male','female')),
	CHECK (birthYear > 1830 AND birthYear < 2015)
)


CREATE TABLE ParticicateIn(
	id INT,
	medal VARCHAR(10),
	nationality VARCHAR(20),
	year INT,
	eventName VARCHAR(30),
	PRIMARY KEY(id, eventName, eventName),
	FOREIGN KEY(id) REFERENCES Athletes(id),
	FOREIGN KEY(id) REFERENCES Games(year)
)

CREATE TABLE Mascot(
	Year INT,
	Character VARCHAR(255),
  Name VARCHAR(255),
	Designer VARCHAR(255),
  Significance VARCHAR(500),
  Rating INT,
  Reference VARCHAR(500),
	PRIMARY KEY(Year,Name),
	FOREIGN KEY(Year) REFERENCES Games(HostYear)
)

CREATE TABLE Stadiums(
	Year INT,
	Venue VARCHAR(250),
	Events VARCHAR(250),
  Rating INT,
  Reference VARCHAR(250),
	PRIMARY KEY(Year, Venue),
	FOREIGN KEY(Year) REFERENCES GAMES(HostYear)
)

CREATE TABLE Story(
	year INT,
	title VARCHAR(50),
	content VARCHAR(100),
	PRIMARY KEY(year,title),
	FOREIGN KEY(year) REFERENCES GAMES(year)
)

CREATE TABLE Theme(
	year INT,
	name VARCHAR(50),
  singer VARCHAR(50),
	composer VARCHAR(50),
	rating INT,
  Reference VARCHAR(100),
	PRIMARY KEY(year,name),
	FOREIGN KEY(year)REFERENCES GAMES(hostyear)
)

CREATE TABLE Host(
	year INT,
	city VARCHAR(50),
  country VARCHAR(50),
	PRIMARY KEY(year),
	FOREIGN KEY(year) REFERENCES Games(HostYear),
	FOREIGN KEY(country) REFERENCES Countries(country)
)

CREATE TABLE CompeteIn(
  year INT,
	country VARCHAR(100),
	gold# INT,
	silver# INT,
	bronze# INT,
  medal# INT,
	PRIMARY KEY(year, country),
	FOREIGN KEY(year) REFERENCES GAMES(hostyear),
	FOREIGN KEY(country)REFERENCES Countries(country)
)

INSERT INTO Countries VALUES('Italy (until 1946)',null,null);
INSERT INTO Countries VALUES('International Team',null,null);
INSERT INTO Countries VALUES('Australasia (until 1920)',null,null);
INSERT INTO Countries VALUES('South Africa (until 1960)',null,null);
INSERT INTO Countries VALUES('Czechoslovakia (until 1992)',null,null);
INSERT INTO Countries VALUES('Yugoslavia (until 1988)',null,null);
INSERT INTO Countries VALUES('Soviet Union',null,null);
INSERT INTO Countries VALUES('Germany (until 1912)',null,null);
INSERT INTO Countries VALUES('British India',null,null);

INSERT INTO Countries VALUES('Bohemia',null,null);
INSERT INTO Countries VALUES('Ivory Coast',null,null);
INSERT INTO Countries VALUES('British West Indies',null,null);
INSERT INTO Countries VALUES('West Germany',null,null);
INSERT INTO Countries VALUES('East Germany',null,null);
INSERT INTO Countries VALUES('Yugoslav Federation',null,null);
INSERT INTO Countries VALUES('Montenegro',null,null);

Drop Table Countries;

select *
from Countries;