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
import android.widget.Toast;

import com.validate.androidformvalidatior.annotations.Checked;
import com.validate.androidformvalidatior.annotations.ConfirmPassword;
import com.validate.androidformvalidatior.annotations.Email;
import com.validate.androidformvalidatior.annotations.GroupCheck;
import com.validate.androidformvalidatior.annotations.IPAddress;
import com.validate.androidformvalidatior.annotations.NotEmpty;
import com.validate.androidformvalidatior.annotations.Password;
import com.validate.androidformvalidatior.annotations.RadioButtonCheck;
import com.validate.androidformvalidatior.annotations.Url;


public class Validation {
	Activity mActivity;
    private boolean isValid = true;

	public boolean validate(Activity activity)
	{
        isValid = true;
        mActivity = activity;
		Class cl = activity.getClass();
		for(Field f: cl.getDeclaredFields())
		{
			for (Annotation a : f.getDeclaredAnnotations()) {

				if(a.annotationType() == NotEmpty.class){
					NotEmpty valid = (NotEmpty)a;
					nonEmpty(valid.id(), valid.message());
				}
				else if(a.annotationType() == Email.class)
				{
					Email valid = (Email)a;
				    emailTest(valid.id(), valid.message());
				}
				else if(a.annotationType() == Password.class)
				{
					Password valid = (Password)a;
					passwordTest(valid.id(), valid.maxLength(), valid.message());
				}
				else if(a.annotationType() == ConfirmPassword.class)
				{
					ConfirmPassword valid = (ConfirmPassword)a;
					confirmPasswordTest(valid.firstID(), valid.secondID(), valid.message());
				}
				else if(a.annotationType() == Checked.class)
				{
					Checked valid = (Checked)a;
					validateCheckBox(valid.id(), valid.message());
				}
				else if(a.annotationType() == GroupCheck.class)
				{
					GroupCheck valid = (GroupCheck)a;
					validateRadioGroup(valid.id(), valid.message());
				}
				else if(a.annotationType() == RadioButtonCheck.class)
				{
					RadioButtonCheck valid = (RadioButtonCheck)a;
					validateRadioButton(valid.id(), valid.message());
				}
				else if(a.annotationType() == Url.class)
				{
					Url valid = (Url)a;
					urlTest(valid.id(), valid.message());
				}
				else if(a.annotationType() == IPAddress.class)
				{
					IPAddress valid = (IPAddress)a;
					ipAddrTest(valid.id(), valid.message());
				}
			}
		}
		return isValid;
	}
	
	private void nonEmpty(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
			isValid = false;
        }
	}
	
	private void emailTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString()))
		{
			et.setError(message);
            isValid = false;
		}
		else if(!Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches())
		{
			et.setError(message);
            isValid = false;
		
		}
	}
	
	private void passwordTest(int id,int length,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString()))
		{
			et.setError(message);
            isValid = false;
		}
		else if(et.getText().toString().length() < length)
		{
			et.setError(message);
            isValid = false;
		}

	}
	
	private void confirmPasswordTest(int firstId, int secondID,String message)
	{
		EditText firstEt = (EditText) mActivity.findViewById(firstId);
		EditText secondEt = (EditText) mActivity.findViewById(secondID);
        if(TextUtils.isEmpty(secondEt.getText().toString()))
        {
            secondEt.setError(message);
            isValid = false;
        }
		else if(!firstEt.getText().toString().equals(secondEt.getText().toString()))
		{
			secondEt.setError(message);
            isValid = false;
		}

    }
	
	private void validateCheckBox(int id,String message)
	{
		CheckBox checkBox = (CheckBox) mActivity.findViewById(id);
		if(checkBox.isChecked()){
			onValidate(true, message);
		}
		else{
            Toast.makeText(mActivity,message,Toast.LENGTH_LONG).show();
            isValid = false;
        }
    }
	
	private void validateRadioGroup(int id,String message)
	{
		RadioGroup radioGroup = (RadioGroup) mActivity.findViewById(id);
		if(radioGroup.getCheckedRadioButtonId() != -1){
			onValidate(true, message);
		}
		else
            isValid = false;
			
	}
	
	private void validateRadioButton(int id,String message)
	{
		RadioButton radioButton = (RadioButton) mActivity.findViewById(id);
		if(radioButton.isChecked()){
			onValidate(true, message);
        }
		else {
            Toast.makeText(mActivity,message,Toast.LENGTH_LONG).show();
            isValid = false;

        }
	}
	
	private void urlTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
            isValid = false;
		}
		else if(!Patterns.WEB_URL.matcher(message).matches())
		{
			et.setError(message);
            isValid = false;
		}

	}
	
	private void ipAddrTest(int id,String message)
	{
		EditText et = (EditText) mActivity.findViewById(id);
		if(TextUtils.isEmpty(et.getText().toString())){
			et.setError(message);
            isValid = false;
		}
		else if(!Patterns.IP_ADDRESS.matcher(message).matches())
		{
			et.setError(message);
            isValid = false;
		}
    }
	
	protected void onValidate(boolean answer,String message)
	{
		
	}
}
