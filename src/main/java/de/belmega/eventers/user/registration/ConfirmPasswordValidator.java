package de.belmega.eventers.user.registration;

import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        String confirm = (String) component.getAttributes().get("confirmPassword");

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirm)) {
            return; // Just ignore and let required="true" do its job.
        }

        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage("Die eingegebenen Passwörter stimmen nicht überein."));
        }
    }
}
