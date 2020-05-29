package ru.otus.bookregistry.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.bookregistry.model.Author;
import ru.otus.bookregistry.model.Book;
import ru.otus.bookregistry.model.Genre;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@ChangeLog(order = "000")
public class InitDBChangeLog {

	@ChangeSet(order = "000", id = "drop collection books", author = "shubnikofff", runAlways = true)
	public void dropCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.dropCollection(Book.class);
	}

	@ChangeSet(order = "001", id = "initBooks", author = "shubnikofff", runAlways = true)
	public void initCollectionBooks(MongoOperations mongoOperations) {
		mongoOperations.save(new Book("1", "Путешествия Гулливера", new Genre("Детская литература"), singletonList(new Author("Джонатан Свифт")), "fowler"));
		mongoOperations.save(new Book("2","Четвертая мировая война", new Genre("Научно-популярная литература"), singletonList(new Author("Андрей Курпатов")), "fowler"));
		mongoOperations.save(new Book("3","Шаблоны корпоративных приложений", new Genre("Компьютерная литература"), singletonList(new Author("Мартин Фаулер")), "fowler"));
		mongoOperations.save(new Book("4","Доктор де Сото", new Genre("Детская литература"), singletonList(new Author("Ульям Стайг")), "martin"));
		mongoOperations.save(new Book("5","Зоки и Бада", new Genre("Детская литература"), asList(new Author("Ирина Тюхтяева"),  new Author("Леонид Тюхтяев")), "martin"));
		mongoOperations.save(new Book("6","Кольцо тьмы", new Genre("Фэнтези"), singletonList(new Author("Ник Перумов")), "martin"));
		mongoOperations.save(new Book("7","Александр и ужасный, кошмарный, нехороший, очень плохой день", new Genre("Детская литература"), singletonList(new Author("Джуди Виорст")), "martin"));
		mongoOperations.save(new Book("8","Москва для детей", new Genre("Детская литература"), singletonList(new Author("Наталья Андрианова")), "martin"));
		mongoOperations.save(new Book("9","Едим дома круглый год", new Genre("кулинарная книга"), singletonList(new Author("Юлия Высоцкая")), "fowler"));
		mongoOperations.save(new Book("10","Фэн-шуй", new Genre("Эзотерика"), singletonList(new Author("Стефан Скиннер")), "martin"));
		mongoOperations.save(new Book("11","Design Patterns", new Genre("Computer science"), asList(new Author("Erich Gamma"),  new Author("Richard Helm"), new Author("Ralph Johnson"),  new Author("John Vlissides")), "martin"));
	}
}
