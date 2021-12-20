package bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles;
import com.mycompany.excepciones.ExcepcionClaveNoExiste;

/**
 *
 * @author Marco Toledo
 * @param <K>
 * @param <V>
 */
public class AVL <K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {
    // agregando una variable estatica
    private static final byte TOPE_DIFERENCIA = 1;
    //Sobreescribiendo metodos
    @Override
    public void insertar (K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if (valorAInsertar == null) {
        throw new NullPointerException ("No se permite insertar claves nulas en el árbol");
        }
        this.raiz = this.insertar (this.raiz, claveAInsertar, valorAInsertar);
    }
    /*
    1. Para el árbol AVL implemente el método insertar
    */
    //Metodo recursivo
    private NodoBinario<K, V> insertar (NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar){
        // No se valancea por q es hoja
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        //Comparando
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoDerecho(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        //si llego a este punto quiere decir que encontre en el arbol la clave que quiero 
        //insertar, entonces actualizo el valor
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
        }
    
    /*
    2. Para el árbol AVL implemente el método eliminar
    */
    @Override
     public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzq = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDer = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return balancear(nodoActual);
        }
        
        //Caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //Caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //Caso 2.2
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return balancear(nodoActual.getHijoDerecho());
        }
        //Caso 3
        NodoBinario<K, V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return balancear(nodoActual);
    }
    
    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        
        int diferenciaDeAltura = alturaPorIzquierda - alturaPorDerecha;
        if (diferenciaDeAltura > TOPE_DIFERENCIA) { //D_P = 1 , la altura de izquierda es mayor a la derecha
            //rotacion a derecha
            NodoBinario<K, V> hijoIzquierdoDelActual = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = altura(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha = altura(hijoIzquierdoDelActual.getHijoDerecho());
            if (alturaPorDerecha > alturaPorIzquierda) {
                return rotacionDobleADerecha(nodoActual);
            }
            return rotacionSimpleADerecha(nodoActual);
            
        } else if (diferenciaDeAltura < -TOPE_DIFERENCIA) { //D_P = -1, la altura derecha es mayor a la izquierd
            //rotancion a izquierda
            NodoBinario<K, V> hijoDerechoDelActual = nodoActual.getHijoDerecho();
            alturaPorIzquierda = altura(hijoDerechoDelActual.getHijoIzquierdo());
            alturaPorDerecha = altura(hijoDerechoDelActual.getHijoDerecho());
            if (alturaPorIzquierda > alturaPorDerecha) {// era esto el problema xd 
                return rotacionDobleAIzquierda(nodoActual);
            }
            return rotacionSimpleAIzquierda(nodoActual);
        }
        //si estoy aca, quiere decir que no hay que hacer rotaciones
        return nodoActual;
    }
    private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoIzquierdo();//AQUI EL ERROR?
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoDePrimeraRotacion = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        //NodoBinario<K, V> nodoDePrimeraRotacion = rotacionSimpleADerecha(nodoActual.getHijoIzquierdo());//esto era el problema
        nodoActual.setHijoIzquierdo(nodoDePrimeraRotacion);
        return rotacionSimpleADerecha(nodoActual);
    }
    
    private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoDePrimeraRotacion = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        //NodoBinario<K, V> nodoDePrimeraRotacion = rotacionSimpleAIzquierda(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoDePrimeraRotacion);
        return rotacionSimpleAIzquierda(nodoActual);  
    }
    
    
}
