📘 XML Parser – Курсов проект по ООП (Java)
Този проект представлява конзолно Java приложение, което парсва и обработва XML файлове без използване на външни библиотеки. Програмата поддържа зареждане на файл, изпълнение на команди за извличане и промяна на съдържанието, както и частични XPath заявки.

🚀 Стартиране
Компилирай проекта в IDE (напр. IntelliJ)

Стартирай Application.java

Използвай командния ред (REPL), за да въвеждаш команди

🛠 Поддържани команди с примери
Команда	Пример
open <file>	open src/main/resources/sample.xml
close	close
save	save
saveas <file>	saveas src/main/resources/output.xml
print	print
select <id> <key>	select 1_2 id
set <id> <key> <value>	set 1_2 genre Programming
delete <id> <key>	delete 1_2 genre
newchild <id>	newchild 1_1
children <id>	children 1_1
child <id> <index>	child 1_1 0
text <id>	text 1_3
xpath <id> <XPath>	xpath 1_1 book(title="Clean Code")/author
help	help
exit	exit

📂 Примерен XML (sample.xml)
xml
Копиране на код
<library>
    <book>
        <title>Effective Java</title>
        <author>Joshua Bloch</author>
    </book>
    <book>
        <title>Clean Code</title>
        <author>Robert C. Martin</author>
    </book>
</library>
✅ Реализирана функционалност
Собствен парсър без DOM/SAX

Генериране на уникални id за елементи

Поддръжка на:

/ навигация

[] индекси

(@key) достъп до атрибути

(tag="value") филтри

Команден интерфейс (без switch-case)

Изцяло модулен код с интерфейс Command
