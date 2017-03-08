# Пользователь сайта
CREATE TABLE users (
  id                INTEGER AUTO_INCREMENT PRIMARY KEY,

  login             VARCHAR(30)   NOT NULL UNIQUE,
  uid               CHAR(36)      NOT NULL UNIQUE,
  email             VARCHAR(50)   NOT NULL UNIQUE,
  password_hash     CHAR(32)      NOT NULL,

  registration_date DATETIME      NOT NULL,
  termination_date  DATETIME      NULL,

  last_login_date   DATETIME      NOT NULL,

  settings          VARCHAR(2048) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# Запрос на что-либо: сброс пароля и тп. Определяется типом type.
CREATE TABLE verification_record (
  id                INTEGER AUTO_INCREMENT PRIMARY KEY,
  hash              CHAR(36)     NOT NULL UNIQUE,
  user_id           INTEGER      NOT NULL REFERENCES users (id),
  type              INTEGER      NOT NULL,
  value             VARCHAR(100) NOT NULL,
  creation_date     DATETIME     NOT NULL,
  verification_date DATETIME     NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# Категория (логическа группа) заметок
CREATE TABLE groups (
  id      INTEGER AUTO_INCREMENT PRIMARY KEY,
  # Владелец категории.
  user_id INTEGER     NOT NULL REFERENCES users (id),
  # Короткое название категории.
  title   VARCHAR(48) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# Персональная заметка
CREATE TABLE zametka (
  id            INTEGER AUTO_INCREMENT PRIMARY KEY,
  # Автор заметки.
  user_id       INTEGER  NOT NULL REFERENCES users (id),
  # Дата создания заметки.
  creation_date DATETIME NOT NULL,
  # Соредржание заметки. Сегодня - прямо в ее теле. В будущем можно поменять.
  content       TEXT     NOT NULL,
  # Группа которой принадлежит заметка
  group_id      INTEGER  NOT NULL REFERENCES groups (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
