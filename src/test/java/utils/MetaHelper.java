package utils;

import clients.MetaClient;
import io.restassured.response.Response;
import models.MetaModel;

public class MetaHelper {

    public static Response adicionarMeta(String token, MetaModel meta) {
        return MetaClient.adicionarMeta(token, meta);
    }

    public static Response atualizarMeta(String token, MetaModel meta) {
        return MetaClient.editarMeta(token, meta);
    }

    public static Response deletarMeta(String token) {
        return MetaClient.deletarMeta(token);
    }

}
