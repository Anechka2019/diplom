# Отчет по итогам тестирования

### Краткое описание

Автоматизация тестирования сервиса "Покупка тура" выполнялась с использованием следующих инструментов:

1. IDEA для написания тестов, как одна из наиболее распространенных в разработке, удобная для работы (не требует установки дополнений, комфортна в работе, дает возможность подключения различных библиотек для написания тестов) 
2. Java 8 как наиболее оптимальная и распространенная версия 
3. Play with docker для "упаковки" готового к тестированию продукта, передачи всех параметров тестируемой среды и создания "виртуальной машины" для тестирования приложения 
4. Junit, jupiter 5.5.1 - для создания тестов и параметризированных тестов 
5. Selenide т.к имеет более оптимальный синтаксис по сравнению с Selenium и позволяет управлять браузером 
6. Faker для быстрой генерации данных, позволяет не привязывать тесты к определенным данным и каждый раз генерировать уникальные данные 
7. Allure для построения удобочитаемой единой отчетности по всем тестам со скрин-шотами, подробными описаниями 
8. MySQL и PostgreSQL для проверки работоспособности системы в различных БД. 
______________________________________________________________________

Для работы с БД и симулятором банковских сервисов использовался docker

Были протестированы сценарии:
* успешная покупка с дебетовой и кредитной карты
* отказ банка с дебетовой и кредитной картой
* негативные проверки полей: невалидные значения, незаполненные поля

### Количество тест-кейсов

* Всего: 12 тест-кейсов
* Успешных: 8 (67%)
* Неуспешных: 4 (33%)

### Общие рекомендации

#### Найденные баги

* [нет валидации поля Имя](https://github.com/Anechka2019/diplom/issues/5)
* [нет валидации поля Номер карты](https://github.com/Anechka2019/diplom/issues/4)
* [после исправления значений остается подсветка полей как некорректных](https://github.com/Anechka2019/diplom/issues/3)
* [некорректное сообщение об ошибке](https://github.com/Anechka2019/diplom/issues/2)

#### Рекомендации по улучшению интерфейса

* реализовать валидацию номера карты, проверку контрольной цифры.
* реализовать валидацию имени владельца карты.
* в поле Владелец добавить пояснение: надо ли писать имя как в паспорте или как на карте. Во втором случае сделать невозможным ввод кирилицы.
* при переключении между разделами Оплата по карте и Купить в кредит все поля очищаются. Возможно, будет удобнее, если данные будут сохраняться.
* исправить орфографическую ошибку в слове "Марракэш". Правильно - Марракеш.