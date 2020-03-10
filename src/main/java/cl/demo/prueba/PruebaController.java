package cl.demo.prueba;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * https://earthquake.usgs.gov/fdsnws/event/1/
 * 
 * ejemplo: 1.
 * https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02
 */

@RestController
public class PruebaController {

  @Autowired
  private JdbcTemplate jtm;

  /**
   * Se debe lograr la construcción de Endpoint para obtener los sismos a partir
   * de una fecha inicial y final
   * 
   * @param fechaInicial
   * @param fechaFinal
   * @return
   */
  @RequestMapping(value = "/ejercicio/a/{fechaInicial}/{fechaFinal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ejercicioA(@PathVariable("fechaInicial") String fechaInicial, @PathVariable("fechaFinal") String fechaFinal) {

    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + fechaInicial + "&endtime=" + fechaFinal;
    RestTemplate restTemplate = new RestTemplate();
    String json = restTemplate.getForEntity(url, String.class).getBody();

    return new ResponseEntity<>(json, HttpStatus.OK);
  }

  /**
   * Endpoint encargado de obtener los sismos con una magnitud inicial y una
   * magnitud final
   * 
   * @param magnitudInicial
   * @param magnitudFinal
   * @return
   */
  @RequestMapping(value = "/ejercicio/b/{magnitudInicial}/{magnitudFinal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ejercicioB(@PathVariable("magnitudInicial") String magnitudInicial, @PathVariable("magnitudFinal") String magnitudFinal) {

    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=" + magnitudInicial + "&maxmagnitude=" + magnitudFinal;
    RestTemplate restTemplate = new RestTemplate();

    String json = restTemplate.getForEntity(url, String.class).getBody();

    return new ResponseEntity<>(json, HttpStatus.OK);
  }

  
  /**
   * Endpoint encargado de almacenar en una base de datos H2 todos los sismos que hayan ocurrido durante el día en que se hace el llamado al endpoint
   */
  @RequestMapping(value = "/ejercicio/c/{fechaInicial}/{fechaFinal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public void ejercicioC(@PathVariable("fechaInicial") String fechaInicial, @PathVariable("fechaFinal") String fechaFinal) throws ParseException {

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + fechaInicial + "&endtime=" + fechaFinal;
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForEntity(url, String.class).getBody();

        JSONParser parser = new JSONParser(json);
        LinkedHashMap<?,?> linkedHashMap = (LinkedHashMap<?,?>) parser.parse();        
        List<?> listado = (List<?>) linkedHashMap.get("features");

        for(Object o : listado){          
          LinkedHashMap<?,?> feature = (LinkedHashMap<?,?>) o;
          LinkedHashMap<?,?> propiedades = (LinkedHashMap<?,?>) feature.get("properties");
          System.out.println("jsonObject="+ (propiedades.get("mag")) + "\n");

          String place = propiedades.get("place")!=null ? propiedades.get("place").toString() : "";
          String magnitud = propiedades.get("mag").toString();
          String time = propiedades.get("time").toString();
          String title = propiedades.get("title").toString();          

          place = place.replace("'", "''");
          title = title.replace("'", "''");

          jtm.execute("insert into sismos(fecha, magnitud, titulo, lugar) values('"+time+"','"+magnitud+"', '"+title+"', '"+place+"')");
        }

    }     

}

