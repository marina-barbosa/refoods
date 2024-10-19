package com.projeto.ReFood.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Exceção personalizada para CNPJ já cadastrado
  public static class CnpjAlreadyExistsException extends RuntimeException {
    public CnpjAlreadyExistsException() {
      super();
    }
  }

  @ExceptionHandler(CnpjAlreadyExistsException.class)
  public ResponseEntity<CustomError> handleCnpjAlreadyExists(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, "CNPJ já cadastrado", request);
  }

  // Exceção personalizada para CPF já cadastrado
  public static class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException() {
      super();
    }
  }

  @ExceptionHandler(CpfAlreadyExistsException.class)
  public ResponseEntity<CustomError> handleCpfAlreadyExists(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, "CPF já cadastrado", request);
  }

  // Exceção personalizada para Email já cadastrado
  public static class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
      super();
    }
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<CustomError> handleEmailAlreadyExists(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, "Email já cadastrado", request);
  }

  // Exceção personalizada para recurso não encontrado
  public static class NotFoundException extends RuntimeException {
    public NotFoundException() {
      super();
    }
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomError> handleNotFound(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.NOT_FOUND, "Recurso não encontrado", request);
  }

  // Exceção personalizada para erro interno do servidor
  public static class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
      super();
    }
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<CustomError> handleInternalServerError(HttpServletRequest request,
      InternalServerErrorException ex) {
    String errorMessage = "Erro interno do servidor: " + ex.getMessage();
    return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, request);
  }

  // Exceção personalizada para acesso negado (forbidden)
  public static class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
      super();
    }
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<CustomError> handleForbidden(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.FORBIDDEN, "Acesso negado", request);
  }

  // Exceção personalizada para credenciais inválidas
  public static class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
      super();
    }
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<CustomError> handleBadCredentials(HttpServletRequest request) {
    return createErrorResponse(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", request);
  }

  // Exceção personalizada para erro no banco de dados
  public static class DatabaseException extends RuntimeException {
    public DatabaseException() {
      super();
    }
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<CustomError> handleDatabase(DatabaseException ex, HttpServletRequest request) {
    return createErrorResponse(HttpStatus.BAD_REQUEST, "Erro no banco de dados", request);
  }

  // Handler para erros de validação
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError validationError = new ValidationError(Instant.now(), status.value(), "Dados inválidos",
        request.getRequestURI());

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(status).body(validationError);
  }

  // Método auxiliar para criar resposta de erro
  private ResponseEntity<CustomError> createErrorResponse(HttpStatus status, String message,
      HttpServletRequest request) {
    CustomError error = new CustomError(Instant.now(), status.value(), message, request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  public record CustomError(Instant timestamp, Integer status, String error, String path) {
  }

  public record FieldMessage(String fieldName, String message) {
  }

  public record ValidationError(
      Instant timestamp,
      Integer status,
      String error,
      String path,
      List<FieldMessage> errors) {

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
      this(timestamp, status, error, path, new ArrayList<>());
    }

    public void addError(String fieldName, String message) {
      errors.add(new FieldMessage(fieldName, message));
    }

    public void addErrors(List<FieldMessage> fieldMessages) {
      errors.addAll(fieldMessages);
    }
  }
}
