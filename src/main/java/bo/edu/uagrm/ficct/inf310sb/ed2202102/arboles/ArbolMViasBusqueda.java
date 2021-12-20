package bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles;
import com.mycompany.excepciones.ExcepcionClaveNoExiste;
import com.mycompany.excepciones.ExcepcionOrdenInvalido;

import java.awt.font.FontRenderContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
/**
 *
 * @author Marco Toledo
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {
    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;

    public ArbolMViasBusqueda() {
        this.orden = 3;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < 3) {
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }
    
    /*
    5. Para el árbol m vias de búsqueda implemente el método insertar
    */
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (valorAInsertar == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }
        if (claveAInsertar == null) {
            throw new RuntimeException("No se permite insertar claves nulas");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }

        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClave = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClave != POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClave, valorAInsertar);//remplaza el valor de esa clave
                nodoActual = NodoMVias.nodoVacio();
            } else {
                if (nodoActual.esHoja()) {
                    if (nodoActual.estanClavesLlenas()) {
                        int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);//?
                        NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    } else {
                        this.insertarClaveYValorOrdenadoEnNodo(nodoActual, claveAInsertar, valorAInsertar);
                    }
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                        NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                        nodoActual = NodoMVias.nodoVacio();
                    } else {
                        nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                    }
                }
            }
        }
    }
    /*
    6. Para el árbol m vias de búsqueda implemente el método eliminar
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
    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveActual) == 0) {
                if (nodoActual.esHoja()) {
                    this.eliminarClaveYValorDelNodo(nodoActual, i);
                    if (nodoActual.cantidadDeClavesNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }

                //no es hoja el nodo actual
                K claveDeReemplazo;
                if (this.hayHijosMasAdelante(nodoActual, i)) {
                    claveDeReemplazo = this.buscarClaveSucesoraInOrden(nodoActual, claveAEliminar);
                } else {
                    claveDeReemplazo = this.buscarClavePredecesoraInOrden(nodoActual, claveAEliminar);
                }
                V valorDeReemplazo = buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual, claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }

            //no esta en la posicion i del nodoActual
            if (claveAEliminar.compareTo(claveActual) < 0) {
                NodoMVias<K, V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }

        //si llego sin retornar, quiere decir que nunca baje por ningun lado, ni la encontre
        NodoMVias<K, V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),
                        claveAEliminar);
        nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }
    
    
    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }
    
    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }

        int cantidadDeNodos = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {// recorrido por niveles
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                cantidadDeNodos++;
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }

        return cantidadDeNodos;
    }
    
    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }
    
    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias()
                    && !huboCambioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) { // claveABuscar == claveActual
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    huboCambioDeNodoActual = true;
                }
            }
            if (!huboCambioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }

        return (V) NodoMVias.datoVacio(); //retorno NULO
    }
    
    protected int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (nodoActual.getClave(i).compareTo(claveAInsertar) == 0) {//si las claves son iguales
                return i;
            }
        }
        return -1;
    }
    
    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (claveAInsertar.compareTo(nodoActual.getClave(i)) < 0) {//si la clave a insertar es menor 
                return i;
            }
        }
        
        //si llego hasta aqui quiere decir que la clave a insertar es mayor que la ultima clave del nodo
        return (nodoActual.cantidadDeClavesNoVacias()); 
    }
    
    protected void insertarClaveYValorOrdenadoEnNodo(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        int posicion = nodoActual.cantidadDeClavesNoVacias();
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (claveAInsertar.compareTo(nodoActual.getClave(i)) < 0) {
                posicion = i;//posicion donde insertar
                break;// para saltar las demas instrucciones del for y pasar a la sgt instruccion del metodo
            }
        }
        
        // si posicion no se altera quiere decir que la clave a insertar en mayor que las claves anteriores
        //inserta en la ultima posicion vacia
        if (posicion == nodoActual.cantidadDeClavesNoVacias()) {
            nodoActual.setClave(posicion, claveAInsertar);
            nodoActual.setValor(posicion, valorAInsertar);
            return;//return para que se salga del metodo y no realize las demas instrucciones del metodo
        }

        //recorremos los nodos una casilla a la derecha 
        for (int j = nodoActual.cantidadDeClavesNoVacias() ; j > posicion; j--) {
            nodoActual.setClave(j, nodoActual.getClave(j-1));
            nodoActual.setValor(j, nodoActual.getValor(j-1));
            nodoActual.setHijo(j+1, nodoActual.getHijo(j));//esto nodoActual.setHijo(j, nodoActual.getHijo(j-1));
        }
        //insertamos la clave y el valor en la posicion correspondiente
        nodoActual.setClave(posicion, claveAInsertar);
        nodoActual.setValor(posicion, valorAInsertar);
        nodoActual.setHijo(posicion, NodoMVias.nodoVacio());//esto
    }
    private boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
        for (int j = i+1; j < this.orden; j++) {
            if (!nodoActual.esHijoVacio(j)) {
                return true;
            }
        }
        return false;
    }
    protected void eliminarClaveYValorDelNodo(NodoMVias<K, V> nodoActual, int i) {
        //el nodo es hoja por lo tanto no tienen hijos y no hace falta moverlos
        int cantDeClavesNoVacias = nodoActual.cantidadDeClavesNoVacias();
        nodoActual.setClave(i,(K)NodoMVias.datoVacio());
        nodoActual.setValor(i, (V)NodoMVias.datoVacio());

        if (i+1 < cantDeClavesNoVacias) {//verifica que tenga mas claves adelante
            //traemos las claves y valores de derecha hacia izq
            for (int j = i; j < cantDeClavesNoVacias-1; j++) {
                nodoActual.setClave(j, nodoActual.getClave(j+1));
                nodoActual.setValor(j, nodoActual.getValor(j+1));
            }

            //eliminar la clave y valor de la ultima posicion
            if (cantDeClavesNoVacias > 0) {
                nodoActual.setClave(cantDeClavesNoVacias-1, (K)NodoMVias.datoVacio());
                nodoActual.setValor(cantDeClavesNoVacias-1, (V)NodoMVias.datoVacio());
            }
        }
    }
    private K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {       
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return ((K)NodoMVias.datoVacio());
        }

        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (nodoActual.getClave(i).compareTo(claveAEliminar) == 0) {//encuentro la posicion donde esta la claveAEliminar
                if (!nodoActual.esHijoVacio(i)) {//pregunto si tiene hijo esa clave
                    nodoActual = nodoActual.getHijo(i);
                    while (!NodoMVias.esNodoVacio(nodoActual)) {//recorro los nodos en InOrden
                        claveAEliminar = nodoActual.getClave(0);
                        nodoActual = nodoActual.getHijo(0);
                    }
                    return claveAEliminar;
                }

                if (i+1 < nodoActual.cantidadDeClavesNoVacias()) {//si no tiene hijo en la pos i, su sucesor seria la clave siguiente
                    if (!nodoActual.esHijoVacio(i+1)) {//si la clave siguiente tiene hijo se recorre en InOrden el hijo
                        nodoActual = nodoActual.getHijo(i+1);
                        while (!NodoMVias.esNodoVacio(nodoActual)) {//recorro los nodos en InOrden
                            claveAEliminar = nodoActual.getClave(0);
                            nodoActual = nodoActual.getHijo(0);
                        }
                    } else {//si no tiene hijo la clave siguiente, entonces el sucesor es la clave siguiente
                        claveAEliminar = nodoActual.getClave(i+1);
                    }
                    return  claveAEliminar;
                }
            }
        }//fin del for

        //si llego hasta aca quiere decir que su sucesor esta en el ultimo hijo
        nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
        while (!NodoMVias.esNodoVacio(nodoActual)) {//recorremos los nodos en InOrden
            claveAEliminar = nodoActual.getClave(0);
            nodoActual = nodoActual.getHijo(0);
        }

        return claveAEliminar;
    }
    private K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return (K)NodoMVias.datoVacio();
        }

        List<K> recorrido = new LinkedList<>();
        Stack<NodoMVias<K, V>> pilaDeNodos = new Stack<>();
        NodoMVias<K, V> nodoRecorrido = this.raiz;
        this.insertarEnPilaParaInOrden(nodoRecorrido, pilaDeNodos);//llenamos la pila con los nodos del arbol
        boolean huboCambioDeNodo = false;

        while (!pilaDeNodos.isEmpty()) {
            nodoRecorrido = pilaDeNodos.pop();
            for (int i = 0; i < nodoRecorrido.cantidadDeClavesNoVacias() && !huboCambioDeNodo; i++) {
                if (!recorrido.contains(nodoRecorrido.getClave(i))) {//pregunta si en el recorrido no esta esa clave(para no tener clavesrepetidas)

                    if (nodoRecorrido.getClave(i).compareTo(claveAEliminar) == 0) {//si la clave que voy a insertar es igual a la claveAEliminar
                        claveAEliminar = recorrido.get(recorrido.size()-1);//retorna el ultimo del recorrido(osea el predecesor)
                        return claveAEliminar;
                    }

                    recorrido.add(nodoRecorrido.getClave(i));
                    if (!nodoRecorrido.esHijoVacio(i+1)) {//pregunta si tiene hijo mas adelante
                        if (i+1 < nodoRecorrido.cantidadDeClavesNoVacias()) {//si el nodoActual aun tiene datos que no se insertaron al recorrido
                            //aqui volvemos a insertar el nodoActual
                            pilaDeNodos.push(nodoRecorrido);
                        }
                        this.insertarEnPilaParaInOrden(nodoRecorrido.getHijo(i+1), pilaDeNodos);
                        huboCambioDeNodo = true;//para que pare el for
                    }
                }
            }
            huboCambioDeNodo = false;
        }
        return claveAEliminar;
    }
    private void insertarEnPilaParaInOrden(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeNodos) {
        while (!NodoMVias.esNodoVacio(nodoActual)) {           
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijo(0);
        }  
    }
    
    private void irAlNivelN(Queue<NodoMVias<K, V>> colaDeNodos, int nivelN) {
        while (!colaDeNodos.isEmpty() && nivelN > 0) {            
            int cantDeNodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < cantDeNodosDelNivel) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esHoja()) {
                    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                        if (!nodoActual.esHijoVacio(i)) {
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                    }
                }
                contador++;
            }
            nivelN--;
        }
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < this.orden; i++) {
            int alturaActual = altura(nodoActual.getHijo(i));
            if (alturaActual > alturaMayor) {
                alturaMayor = alturaActual;
            }
        }
        return alturaMayor + 1;
    }

    
    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    @Override
    public List<K> recorridoPorNiveles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> recorridoEnInOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> recorridoEnPosOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String toString() {
        return generarCadenaDeArbol(this.raiz, "",  true, 0); //raiz, prefijo "", ponerCodo true
    }
    private String generarCadenaDeArbol(NodoMVias<K, V> nodoActual, String prefijo, boolean ponerCodo, int num) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append("|__(R)");// └ alt+192 +196 , ├ alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(" : "|--(");
            cadena.append(num);
            cadena.append(")");
        }
        if (NodoMVias.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ╣ alt+185
            return cadena.toString();
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            cadena.append(nodoActual.getClave(i).toString());
            cadena.append(" ");
        }
        cadena.append("\n");
        
        for (int i = 0; i < this.orden - 1; i++) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(i);
            String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
            cadena.append(generarCadenaDeArbol(nodoHijo, prefijoAux, false, i+1));
        }
        
        NodoMVias<K, V> nodoHijo = nodoActual.getHijo(this.orden-1);
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
        cadena.append(generarCadenaDeArbol(nodoHijo, prefijoAux, true, this.orden));
        
        return cadena.toString();
    }
    
    /*
    EJERCICIOS PRACTICO
    */
    /*
    12. Implemente un método que retorne verdadero si solo hay nodos completos 
    en el nivel n de un árbol m vias. Falso en caso contrario.
    */
    public boolean nodosCompletosEnNivelN(int nivelN) {
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            return false;
        }
        if (this.esArbolVacio()) {
            return false;
        }

        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            if (nodoActual.cantidadDeClavesNoVacias() != this.orden - 1) {
                //if (nodoActual.cantidadDeHijosNoVacios() != this.orden) {
                    return false;
                //}
            }
        }

        return true;
    }
    public int nivel() {
        return nivel(this.raiz);
    }
    protected int nivel(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return -1;
        }
        int nivelMayor = -1;
        for (int i = 0; i < this.orden; i++) {
            int nivelActual = nivel(nodoActual.getHijo(i));
            if (nivelActual > nivelMayor) {
                nivelMayor = nivelActual;
            }
        }
        return nivelMayor + 1;
    }
    
    /*
    14. Para un árbol m vías implementar un método que reciba otro árbol de 
    parámetro y que retorne verdadero si los arboles son similares. 
    Falso en caso contrario.
    */
    public boolean esSimilar(ArbolMViasBusqueda<K,V> arbol2){
        return esSimilar1(raiz, arbol2.raiz);
    }
    public boolean esSimilar1(NodoMVias<K,V> arbol1,NodoMVias<K,V> arbol2){
        if (NodoMVias.esNodoVacio(arbol1) && NodoMVias.esNodoVacio(arbol2)) {
            return true;
        }
        
        if (NodoMVias.esNodoVacio(arbol1) || NodoMVias.esNodoVacio(arbol2)) {
            return false;
        }
        
        if (arbol1.esHoja() && arbol2.esHoja()) {
            if (arbol1.cantidadDeClavesNoVacias() == arbol2.cantidadDeClavesNoVacias()) {
                for (int i = 0; i < arbol1.cantidadDeClavesNoVacias(); i++) {
                    if (arbol1.getClave(i).compareTo(arbol2.getClave(i)) != 0) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        
        for (int i = 0; i < orden-1; i++) {
            if (arbol1.getClave(i).compareTo(arbol2.getClave(i)) != 0) {
                return false;
            }
        }
        
        if (arbol1.esHoja() || arbol2.esHoja()) {
            return false;
        }
        
        if (arbol1.cantidadDeHijosNoVacios()!= arbol2.cantidadDeHijosNoVacios()) {
            return false;
        }
       
        for (int i = 0; i < orden; i++) {
            if (esSimilar1(arbol1.getHijo(i), arbol2.getHijo(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    
    
}


