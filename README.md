## Платформа по перепродаже вещей
Этот проект представляет собой бэкенд-часть платформы для перепродажи вещей, реализованную на Java. Она предоставляет функционал для управления объявлениями, комментариями и пользователями.
## Введение
Платформа по перепродаже вещей включает функционал для авторизации и аутентификации пользователей, распределения ролей, управления объявлениями и комментариями, а также поддержки изображений. Эта система позволяет пользователям взаимодействовать друг с другом через объявления и комментарии, а администраторам — управлять всеми данными на платформе.
## Функционал

## Авторизация и аутентификация: Управление доступом и идентификацией пользователей.
## Распределение ролей: Разделение пользователей на обычных пользователей и администраторов.
## CRUD-операции:

Создание, чтение, обновление и удаление объявлений и комментариев.
Администраторы могут управлять всеми объявлениями и комментариями.
Пользователи могут управлять только своими объявлениями и комментариями.


Комментарии: Пользователи могут оставлять комментарии под объявлениями.
Изображения: Поддержка загрузки и отображения изображений для объявлений и аватарок пользователей.

## Использование
Чтобы начать использовать платформу, вам потребуется настроить и развернуть бэкенд. Следуйте инструкциям ниже.
Начало работы
Эти инструкции помогут вам настроить проект и запустить бэкенд в вашем окружении.
## Подготовка

Установите Java Development Kit (JDK) на вашем компьютере.
Убедитесь, что у вас установлен Maven для управления зависимостями.

## Установка

Клонируйте репозиторий:
bashCopygit clone <URL-репозитория>
cd <название-проекта>

Настройте параметры подключения к базе данных и другие конфигурации. Замените параметры в src/main/resources/application.properties на ваши настройки.
Соберите проект с помощью Maven:
bashCopymvn clean install

Запустите приложение:
bashCopymvn spring-boot:run


## Вклад
Вклад в развитие проекта приветствуется! Если у вас есть предложения, отчеты о багах или запросы на добавление функций, пожалуйста, создайте issue или отправьте pull request.
## Авторы

Денис Силюков (@ViajerodeRusia)
Дмитрий Хань (@xpycteam88)
Максим Максимов (@maximovmaxim100500)

## Лицензия
Этот проект лицензируется под лицензией MIT - подробности смотрите в файле LICENSE.
