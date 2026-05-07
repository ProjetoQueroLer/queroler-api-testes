package utils;

import net.datafaker.Faker;

import java.util.Locale;

public class DataFakerUtils {

    public static final Faker faker = new Faker(new Locale("pt", "BR"));

    public static String nome() {
        return faker.name().fullName();
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String cpf() {
        return faker.cpf().valid();
    }

    public static String dataNascimento() {
        return faker.timeAndDate().birthday("yyyy-MM-dd");
    }

    public static String cidade() {
        return faker.address().city();
    }

    public static String estado() {
        return faker.address().state();
    }

    public static String pais() {
        return faker.address().country();
    }

    public static String foto() {
        return faker.internet().image();
    }


}
