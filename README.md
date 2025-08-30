# ЧаВо

`websocket-spring` – экспериментальный репозиторий на пощупать WebSocket в Spring. 

## Содержание

- [mvc](#mvc)
- [sockjs](#sockjs)
- [stomp](#stomp)
- [ws](#ws)
- [js-demo-client](#js-demo-client)

---

## mvc

**WebMvcConfig**  
Конфигурация для Spring Web MVC. Здесь заданы настройки CORS (Cross-Origin Resource Sharing). Добавлено из-за SockJS.

---

## sockjs

**SockJsConfig**  
Настройка WebSocket с использованием SockJS.

---

## stomp

**StomConfiguration**  
Конфигурация WebSocket с использованием протокола STOMP. Здесь определены точки подключения (`/portfolio`) и префиксы для брокера сообщений (`/app`, `/topic`, `/queue`).

---

## ws

Этот каталог содержит основные компоненты для работы с нативными WebSocket.

### config

- **WebSocketConfig**  
  Основная конфигурация для нативных WebSocket. Здесь регистрируются обработчики (`/handler-a`, `/handler-b`) и interceptors.

### handler

- **TextHandlerA**  
  Обработчик WebSocket-сообщений. Пример создания комнат на основе параметра `room` из URL. Сообщения рассылаются только участникам одной комнаты. Используется дефолтный interceptor.

- **TextHandlerB**  
  Такой же обработчик, но использующий кастомный interceptor.

### interceptor

- **CustomInterceptor**  
  Пример кастомного перехватчика. Используется для проверки токена аутентификации до установления соединения и передачи данных в сессию WebSocket.

## js-demo-client

Клиенты для тестирования ws

- **Caddyfile**
 Конфиг для Caddy (веб-сервер, я его использую для dev окружения):
 ``caddy run --config ./Caddyfile``
 ```
  cat /etc/hosts
  127.0.0.1  app.local
  127.0.0.1  api.local
 ```