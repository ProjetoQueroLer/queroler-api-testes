package utils;

import clients.MetaClient;
import io.restassured.response.Response;
import models.MetaModel;

public class MetaHelper {

    public static Response adicionarMeta(String token, MetaModel meta) {
        return MetaClient.adicionarMeta(token, meta);
    }

    public static Response adicionarLivroMeta(String token, int livroId) {
        return MetaClient.adicionarLivroMeta(token, livroId);
    }

    public static Response atualizarMeta(String token, MetaModel meta) {
        return MetaClient.editarMeta(token, meta);
    }

    public static Response deletarMeta(String token) {
        return MetaClient.deletarMeta(token);
    }

    public static Response buscarMeta(String token) {
        return MetaClient.buscarMeta(token);
    }

}
