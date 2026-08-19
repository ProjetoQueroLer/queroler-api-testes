package factories;

import models.AutorModel;
import models.LivroModel;
import utils.DataFakerUtils;

import java.util.List;

public class LivroFactory {

    private static LivroModel criarLivroBase() {
        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());

        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));

        return livro;
    }

    public static LivroModel criarLivroIsbn13() {
        return criarLivroBase();
    }

    public static LivroModel criarLivroIsbn10() {
        LivroModel livro = criarLivroBase();
        livro.setIsbn(DataFakerUtils.isbn10());
        return livro;
    }

    public static LivroModel criarLivroIsbnInvalido() {
        LivroModel livro = criarLivroBase();
        livro.setIsbn("999999999999999");
        return livro;
    }

    public static LivroModel criarLivroIsbnComLetra() {
        LivroModel livro = criarLivroBase();
        livro.setIsbn(DataFakerUtils.caracteresComQuantidade(10));
        return livro;
    }

    public static LivroModel criarLivroTituloVazio() {
        LivroModel livro = criarLivroBase();
        livro.setTitulo("");
        return livro;
    }

    public static LivroModel criarLivroEditoraVazio() {
        LivroModel livro = criarLivroBase();
        livro.setEditora("");
        return livro;
    }

    public static LivroModel criarLivroIsbnVazio() {
        LivroModel livro = criarLivroBase();
        livro.setIsbn("");
        return livro;
    }

    public static LivroModel criarLivroAnoPublicacaoVazio() {
        LivroModel livro = criarLivroBase();
        livro.setAnoDePublicacao("");
        return livro;
    }

    public static LivroModel criarLivroPaginasVazio() {
        LivroModel livro = criarLivroBase();
        livro.setNumeroDePaginas(null);
        return livro;
    }

    public static LivroModel criarLivroPaginasNumeroNegativoEZero(Integer numero) {
        LivroModel livro = criarLivroBase();
        livro.setNumeroDePaginas(numero);
        return livro;
    }

    public static LivroModel criarLivroIdiomaVazio() {
        LivroModel livro = criarLivroBase();
        livro.setIdioma("");
        return livro;
    }

    public static LivroModel criarLivroSinopseVazio() {
        LivroModel livro = criarLivroBase();
        livro.setSinopse("");
        return livro;
    }

    public static LivroModel criarLivroSinopseAleotorio(int sinopse) {
        LivroModel livro = criarLivroBase();
        livro.setSinopse(DataFakerUtils.caracteresComQuantidade(sinopse));
        return livro;
    }

    public static LivroModel criarLivroAutorVazio() {
        LivroModel livro = criarLivroBase();
        AutorModel autor = new AutorModel();
        autor.setNome("");
        livro.setAutores(List.of(autor));
        return livro;
    }

}
