package queroLerTests.diario;

import baseTest.BaseTest;
import clients.DiarioClient;
import clients.LeituraClient;
import factories.DiarioFactory;
import factories.LeituraStatusFactory;
import factories.LivroFactory;
import io.restassured.response.Response;
import models.DiarioModel;
import models.LeituraStatusModel;
import models.LivroModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.Setup;
import utils.DataFakerUtils;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class DiarioCriarTest extends BaseTest {

    @Test
    public void cadastrarDiarioLivroInexistente() {
        String token = UsuarioHelper.loginLeitor();

        int livroId = -1;

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Usuário/Livro não encontrado na estante."));
    }

    @Test
    public void cadastrarDiarioLivro() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void deveRecusarTerminoLeituraAnteriorAoInicio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.terminoLeituraAnteriorAoInicio(livroId);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("terminoDaLeitura não pode ser anterior a inicioDaLeitura."));
    }

    @Test
    public void deveRecusarInicioDaLeituraDataComFormatoInvalido() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setInicioDaLeitura(DataFakerUtils.dataFormatoInvalido());
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("O campo 'inicioDaLeitura' está com um formato de data inválido. Use o padrão DD/MM/YYYY."));
    }

    @Test
    public void deveRecusarTerminoDaLeituraDataComFormatoInvalido() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setTerminoDaLeitura(DataFakerUtils.dataFormatoInvalido());
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("O campo 'terminoDaLeitura' está com um formato de data inválido. Use o padrão DD/MM/YYYY."));
    }

    @Test
    public void inicioLeituraObrigatorio() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setInicioDaLeitura(null);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("inicioDaLeitura é obrigatório."));
    }

    @Test
    public void paginasLidasValorNegativo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setPaginasLidas(-1);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void paginasLidasValorNulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setPaginasLidas(null);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("{\"paginasLidas\":\"O número de páginas lidas é obrigatório.\"}"));
    }

    @Test
    public void paginasLidasMaiorQueTotalDePaginas() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        LivroModel livro = LivroFactory.criarLivroIsbn13();

        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .statusCode(201);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setPaginasLidas(livro.getNumeroDePaginas()+1);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("O número de páginas lidas não pode ser maior que o total de páginas do livro. Total: ("+livro.getNumeroDePaginas()+")"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -1,
            0,
            6,
    })
    public void notaValorInvalido(double notas) throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setNota(notas);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("nota deve estar entre 0.5 e 5.0."));
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            0.55,
            4.555,
    })
    public void notaValorDecimalInvalido(double notas) throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setNota(notas);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400)
                .body(equalTo("nota deve ser um múltiplo de 0.5."));
    }

    @Test
    public void tituloDaResenhaNulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setTituloDaResenha(null);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void tituloDaResenhaMais250Caracteres() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setTituloDaResenha(DataFakerUtils.caracteresComQuantidade(251));
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void resenhaNulo() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setResenha(null);
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void resenhaMenos100Caracteres() throws IOException {
        String token = UsuarioHelper.loginLeitor();

        int livroId = criarLivroId(token);

        DiarioModel diarioModel = DiarioFactory.criarDiarioLido(livroId);
        diarioModel.setResenha(DataFakerUtils.caracteresComQuantidade(50));
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(400);
    }

    private int criarLivroId(String token) throws IOException {

        LivroModel livro = LivroFactory.criarLivroIsbn13();
        Response responseLivro = LivroHelper.criarLivroCadastrar(token, livro);
        responseLivro
                .then()
                .statusCode(201);

        int livroId = responseLivro.jsonPath().getInt("id");

        LeituraStatusModel leituraStatusModel = LeituraStatusFactory.criarLeituraLivroStatusQueroLer(livroId);
        Response responseLeitura = LeituraClient.criarLeituraStatus(token, leituraStatusModel);
        responseLeitura
                .then()
                .statusCode(201);

        return livroId;
    }
}
