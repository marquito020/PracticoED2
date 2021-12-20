package bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles;

/**
 *
 * @author Marco Toledo
 */
    /*
    Creamos el tipo de nodo que vamos a utilizar en el arbol 
    en este caso es clave(hZ), valor(hD), entonces se utilizaran dos genericos(K,V)
    */
public class NodoBinario<K,V> {
    private K clave;
    private V valor;
    NodoBinario<K,V> hijoIzquierdo;
    NodoBinario<K,V> hijoDerecho;
    
    //Constructor
    public NodoBinario() {
    }
    
    //Constructor con clave y valor
    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    //Miembro solo de la clase
    //Nodo vacio o null
    public static NodoBinario nodoVacio(){
        return null;
    }
    //Es Verdad si el nodo dado esta vacio
    public static boolean esNodoVacio(NodoBinario elNodo){
        return elNodo == nodoVacio();
    }
    //Es Verdad si el nodo de la izquieda es vacio 
    public boolean esVacioHijoIzquierdo(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    //Es verdad si el nodo de la derecha es vacio
    public boolean esVacioHijoDerecho(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }
    //Es verdad si el nodo es Hoja(no tiene hijos por lo tanto sus hijos deben ser vacios)
    public boolean esHoja(){
        return this.esVacioHijoIzquierdo() &&
                this.esVacioHijoDerecho();
    }
    //adicionales
    public int cantHijos() {
        if (NodoBinario.esNodoVacio(this)) {
            return 0;
        }
        int cant = 0;
        if (!this.esVacioHijoIzquierdo()) {
            cant++;
        }
        if (!this.esVacioHijoDerecho()) {
            cant++;
        }
        return cant;
    }
 
    
}
