

import com.mysql.cj.jdbc.StatementImpl;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(email, username, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE users.id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET email = ? , username = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE users.id = ?";


    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User create(User user) {
        try (Connection conn = DBUtils.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User[] findAll() {
        User user = null;
        User[] users = new User[100];
        try (Connection conn = DBUtils.getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while(rs.next()){
               user = new User();
               user.setUsername(rs.getString("username"));
               user.setEmail(rs.getString("email"));
               user.setPassword(rs.getString("password"));

               if(rs.getInt("id") <= 100){
                   users[rs.getInt("id") - 1] = user;
               }else {
                   users = Arrays.copyOf(users, users.length + 1);
                   users[users.length - 1] = user;
               }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }



    public User read(int id) {
        User user = null;
        try (Connection conn = DBUtils.getConnection()) {
            user = new User();

            PreparedStatement pstmt = conn.prepareStatement(READ_USER_QUERY);
            pstmt.setString(1,String.valueOf(id));

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public void update(User user){
        try (Connection conn = DBUtils.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setString(4, String.valueOf(user.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        try(Connection conn = DBUtils.getConnection()){
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setString(1,String.valueOf(id));
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
