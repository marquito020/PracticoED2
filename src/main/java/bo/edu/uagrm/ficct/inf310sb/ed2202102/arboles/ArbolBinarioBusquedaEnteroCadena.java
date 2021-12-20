package bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles;
import java.util.*;

/**
 *
 * @author Marco Toledo
 */
public class ArbolBinarioBusquedaEnteroCadena {
    protected NodoBinario<Integer, String> raiz;

    public ArbolBinarioBusquedaEnteroCadena() {
    }

    /*
    Implemente una clase ArbolBinarioBusquedaEnteroCadena que usando como base 
    el ArbolBinarioBusqueda ya no sea un árbol genérico, si no un árbol binario 
    de búsqueda con claves enteras y valores cadena.
    */
    public void insertar(Integer claveAInsertar, String valorAInsertar) throws NullPointerException {
        if (claveAInsertar == null) {
            throw new RuntimeException("No se permite insertar claves nulas");
        }
        if (valorAInsertar == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }

        NodoBinario<Integer, String> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<Integer, String> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            Integer claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual) < 0) {//claveAInstertar < claveActual
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0 ) {//claveAInstertar > claveActual
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                //el valorAInsertar es igual al que tiene el nodoActual (remplazar)
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        
        NodoBinario<Integer, String> nodoNuevo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        Integer claveAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }
    }
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }
    @Override
    public String toString() {
        return generarCadenaDeArbol(raiz, "", true); //raiz, prefijo "", ponerCodo true
    }

    private String generarCadenaDeArbol(NodoBinario<Integer, String> nodoActual, String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "|__(R)" : "|--(R)");// └ alt+192 +196 , ├ alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(D)" : "|--(I)");// └ alt+192 +196 , ├ alt+195 +196
        }
        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ╣ alt+185
            return cadena.toString();
        }

        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<Integer, String> nodoIzq = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
        cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));

        NodoBinario<Integer, String> nodoDer = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }
}
