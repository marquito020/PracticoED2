package bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles;
import com.mycompany.excepciones.ExcepcionClaveNoExiste;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Marco Toledo
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V> {
    
    protected NodoBinario<K,V> raiz;
    
    public ArbolBinarioBusqueda(){
        
    }
    /*
    pre-condicion se asume que los recorridos estan correctos
    */
    /*
    10. Implemente un método que reciba en listas de parámetros las llaves y 
    valores de los recorridos en postorden e inorden respectivamente y que 
    reconstruya el árbol binario original. Su método no debe usar el método insertar.
    */
    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden, boolean esConPreOrden){
        if ((clavesInOrden.size() != clavesNoInOrden.size())
                || (valoresInOrden.size() != valoresNoInOrden.size())) {
            throw new RuntimeException("Los recorridos de las listas son incorrectos");
        }

        if (clavesInOrden.isEmpty() || valoresInOrden.isEmpty()
                || clavesNoInOrden.isEmpty() || valoresNoInOrden.isEmpty()) {
            throw new RuntimeException("Error, una de las listas esta vacia");
        }

        if (esConPreOrden) {
            this.raiz = this.reconstruirConPreOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        } else {
            this.raiz = this.reconstruirConPostOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        }
    }
    private NodoBinario<K, V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        K clave = clavesNoInOrden.get(0);
        V valor = valoresNoInOrden.get(0);
        NodoBinario<K, V> nodoRaiz = new NodoBinario<>(clave, valor);
        //posicion de la raiz en la lista inOrden
        int posicionRaiz = this.buscarPosicionRaiz(clavesInOrden, clave);

        //Hijo Izquierdo
        List<K> subListaIzqClavesInOrden = clavesInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresInOrden = valoresInOrden.subList(0, posicionRaiz);
        List<K> subListaIzqClavesNoInOrden = clavesNoInOrden.subList(1, posicionRaiz + 1);
        List<V> subListaIzqValoresNoInOrden = valoresNoInOrden.subList(1, posicionRaiz + 1);
        NodoBinario<K, V> hijoIzquierdo = this.reconstruirConPreOrden(subListaIzqClavesInOrden,
                subListaIzqValoresInOrden, subListaIzqClavesNoInOrden, subListaIzqValoresNoInOrden);

        //Hijo Derecho
        List<K> subListaDerClavesInOrden = clavesInOrden.subList(posicionRaiz + 1,
                clavesInOrden.size());
        List<V> subListaDerValoresInOrden = valoresInOrden.subList(posicionRaiz + 1,
                valoresInOrden.size());
        List<K> subListaDerClavesNoInOrden = clavesNoInOrden.subList(posicionRaiz + 1,
                clavesNoInOrden.size());
        List<V> subListaDerValoresNoInOrden = valoresNoInOrden.subList(posicionRaiz + 1,
                valoresNoInOrden.size());
        NodoBinario<K, V> hijoDerecho = this.reconstruirConPreOrden(subListaDerClavesInOrden,
                subListaDerValoresInOrden, subListaDerClavesNoInOrden, subListaDerValoresNoInOrden);

        nodoRaiz.setHijoIzquierdo(hijoIzquierdo);
        nodoRaiz.setHijoDerecho(hijoDerecho);
        return nodoRaiz;
    }
    private NodoBinario<K, V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        K clave = clavesNoInOrden.get(clavesNoInOrden.size() - 1);//ultimo elemento
        V valor = valoresNoInOrden.get(valoresNoInOrden.size() - 1);
        NodoBinario<K, V> nodoRaiz = new NodoBinario<>(clave, valor);
        //posicion de la raiz en la lista inOrden
        int posicionRaiz = this.buscarPosicionRaiz(clavesInOrden, clave);

        //Hijo Izquierdo
        List<K> subListaIzqClavesInOrden = clavesInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresInOrden = valoresInOrden.subList(0, posicionRaiz);
        List<K> subListaIzqClavesNoInOrden = clavesNoInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresNoInOrden = valoresNoInOrden.subList(0, posicionRaiz);
        NodoBinario<K, V> hijoIzquierdo = this.reconstruirConPostOrden(subListaIzqClavesInOrden,
                subListaIzqValoresInOrden, subListaIzqClavesNoInOrden, subListaIzqValoresNoInOrden);

        //Hijo Derecho
        List<K> subListaDerClavesInOrden = clavesInOrden.subList(posicionRaiz + 1,
                clavesInOrden.size());
        List<V> subListaDerValoresInOrden = valoresInOrden.subList(posicionRaiz + 1,
                valoresInOrden.size());
        List<K> subListaDerClavesNoInOrden = clavesNoInOrden.subList(posicionRaiz,
                clavesNoInOrden.size() - 1);
        List<V> subListaDerValoresNoInOrden = valoresNoInOrden.subList(posicionRaiz,
                valoresNoInOrden.size() - 1);
        NodoBinario<K, V> hijoDerecho = this.reconstruirConPostOrden(subListaDerClavesInOrden,
                subListaDerValoresInOrden, subListaDerClavesNoInOrden, subListaDerValoresNoInOrden);

        nodoRaiz.setHijoIzquierdo(hijoIzquierdo);
        nodoRaiz.setHijoDerecho(hijoDerecho);
        return nodoRaiz;
    }
    private int buscarPosicionRaiz(List<K> clavesInOrden, K claveABuscar) {
        for (int i = 0; i < clavesInOrden.size(); i++) {
            if (claveABuscar.compareTo(clavesInOrden.get(i)) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if (claveAInsertar == null) {
            throw new NullPointerException("No se permiten insertar claves nulas");
        }
        if (valorAInsertar == null) {
            throw new NullPointerException("No se permiten insertar valores vulos");
        }
        
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }
        
        NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K,V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual)<0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            }else 
                if (claveAInsertar.compareTo(claveActual)>0){
                    nodoActual = nodoActual.getHijoDerecho();
                }else{
                    nodoActual.setValor(valorAInsertar);
                    return;
            }
        }
        
        K calveAnterior = nodoAnterior.getClave();
        NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        
        if (claveAInsertar.compareTo(calveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        }else{
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
        
    }

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
            NodoBinario<K, V> supuestoNuevoHijoIzq=  
                    eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDer = 
                    eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoActual;
        }
        
        //si llego aca, ya encontre el nodo con la clave a eliminar (revisar que caso es)
        //Caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //Caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() && 
                nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //Caso 2.2
        if (nodoActual.esVacioHijoIzquierdo() && 
                !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        //Caso 3
        NodoBinario<K, V> nodoDelSucesor = nodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo = eliminar(
                nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }
    protected NodoBinario<K, V> nodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {            
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }
    private void pilaParaInOrden(NodoBinario<K, V> nodoActual, 
            Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    @Override
    public V buscar(K claveABuscar) {
        // si la clave es null acba la busqueda y devuelve null
        if (claveABuscar == null) {
            return null;
        }
        //Tomando el nodo raiz
        NodoBinario<K,V> nodoActual = this.raiz;
        //Si el nodo no es vacio
        while (!NodoBinario.esNodoVacio(nodoActual)){
            //Tomando la clave del nodo actual
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual)<0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            }else if (claveABuscar.compareTo(claveActual)>0){
                nodoActual = nodoActual.getHijoDerecho();
            }else{
                return nodoActual.getValor();
            }
        }
        
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {
        //Creando la lista para guardar la clave
        int cantidad =0;
        //si es un arbol vacio acaba el recorrido
        if (this.esArbolVacio()) {
        }
        //Declarando una pila
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        // Tomar el nodo raiz e apilar en la pila
        NodoBinario<K,V> nodoActual = this.raiz;
        
        meterEnPilaParaPostOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty() ){
            nodoActual = pilaDeNodos.pop();
            //visita del nodo. En este caso estoy agregado la clave a la lista
            cantidad ++;
            
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K,V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() &&
                        nodoDelTope.getHijoDerecho() != nodoActual) {
                    meterEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(), pilaDeNodos);
                }
            }
        }
        return cantidad;
    }
    
    public int sizeRec(){
        return sizeRec(this.raiz);
    }
    private int sizeRec(NodoBinario<K, V> nodoActual){
        // n==0
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int sizePorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int sizePorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return sizePorIzquierda + sizePorDerecha + 1;
    
    }
    
    @Override
    public int altura() {//
        return altura(this.raiz);
    }
    /*
    Metodo que devuelve altura
    metodo recursivo
    */
    protected int altura(NodoBinario<K, V> nodoActual) {//OJITO con el protected
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha? 
                alturaPorIzquierda +1 : 
                alturaPorDerecha +1; 
    }
    /*
    Metodo que devuelve altura
    metodo iterativo
    */
    public int alturaIt() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int alturaDelArbol = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {            
         int nroDeNodosDelNivel = colaDeNodos.size();
         int posicion = 0;
            while (posicion < nroDeNodosDelNivel) {                
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        //Creando la lista para guardar la clave
        List<K> recorrido = new ArrayList<>();
        //si es un arbol vacio acaba el recorrido
        if (this.esArbolVacio()) {
            return recorrido;
        }
        //Declarando una cola
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty() ){
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            //visita del nodo. En este caso estoy agregado la clave a la lista
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                // si existe un hijo izquiedo añadir a la cola
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                // si existe un hijo derecho añadimos a la cola
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        //retornar el recorrido
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        //Creando la lista para guardar la clave
        List<K> recorrido = new ArrayList<>();
        //si es un arbol vacio acaba el recorrido
        if (this.esArbolVacio()) {
            return recorrido;
        }
        //Declarando una pila
        Stack<NodoBinario<K,V>> pilaDeNodo = new Stack<>();
        pilaDeNodo.push(this.raiz);
        while(!pilaDeNodo.isEmpty()){
            NodoBinario<K, V> nodoActual = pilaDeNodo.pop();
            
            recorrido.add(nodoActual.getClave());
            
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodo.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodo.push(nodoActual.getHijoIzquierdo());
            }
        }
        
        //retornar el recorrido
        return recorrido;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        //Crenado lista bacia recorrido
        List<K> recorrido = new ArrayList<>();
        this.recorridoEnInOrden(this.raiz, recorrido);
        
        return recorrido;
    }
    private void recorridoEnInOrden(NodoBinario<K,V> nodoActual, List<K> recorrido){
        // n==0
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnPosOrden() {
        //Creando la lista para guardar la clave
        List<K> recorrido = new ArrayList<>();
        //si es un arbol vacio acaba el recorrido
        if (this.esArbolVacio()) {
            return recorrido;
        }
        //Declarando una pila
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        // Tomar el nodo raiz e apilar en la pila
        NodoBinario<K,V> nodoActual = this.raiz;
        
        meterEnPilaParaPostOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty() ){
            nodoActual = pilaDeNodos.pop();
            //visita del nodo. En este caso estoy agregado la clave a la lista
            recorrido.add(nodoActual.getClave());
            
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K,V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() &&
                        nodoDelTope.getHijoDerecho() != nodoActual) {
                    meterEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(), pilaDeNodos);
                }
            }
        }
        //retornar el recorrido
        return recorrido;
    }
    
    private void meterEnPilaParaPostOrden(NodoBinario<K, V> nodoActual, 
            Stack<NodoBinario<K, V>> pilaDeNodo){
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            //Apilando nodo actual
            pilaDeNodo.push(nodoActual);
            //Si el nodo actual no tiene como bacio al hizo izquiedo
            if (!nodoActual.esVacioHijoIzquierdo()) {
                //Tomando al hijo izquiedo como nodo actual
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{//Si el hijo izquiedo es vacio tomamos el hijo derecho como nodo actual
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }
    protected NodoBinario<K, V> buscarSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {            
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }
    
    //Cuenta la cantidad de hojas del arbol
    public int cantHojasDelArbol() {//funciona :)
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cantidadHojas = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {            
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            } else if (nodoActual.esVacioHijoDerecho()) {//con eso comprueba que el nodo es Hoja
                cantidadHojas++;
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }        
        return cantidadHojas;
    }
    
    private void insertarEnPilaParaInOrden(NodoBinario<K, V> nodoActual, 
            Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {           
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }  
    }
    
    @Override
    public int nivel() { 
        if (this.esArbolVacio()) {
            return -1;// la raiz es el nivel 0, por eso devuelve -1 si el arbol esta vacio
        }
        
        int nivelDelArbol = -1;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {            
         int nroDeNodosDelNivel = colaDeNodos.size();
         int posicion = 0;
            while (posicion < nroDeNodosDelNivel) {                
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }
            nivelDelArbol++;
        }
        return nivelDelArbol;
    }
    
    private void meterEnPilaParaInOrden(NodoBinario<K, V> nodoActual,
            Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }
    
    @Override
    public String toString() {
        return generarCadenaDeArbol(raiz, "",  true); //raiz, prefijo "", ponerCodo true
    }
    
    private String generarCadenaDeArbol(NodoBinario<K, V> nodoActual, 
            String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "|__(R)" : "|--(R)");
            // └ alt+192 +196 , ├ alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(D)" : "|--(I)" );
            // └ alt+192 +196 , ├ alt+195 +196
        }
        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ╣ alt+185
            return cadena.toString();
        }
        
        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<K, V> nodoIzq = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
        cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));
        
        NodoBinario<K, V> nodoDer = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));
        
        return cadena.toString();
    }
    
    /*
    ----------------------------PRACTICO----------------------------
    */
    /*
    7. Implemente un método iterativo con el recorrido en inorden que retorne 
    la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario
    */
    public int ambosHijosInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty()) {            
            nodoActual = pilaDeNodos.pop();    
            if (!nodoActual.esVacioHijoDerecho()) {
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    cant++;
                }
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }
        
        return cant;
    }
    /*
    8. Implemente un método recursivo que retorne la cantidad de nodos que 
    tienen un solo hijo no vació
    */
    public int cantidadNodoUnSoloHijo() {
        if (this.esArbolVacio()) {
            return 0;
        }
        return cantidadNodoUnSoloHijoR(this.raiz);
    }
    private int cantidadNodoUnSoloHijoR(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 0;
        }
        int cantPorIzq = cantidadNodoUnSoloHijoR(nodoActual.getHijoIzquierdo());
        int cantPorDer = cantidadNodoUnSoloHijoR(nodoActual.getHijoDerecho());
        if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
            || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
            return cantPorIzq + cantPorDer + 1;
        }
        return cantPorIzq + cantPorDer;
    }
    
    /*
    9. Implemente un método iterativo con la lógica de un recorrido en inOrden 
    que retorne el número de hijos vacios que tiene un árbol binario.
    */
    public int numeroHijosVacioInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty()) {            
            nodoActual = pilaDeNodos.pop();    
            if (nodoActual.esVacioHijoIzquierdo()) {
                cant++;
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            } else {
                cant++;
            }
        }
        
        return cant;
    }
    
    /*
    11. Implemente un método privado que reciba un nodo binario de un árbol binario 
    y que retorne cuál sería su predecesor inorden de la clave de dicho nodo.
    */
    public K predecesor() {
        return predecesorInOrden(this.raiz.getHijoDerecho().getHijoDerecho());
    }
    private K predecesorInOrden(NodoBinario<K, V> nodoActual) {
        K predecesor = (K)NodoBinario.nodoVacio();
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return predecesor;
        }

        K claveActual = nodoActual.getClave();
        NodoBinario<K, V> nodoPredecesor = this.raiz;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();

        if (claveActual.compareTo(nodoPredecesor.getClave()) > 0) {//claveActual > raiz.getClave
            this.pilaParaInOrden(nodoPredecesor.getHijoDerecho(), pilaDeNodos);
        } else if (claveActual.compareTo(nodoPredecesor.getClave()) < 0) {//claveActual < raiz.getClave
            this.pilaParaInOrden(nodoPredecesor.getHijoIzquierdo(), pilaDeNodos);
        } else {
            return predecesor;//si la claveActual == raiz.getClave retornara null
        }

        NodoBinario<K, V> nodoAux;
        while (!pilaDeNodos.isEmpty()) {
            nodoAux = nodoPredecesor;
            nodoPredecesor = pilaDeNodos.pop();

            if (nodoPredecesor.getClave().compareTo(claveActual) == 0) {
                //si pilla la claveActual y la pila esta vacia quiere decir que su clave predecesora es la clave del nodoAux
                if (pilaDeNodos.isEmpty()) {
                    predecesor = nodoAux.getClave();
                } else {//si la pila no esta vacia su clave predecera sera la siguiente en la Pila
                    nodoPredecesor = pilaDeNodos.pop();
                    predecesor = nodoPredecesor.getClave();
                    return predecesor;
                }
            }
            if (!nodoPredecesor.esVacioHijoDerecho()) {
                this.pilaParaInOrden(nodoPredecesor.getHijoDerecho(), pilaDeNodos);
            }
        }

        return predecesor;
    }
    
    /*
    15. Para un árbol binario de búsqueda implemente un método que reciba como 
    parámetro otro árbol y que retorne verdadero si los arboles son similares, 
    falso en caso contrario.
    */
    public boolean sonSimilares(ArbolBinarioBusqueda<K, V> arbol2) {
        if (this.esArbolVacio() && arbol2.esArbolVacio()) {
            return true;
        }
        if ((!this.esArbolVacio() && arbol2.esArbolVacio())
            || (this.esArbolVacio() && !arbol2.esArbolVacio())) {
            return false;
        }

        Queue<NodoBinario<K, V>> colaDeNodos1 = new LinkedList<>();
        Queue<NodoBinario<K, V>> colaDeNodos2 = new LinkedList<>();
        colaDeNodos1.offer(this.raiz);
        colaDeNodos2.offer(arbol2.raiz);

        while (!colaDeNodos1.isEmpty() && !colaDeNodos2.isEmpty()) {
            NodoBinario<K, V> nodoActual1 = colaDeNodos1.poll();
            NodoBinario<K, V> nodoActual2 = colaDeNodos2.poll();

            if (!nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo()) {
                colaDeNodos1.offer(nodoActual1.getHijoIzquierdo());
                colaDeNodos2.offer(nodoActual2.getHijoIzquierdo());
            } else {
                if ((!nodoActual1.esVacioHijoIzquierdo() && nodoActual2.esVacioHijoIzquierdo())
                    || (nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo())) {
                    return false;
                }
            }

            if (!nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho()) {
                colaDeNodos1.offer(nodoActual1.getHijoDerecho());
                colaDeNodos2.offer(nodoActual2.getHijoDerecho());
            } else {
                if ((!nodoActual1.esVacioHijoDerecho() && nodoActual2.esVacioHijoDerecho())
                        || (nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho())) {
                    return false;
                }
            }

        }

        return true;
    }
}
