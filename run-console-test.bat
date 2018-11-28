CD /D target\classes
chcp 1251 && echo HELLO FROM CONSOLE | java ru.ezhov.test.console.ConsoleTest rem отправка символов на вход
chcp 1251 && java ru.ezhov.test.console.ConsoleTest < D:\programmer\git\app-test\src\main\java\ru\ezhov\test\console\ConsoleTest.java rem отправка символов на вход из файла
chcp 1251 && java ru.ezhov.test.console.ConsoleTest rem проверка что ничего нет
CD /D ..\..