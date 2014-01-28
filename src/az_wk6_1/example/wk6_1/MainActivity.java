package az_wk6_1.example.wk6_1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// prepend m onto instance variables to indicate that they are member
	// variables
	private EditText mName;
	private EditText mPhone;
	private EditText mEmail;
	private EditText mComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get pointer to view
		// this is pointer to a our name
		// note we have to cast to EditText object
		mName = (EditText) findViewById(R.id.name);
		mPhone = (EditText) findViewById(R.id.phone);
		mEmail = (EditText) findViewById(R.id.email);
		mComment = (EditText) findViewById(R.id.comments);
		
		
		// final MainActivity context = this; 
		
		// example we make the button appear/disappear based on the text in the comments
		// defining the class and creating it at the same time
		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				// we could also use 
				// note that we connect this object to the comment field
				// mComment, so we know arg0 holds whatever is typed in the textbox
				
				String comments = arg0.toString(); // we could use as well mComment.getText(); 
				boolean valid = comments.length() > 0 && comments.toLowerCase().indexOf("duck") == -1; 
				View view = findViewById(R.id.imageButton1);
				
				
				boolean isVisible = view.getVisibility() == View.VISIBLE; 
				
				if(isVisible == valid) { 
					return; 
				}	
				 
				
				// we are currently inside a TextWatcher object
			    // makeInAnimation expects a context (i.e. an activity) 
				// 2 possible fixes
			    // 1. give the name of the activity MainActivity.this
			    // 2. create a variable before the TextWatcher $ final MainActivity context = this; 
			    //    don't recommend. if you do this you're forgetting the TextWatch does have a link
			    //    back to the MainActivity
					
				Animation anim;
				if(valid){ 
					view.setVisibility(View.VISIBLE); 
					anim = AnimationUtils.makeInAnimation(MainActivity.this, true); 
				} else { 
					anim = AnimationUtils.makeOutAnimation(MainActivity.this, true); 
					view.setVisibility(View.INVISIBLE); 
				}
		
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			
			}
		}; 
		
		mComment.addTextChangedListener(watcher); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// takes a pointer to a view
	public void processForm(View submitButton) {

		// with toast we can send either a R.id-- or we can give it a pointer to
		// a string
		// or string literal
		Log.d("MainActivity", "processForm");
		String comment = mComment.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();

		// Initial example. allows app to response will all actions on phone
		// Intent activeIntent = new Intent(Intent.ACTION_SEND); 
		
		// if we want to force a specific mode of response we specify the intent type
		// e.g. we want to force the response to be an sms we use the constant ACTION_VIEW
		
		// instead of setting the type directly we're going to set the data and 
		// pass in a particular URI
		
		
//		Intent activeIntent = new Intent(Intent.ACTION_VIEW); 
//		activeIntent.setType("text/plain"); 
//		activeIntent.putExtra(Intent.EXTRA_TEXT, "What a wonderful app!");
//		startActivity(activeIntent); 

		// send an sms message ... 
//		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//		smsIntent.setData(Uri.parse("sms:" + phone));	//alt: Uri.fromParts("sms", phone,null) 
//		smsIntent.putExtra("sms_body", "I love this app!");  
//		startActivity(smsIntent);
		
		
		// Uri.fromParts: similar to Uri.parse
		// takes 3 parameters
		// type of Uri
		// 
		
		
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "someone@somewhere", null)); 
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "important news"); 
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email, can use variables here"); 
		
		// Handling the case where the phone cannot email 
		// Here we will use the chooser intent where the phone gives the user all options for
		// completing the action that the phone knows are available (e.g. if the phone doesn't have email, use sms)
		
		
		
		// here we pre-emptively handle the case where there is no email client
		// options. don't allow them to complete form is they don't have an email client, don't have butt 
		// 
		//
		if( emailIntent.resolveActivity(getPackageManager()) == null) { 
			Toast.makeText(getApplicationContext(), "configure you email client", Toast.LENGTH_LONG).show(); 
		} else { 
			startActivity( Intent.createChooser(emailIntent, "choose your email app")); 
		}
		
		
		// Present chooser
		// rather than start intent directly, create chooser intent
		// wrap the intent in another intent 
//		Intent chooseIntent = Intent.createChooser(emailIntent, "Please choose your email app"); 
//		startActivity(chooseIntent); // often we don't bother with the chooseIntent local variable
		
		
		
		// handle case where the app cannot email
//		try { 
//			startActivity(emailIntent); 
//		} catch(Exception ex) { 
//			// we send the user a toast message
//			Toast.makeText(this.getApplicationContext(), "Cannont send email", Toast.LENGTH_LONG).show(); 
//			// we're also logging the failure to the log window
//			// note we're passing the exception as the third argument
//			Log.e("MainActivity", "Could not send email", ex); 
//		}
	}

	// takes a pointer to a view
	public void processFormOld(View submitButton) {

		// with toast we can send either a R.id-- or we can give it a pointer to
		// a string
		// or string literal
		Log.d("MainActivity", "processForm");
		String comment = mComment.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();

		// check whether there is an "@" sign
		// int is a primitive. this holds the bit pattern for the int
		int position = email.indexOf("@");

		// conditional, if "@" not found
		if (position == -1) {
			Toast.makeText(getApplicationContext(), "Invalid email address",
					Toast.LENGTH_LONG).show();
			// change focus to email field
			mEmail.requestFocus();
			return;
		}

		int len = comment.length(); 
		if(len == 0){ 
			Toast.makeText(getApplicationContext(), "Give me comments", Toast.LENGTH_LONG).show(); 
		}
		
		
		
		// animation can be applied to any object
		Animation anim = AnimationUtils.makeOutAnimation(this, true); 
		submitButton.startAnimation(anim); 
		
		
		
		String username = email.substring(0, position);
		String thankyou = "Thankyou " + username + "!";
		Toast.makeText(getApplicationContext(), thankyou, Toast.LENGTH_LONG)
				.show();

		Toast.makeText(this.getApplicationContext(), comment, Toast.LENGTH_LONG)
				.show();
		// stop people from pressing the duck more than once
		// there are 3 values that visibility takes

		// two ways to make button go away
		// INVISIBLE: invisible, takes up space
		// GONE: invisible, takes up no space
		submitButton.setVisibility(View.INVISIBLE);

		// Toast messages. Often used to say thanks. Ephemeral message
		// context is a application context
		Toast.makeText(this.getApplicationContext(), R.string.app_name,
				Toast.LENGTH_LONG).show();

	}
}
