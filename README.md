# Relógios Lógicos de Lamport

Implementação dos Relógios Lógicos de Lamport utilizando Java 17.

## Requisitos

- Java 17+
- Maven 3.9+

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
