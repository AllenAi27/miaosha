package com.imooc.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.imooc.miaosha.utils.ValidateUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{
	
	private boolean required = false;

	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidateUtil.mobileValidate(value);
		}else {
			if(StringUtils.isBlank(value)) {
				return true;
			}else {
				return ValidateUtil.mobileValidate(value);
			}
		}
	}

}
