# Тестирование сервиса медицинских показаний

В данной задаче сделан fork [репозитория](https://github.com/neee/healthcare-service), в котором находится код приложения для хранения медицинских показаний пациентов клиники (фамилия, имя, дата рождения, кровяное давление, температура), вся информация записывается в файл. В моём репозитории написаны Unit-тесты с использованием библиотеки Mockito для проверки корректности работы функционала.

## Что сделано
- Написаны тесты для проверки класса MedicalServiceImpl, используя заглушки для классов PatientInfoFileRepository и SendAlertServiceImpl, от которых он зависит:
    1. Проверен вывод сообщения во время проверки давления `checkBloodPressure`
    2. Проверен вывод сообщения во время проверки температуры `checkTemperature`
    3. Проверено, что сообщения не выводятся, когда показатели в норме.
