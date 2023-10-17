INSERT INTO `users` (`id`,`email`,`username`,`password`) VALUES (1,'bartek@gmail.com','Bartek','Bartek');
INSERT INTO `users` (`id`,`email`,`username`,`password`) VALUES (2,'kamil@gmail.com','Kamil','Kamil');
INSERT INTO `users` (`id`,`email`,`username`,`password`) VALUES (3,'Ola@gmail.com','Aleksandra','Ola123');
INSERT INTO `users` (`id`,`email`,`username`,`password`) VALUES (4,'Magda@gmail.com','Magda','Mojehaslo');

INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (1,'moj pierwszy post',1,'2023-10-11 11:23:57');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (2,'moj drugi post',1,'2023-10-11 11:24:07');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (3,'moj pierwszy post',2,'2023-10-11 11:24:20');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (4,'moj drugi post',2,'2023-10-11 11:24:24');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (5,'moj pierwszy post',4,'2023-10-11 11:24:43');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (6,'moj trzeci post',1,'2023-10-11 11:24:54');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (7,'moj pierwszy post',3,'2023-10-11 11:25:20');
INSERT INTO `posts` (`id`,`body`,`user_id`,`created_at`) VALUES (8,'Post(id=3, user=User(id=2, email=kamil@gmail.com, username=Kamil, password=Kamil), body=moj pierwszy post, dateTime=2023-10-11T11:24:20)',1,'2023-10-11 15:06:36');

INSERT INTO `comments` (`id`,`body`,`user_id`,`post_id`,`created_at`) VALUES (1,'Super post',3,1,'2023-10-11 11:26:19');
INSERT INTO `comments` (`id`,`body`,`user_id`,`post_id`,`created_at`) VALUES (2,'Lubię to!',2,1,'2023-10-11 11:26:38');
INSERT INTO `comments` (`id`,`body`,`user_id`,`post_id`,`created_at`) VALUES (3,'Podoba mi sie',4,3,'2023-10-11 11:27:20');
INSERT INTO `comments` (`id`,`body`,`user_id`,`post_id`,`created_at`) VALUES (4,'Mi tez!!!!!',1,3,'2023-10-11 11:27:33');
INSERT INTO `comments` (`id`,`body`,`user_id`,`post_id`,`created_at`) VALUES (5,'I mi',3,3,'2023-10-11 11:27:43');

INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (1,1,2);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (2,1,3);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (3,3,1);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (4,3,2);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (5,3,4);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (6,4,3);
INSERT INTO `followers` (`id`,`user_id`,`following_id`) VALUES (7,2,1);

INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (1,3,1);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (2,4,1);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (3,5,1);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (4,6,1);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (5,7,1);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (6,5,2);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (7,7,2);
INSERT INTO `likes` (`id`,`post_id`,`user_id`) VALUES (8,5,4);

