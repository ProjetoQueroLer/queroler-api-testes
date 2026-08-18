package utils;

import net.datafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DataFakerUtils {

    public static final Faker faker = new Faker(new Locale("pt", "BR"));

    public static String nome() {
        return faker.name().fullName();
    }

    public static String nomeMais80Caracteres() {
        return faker.regexify("[a-zA-Z]{81}");
    }

    public static String caracteresAleatorios(String numero) {
        return faker.regexify("[a-zA-Z]{"+numero+"}");
    }

    public static String caracteresComQuantidade(int valor) {
        return faker.lorem().characters(valor);
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String emailMais256Caracteres(String numero) {
        return faker.regexify("[a-zA-Z0-9]{"+numero+"}") + "@teste.com";
    }

    public static String caracteres(int numero) {
        StringBuilder aleatorio = new StringBuilder();
        aleatorio.append(faker.regexify("[a-z]"));
        aleatorio.append(faker.regexify("[A-Z]"));
        aleatorio.append(faker.regexify("[0-9]"));
        aleatorio.append(faker.regexify("[!@#$&]"));
        if (numero > 4) {
            aleatorio.append(faker.regexify("[a-zA-Z0-9!@#$&]{" + (numero - 4) + "}"));
        }
        return aleatorio.toString();
    }

    public static String cpf() {
        return faker.cpf().valid();
    }

    public static String dataNascimento() {
        return faker.timeAndDate().birthday("dd/MM/yyyy");
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

    public static String senhaAleatoria() {
        String maiuscula = faker.regexify("[A-Z]");
        String minuscula = faker.regexify("[a-z]");
        String numero = faker.regexify("[0-9]");
        String especial = faker.regexify("[@#$%&*!]");
        String restantes = faker.regexify("[A-Za-z0-9@#$%&*!]{4}");

        return maiuscula + minuscula + numero + especial + restantes;
    }

    public static String nomeAleatorio() {
        return faker.letterify("??????????");
    }

    public static String titulo() {
        return faker.book().title();
    }

    public static String isbn13() {
        return faker.number().digits(13);
    }

    public static String isbn10() {
        return faker.number().digits(10);
    }

    public static String editora() {
        return faker.book().publisher();
    }

    public static String ano() {
        return String.valueOf(faker.number().numberBetween(1900, 2026));
    }

    public static int numeroDePagina() {
        return faker.number().numberBetween(50, 1000);
    }

    public static String idioma() {
        return faker.options().option(
                "PORTUGUES",
                "INGLES",
                "ESPANHOL",
                "FRANCES",
                "ALEMAO",
                "ITALIANO",
                "JAPONES"
        );
    }

    public static String sinopse() {
        String sinopse;
        do {
            sinopse = faker.lorem().paragraph();
        } while (sinopse.length() < 50);
        return sinopse;
    }

    public static String dataInicio() {
        Date date = faker.date().past(30, TimeUnit.DAYS);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return formato.format(date);
    }

    public static String dataTermino() {
        Date date = faker.date().past(5, TimeUnit.DAYS);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return formato.format(date);
    }

    public static String dataFormatoInvalido() {
        Date date = faker.date().birthday(18,100);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return formato.format(date);
    }

    public static double nota() {
        return faker.number().randomDouble(1,1,5);
    }

    public static int paginasLidas() {
        return faker.number().numberBetween(1, 1000);
    }

    public static String tituloResenha() {
        return faker.lorem().characters(1, 250);
    }

    public static String resenha() {
        return faker.lorem().characters(100, 300);
    }

    public static boolean spoiler() {
        return faker.bool().bool();
    }

    public static int paginaInicial() {
        return faker.number().numberBetween(1, 500);
    }

    public static int paginaFinal(int paginaInicial) {
        return faker.number().numberBetween(paginaInicial + 1, 1000);
    }

    public static String comentario() {
        return faker.lorem().sentence();
    }
}