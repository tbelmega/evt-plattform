package de.belmega.eventers.jsf.validator;

import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmEmailadressValidator")
public class ConfirmEmailadressValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String emailAdress = (String) value;
        String confirm = (String) component.getAttributes().get("confirmEmailadress");

        if (StringUtils.isEmpty(emailAdress)|| StringUtils.isEmpty(confirm)) {
            return; // Just ignore and let required="true" do its job.
        }

        if (!emailAdress.equals(confirm)) {
            throw new ValidatorException(new FacesMessage("Die eingegebenen Emailadressen stimmen nicht überein."));
        }
    }
}
