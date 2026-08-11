import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;

public class TestHikari {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("schema", "my_schema");
        props.setProperty("jdbcUrl", "jdbc:postgresql://localhost/test");
        props.setProperty("dataSource.user", "user");
        props.setProperty("dataSource.password", "pass");
        
        HikariConfig config = new HikariConfig(props);
        System.out.println("Schema from config: " + config.getSchema());
    }
}
