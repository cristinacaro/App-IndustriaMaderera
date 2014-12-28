package industrias.rioitata;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class PrincipalActivity extends Activity   {
	
	ListView Menu;
	String[] values={"Ultimos Registros Secado","Ultimos Registros Cocina","Ultimos Registros Prensa"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityprincipal);
		
		Menu =(ListView)findViewById(R.id.ListMenu);
				 
ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,values);
Menu.setAdapter(adapter);
				 
 Menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int i,
			long id) {
		
//		Toast t=Toast.makeText(getApplicationContext(),values[i],Toast.LENGTH_LONG);
//		t.show();
		
		if(values[i]=="Ultimos Registros Secado"){
			
			Intent datossecado = new Intent(getApplicationContext(),SecadoActivity.class);
			startActivity(datossecado);
			finish();
		}else if(values[i]=="Ultimos Registros Cocina"){
			
			Intent datoscocina = new Intent(getApplicationContext(),CocinaActivity.class);
			startActivity(datoscocina);
			finish();
		}else{
			
			Intent datosprensa = new Intent(getApplicationContext(),PrensaActivity.class);
			startActivity(datosprensa);
			
		}
		
	}
	
     } );
	
	
	}
}
