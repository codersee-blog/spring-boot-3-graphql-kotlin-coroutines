package com.codersee.graphql

import graphql.ErrorType
import graphql.GraphQLError
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler {

    @GraphQlExceptionHandler
    fun handleGenericNotFound(ex: GenericNotFound): GraphQLError =
        GraphQLError.newError()
            .errorType(ErrorType.DataFetchingException)
            .message(ex.msg)
            .build()
}