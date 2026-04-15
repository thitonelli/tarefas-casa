# 📋 Tarefas App

Gerenciador de tarefas diárias construído com **Java 17 + Spring Boot 3 + PostgreSQL**.
Funciona no navegador de qualquer celular (Android e iPhone).

---

## 🗂 Estrutura do Projeto

```
tarefas-app/
├── src/main/java/com/tarefas/
│   ├── TarefasApplication.java         ← Classe principal
│   ├── model/
│   │   ├── Tarefa.java                 ← Entidade JPA
│   │   └── Frequencia.java             ← Enum: DIARIA, SEMANAL, DATA_ESPECIFICA, ESPORADICA
│   ├── dto/
│   │   └── TarefaDTO.java              ← Dados de entrada da API
│   ├── repository/
│   │   └── TarefaRepository.java       ← Acesso ao banco
│   ├── service/
│   │   └── TarefaService.java          ← Lógica de negócio
│   └── controller/
│       ├── TarefaController.java        ← API REST
│       └── FrontendController.java      ← Serve o HTML
├── src/main/resources/
│   ├── application.properties           ← Configurações
│   └── static/index.html               ← Frontend mobile
├── Dockerfile
└── pom.xml
```

---

## 🚀 Como rodar localmente

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL rodando localmente

### 1. Criar banco de dados
```sql
CREATE DATABASE tarefasdb;
```

### 2. Configurar variáveis (ou editar application.properties)
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/tarefasdb
export DATABASE_USER=postgres
export DATABASE_PASSWORD=sua_senha
```

### 3. Rodar o projeto
```bash
mvn spring-boot:run
```

### 4. Acessar no celular
Abra o navegador em: `http://SEU_IP_LOCAL:8080`

Para descobrir seu IP local: `ipconfig` (Windows) ou `ifconfig` (Mac/Linux)

---

## 🌐 API REST

| Método | Endpoint                      | Descrição                          |
|--------|-------------------------------|------------------------------------|
| GET    | `/api/tarefas`                | Todas as tarefas                   |
| GET    | `/api/tarefas/hoje`           | Tarefas do dia atual               |
| GET    | `/api/tarefas/semanais`       | Tarefas semanais                   |
| GET    | `/api/tarefas/datas`          | Tarefas com data específica        |
| GET    | `/api/tarefas/data?dia=DATE`  | Tarefas de uma data (yyyy-MM-dd)   |
| GET    | `/api/tarefas/{id}`           | Buscar uma tarefa                  |
| POST   | `/api/tarefas`                | Criar nova tarefa                  |
| PUT    | `/api/tarefas/{id}`           | Atualizar tarefa                   |
| PATCH  | `/api/tarefas/{id}/concluir`  | Marcar/desmarcar como concluída    |
| DELETE | `/api/tarefas/{id}`           | Deletar tarefa                     |

### Exemplo de corpo para criar tarefa (POST)
```json
{
  "nome": "Lavar a louça",
  "frequencia": "DIARIA",
  "horario": "08:00",
  "prazo": "12:00",
  "diasSemana": [],
  "dataEspecifica": null,
  "concluida": false
}
```

### Valores de `frequencia`
| Valor              | Descrição                           |
|--------------------|-------------------------------------|
| `DIARIA`           | Aparece todo dia                    |
| `SEMANAL`          | Aparece nos dias indicados em `diasSemana` (0=Dom … 6=Sáb) |
| `DATA_ESPECIFICA`  | Aparece somente na data indicada    |
| `ESPORADICA`       | Aparece sempre (sem padrão fixo)    |

---

## ☁️ Deploy no Railway (gratuito)

1. Crie conta em [railway.app](https://railway.app)
2. Novo projeto → "Deploy from GitHub" → selecione o repositório
3. Adicione um serviço PostgreSQL no mesmo projeto
4. O Railway preenche `DATABASE_URL` automaticamente
5. Acesse pelo link gerado — funciona em qualquer celular!

---

## ☁️ Deploy no Render (alternativa gratuita)

1. Crie conta em [render.com](https://render.com)
2. New → Web Service → conecte seu repositório
3. Build Command: `mvn clean package -DskipTests`
4. Start Command: `java -jar target/tarefas-app-1.0.0.jar`
5. Adicione variáveis de ambiente: `DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`
6. Crie um PostgreSQL database no Render e copie as credenciais

---

## 📱 Dica: Adicionar à tela inicial do celular

**Android (Chrome):** Abra o site → menu (⋮) → "Adicionar à tela inicial"

**iPhone (Safari):** Abra o site → botão compartilhar → "Adicionar à tela de início"

O app vai parecer um aplicativo nativo instalado!
