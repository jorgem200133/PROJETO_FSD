import java.rmi.registry.LocateRegistry;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class getTemperature {
	
	String SERVICE_NAME;
	String SERVICE_IP;
	String[] args;
	Instant tsp;

	public getTemperature(String ip_servico, String nome_servico, Instant instant)  {
		this.SERVICE_IP = ip_servico;
		this.SERVICE_NAME = nome_servico;
		this.tsp = instant;
	}

	public void putTemperature() {

		try {

			ServicesInterface temps = (ServicesInterface) LocateRegistry.getRegistry(SERVICE_IP).lookup(SERVICE_NAME);
			
			float temperatura = temps.getTemperature(tsp);
			
			if(temperatura==9999.9999f){
				System.out.println("Tempo de acesso esgotado!");
			}else{
				System.out.println("Temperatura: " + temperatura);
			}

		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
	}
}