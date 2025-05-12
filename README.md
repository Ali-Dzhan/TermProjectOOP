# 📘 XML Parser – Курсов проект по ООП (Java)

Този проект представлява **конзолно Java приложение**, което парсва и обработва XML файлове **без използване на външни библиотеки**. Програмата поддържа зареждане на файл, изпълнение на команди за извличане и промяна на съдържанието, както и частични XPath заявки.

---

## 🚀 Стартиране

1. Компилирай проекта в IDE (напр. IntelliJ)
2. Стартирай `Application.java`
3. Използвай командния ред (REPL), за да въвеждаш команди

---

## 🛠 Поддържани команди с примери

| Команда                    | Пример                                              |
|----------------------------|------------------------------------------------------|
| `open <file>`              | `open src/main/resources/sample.xml`                |
| `close`                    | `close`                                             |
| `save`                     | `save`                                              |
| `saveas <file>`            | `saveas src/main/resources/output.xml`              |
| `print`                    | `print`                                             |
| `select <id> <key>`        | `select 1_2 id`                                     |
| `set <id> <key> <val>`     | `set 1_2 genre Programming`                         |
| `delete <id> <key>`        | `delete 1_2 genre`                                  |
| `newchild <id>`            | `newchild 1_1`                                      |
| `children <id>`            | `children 1_1`                                      |
| `child <id> <index>`       | `child 1_1 0`                                       |
| `text <id>`                | `text 1_3`                                          |
| `xpath <id> <XPath>`       | `xpath 1_1 book(title="Clean Code")/author`         |
| `help`                     | `help`                                              |
| `exit`                     | `exit`                                              |

---

## 🧭 XPath заявки

Командата `xpath <id> <заявка>` позволява извличане на елементи от XML дървото, като поддържа следните синтактични конструкции:

| Синтаксис        | Пример | Описание |
|------------------|--------|----------|
| `/`              | `book/title` | Навигация по подетаги |
| `[]`             | `book[0]/title` | Индексиране (0-базирано) |
| `(@ключ)`        | `book(@id)` | Извличане на стойност на атрибут |
| `(таг="стойност")` | `book(title="Clean Code")/author` | Филтриране по стойност на дете |

### 📌 Примерни заявки за тест:

```bash
xpath 1_1 book/title
xpath 1_1 book[0]/author
xpath 1_1 book(@id)
xpath 1_1 book(title="Clean Code")/author
