package industrias.rioitata;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class LoginActivity extends Activity {
    //Set Error Status
    static boolean errored = false;
    Button b;
    TextView statusTV;
    EditText userNameET , passWordET;    
    ProgressBar webservicePG;
    String editTextUsername;
   String loginStatus;
    String editTextPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);
    
        
        
        userNameET = (EditText) findViewById(R.id.EtRut);
        passWordET = (EditText) findViewById(R.id.EtPass);
        statusTV = (TextView) findViewById(R.id.TvResultado);
        b = (Button) findViewById(R.id.BtnEntrar);
        
        webservicePG = (ProgressBar)findViewById(R.id.ProgreBar);
        
           b.setOnClickListener(new OnClickListener() {
        	   
            public void onClick(View v) {
               
            	
                if (userNameET.getText().length() != 0 && userNameET.getText().toString() != "") {
                    if(passWordET.getText().length() != 0 && passWordET.getText().toString() != ""){
                        editTextUsername = userNameET.getText().toString();
                        editTextPassword = passWordET.getText().toString();
                        statusTV.setText("");
                      
                        AsyncCallWS task = new AsyncCallWS();
                     
                        task.execute();
                    }
                   
                    else{
                        statusTV.setText("PorFavor Ingrese Contraseña");
                    }
                
                } else {
                    statusTV.setText("PorFavor Ingrese Usuario");
                }
            }
        });
    }
    
 
  
     class AsyncCallWS extends AsyncTask<String,Void,Void>{

		@Override
		protected Void doInBackground(String... params) {
		loginStatus=WebserviceLogin.invokeLoginWS(editTextUsername,editTextPassword);
			
           
            return null;
		}
    	 
		 @Override
	    
	        protected void onPostExecute(Void result) {
	            
	            webservicePG.setVisibility(View.INVISIBLE);
	            
                if(!errored){
	              
	            if(loginStatus.equals("ok")){
	                 Intent intObj = new Intent(LoginActivity.this,PrincipalActivity.class);
	                 startActivity(intObj);
	                 
	                }else{
	                    
	                    statusTV.setText("Login Fallido,Vuelva a Intentar");
	                }
	               
	            }else{
	                statusTV.setText("Error");
	            }
	         
	            errored = false;
	        }
	 
	        @Override
	        //Make Progress Bar visible
	        protected void onPreExecute() {
	            webservicePG.setVisibility(View.VISIBLE);
	        }
	 
	        protected void onProgressUpdate(Void... values) {
	        }
      
       }    
}	
