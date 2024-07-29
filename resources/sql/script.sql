CREATE TABLE `TOPICS` (
    `topic_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50),
    `description` VARCHAR(2000)
);

CREATE TABLE `POSTS` (
    `post_id` INT PRIMARY KEY AUTO_INCREMENT,
    `topic_id` INT,
    `title` VARCHAR(50),
    `author_id` INT,
    `content` VARCHAR(10000),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `COMMENTS` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `author_id` INT,
    `post_id` INT,
    `message` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `USERS` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(40),
    `email` VARCHAR(100),
    `password` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `SUBSCRIPTIONS` (
    `user_id` INT,
    `topic_id` INT
);

ALTER TABLE `POSTS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`topic_id`);
ALTER TABLE `POSTS` ADD FOREIGN KEY (`author_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`author_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`post_id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`topic_id`);


INSERT INTO TOPICS (name, description)
VALUES ('Java', 'Topic about Java language'),
       ('Angular', 'Topic about Angular framework'),
       ('TypeScript', 'Topic about TypeScript language');
