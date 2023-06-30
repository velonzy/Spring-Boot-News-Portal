package ru.nicetu.newsportal;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nicetu.newsportal.models.*;
import ru.nicetu.newsportal.services.CommentsService;
import ru.nicetu.newsportal.services.LikesService;
import ru.nicetu.newsportal.services.NewsService;
import ru.nicetu.newsportal.services.PeopleService;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class NewsPortalApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(NewsPortalApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner run(PeopleService peopleService, NewsService newsService, CommentsService commentsService, LikesService likesService) {

		return args -> {
			News News1 = new News("World", "Some text about events", "picture1");
			News News2 = new News("Animals", "Animals are our friends", "picture2");
			News News3 = new News("Cats", "Cat is the most cute animal", "picture3");
			News News4 = new News("Job", "I need to go on the vacation", "picture4");
			News News5 = new News("Capybara", "New memes with capybara are awesome!!", "picture5");

			newsService.saveNews(News1);
			newsService.saveNews(News2);
			newsService.saveNews(News3);
			newsService.saveNews(News4);
			newsService.saveNews(News5);

			peopleService.saveRole(new Role(1, "ROLE_USER"));
			peopleService.saveRole(new Role(2, "ROLE_ADMIN"));

			Person John = new Person("john@mail.com", "John", "White", "1234");
			Person Alice = new Person("alice@mail.com", "Alice", "White", "1234567");
			Person Mike = new Person("mike@mail.com", "Mike", "Strong", "56tre");

			peopleService.savePerson(John);
			peopleService.savePerson(Alice);
			peopleService.savePerson(Mike);

			peopleService.addRoleToUser("john@mail.com", "ROLE_USER");
			peopleService.addRoleToUser("john@mail.com", "ROLE_ADMIN");
			peopleService.addRoleToUser("alice@mail.com", "ROLE_USER");
			peopleService.addRoleToUser("mike@mail.com", "ROLE_USER");

			Comment comment1 = new Comment("I'm tired too");
			Comment comment2 = new Comment("My cat's name is Filya");
			Comment comment3 = new Comment("My cat's name is Mursik");
			Comment comment4 = new Comment("More news about the world should be posted on your website");
			commentsService.saveComment(comment1, News4.getId(), John.getId());
			commentsService.saveComment(comment2, News3.getId(), John.getId());
			commentsService.saveComment(comment3, News3.getId(), Mike.getId());
			commentsService.saveComment(comment4, News1.getId(), Alice.getId());

			likesService.addLikeToNews(John, News1);
			likesService.addLikeToNews(John, News1);
			likesService.addLikeToNews(Alice, News1);
			likesService.addLikeToNews(Alice, News3);

			newsService.saveTheme(new Theme("animals"));
			newsService.saveTheme(new Theme("cats"));
			newsService.saveTheme(new Theme("politics"));
			newsService.saveTheme(new Theme("world"));
			newsService.saveTheme(new Theme("lifestyle"));
			newsService.saveTheme(new Theme("memes"));
			newsService.saveTheme(new Theme("job"));

			peopleService.addFavouriteTheme(John.getId(), "animals");
			peopleService.addFavouriteTheme(John.getId(), "cats");
			peopleService.addFavouriteTheme(John.getId(), "memes");
			peopleService.addBannedThemes(John.getId(), "world");
			peopleService.addBannedTheme(Alice.getId(), "job");
			newsService.addThemeToNews(News1.getId(), "worlds");
			newsService.addThemeToNews(News2.getId(), "animals");
			newsService.addThemeToNews(News3.getId(), "animals");
			newsService.addThemeToNews(News3.getId(), "cats");
			newsService.addThemeToNews(News4.getId(), "job");
			newsService.addThemeToNews(News5.getId(), "memes");
		};

	}
}
