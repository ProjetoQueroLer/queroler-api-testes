package utils;

public class EndPoints {

    public static final String BASE_URI_LOCAL = "http://localhost:8080/";
    public static final String BASE_URI_AMBIENTE = "http://queroler-tst.duckdns.org:8080/";

    public static final String BASE_URI =
            "netlify".equalsIgnoreCase(System.getProperty("ambiente"))
                    ? BASE_URI_AMBIENTE
                    : BASE_URI_LOCAL;

    public static final String USUARIOS = "usuarios";
    public static final String USUARIOS_FOTO = "usuarios/foto";
    public static final String USUARIOS_DADOS_ADICIONAIS = "usuarios/dados-adicionais";
    public static final String USUARIOS_ALTERAR_SENHA = "usuarios/alterar-senha";
    public static final String USUARIOS_ID_COMENTARIOS = "usuarios/{id}/comentarios";
    public static final String LOGINS = "logins";
    public static final String LIVROS = "livros";
    public static final String LIVROS_ISBN = "livros/buscar/{isbn}";
    public static final String LIVROS_ID_CAPA = "livros/{id}/capa";
    public static final String LEITURAS = "leituras";
    public static final String LEITURAS_LIVROID = "leituras/{livroId}";
    public static final String LEITURAS_DIARIOID_COMENTARIO = "leituras/{diarioId}/comentarios";
    public static final String DIARIO = "diario";


}
