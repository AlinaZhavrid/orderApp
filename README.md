# orderApp

Приложение для управления заказами представляет собой RESTful API сервис, позволяющий создавать, просматривать, обновлять и удалять заказы.
## Основные возможности

- Создание новых заказов
- Просмотр информации о заказе
- Получение списка всех заказов
- Обновление данных заказа
- Удаление заказов
- Изменение статуса заказа

## Модель данных

Основная сущность - Order (заказ) со следующими полями:
- id (UUID) - уникальный идентификатор заказа
- description (String) - описание заказа
- amount (Double) - сумма заказа
- customerName (String) - имя клиента
- customerEmail (String) - email клиента
- status (OrderStatus) - статус заказа (NEW, IN_PROGRESS, DONE)
- createdAt (LocalDateTime) - дата и время создания заказа

## Технологии

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API

## Требования к данным
- Описание заказа не может быть пустым (максимальная длина 1000 символов)
- Сумма заказа должна быть положительным числом
- Имя клиента не может быть пустым
- Email клиента не может быть пустым
- Статус заказа должен быть одним из предопределенных значений

## API Endpoints

| Операция       | HTTP-метод | Пример URL           | Описание                          |
|----------------|------------|----------------------|-----------------------------------|
| Создать заказ  | POST       | `/orders`            | Создать новый заказ               |
| Получить все   | GET        | `/orders`            | Получить список всех заказов      |
| Получить один  | GET        | `/orders/{id}`       | Получить заказ по его ID          |
| Изменить       | PUT        | `/orders/{id}`       | Полностью обновить заказ          |
| Удалить        | DELETE     | `/orders/{id}`       | Удалить заказ                     |
| Изменить статус| PATCH      | `/orders/{id}/status`| Изменить статус заказа            |

## Запуск приложения
Убедитесь, что у вас установлены:

- Java JDK 11 или выше
- Maven
- База данных (настроенная в application.properties)

Соберите приложение:
`mvn clean install`

Запустите приложение:
`mvn spring-boot:run`

Важно! Для запуска необходим файл .env который не присутствует в репозитории.
## Пример файла .env 

```bash
# Настройки PostgreSQL контейнера
POSTGRES_DB=orders_db          # Название создаваемой базы данных
POSTGRES_USER=postgres         # Имя администратора БД (по умолчанию 'postgres')
POSTGRES_PASSWORD=postgres     # Пароль администратора БД

# Настройки подключения Spring Boot к PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/orders_db  # JDBC URL для подключения
SPRING_DATASOURCE_USERNAME=postgres       # Логин для подключения Spring приложения
SPRING_DATASOURCE_PASSWORD=postgres       # Пароль для подключения Spring приложения
```
Приложение будет доступно по адресу: `http://localhost:8080/orders`

##  healthcheck endpoint 
Healthcheck endpoint в Spring Boot Actuator будет доступен по стандартному URL:
`http://localhost:8080/actuator/health`

Как выглядит ответ
```bash
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "free": 325154160640,
        "threshold": 10485760
      }
    }
  }
}
```
## Docker

Для запуска приложения:
1. Создайте файл .env
2. Запустите приложение командой `docker-compose up --build`. Флаг `--build` пересоберет образ Spring Boot приложения
3. Проверьте работоспосодность приложения, перейдя на `http://localhost:8080/orders` или `http://localhost:8080/actuator/health`
4. Остановка приложения осуществляется `docker-compose down`, а для остановки и очистки ресурсов `docker-compose down -v`
