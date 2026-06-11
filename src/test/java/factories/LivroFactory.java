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

}
