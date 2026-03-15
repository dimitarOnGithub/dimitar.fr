DROP TABLE blog_test.posts;

CREATE TABLE blog_test.posts (
    id INT PRIMARY KEY UNIQUE AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    published_date datetime NOT NULL,
    is_draft int(1) NOT NULL CHECK (is_draft in (0, 1))
);

DROP TABLE blog_test.users;

CREATE TABLE blog_test.users (
    id INT PRIMARY KEY UNIQUE AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

