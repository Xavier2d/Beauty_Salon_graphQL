package ws.beauty.salon.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import graphql.schema.DataFetchingEnvironment;
@SuppressWarnings("null")
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

        // Manejo de NOT FOUND
        if (ex instanceof EntityNotFoundException) {
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .errorType(graphql.ErrorType.DataFetchingException)
                    .build();
        }

        // Manejo de BAD REQUEST
        if (ex instanceof IllegalArgumentException) {
            return GraphqlErrorBuilder.newError()
                    .message("Petición inválida: " + ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .errorType(graphql.ErrorType.ValidationError)
                    .build();
        }

        // Regresar null permite a Spring manejar otras excepciones
        return null;
    }
}
