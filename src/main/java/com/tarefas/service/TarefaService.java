package com.tarefas.service;

import com.tarefas.dto.TarefaDTO;
import com.tarefas.model.Frequencia;
import com.tarefas.model.Tarefa;
import com.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TarefaService {

    private final TarefaRepository repo;

    public TarefaService(TarefaRepository repo) {
        this.repo = repo;
    }

    // Listar todas as tarefas
    public List<Tarefa> listarTodas() {
        return repo.findAll();
    }

    // Listar tarefas do dia de hoje
    public List<Tarefa> listarHoje() {
        LocalDate hoje = LocalDate.now();
        int diaSemana = hoje.getDayOfWeek().getValue() % 7; // 0=Dom, 1=Seg, ..., 6=Sáb

        return repo.findAll().stream()
                .filter(t -> !t.isConcluida())
                .filter(t -> switch (t.getFrequencia()) {
                    case DIARIA -> true;
                    case SEMANAL -> t.getDiasSemana() != null && t.getDiasSemana().contains(diaSemana);
                    case DATA_ESPECIFICA -> hoje.equals(t.getDataEspecifica());
                    case ESPORADICA -> true;
                })
                .toList();
    }

    // Listar tarefas da semana (semanais)
    public List<Tarefa> listarSemanais() {
        return repo.findByFrequenciaAndConcluida(Frequencia.SEMANAL, false);
    }

    // Listar tarefas por data específica
    public List<Tarefa> listarPorData(LocalDate data) {
        return repo.findByDataEspecifica(data);
    }

    // Listar todas as tarefas com data específica
    public List<Tarefa> listarDataEspecifica() {
        return repo.findByFrequencia(Frequencia.DATA_ESPECIFICA)
                .stream()
                .sorted((a, b) -> {
                    if (a.getDataEspecifica() == null) return 1;
                    if (b.getDataEspecifica() == null) return -1;
                    return a.getDataEspecifica().compareTo(b.getDataEspecifica());
                })
                .toList();
    }

    // Buscar por ID
    public Tarefa buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarefa não encontrada: " + id));
    }

    // Criar nova tarefa
    public Tarefa criar(TarefaDTO dto) {
        validarDTO(dto);
        Tarefa tarefa = new Tarefa();
        preencherTarefa(tarefa, dto);
        return repo.save(tarefa);
    }

    // Atualizar tarefa existente
    public Tarefa atualizar(Long id, TarefaDTO dto) {
        Tarefa tarefa = buscarPorId(id);
        validarDTO(dto);
        preencherTarefa(tarefa, dto);
        return repo.save(tarefa);
    }

    // Marcar como concluída / não concluída
    public Tarefa alternarConclusao(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setConcluida(!tarefa.isConcluida());
        tarefa.setDataConclusao(tarefa.isConcluida() ? LocalDate.now() : null);
        return repo.save(tarefa);
    }

    // Deletar tarefa
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Tarefa não encontrada: " + id);
        }
        repo.deleteById(id);
    }

    // ---- helpers ----

    private void preencherTarefa(Tarefa tarefa, TarefaDTO dto) {
        tarefa.setNome(dto.getNome());
        tarefa.setFrequencia(dto.getFrequencia());
        tarefa.setHorario(dto.getHorario());
        tarefa.setPrazo(dto.getPrazo());
        tarefa.setDiasSemana(dto.getDiasSemana());
        tarefa.setDataEspecifica(dto.getDataEspecifica());
        tarefa.setConcluida(dto.isConcluida());
        if (!dto.isConcluida()) {
            tarefa.setDataConclusao(null);
        }
    }

    private void validarDTO(TarefaDTO dto) {
        if (dto.getFrequencia() == Frequencia.SEMANAL
                && (dto.getDiasSemana() == null || dto.getDiasSemana().isEmpty())) {
            throw new IllegalArgumentException("Para tarefas semanais, informe pelo menos um dia da semana.");
        }
        if (dto.getFrequencia() == Frequencia.DATA_ESPECIFICA && dto.getDataEspecifica() == null) {
            throw new IllegalArgumentException("Para tarefas com data específica, informe a data.");
        }
    }
}
