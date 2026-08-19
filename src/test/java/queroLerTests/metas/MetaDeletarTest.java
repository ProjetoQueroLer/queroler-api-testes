package queroLerTests.metas;

import baseTest.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.Setup;
import utils.MetaHelper;
import utils.UsuarioHelper;

@ExtendWith(Setup.class)
public class MetaDeletarTest extends BaseTest {

    @Test
    public void deletarMeta() {
        String token = UsuarioHelper.loginLeitor();

        MetaHelper.deletarMeta(token)
                .then()
                .log().body()
                .statusCode(204);

    }

}
