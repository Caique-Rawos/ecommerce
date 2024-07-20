package com.example.ecommerce.shared;

/**
 * Resposta de retorna padrão para dados não paginados
 *
 * @param message Mensagem de retorno para a aplicação que está consumindo a API
 * @param data    Lista de dados paginados
 */
public record DefaultHttpResponse<T>(String message, T data) {
}