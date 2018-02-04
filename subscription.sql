-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema subscription
-- -----------------------------------------------------
-- Приложение предлагает пользователям (читателям) подписку на различные периодические издания — газеты,
-- журналы, книжные серии. После регистрации и пополнения счета
-- читатель может оформить подписку, предварительно выбрав периодические издания из
-- списка. У каждой подписки есть тип — еженедельная, месячная, ежегодная и т.д. 
-- В одну подписку могут включаться несколько изданий. Пользователь может иметь несколько подписок. 
-- Подписки могут быть обновлены и продлены. 
-- 
DROP SCHEMA IF EXISTS `subscription` ;

-- -----------------------------------------------------
-- Schema subscription
--
-- Приложение предлагает пользователям (читателям) подписку на различные периодические издания — газеты,
-- журналы, книжные серии. После регистрации и пополнения счета
-- читатель может оформить подписку, предварительно выбрав периодические издания из
-- списка. У каждой подписки есть тип — еженедельная, месячная, ежегодная и т.д. 
-- В одну подписку могут включаться несколько изданий. Пользователь может иметь несколько подписок. 
-- Подписки могут быть обновлены и продлены. 
-- 
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `subscription` DEFAULT CHARACTER SET utf8 ;
USE `subscription` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор для каждого пользователя. Синтетический первичный ключ.',
  `email` VARCHAR(254) NOT NULL COMMENT 'Почтовый адрес пользователя, использующийся для входа в систему. Должен быть уникальным.',
  `password` CHAR(64) NOT NULL COMMENT 'Хэш пароля пользователя. 64-знаковый CHAR — результат работы алгоритма хэширования SHA-256, являющегося на сегодня стандартом в подобных приложениях.',
  `admin` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Роль пользователя на сайте — простой юзер или админ. Для удобства дефолтное значение установлено как юзер.',
  `registrationDate` DATE NOT NULL COMMENT 'Время регистрации пользователя в системе (понадобится в дальнейшем для генерации токена авторизации).',
  `firstName` VARCHAR(35) NOT NULL COMMENT 'Имя пользователя.',
  `lastName` VARCHAR(35) NOT NULL COMMENT 'Фамилия пользователя.',
  `balance` DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Баланс пользователя. Беззнаковый тип, баланс не может быть минусовым. Для удобства дефолтное значение — ноль.',
  `login` VARCHAR(32) NOT NULL,
  `banned` TINYINT(1) NOT NULL DEFAULT 0,
  `city` VARCHAR(45) NOT NULL,
  `address` VARCHAR(120) NOT NULL,
  `postalIndex` VARCHAR(45) NOT NULL,
  `photo` VARCHAR(1024) NULL,
  `loan` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB
COMMENT = 'Данные о клиенте-читателе приложения.';


-- -----------------------------------------------------
-- Table `periodicity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `periodicity` ;

CREATE TABLE IF NOT EXISTS `periodicity` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор типа подписки.',
  `periodicity` VARCHAR(45) NOT NULL COMMENT 'Непосредственно тип подписки (weekly, monthly, yearly, etc.)',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `subscription_type_UNIQUE` (`periodicity` ASC))
ENGINE = InnoDB
COMMENT = 'Частота выхода изданий — еженедельная, ежемесячная, годовая и т.д.';


-- -----------------------------------------------------
-- Table `genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `genre` ;

CREATE TABLE IF NOT EXISTS `genre` (
  `name` VARCHAR(45) NOT NULL COMMENT 'Непосредственно название жанра.',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  PRIMARY KEY (`name`))
ENGINE = InnoDB
COMMENT = 'Данные о жанрах книжных серий — роман, детектив, приключенческий и т.д.';


-- -----------------------------------------------------
-- Table `author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `author` ;

CREATE TABLE IF NOT EXISTS `author` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `fullName` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodical_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `periodical_type` ;

CREATE TABLE IF NOT EXISTS `periodical_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(18) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodical`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `periodical` ;

CREATE TABLE IF NOT EXISTS `periodical` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор периодического издания.',
  `title` VARCHAR(255) NOT NULL COMMENT 'Название.',
  `price` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'Цена за единицу. ',
  `periodicity_id` INT UNSIGNED NOT NULL COMMENT 'Частота выхода издания — ежегодная, ежемесячная, еженедельная и т.д.',
  `author_id` INT UNSIGNED NULL DEFAULT NULL,
  `periodical_type_id` INT UNSIGNED NOT NULL,
  `coverImage` VARCHAR(1024) NOT NULL,
  `description` TEXT(63000) NOT NULL,
  `booksAmount` INT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_periodical_periodicity1_idx` (`periodicity_id` ASC),
  INDEX `fk_periodical_author1_idx` (`author_id` ASC),
  INDEX `fk_periodical_periodical_type1_idx` (`periodical_type_id` ASC),
  CONSTRAINT `fk_periodical_periodicity1`
    FOREIGN KEY (`periodicity_id`)
    REFERENCES `periodicity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_periodical_author1`
    FOREIGN KEY (`author_id`)
    REFERENCES `author` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_periodical_periodical_type1`
    FOREIGN KEY (`periodical_type_id`)
    REFERENCES `periodical_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Данные об одном из продуктов — периодическом издании (газета, журнал).';


-- -----------------------------------------------------
-- Table `subscription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription` ;

CREATE TABLE IF NOT EXISTS `subscription` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор заказов.',
  `user_id` INT UNSIGNED NOT NULL,
  `periodical_id` INT UNSIGNED NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `price` DECIMAL(5,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_subscription_user1_idx` (`user_id` ASC),
  INDEX `fk_subscription_periodical1_idx` (`periodical_id` ASC),
  CONSTRAINT `fk_subscription_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscription_periodical1`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `periodical` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Таблица, которая служит для сборки всех изданий, на которые оформляет подписку пользователь, в одну подписку. ';


-- -----------------------------------------------------
-- Table `periodical_genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `periodical_genre` ;

CREATE TABLE IF NOT EXISTS `periodical_genre` (
  `periodical_id` INT UNSIGNED NOT NULL,
  `genre_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`periodical_id`, `genre_name`),
  INDEX `fk_periodical_genre_periodical1_idx` (`periodical_id` ASC),
  INDEX `fk_periodical_genre_genre1_idx` (`genre_name` ASC),
  CONSTRAINT `fk_periodical_genre_periodical1`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `periodical` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_periodical_genre_genre1`
    FOREIGN KEY (`genre_name`)
    REFERENCES `genre` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `periodicity`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `periodicity` (`id`, `periodicity`) VALUES (1, '1 раз в месяц');
INSERT INTO `periodicity` (`id`, `periodicity`) VALUES (2, '4 раза в месяц');
INSERT INTO `periodicity` (`id`, `periodicity`) VALUES (3, '8 раз в месяц');
INSERT INTO `periodicity` (`id`, `periodicity`) VALUES (4, '12 раз в месяц');
INSERT INTO `periodicity` (`id`, `periodicity`) VALUES (5, '2 раза в месяц');

COMMIT;


-- -----------------------------------------------------
-- Data for table `genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `genre` (`name`) VALUES ('Подростковая литература');
INSERT INTO `genre` (`name`) VALUES ('Фэнтези');
INSERT INTO `genre` (`name`) VALUES ('Роман');
INSERT INTO `genre` (`name`) VALUES ('Мужской');
INSERT INTO `genre` (`name`) VALUES ('История');
INSERT INTO `genre` (`name`) VALUES ('Лайфстайл');
INSERT INTO `genre` (`name`) VALUES ('Новости');
INSERT INTO `genre` (`name`) VALUES ('Политика');
INSERT INTO `genre` (`name`) VALUES ('Экономика');
INSERT INTO `genre` (`name`) VALUES ('Общество');
INSERT INTO `genre` (`name`) VALUES ('Эпическое');
INSERT INTO `genre` (`name`) VALUES ('Антиутопия');

COMMIT;


-- -----------------------------------------------------
-- Data for table `author`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `author` (`id`, `fullName`) VALUES (1, 'Джоан Роулинг');
INSERT INTO `author` (`id`, `fullName`) VALUES (2, 'Джордж Мартин');
INSERT INTO `author` (`id`, `fullName`) VALUES (3, 'Джон Р. Р. Толкин');
INSERT INTO `author` (`id`, `fullName`) VALUES (4, 'Сьюзен Коллинз');
INSERT INTO `author` (`id`, `fullName`) VALUES (5, 'Вероника Рот');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodical_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `periodical_type` (`id`, `type`) VALUES (1, 'Книжная серия');
INSERT INTO `periodical_type` (`id`, `type`) VALUES (2, 'Газета');
INSERT INTO `periodical_type` (`id`, `type`) VALUES (3, 'Журнал');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodical`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (1, 'Дилетант', 7.2, 2, NULL, 3, 'http://journal-off.info/uploads/posts/2016-09/1474895768_310fd331e1dfec82589e217b7f3b9f21.jpg', 'Дилентант — это познавательный журнал, всецело посвященный истории. Наш проект создан для тех, кто любит историю и хочет знать о ней как можно больше. \nМы придумали для вас простой и понятный способ войти в мир захватывающего водоворота исторических событий. Мир истории, от древних, не знавших письменности племен, до XXI века.\nНаш журнал рассчитан на тех, кто любит историю, хочет знать о ней как можно больше, но не является профессиональным историком. Научных текстов, написанных сложным языком, вы у нас не найдете, зато найдете увлекательный исторический контент, часть которого создают сами читатели.', NULL);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (2, 'Большой', 5.7, 5, NULL, 3, 'http://jurnali-online.ru/wp-content/uploads/2013/04/1365962585_bol042013600.jpg', 'Журнал \"Большой\" ищет ответы на вопросы об устройстве общества, законах социума и мироощущении у известных режиссеров, бизнесменов, музыкантов, политологов, дизайнеров, писателей, телеведущих. Ему интересна личность и объективный взгляд на события.', NULL);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (3, 'Esquire', 9, 1, NULL, 3, 'https://s-media-cache-ak0.pinimg.com/originals/11/1f/4c/111f4cbe6338b3dfda9754a3e90d1bd2.jpg', 'Издание для тех, кого не прельщают дешевые сенсации, для тех, кто в состоянии оценить настоящий стиль в литературе и моде.', NULL);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (4, 'Народная воля', 2.7, 3, NULL, 2, 'https://nn.by/img/w924d4/photos/z_2016_07/img_20160726_131901-rytpz.jpg', 'Актуальныя падзеі і эксклюзіўная аналітыка з Беларусі. Палітыка, эканоміка, культура і адпачынак. Важныя навіны на карысць грамадству.', NULL);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (5, 'Салiдарнасць', 3.6, 4, NULL, 2, 'https://nn.by/img/w500d4/photos/z_2012_12/salidarnasc_redyzajn.jpg', 'Дайджест и самые быстрые новости Беларуси и мира, фото и видео дня.', NULL);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (6, 'Гарри Поттер', 8.4, 1, 1, 1, 'https://hpbooks.ru/uploadedFiles/eshopimages/big/7k_QjtDkslM.jpg', 'Серия из семи романов, написанных английской писательницей Дж. К. Роулинг. Книги представляют собой хронику приключений юного волшебника Гарри Поттера, а также его друзей Рона Уизли и Гермионы Грейнджер, обучающихся в школе чародейства и волшебства Хогвартс. Основной сюжет посвящён противостоянию Гарри и тёмного волшебника по имени лорд Волан-де-Морт, в чьи цели входит обретение бессмертия и порабощение магического мира.', 7);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (7, 'Песнь льда и пламени', 12, 1, 2, 1, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/0074/9780007477159.jpg', 'Серия эпических фэнтези-романов американского писателя и сценариста Джорджа Р. Р. Мартина. Мартин начал писать эту серию в 1991 году. Изначально задуманная как трилогия, к настоящему моменту она разрослась до пяти опубликованных томов, и ещё два находятся в проекте. Автором также написаны повести-приквелы и серия повестей, представляющих собой выдержки из основных романов серии. Одна из таких повестей, «Кровь дракона», была удостоена Премии Хьюго. Три первых романа серии были награждены премией «Локус» за лучший роман фэнтези в 1997, 1999 и 2001 годах соответственно.', 5);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (8, 'Властелин колец', 10, 1, 3, 1, 'https://ozon-st.cdn.ngenix.net/multimedia/1007185431.jpg', 'Роман-эпопея английского писателя Дж. Р. Р. Толкина, одно из самых известных произведений жанра фэнтези. «Властелин колец» был написан как единая книга, но из-за объёма при первом издании его разделили на 3 части — «Братство Кольца», «Две крепости» и «Возвращение короля». В виде трилогии он публикуется и по сей день. «Властелин колец» является одной из самых известных и популярных книг XX века. Он переведён по меньшей мере на 38 языков. Эта книга оказала огромное влияние на литературу в жанре фэнтези, на настольные и компьютерные игры, на кинематограф и вообще на мировую культуру. Именно под влиянием работ профессора Толкина появилось ролевое движение.', 3);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (9, 'Голодные игры', 5.4, 1, 4, 1, 'https://vignette.wikia.nocookie.net/thehungergames/images/1/1b/Hunger_games_trilogy.jpg/revision/latest?cb=20110529021953', 'Трилогия американской писательницы Сьюзен Коллинз. В трилогию входят романы «Голодные игры» 2008 года, «И вспыхнет пламя» 2009 года и «Сойка-пересмешница» 2010 года. За короткое время книги трилогии стали бестселлерами, первые два романа почти два года находились в списке самых продаваемых книг на территории США. Компания Lionsgate выкупила права на экранизацию всех частей трилогии, мировая премьера фильма по первому роману состоялась 12 марта 2012 года, по второму роману — 11 ноября 2013 года, а выход оставшихся (фильм по роману «Сойка-пересмешница» разделён на две части) состоялся 10 ноября 2014 года и 4 ноября 2015 года.', 3);
INSERT INTO `periodical` (`id`, `title`, `price`, `periodicity_id`, `author_id`, `periodical_type_id`, `coverImage`, `description`, `booksAmount`) VALUES (10, 'Дивергент', 6.3, 1, 5, 1, 'https://best-reviews.com/wp-content/uploads/2017/09/divergent-book-free-pdf-download.png', 'В начале XXI века антиутопия уверенно заняла место в списке популярных жанров литературы для подростков. \"Дивергент\" — очередное тому подтверждение. Вселенная романа — мир, развернувшийся на руинах бывшего Чикаго, в котором люди, пытаясь побороть пороки, приведшие их на грань гибели, образовали пять фракций — своеобразных закрытых каст: Отречение, Эрудиция, Бесстрашие, Дружелюбие и Искренность. Каждая фракция выполняет свою функцию в обществе, и всем её членам присущ общий набор черт характера. Есть также Изгои — те, кто не подошел ни к одной фракции или по тем или иным причинам выбыл из одной из них.', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodical_genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (1, 'История');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (2, 'Лайфстайл');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (3, 'Мужской');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (4, 'Новости');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (4, 'Политика');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (4, 'Общество');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (4, 'Экономика');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (5, 'Новости');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (5, 'Экономика');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (5, 'Политика');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (3, 'Лайфстайл');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (6, 'Фэнтези');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (6, 'Роман');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (7, 'Фэнтези');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (7, 'Роман');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (7, 'Эпическое');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (8, 'Фэнтези');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (8, 'Роман');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (8, 'Эпическое');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (9, 'Антиутопия');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (9, 'Роман');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (9, 'Подростковая литература');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (10, 'Антиутопия');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (10, 'Подростковая литература');
INSERT INTO `periodical_genre` (`periodical_id`, `genre_name`) VALUES (10, 'Роман');

COMMIT;

