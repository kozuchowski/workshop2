import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

//        dodawanie użytkownika,
//        zmiana danych,
//        pobieranie po id,
//        usuwanie po id,
//        pobieranie wszystkich użytkowników.

public class WorkshopMain {

    public static User[] users = new User[100];
    public static void main(String[] args) throws ClassNotFoundException {
        User user1 = new User();
        user1.setEmail("ziok@wp.pl");
        user1.setUsername("Zofian");
        user1.setPassword("asdfas");

     UserDao ud = new UserDao();

      users[ud.read(1).getId() - 1] = ud.read(1);


        User user2 = ud.read(2);
        user2.setUsername("nowiutki");
        user2.setEmail("nowiutenki@mail.com");
        user2.setPassword("advfg");


        ud.delete(2);



    }
}
