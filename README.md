# Spring Boot Security Demo

Веб-приложение на Spring Boot с аутентификацией и авторизацией по ролям. Реализован CRUD для пользователей с разделением доступа: администратор управляет пользователями, обычный пользователь видит свою страницу и список пользователей.

## Технологии

- **Java 17**
- **Spring Boot 2.6.2**
- **Spring Security** — аутентификация и ролевой доступ
- **Spring Data JPA** — работа с БД
- **Thymeleaf** — шаблоны страниц
- **MySQL** — база данных
- **BCrypt** — шифрование паролей

## Структура проекта

```
src/main/java/ru/kata/spring/boot_security/demo/
├── config/           # Конфигурация безопасности и MVC
│   ├── WebSecurityConfig.java
│   ├── SuccessUserHandler.java
│   └── MvcConfig.java
├── controller/       # Контроллеры
│   ├── AdminController.java   # CRUD пользователей (только ADMIN)
│   ├── UserController.java    # Страница пользователя
│   └── LoginController.java   # Страница входа
├── dao/              # Доступ к данным (репозитории)
├── dto/              # UserDto для форм
├── model/            # Сущности User, Role
├── service/          # Бизнес-логика и UserDetailsService
└── SpringBootSecurityDemoApplication.java
```

## Роли и доступ

| Роль    | Доступ |
|--------|--------|
| `ROLE_ADMIN` | `/admin` — полный CRUD пользователей, назначение ролей |
| `ROLE_USER`  | `/user` — просмотр своей страницы и списка пользователей |

После входа пользователь перенаправляется:
- с ролью ADMIN → `/admin`
- с ролью USER → `/user`

## Требования

- JDK 17
- Maven
- MySQL 8 (или совместимая версия)

## Настройка и запуск

### 1. База данных MySQL

Создайте базу и пользователя (или используйте существующие):

```sql
CREATE DATABASE testdb;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'my-secret-pw';
GRANT ALL PRIVILEGES ON testdb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Конфигурация

Параметры подключения к БД задаются в `src/main/resources/application.properties`:

- `spring.datasource.url` — URL БД (по умолчанию `jdbc:mysql://localhost:3306/testdb`)
- `spring.datasource.username` — пользователь
- `spring.datasource.password` — пароль

При необходимости измените их под своё окружение.

### 3. Запуск приложения

```bash
./mvnw spring-boot:run
```

Или соберите JAR и запустите:

```bash
./mvnw clean package
java -jar target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar
```

Приложение будет доступно по адресу: **http://localhost:8080**

### 4. Первый вход

Схема создаётся при старте (`spring.jpa.hibernate.ddl-auto=update`). Роли и пользователей нужно добавить вручную (через SQL или отдельный скрипт инициализации), либо реализовать начальную загрузку данных (например, `CommandLineRunner` или `data.sql` с созданием ролей и первого администратора).

## Основные эндпоинты

| URL        | Метод | Описание                    | Доступ   |
|-----------|--------|-----------------------------|----------|
| `/login`  | GET    | Страница входа              | Все      |
| `/admin`  | GET    | Список пользователей, CRUD  | ADMIN    |
| `/admin/newUser`     | POST | Создание пользователя       | ADMIN    |
| `/admin/update/{id}` | POST | Обновление пользователя     | ADMIN    |
| `/admin/delete/{id}` | POST | Удаление пользователя       | ADMIN    |
| `/admin/load/{id}`   | GET  | Загрузка пользователя для редактирования | ADMIN |
| `/user`   | GET    | Страница текущего пользователя и список пользователей | ADMIN, USER |
| `/logout` | POST   | Выход                       | Аутентифицированные |

## Модель данных

- **User** — пользователь: id, username, firstName, lastName, age, password, роли (ManyToMany с `Role`). Реализует `UserDetails` для Spring Security.
- **Role** — роль: id, name (например, `ROLE_ADMIN`, `ROLE_USER`). Реализует `GrantedAuthority`.

Пароли хранятся в виде хеша BCrypt.

## Тесты

Для тестов используется конфигурация `application-test.yaml` (отдельная БД/настройки при необходимости). Запуск тестов:

```bash
./mvnw test
```

## Лицензия

Учебный демо-проект.
