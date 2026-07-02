package com.elotech.biblioteca.services;

import com.elotech.biblioteca.models.LivroModel;
import com.elotech.biblioteca.repositories.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    private final LivroRepository livroRepository;

    public List<LivroModel> buscarTodosOsLivros() {
        return livroRepository.findAll();
    }

    public LivroModel criarLivro (LivroModel livroModel){
        System.out.println("=== DADOS RECEBIDOS ===");
        System.out.println("Autor: " + livroModel.getAutor());
        System.out.println("Titulo: " + livroModel.getTitulo());
        System.out.println("ISBN: " + livroModel.getIsbn());
        System.out.println("Data: " + livroModel.getDataPublicacao());
        System.out.println("Categoria: " + livroModel.getCategoria()); // ← Verifique se é FICCAO
        System.out.println("========================");

        return livroRepository.save(livroModel);
    }

    public LivroModel buscarLivroPorId(Long id){
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
    }

    public LivroModel atualizarLivro (Long id, LivroModel livroModel){
        LivroModel model = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
        model.setTitulo(livroModel.getTitulo());
        model.setAutor(livroModel.getAutor());
        model.setDataPublicacao(livroModel.getDataPublicacao());

        return livroRepository.save(model);
    }

    public void excluirLivro (Long id){
        livroRepository.deleteById(id);
    }
}
