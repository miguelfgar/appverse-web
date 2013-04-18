package org.appverse.web.framework.backend.frontfacade.json.controllers.exceptions;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {
    private static final long serialVersionUID = 1L;
    private List<String> errors;
 
    public BadRequestException(String... errors)
    {
        this(Arrays.asList(errors));
    }
 
    public BadRequestException(List<String> errors)
    {
        super(Response.status(Status.BAD_REQUEST)/*.type(MediaType.APPLICATION_XHTML_XML)*/
                .entity(new GenericEntity<List<String>>(errors)
                {}).build());
        this.errors = errors;
    }
 
    public List<String> getErrors()
    {
        return errors;
    }	
}
