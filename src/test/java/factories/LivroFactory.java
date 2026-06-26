package factories;

import models.AutorModel;
import models.LivroModel;
import utils.DataFakerUtils;

import java.util.List;

public class LivroFactory {

    public static LivroModel criarLivroIsbn13() {

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

    public static LivroModel criarLivroIsbn10() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn10());
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

    public static LivroModel criarLivroIsbn(String isbn) {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(isbn);
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

    public static LivroModel criarLivroIsbnInvalido() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn("999999999999999");
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

    public static LivroModel criarLivroIsbnComLetra() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.nomeAleatorio());
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

    public static LivroModel criarLivroTituloVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo("");
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

    public static LivroModel criarLivroEditoraVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora("");
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroIsbnVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn("");
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

    public static LivroModel criarLivroAnoPublicacaoVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao("");
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroPaginasVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(null);
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroPaginasNumeroNegativoEZero(Integer numero) {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(numero);
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroIdiomaVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma("");
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroSinopseVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse("");
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroSinopseAleotorio(int sinopse) {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.caracteres(sinopse));
        AutorModel autor = new AutorModel();
        autor.setNome(DataFakerUtils.nome());
        livro.setAutores(List.of(autor));
        return livro;
    }

    public static LivroModel criarLivroAutorVazio() {

        LivroModel livro = new LivroModel();

        livro.setTitulo(DataFakerUtils.titulo());
        livro.setIsbn(DataFakerUtils.isbn13());
        livro.setEditora(DataFakerUtils.editora());
        livro.setAnoDePublicacao(DataFakerUtils.ano());
        livro.setNumeroDePaginas(DataFakerUtils.numeroDePagina());
        livro.setIdioma(DataFakerUtils.idioma());
        livro.setSinopse(DataFakerUtils.sinopse());
        AutorModel autor = new AutorModel();
        autor.setNome("");
        livro.setAutores(List.of(autor));
        return livro;
    }

}
