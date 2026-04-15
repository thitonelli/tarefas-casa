package com.tarefas.dto;

import com.tarefas.model.Frequencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TarefaDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A frequência é obrigatória")
    private Frequencia frequencia;

    private LocalTime horario;

    private LocalTime prazo;

    private List<Integer> diasSemana;

    private LocalDate dataEspecifica;

    private boolean concluida;
}
