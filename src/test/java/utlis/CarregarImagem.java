package utlis;

import java.io.InputStream;
import java.util.Base64;

public class CarregarImagem {

    public static String carregarImagemBase64() {

        try (InputStream is = CarregarImagem.class
                .getClassLoader()
                .getResourceAsStream("teste.jpg")) {

            if (is == null) {
                throw new RuntimeException("Imagem não encontrada");
            }

            byte[] bytes = is.readAllBytes();

            return Base64.getEncoder().encodeToString(bytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
