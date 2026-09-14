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
import report.Setup;
import utils.DataFakerUtils;
import utils.LivroHelper;
import utils.UsuarioHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class DiarioAtualizarTest extends BaseTest {

    @Test
    public void atualizarDiarioLivroId() throws IOException {
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
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);
        int diarioId = responseDiario.jsonPath().getInt("id");
        System.out.println("DIARIO: " + diarioModel);
        DiarioClient.deletarDiarioPorLivro(token, diarioId);

        diarioModel.setInicioDaLeitura(DataFakerUtils.dataInicio());
        diarioModel.setTerminoDaLeitura(DataFakerUtils.dataAtual());
        diarioModel.setPaginasLidas(livro.getNumeroDePaginas()-1);
        diarioModel.setNota(DataFakerUtils.nota());
        diarioModel.setTituloDaResenha(DataFakerUtils.tituloResenha());
        diarioModel.setResenha(DataFakerUtils.resenha());

        Response responseDiarioAtualizar = DiarioClient.criarDiario(token, diarioModel);
        responseDiarioAtualizar
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Transição inválida, para o estado atual somente a transições relendo pode ser realizada"));
    }

    @Test
    public void atualizarDiarioLivroIdInvalido() throws IOException {
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
        Response responseDiario = DiarioClient.criarDiario(token, diarioModel);
        responseDiario
                .then()
                .log().body()
                .statusCode(201);
        System.out.println("DIARIO: " + diarioModel);
        diarioModel.setInicioDaLeitura(DataFakerUtils.dataInicio());
        diarioModel.setTerminoDaLeitura(DataFakerUtils.dataAtual());
        diarioModel.setPaginasLidas(livro.getNumeroDePaginas()-1);
        diarioModel.setNota(DataFakerUtils.nota());
        diarioModel.setTituloDaResenha(DataFakerUtils.tituloResenha());
        diarioModel.setResenha(DataFakerUtils.resenha());

        Response responseDiarioAtualizar = DiarioClient.criarDiario(token, diarioModel);
        responseDiarioAtualizar
                .then()
                .log().body()
                .statusCode(409)
                .body(equalTo("Transição inválida, para o estado atual somente a transições relendo pode ser realizada"));
    }

}
