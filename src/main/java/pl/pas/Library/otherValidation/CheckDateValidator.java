package pl.pas.Library.otherValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

    public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {

        private String pattern;

        @Override
        public void initialize(CheckDateFormat constraintAnnotation) {
            this.pattern = constraintAnnotation.pattern();
        }

        @Override
        public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
            if ( object == null ) {
                return true;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate date = LocalDate.parse(object);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

