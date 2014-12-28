package industrias.rioitata;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class PrensaActivity extends Activity {

	GridView ListPrensa;
	private ProgressDialog dialogo;	
	
	private static String SOAP_ACTION="http://serviciocaca/DatosPrensa";
	private static String NAMESPACE="http://serviciocaca/";
	private static String METHOD_NAME = "DatosPrensa";
	private static String URL="http://10.0.2.2/A/servicio.asmx?WSDL";
	
	private ArrayList<String> prensa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activityprensa);
		
		ListPrensa=(GridView)findViewById(R.id.GridPrensa);
		new asynsecado().execute();
	
	}
	
public Boolean invocarWS(){
		
	    prensa=new ArrayList<String>();
		Boolean bandera=true;
		try{
			
		SoapObject	respuesta= new SoapObject(NAMESPACE, METHOD_NAME);

		SoapSerializationEnvelope sobre=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sobre.dotNet=true;
		sobre.setOutputSoapObject(respuesta);
		HttpTransportSE transporte= new HttpTransportSE(URL);

		transporte.call(SOAP_ACTION,sobre);

		SoapObject response=(SoapObject)sobre.getResponse();
		SoapObject diffgram=(SoapObject)response.getProperty("diffgram");
		SoapObject newdataset=(SoapObject)diffgram.getProperty("DocumentElement");
		int data=newdataset.getPropertyCount();

		//Log.e("valor diffgram",diffgram.toString());
		//Log.e("valor newdataset",newdataset.toString());

	  
		for(int i=0;i<data; i++){
			
	    SoapObject da2=(SoapObject)newdataset.getProperty(i);

			
	 	prensa.add(da2.getProperty(0).toString());
	 	prensa.add(da2.getProperty(1).toString());
	 	prensa.add(da2.getProperty(2).toString());
		prensa.add(da2.getProperty(3).toString());

		}
		
		for(int i=0;i < prensa.size(); i++){

		}
	

		}catch(IOException e){

		e.printStackTrace();
		bandera=false;
			
		}catch(XmlPullParserException e){
		e.printStackTrace();
		bandera=false;
		}

		return  bandera;
		}

	//-----cargar datos en el gridview
		public void cargar_elementos(){

			ArrayAdapter<String> adptador =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prensa);
		    ListPrensa.setAdapter(adptador);
		}

		
		class asynsecado extends AsyncTask<String, String, String>{

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				//super.onPreExecute();
				dialogo= new ProgressDialog(PrensaActivity.this);
				dialogo.setMessage("Cargando Datos Prensa...");
				dialogo.setIndeterminate(false);
				dialogo.setCancelable(false);
				dialogo.show();
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				if(invocarWS()){
					return "ok";
				}else{
			    return "error";
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				//super.onPostExecute(result);
				dialogo.dismiss();
				if(result.equals("ok")){
				//---aqui cargaremos los datos al grid
					cargar_elementos();
					
				}
			}	
	}
	
	
	
}
