package queroLerTests.metas;

import baseTest.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import factories.LivroFactory;
import factories.MetaFactory;
import io.restassured.response.Response;
import models.LivroModel;
import models.MetaModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import queroLerTests.usuarios.UsuarioCadastrarTest;
import report.Setup;
import utils.DataFakerUtils;
import utils.LivroHelper;
import utils.MetaHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class MetaCriarTest extends BaseTest {

    @Test
    public void adicionarMetaLeitor() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void adicionarMetaAdministrador() {
        String token = UsuarioHelper.loginAdministrador();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void adicionarMetaModerador() {
        String token = UsuarioHelper.loginModerador();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void adicionarMetaLeitorComLivro() throws IOException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .log().body()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

        MetaHelper.adicionarLivroMeta(token, livroId)
                .then()
                .log().body()
                .statusCode(204);

    }

    @Test
    public void adicionarMetaAnoJaExistente() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setAno(DataFakerUtils.anoAtual());

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(201);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Já há meta cadastrada para o ano de: " + metaLeitura.getAno()+"."));

    }

    @Test
    public void adicionarMetaAnteriorAnoAtual() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setAno(DataFakerUtils.anoAtual()-1);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("O ano informado não pode ser anterior ao corrente."));

    }

    @ParameterizedTest
    @ValueSource(ints = {
            20266,
            123456789,
            987654321
    })
    public void adicionarMetaAnoInvalido(int ano) throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setAno(ano);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(400)
//                .body(equalTo("O ano informado não pode ser anterior ao corrente."))
        ;

    }

    @Test
    public void adicionarMetaValorNegativoLivroAno() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setMetaLivrosAno(-1);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(400)
//                .body(equalTo("O ano informado não pode ser anterior ao corrente."))
        ;
    }

    @Test
    public void adicionarMetaValorNegativoLivroMes() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setMetaLivrosMes(-1);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(400)
//                .body(equalTo("O ano informado não pode ser anterior ao corrente."))
        ;
    }

    @Test
    public void adicionarMetaValorNegativoPaginasDia() throws JsonProcessingException {
        String token = UsuarioCadastrarTest.cadastrarUsuarioToken();

        MetaModel metaLeitura = MetaFactory.adicionarMetaValida();
        metaLeitura.setMetaPaginasDia(-1);

        MetaHelper.adicionarMeta(token, metaLeitura)
                .then()
                .log().body()
                .statusCode(400)
//                .body(equalTo("O ano informado não pode ser anterior ao corrente."))
        ;
    }

}
