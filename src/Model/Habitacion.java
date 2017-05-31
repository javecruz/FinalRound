package Model;

public class Habitacion {
    
    private int numero;
    private float temperatura;
    
    
    
    
    public Habitacion(int numero, float temperatura){
    
    this.numero = numero;
    this.temperatura = temperatura;
    
    
    }
    
    public Habitacion() {
    
    
    }
    

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        
        this.numero = numero;
    }

    /**
     * @return the temperatura
     */
    public float getTemperatura() {
        return temperatura;
    }

    /**
     * @param temperatura the temperatura to set
     */
    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }
    
    
}
