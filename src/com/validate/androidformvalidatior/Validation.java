package com.validate.androidformvalidatior;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.validate.androidfromvalidator.annotation.Checked;
import com.validate.androidfromvalidator.annotation.ConfirmPassword;
import com.validate.androidfromvalidator.annotation.Email;
import com.validate.androidfromvalidator.annotation.GroupCheck;
import com.validate.androidfromvalidator.annotation.IPAddress;
import com.validate.androidfromvalidator.annotation.NotEmpty;
import com.validate.androidfromvalidator.annotation.Password;
import com.validate.androidfromvalidator.annotation.RadioButtonCheck;
import com.validate.androidfromvalidator.annotation.Url;


public class Validation {
	Activity mActivity;
	boolean isFlag = false;
	public boolean validate(Activity activity)
	{
		mActivity = activity;
		Class cl = activity.getClass();
		for(Field f: cl.getDeclaredFields())
		{
			for (Annotation a : f.getAnnotations()) {
				if(a.annotationType() == NotEmpty.class){
					NotEmpty valid = (NotEmpty)a;
					isFlag = nonEmpty(valid.id(), valid.message());
				}
				else if(a.annotationType() == Email.class)
				{
					Email valid = (Email)a;
					isFlag = emailTest(valid.id(), valid.message());
				}
				else if(a.annotationType() == Password.class)
				{
					Password valid = (Password)a;
					isFlag = passwordTest(valid.id(),valid.maxLength(),valid.message());
				}
				else if(a.annotationType() == ConfirmPassword.class)
				{
					ConfirmPassword valid = (ConfirmPassword)a;
					isFlag = confirmPasswordTest(valid.firstID(), valid.secondID(), valid.message());
				}
				else if(a.annotationType() == Checked.class)
				{
					Checked valid = (Checked)a;
					isFlag = validateCheckBox(valid.id(),valid.message());
				}
				else if(a.annotationType() == GroupCheck.class)
				{
					GroupCheck valid = (GroupCheck)a;
					isFlag = validateRadioGroup(valid.id(),valid.message());
				}
				else if(a.annotationType() == RadioButtonCheck.class)
				{
					RadioButtonCheck valid = (RadioButtonCheck)a;
					isFlag = validateRadioButton(valid.id(),valid.message());
				}
				else if(a.annotationType() == Url.class)
				{
					Url valid = (Url)a;
					isFlag = urlTest(valid.id(),valid.message());
				}
				else if(a.annotationType() == IPAddress.class)
				{
					IPAddress valid = (IPAddress)a;
					isFlag = ipAddrTest(valid.id(),valid.message());
				}
			}
		}
		return isFlag;
	}
	
	private boolean nonEmpty(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
			return false;
		}
		else
			return true;
	}
	
	private boolean emailTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString()))
				
		{
			et.setError(message);
			return false;
		}
		else if(!Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches())
		{
			et.setError(message);
			return false;
		
		}
		else
			return true;
	}
	
	private boolean passwordTest(int id,int length,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString()))
		{
			et.setError(message);
			return false;
		}
		else if(et.getText().toString().length() < length)
		{
			et.setError(message);
			return false;
		}
		else
			return true;
	}
	
	private boolean confirmPasswordTest(int firstId, int secondID,String message)
	{
		EditText firstEt = (EditText) mActivity.findViewById(firstId);
		EditText secondEt = (EditText) mActivity.findViewById(secondID);
		if(firstEt.getText().toString().equals(secondEt.getText().toString()))
		{
			secondEt.setError(message);
			return false;
		}
		else
			return true;
	}
	
	private boolean validateCheckBox(int id,String message)
	{
		CheckBox checkBox = (CheckBox) mActivity.findViewById(id);
		if(checkBox.isChecked()){
			onValidate(true, message);
			return true;
		}
		else
			return false;
			
	}
	
	private boolean validateRadioGroup(int id,String message)
	{
		RadioGroup radioGroup = (RadioGroup) mActivity.findViewById(id);
		if(radioGroup.getCheckedRadioButtonId() != -1){
			onValidate(true, message);
			return true;
		}
		else
			return false;
			
	}
	
	private boolean validateRadioButton(int id,String message)
	{
		RadioButton radioButton = (RadioButton) mActivity.findViewById(id);
		if(radioButton.isChecked()){
			onValidate(true, message);
			return true;
		}
		else
			return false;
	}
	
	private boolean urlTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
			return false;
		}
		else if(!Patterns.WEB_URL.matcher(message).matches())
		{
			et.setError(message);
			return false;
		}
		else
			return true;
	}
	
	private boolean ipAddrTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
			return false;
		}
		else if(!Patterns.IP_ADDRESS.matcher(message).matches())
		{
			et.setError(message);
			return false;
		}
		else
			return true;
	}
	
	protected void onValidate(boolean answer,String message)
	{
		
	}

}
