package com.becommerce.exception;

import com.becommerce.exception.base.BaseException;
import com.becommerce.model.ErrorView;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Produces
@Singleton
@Requires(classes = {BaseException.class, ExceptionHandler.class})
public class BaseExceptionHandler implements ExceptionHandler<BaseException, HttpResponse<ErrorView>> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Override
    @Error(global = true, exception = Exception.class)
    public HttpResponse<ErrorView> handle(HttpRequest request, BaseException exception) {
        LOG.error(exception.getMessage());
        LOG.error(exception.getDetailMessage());
        LOG.error(exception.getCode());
        LOG.error(exception.getLocalizedMessage());
        Arrays.stream(exception.getStackTrace()).forEach(item -> LOG.error(item.toString()));

        ErrorView errorView = new ErrorView();
        errorView.setMessage(exception.getMessage());
        errorView.setDetailMessage(exception.getDetailMessage());
        errorView.setCode(exception.getCode());

        return HttpResponse.serverError(errorView).status(exception.getHttpStatus());
    }
}
