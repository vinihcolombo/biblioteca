package com.elotech.biblioteca.services;

import com.elotech.biblioteca.models.EmprestimoModel;
import com.elotech.biblioteca.repositories.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    public EmprestimoService(EmprestimoRepository emprestimoRepository){
        this.emprestimoRepository = emprestimoRepository;
    }

    private final EmprestimoRepository emprestimoRepository;

    public List<EmprestimoModel> buscarTodosOsEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public EmprestimoModel criarEmprestimo (EmprestimoModel emprestimoModel){
        return emprestimoRepository.save(emprestimoModel);
    }

    public EmprestimoModel buscarEmprestimoPorId(Long id){
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("emprestimo não encontrado!"));
    }

    public EmprestimoModel atualizarEmprestimo (Long id, EmprestimoModel emprestimoModel){
        EmprestimoModel model = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("emprestimo não encontrado!"));
        model.setDataDevolucao(emprestimoModel.getDataDevolucao());
        model.setStatus(emprestimoModel.getStatus());

        return emprestimoRepository.save(model);
    }

    public void excluirEmprestimo (Long id){
        emprestimoRepository.deleteById(id);
    }

}
