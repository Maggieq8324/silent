package pms.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import javafx.util.converter.DefaultStringConverter;

public class DefaultWebBindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DefaultDateEditor());
		binder.registerCustomEditor(String.class, new DefaultStringEditor());
	}

}
