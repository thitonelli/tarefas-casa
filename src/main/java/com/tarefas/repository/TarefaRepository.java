package com.tarefas.repository;

import com.tarefas.model.Frequencia;
import com.tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Todas as tarefas de uma frequência específica
    List<Tarefa> findByFrequencia(Frequencia frequencia);

    // Tarefas de uma data específica
    List<Tarefa> findByDataEspecifica(LocalDate data);

    // Tarefas de uma data específica não concluídas
    List<Tarefa> findByDataEspecificaAndConcluida(LocalDate data, boolean concluida);

    // Tarefas não concluídas
    List<Tarefa> findByConcluida(boolean concluida);

    // Tarefas por frequência não concluídas
    List<Tarefa> findByFrequenciaAndConcluida(Frequencia frequencia, boolean concluida);
}
