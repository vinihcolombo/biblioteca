package com.elotech.biblioteca.services;

import com.elotech.biblioteca.enums.EmprestimoStatus;
import com.elotech.biblioteca.models.EmprestimoModel;
import com.elotech.biblioteca.models.LivroModel;
import com.elotech.biblioteca.models.UsuarioModel;
import com.elotech.biblioteca.repositories.EmprestimoRepository;
import com.elotech.biblioteca.repositories.LivroRepository;
import com.elotech.biblioteca.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;  // ← IMPORTANTE!
    private final LivroRepository livroRepository;      // ← IMPORTANTE!

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public List<EmprestimoModel> buscarTodosOsEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public EmprestimoModel criarEmprestimo(EmprestimoModel emprestimoModel) {
        // 1. Valida se tem usuário
        if (emprestimoModel.getUsuario() == null || emprestimoModel.getUsuario().getId() == null) {
            throw new RuntimeException("Usuário não informado!");
        }

        // 2. Busca o usuário completo no banco
        UsuarioModel usuario = usuarioRepository.findById(emprestimoModel.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 3. Valida se tem livro
        if (emprestimoModel.getLivro() == null || emprestimoModel.getLivro().getId() == null) {
            throw new RuntimeException("Livro não informado!");
        }

        // 4. Busca o livro completo no banco
        LivroModel livro = livroRepository.findById(emprestimoModel.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));

        // 5. Seta os objetos COMPLETOS na entidade
        emprestimoModel.setUsuario(usuario);  // ← Agora com todos os dados
        emprestimoModel.setLivro(livro);      // ← Agora com todos os dados

        // 6. Define valores padrão se não foram enviados
        if (emprestimoModel.getDataEmprestimo() == null) {
            emprestimoModel.setDataEmprestimo(LocalDate.now());
        }

        if (emprestimoModel.getDataDevolucao() == null) {
            emprestimoModel.setDataDevolucao(LocalDate.now().plusDays(7));
        }

        if (emprestimoModel.getStatus() == null) {
            emprestimoModel.setStatus(EmprestimoStatus.ATIVO);
        }

        // 7. Salva
        return emprestimoRepository.save(emprestimoModel);
    }

    public EmprestimoModel buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado!"));
    }

    public EmprestimoModel atualizarEmprestimo(Long id, EmprestimoModel emprestimoModel) {
        EmprestimoModel model = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado!"));

        if (emprestimoModel.getDataDevolucao() != null) {
            model.setDataDevolucao(emprestimoModel.getDataDevolucao());
        }

        if (emprestimoModel.getStatus() != null) {
            model.setStatus(emprestimoModel.getStatus());
        }

        return emprestimoRepository.save(model);
    }

    public void excluirEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }
}