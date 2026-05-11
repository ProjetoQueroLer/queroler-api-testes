package utlis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties prop = new Properties();

    static {
        try {
            InputStream input = ConfigProperties.class
                    .getClassLoader()
                    .getResourceAsStream("dados.properties");

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

}
