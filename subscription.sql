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
-- Table `subscription`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`user` ;

CREATE TABLE IF NOT EXISTS `subscription`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор для каждого пользователя. Синтетический первичный ключ.',
  `email` VARCHAR(254) NOT NULL COMMENT 'Почтовый адрес пользователя, использующийся для входа в систему. Должен быть уникальным.',
  `password` CHAR(64) NOT NULL COMMENT 'Хэш пароля пользователя. 64-знаковый CHAR — результат работы алгоритма хэширования SHA-256, являющегося на сегодня стандартом в подобных приложениях.',
  `isAdmin` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Роль пользователя на сайте — простой юзер или админ. Для удобства дефолтное значение установлено как юзер.',
  `registrationDate` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'Время регистрации пользователя в системе (понадобится в дальнейшем для генерации токена авторизации).',
  `firstName` VARCHAR(35) NOT NULL COMMENT 'Имя пользователя.',
  `lastName` VARCHAR(35) NOT NULL COMMENT 'Фамилия пользователя.',
  `balance` DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Баланс пользователя. Беззнаковый тип, баланс не может быть минусовым. Для удобства дефолтное значение — ноль.',
  `login` VARCHAR(32) NOT NULL,
  `isBanned` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB
COMMENT = 'Данные о клиенте-читателе приложения.';


-- -----------------------------------------------------
-- Table `subscription`.`genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`genre` ;

CREATE TABLE IF NOT EXISTS `subscription`.`genre` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор жанра.',
  `name` VARCHAR(45) NOT NULL COMMENT 'Непосредственно название жанра.',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
COMMENT = 'Данные о жанрах книжных серий — роман, детектив, приключенческий и т.д.';


-- -----------------------------------------------------
-- Table `subscription`.`book_series`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`book_series` ;

CREATE TABLE IF NOT EXISTS `subscription`.`book_series` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор книжной серии.',
  `genre_id` INT UNSIGNED NOT NULL COMMENT 'Поле, ссылающееся на жанр данной книжной серии.',
  `title` VARCHAR(255) NOT NULL COMMENT 'Название книжной серии.',
  `author_fullname` VARCHAR(105) NOT NULL COMMENT 'Полное имя автора книжной серии. ',
  `pages_amount` INT UNSIGNED NOT NULL COMMENT 'Количество страниц в книжной серии.',
  `books_amount` INT UNSIGNED NOT NULL COMMENT 'Количество книг в книжной серии.',
  `form` ENUM('poetry', 'prose') NOT NULL DEFAULT 'prose' COMMENT 'Тип или форма повествования — поэзия либо проза.',
  `publisher` VARCHAR(90) NOT NULL COMMENT 'Издатель книжной серии.',
  `price` DECIMAL(7,2) UNSIGNED NOT NULL COMMENT 'Цена подписки на книжную серию.',
  PRIMARY KEY (`id`),
  INDEX `title_idx` (`title` ASC),
  INDEX `author_idx` (`author_fullname` ASC),
  INDEX `form_idx` (`form` ASC),
  INDEX `publisher_idx` (`publisher` ASC),
  INDEX `fk_book_series_genre_idx` (`genre_id` ASC),
  CONSTRAINT `fk_book_series_genre1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `subscription`.`genre` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Данные об одном из продуктов — книжных сериях. Книжные серии объединяет жанр, автор, издатель. Книжные серии могут включать в себя как все части одной книги, так и подборку-многотомник определенного автора. ';


-- -----------------------------------------------------
-- Table `subscription`.`periodicity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`periodicity` ;

CREATE TABLE IF NOT EXISTS `subscription`.`periodicity` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор типа подписки.',
  `periodicity` VARCHAR(45) NOT NULL COMMENT 'Непосредственно тип подписки (weekly, monthly, yearly, etc.)',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `subscription_type_UNIQUE` (`periodicity` ASC))
ENGINE = InnoDB
COMMENT = 'Частота выхода изданий — еженедельная, ежемесячная, годовая и т.д.';


-- -----------------------------------------------------
-- Table `subscription`.`periodical`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`periodical` ;

CREATE TABLE IF NOT EXISTS `subscription`.`periodical` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор периодического издания.',
  `ISSN` CHAR(8) NOT NULL COMMENT 'Уникальный ISSN-номер для идентификации любое периодическое издание. ',
  `type` ENUM('paper', 'magazine', 'газета', 'журнал') NOT NULL COMMENT 'Тип периодического издания — газета или журнал.',
  `title` VARCHAR(255) NOT NULL COMMENT 'Название.',
  `publisher` VARCHAR(90) NOT NULL COMMENT 'Издательство.',
  `unit_price` DECIMAL(4,2) UNSIGNED NOT NULL COMMENT 'Цена за единицу. ',
  `pages_amount` INT UNSIGNED NOT NULL COMMENT 'Количество страниц',
  `age_limit` INT UNSIGNED NOT NULL COMMENT 'Возрастное ограничение.',
  `periodicity_id` INT UNSIGNED NOT NULL COMMENT 'Частота выхода издания — ежегодная, ежемесячная, еженедельная и т.д.',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ISSN_UNIQUE` (`ISSN` ASC),
  INDEX `fk_periodical_periodicity1_idx` (`periodicity_id` ASC),
  CONSTRAINT `fk_periodical_periodicity1`
    FOREIGN KEY (`periodicity_id`)
    REFERENCES `subscription`.`periodicity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Данные об одном из продуктов — периодическом издании (газета, журнал).';


-- -----------------------------------------------------
-- Table `subscription`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`order` ;

CREATE TABLE IF NOT EXISTS `subscription`.`order` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор подписки.',
  `client_id` INT UNSIGNED NOT NULL COMMENT 'Поле, идентифицирующее читателя, которому принадлежит подписка.',
  `start_date` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Начало действия подписки. С какого числа читатель получает заказанные издания. ',
  `end_date` TIMESTAMP(0) NULL COMMENT 'Конец подписки. С какого числа читатель перестает получать заказанные издания.',
  PRIMARY KEY (`id`),
  INDEX `fk_subscription_client1_idx` (`client_id` ASC),
  CONSTRAINT `fk_subscription_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `subscription`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Данные о заказе пользователем различных изданий — книг, газет, журналов.';


-- -----------------------------------------------------
-- Table `subscription`.`subscription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription`.`subscription` ;

CREATE TABLE IF NOT EXISTS `subscription`.`subscription` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор заказов.',
  `book_series_id` INT UNSIGNED NULL COMMENT 'Книжная серия, на которую оформлена подписка.',
  `periodical_id` INT UNSIGNED NULL COMMENT 'Периодическое издание, на которое подписан пользователь.',
  `order_id` INT UNSIGNED NOT NULL COMMENT 'Номер заказа, который содержит дату начала и конца действия подписки.',
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_book_series1_idx` (`book_series_id` ASC),
  INDEX `fk_order_item_periodical1_idx` (`periodical_id` ASC),
  INDEX `fk_order_item_subscription1_idx` (`order_id` ASC),
  CONSTRAINT `fk_order_item_book_series1`
    FOREIGN KEY (`book_series_id`)
    REFERENCES `subscription`.`book_series` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_item_periodical1`
    FOREIGN KEY (`periodical_id`)
    REFERENCES `subscription`.`periodical` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_item_subscription1`
    FOREIGN KEY (`order_id`)
    REFERENCES `subscription`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Таблица, которая служит для сборки всех изданий, на которые оформляет подписку пользователь, в одну подписку. ';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `subscription`.`genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`genre` (`id`, `name`) VALUES (1, 'Подростковая литература');
INSERT INTO `subscription`.`genre` (`id`, `name`) VALUES (2, 'Фентези');
INSERT INTO `subscription`.`genre` (`id`, `name`) VALUES (3, 'Роман');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription`.`book_series`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (1, 2, 'Гарри Поттер. Полное собрание', 'Джоан Роулинг', 4100, 7, DEFAULT, 'Росмэн', 72);
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (2, 2, 'Песнь льда и пламени', 'Джордж Рэймонд Ричард Мартин', 5216, 5, DEFAULT, 'АСТ', 54);
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (3, 2, 'Властелин колец', 'Джон Рональд Руэл Толкин', 1216, 3, DEFAULT, 'АСТ', 36);
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (4, 3, 'Полное собрание романов', 'Федор Михайлович Достоевский', 1679, 5, DEFAULT, 'Альфа-книга', 45);
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (5, 1, 'Голодные игры', 'Сьюзен Коллинз', 1155, 3, DEFAULT, 'Азбука-Аттикус', 12);
INSERT INTO `subscription`.`book_series` (`id`, `genre_id`, `title`, `author_fullname`, `pages_amount`, `books_amount`, `form`, `publisher`, `price`) VALUES (6, 1, 'Дивергент', 'Вероника Рот', 1616, 3, DEFAULT, 'Геликон', 10.5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription`.`periodicity`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`periodicity` (`id`, `periodicity`) VALUES (1, 'раз в месяц');
INSERT INTO `subscription`.`periodicity` (`id`, `periodicity`) VALUES (2, 'раз в неделю');
INSERT INTO `subscription`.`periodicity` (`id`, `periodicity`) VALUES (3, '2 раза в неделю');
INSERT INTO `subscription`.`periodicity` (`id`, `periodicity`) VALUES (4, '3 раза в неделю');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription`.`periodical`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`periodical` (`id`, `ISSN`, `type`, `title`, `publisher`, `unit_price`, `pages_amount`, `age_limit`, `periodicity_id`) VALUES (1, '22266887', 'журнал', 'Дилетант', 'Интерлинк', 5, 96, 14, 2);
INSERT INTO `subscription`.`periodical` (`id`, `ISSN`, `type`, `title`, `publisher`, `unit_price`, `pages_amount`, `age_limit`, `periodicity_id`) VALUES (2, '22266147', 'журнал', 'Большой', 'Идея Консалт', 4.5, 100, 16, 1);
INSERT INTO `subscription`.`periodical` (`id`, `ISSN`, `type`, `title`, `publisher`, `unit_price`, `pages_amount`, `age_limit`, `periodicity_id`) VALUES (3, '00140791', 'журнал', 'Esquire', 'Hearst Magazines', 9, 89, 16, 1);
INSERT INTO `subscription`.`periodical` (`id`, `ISSN`, `type`, `title`, `publisher`, `unit_price`, `pages_amount`, `age_limit`, `periodicity_id`) VALUES (4, '20719647', 'газета', 'Народная воля', 'Народная воля', 0.45, 32, 12, 3);
INSERT INTO `subscription`.`periodical` (`id`, `ISSN`, `type`, `title`, `publisher`, `unit_price`, `pages_amount`, `age_limit`, `periodicity_id`) VALUES (5, '20179557', 'газета', 'Солидарность', 'Солидарность', 0.39, 24, 12, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription`.`order`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`order` (`id`, `client_id`, `start_date`, `end_date`) VALUES (1, 1, DEFAULT, '2018-01-08 18:19:03');
INSERT INTO `subscription`.`order` (`id`, `client_id`, `start_date`, `end_date`) VALUES (2, 1, DEFAULT, '2018-03-08 18:21:03');
INSERT INTO `subscription`.`order` (`id`, `client_id`, `start_date`, `end_date`) VALUES (3, 2, DEFAULT, NULL);
INSERT INTO `subscription`.`order` (`id`, `client_id`, `start_date`, `end_date`) VALUES (4, 3, DEFAULT, '2018-06-08 18:30:03');
INSERT INTO `subscription`.`order` (`id`, `client_id`, `start_date`, `end_date`) VALUES (5, 3, DEFAULT, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription`.`subscription`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription`;
INSERT INTO `subscription`.`subscription` (`id`, `book_series_id`, `periodical_id`, `order_id`) VALUES (1, 1, NULL, 1);
INSERT INTO `subscription`.`subscription` (`id`, `book_series_id`, `periodical_id`, `order_id`) VALUES (2, NULL, 1, 2);
INSERT INTO `subscription`.`subscription` (`id`, `book_series_id`, `periodical_id`, `order_id`) VALUES (3, 3, NULL, 3);
INSERT INTO `subscription`.`subscription` (`id`, `book_series_id`, `periodical_id`, `order_id`) VALUES (4, 2, NULL, 3);
INSERT INTO `subscription`.`subscription` (`id`, `book_series_id`, `periodical_id`, `order_id`) VALUES (5, NULL, 2, 5);

COMMIT;

