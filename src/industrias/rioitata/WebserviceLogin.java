package industrias.rioitata;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class WebserviceLogin {
	    // Cadena de conexión para webservice
	 	private static String SOAP_ACTION="http://serviciocaca/Login"; 
		private static String NAMESPACE="http://serviciocaca/";
		private static String METHOD_NAME = "Login";
		private static String URL="http://10.0.2.2/A/servicio.asmx?WSDL";
		
	     
   public static String invokeLoginWS(String Usuario,String Contra) {
	        
	        String resultado="";

	        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
	  
	        PropertyInfo UsuarioPI = new PropertyInfo();
	        PropertyInfo ContraPI = new PropertyInfo();
	   
	        UsuarioPI.setName("rut");
	     
	        UsuarioPI.setValue(Usuario);
	        UsuarioPI.setType(String.class);
	       
	        request.addProperty(UsuarioPI);
	       
	        ContraPI.setName("contraseña");
	     
	        ContraPI.setValue(Contra);
	        
	        ContraPI.setType(String.class);
	        
	        request.addProperty(ContraPI);
	        
	       
	        
	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.dotNet = true ; 
         envelope.encodingStyle =  SoapSerializationEnvelope . XSD ;

	     
	        envelope.setOutputSoapObject(request);
	       
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	 
	        try {
	            
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	        
	           SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	           
	            resultado=response.toString();
	 
	        } catch (Exception e) {
	         
	            LoginActivity.errored=true;
	            e.printStackTrace();
	        } 
	   
	        return resultado;
	        
	    }
	
}
