package com.tarefas.controller;

import com.tarefas.dto.TarefaDTO;
import com.tarefas.model.Tarefa;
import com.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    // GET /api/tarefas — todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodas() {
        return service.listarTodas();
    }

    // GET /api/tarefas/hoje — tarefas do dia
    @GetMapping("/hoje")
    public List<Tarefa> listarHoje() {
        return service.listarHoje();
    }

    // GET /api/tarefas/semanais — tarefas semanais
    @GetMapping("/semanais")
    public List<Tarefa> listarSemanais() {
        return service.listarSemanais();
    }

    // GET /api/tarefas/datas — tarefas com data específica
    @GetMapping("/datas")
    public List<Tarefa> listarDataEspecifica() {
        return service.listarDataEspecifica();
    }

    // GET /api/tarefas/data?dia=2026-04-18 — tarefas de uma data específica
    @GetMapping("/data")
    public List<Tarefa> listarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {
        return service.listarPorData(dia);
    }

    // GET /api/tarefas/{id} — buscar uma tarefa
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /api/tarefas — criar tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody TarefaDTO dto) {
        Tarefa criada = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    // PUT /api/tarefas/{id} — atualizar tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id,
                                             @Valid @RequestBody TarefaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // PATCH /api/tarefas/{id}/concluir — marcar/desmarcar como concluída
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(service.alternarConclusao(id));
    }

    // DELETE /api/tarefas/{id} — deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Handler de erros
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", e.getMessage()));
    }
}
