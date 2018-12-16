
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.base.Collection;


public class Main {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int opcion=1;
			while(opcion>0 && opcion<5) {
				System.out.println("Elige una opción:\n"
						+ "1.- Ver un departamento\n"
						+ "2.- Insertar un departamento\n"
						+ "3.- Borrar un departamento\n"
						+ "4.- Modificar un departamento");
				opcion = Integer.parseInt(br.readLine());
				switch(opcion) { 
					case 1:
						System.out.println("Introduce el número del departamento a consultar:");
						int depno = Integer.parseInt(br.readLine());
						verempleados(depno);
						break;
					case 2:
						System.out.println("Introduce el nº de departamento:");
						int num = Integer.parseInt(br.readLine());
						System.out.println("Introduce el nombre:");
						String nombre = br.readLine();
						System.out.println("Introduce la localidad:");
						String localidad = br.readLine();
						
						break;
					case 3:
						break;
					case 4:
						
						break;
				}
				
			}

		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NumberFormatException e) {
			System.out.println("Debes introducir un número");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void verempleados(int deptno) throws XMLDBException {
		String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
		Collection col = null; // Colección
		String URI="xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionPruebas"; //URI colección
		String usu="admin"; //Usuario
		String usuPwd="admin"; //Clave
		try {
			Class cl = Class.forName(driver); //Cargar del driver
			Database database = (Database) cl.newInstance(); //Instancia de la BD
			DatabaseManager.registerDatabase(database); //Registro del driver
			col = (Collection) DatabaseManager.getCollection(URI, usu, usuPwd);
			if(col == null) {
				System.out.println(" *** LA COLECCION NO EXISTE. ***");
			}
			XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = servicio.query ("for $em in /EMPLEADOS/EMP_ROW[DEPT_NO="+deptno+"] return $em");
			System.out.println("Se han obtenido " + result.getSize() + " elementos.");
			// recorrer los datos del recurso.
			ResourceIterator i;
			i = result.getIterator();
			if (!i.hasMoreResources())
			System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
			while (i.hasMoreResources()) {
				Resource r = i.nextResource();
				System.out.println((String) r.getContent());
			}
			col.close(); //cerramos
		} catch (Exception e) {
		System.out.println("Error al inicializar la BD eXist");
		e.printStackTrace(); }

	}
}
