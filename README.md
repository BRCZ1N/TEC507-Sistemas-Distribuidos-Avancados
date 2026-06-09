# Relógios Lógicos de Lamport

Implementação do algoritmo de Relógios Lógicos de Lamport em Java 17, utilizando comunicação entre processos distribuídos via sockets TCP.

O sistema simula três processos independentes (P1, P2 e P3), que se comunicam por troca de mensagens e mantêm um relógio lógico para garantir a ordenação causal dos eventos.

---

## 🧠 Objetivo

Demonstrar o funcionamento de relógios lógicos em um sistema distribuído sem relógio global, garantindo a propriedade:

> Se um evento A ocorre antes de B (relação causal), então o relógio lógico de A é menor que o de B.

---

## 📦 Requisitos

- Java 17+
- Maven 3.9+

---

## Compilação

```bash
mvn clean package
```

O executável será gerado em:

```text
target/lamport.jar
```

## Execução

### Windows

```cmd
java -jar target\lamport.jar P1
```

```cmd
java -jar target\lamport.jar P2
```

```cmd
java -jar target\lamport.jar P3
```

### Linux

```bash
java -jar target/lamport.jar P1
```

```bash
java -jar target/lamport.jar P2
```

```bash
java -jar target/lamport.jar P3
```

## Verificando a versão do Java

Windows:

```cmd
java --version
```

Linux:

```bash
java --version
```

O projeto foi desenvolvido utilizando Java 17.
