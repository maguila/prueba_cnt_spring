package cl.demo.prueba;

class SismoDao{

    private String intensidad;
    private String magnitud;

    public SismoDao(){

    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public String getMagnitud() {
        return magnitud;
    }
    
    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }

}