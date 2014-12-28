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

public class SecadoActivity extends Activity {
	
	GridView ListSecado;
	private ProgressDialog dialogo;

	private static String SOAP_ACTION="http://serviciocaca/DatosSecado";
	private static String NAMESPACE="http://serviciocaca/";
	private static String METHOD_NAME = "DatosSecado";
	private static String URL="http://10.0.2.2/A/servicio.asmx?WSDL";


	private ArrayList<String> secado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activitysecado);
	
		ListSecado=(GridView)findViewById(R.id.GridSecado);
		new asynsecado().execute();
	
	}
	
	//----------conectando con el WS
	public Boolean invocarWS(){
    secado=new ArrayList<String>();
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
//	Log.e("valor newdataset",da2.getProperty(i).toString());
		
 	secado.add(da2.getProperty(0).toString());
 	secado.add(da2.getProperty(1).toString());
 	secado.add(da2.getProperty(2).toString());
	secado.add(da2.getProperty(3).toString());
    //Log.e("caca",secado.toString());
	}
	
	for(int i=0;i < secado.size(); i++){
//		Log.e("caca",secado.get(i));
	}
	
		
	//SoapObject datosxml =(SoapObject)da.getProperty(i);
	//Log.e("valor Tagname",datosxml.getProperty(0).toString());
	//Log.e("valor Column1",datosxml.getProperty(1).toString());
	//Log.e("valor column2",datosxml.getProperty(2).toString());
	//Log.e("valor",datosxml.getProperty(0).toString());

	//secado[fila]=datosxml.getProperty(0).toString();



	//}

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

		ArrayAdapter<String> adptador =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,secado);
	    ListSecado.setAdapter(adptador);
	}
	//-------clase para sincronizar las tareas y no se tome como aplicacion sin respuesta

	class asynsecado extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//super.onPreExecute();
			dialogo= new ProgressDialog(SecadoActivity.this);
			dialogo.setMessage("Cargando Datos Secado...");
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
