package consola;

import bo.edu.uagrm.ficct.inf310sb.ed2202102.arboles.*;
import com.mycompany.excepciones.ExcepcionClaveNoExiste;
import com.mycompany.excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class consola {
    public static void main(String[] args) throws ExcepcionClaveNoExiste, ExcepcionOrdenInvalido {
        AVL<Integer, String> avl = new AVL<>();
        ArbolMViasBusqueda<Integer, String> arbolB;
        ArbolMViasBusqueda<Integer,String> arbolMvias = new ArbolMViasBusqueda<>();
        ArbolMViasBusqueda<Integer,String> arbolMvias2 = new ArbolMViasBusqueda<>();
        ArbolBinarioBusqueda<Integer, String> arbolBusqueda1 = new ArbolBinarioBusqueda<>();
        ArbolBinarioBusqueda<Integer, String> arbolBusqueda3 = new ArbolBinarioBusqueda<>();
        ArbolBinarioBusqueda<String, String> arbolBusqueda2;
        ArbolBinarioBusquedaEnteroCadena arbolEnteroCadena1 = new ArbolBinarioBusquedaEnteroCadena();
        
        List<String> clavesInOrden = new ArrayList<>();
        clavesInOrden.add("CA");
        clavesInOrden.add("CF");
        clavesInOrden.add("CP");
        clavesInOrden.add("CZ");
        clavesInOrden.add("EK");
        clavesInOrden.add("FE");
        clavesInOrden.add("HM");
        clavesInOrden.add("LP");
        clavesInOrden.add("MK");
        clavesInOrden.add("TA");
        clavesInOrden.add("VB");

        List<String> valoresInOrden = new ArrayList<>();
        valoresInOrden.add("Juan");
        valoresInOrden.add("Pedro");
        valoresInOrden.add("Carlos");
        valoresInOrden.add("Jhon");
        valoresInOrden.add("Pepe");
        valoresInOrden.add("Smith");
        valoresInOrden.add("Meg");
        valoresInOrden.add("Marge");
        valoresInOrden.add("Homero");
        valoresInOrden.add("Bart");
        valoresInOrden.add("Lisa");

        List<String> clavesPostOrden = new ArrayList<>();
        clavesPostOrden.add("CF");
        clavesPostOrden.add("CA");
        clavesPostOrden.add("CZ");
        clavesPostOrden.add("CP");
        clavesPostOrden.add("FE");
        clavesPostOrden.add("EK");
        clavesPostOrden.add("LP");
        clavesPostOrden.add("MK");
        clavesPostOrden.add("VB");
        clavesPostOrden.add("TA");
        clavesPostOrden.add("HM");

        List<String> valoresPostOrden = new ArrayList<>();
        valoresPostOrden.add("Pedro");
        valoresPostOrden.add("Juan");
        valoresPostOrden.add("Jhon");
        valoresPostOrden.add("Carlos");
        valoresPostOrden.add("Smith");
        valoresPostOrden.add("Pepe");
        valoresPostOrden.add("Marge");
        valoresPostOrden.add("Homero");
        valoresPostOrden.add("Lisa");
        valoresPostOrden.add("Bart");
        valoresPostOrden.add("Meg");

        List<String> clavesPreOrden = new ArrayList<>();
        clavesPreOrden.add("HM");
        clavesPreOrden.add("EK");
        clavesPreOrden.add("CP");
        clavesPreOrden.add("CA");
        clavesPreOrden.add("CF");
        clavesPreOrden.add("CZ");
        clavesPreOrden.add("FE");
        clavesPreOrden.add("TA");
        clavesPreOrden.add("MK");
        clavesPreOrden.add("LP");
        clavesPreOrden.add("VB");

        List<String> valoresPreOrden = new ArrayList<>();
        valoresPreOrden.add("Meg");
        valoresPreOrden.add("Pepe");
        valoresPreOrden.add("Carlos");
        valoresPreOrden.add("Juan");
        valoresPreOrden.add("Pedro");
        valoresPreOrden.add("Jhon");
        valoresPreOrden.add("Smith");
        valoresPreOrden.add("Bart");
        valoresPreOrden.add("Homero");
        valoresPreOrden.add("Marge");
        valoresPreOrden.add("Lisa");
        
        avl.insertar(32, "CARLA");
        avl.insertar(20, "CARLA");
        avl.insertar(15, "CARLA");
        avl.insertar(52, "CARLA");
        avl.insertar(55, "CARLA");
        //1. Para el árbol AVL implemente el método insertar
        System.out.println("Arbol AVL");
        System.out.println(avl);
        //2. Para el árbol AVL implemente el método eliminar
        avl.eliminar(55);
        System.out.println(avl);
        
        System.out.println("Arbol B");
        arbolB = new ArbolB<>(4);
        arbolB.insertar(33, "Dani");
        arbolB.insertar(22, "Dani");
        arbolB.insertar(44, "Dani");
        arbolB.insertar(29, "Dani");
        arbolB.insertar(49, "Dani");
        arbolB.insertar(30, "Dani");
        //3. Para el árbol B implemente el método insertar
        System.out.println(arbolB);
        
        System.out.println("Arbol M Vias");
        arbolMvias.insertar(5, "David");
        arbolMvias.insertar(12, "David");
        arbolMvias.insertar(3, "David");
        arbolMvias.insertar(9, "David");
        arbolMvias.insertar(17, "David");
        //5. Implemente el método insertar de un árbol m-vias de búsqueda
        System.out.println(arbolMvias);
        //6. Implemente el método eliminar de un árbol m-vias de búsqueda
        
        System.out.println("Arbol Binario de Busqueda");
        arbolBusqueda1.insertar(15, "Pedro");
        arbolBusqueda1.insertar(11, "Pedro");
        arbolBusqueda1.insertar(9,  "Pedro");
        arbolBusqueda1.insertar(13, "Pedro");
        arbolBusqueda1.insertar(8,  "Pedro");
        arbolBusqueda1.insertar(10, "Pedro");
        arbolBusqueda1.insertar(14, "Pedro");
        arbolBusqueda1.insertar(12, "Pedro");
        arbolBusqueda1.insertar(19, "Pedro");
        arbolBusqueda1.insertar(17, "Pedro");
        arbolBusqueda1.insertar(22, "Pedro");
        arbolBusqueda1.insertar(18, "Pedro");
        arbolBusqueda1.insertar(16, "Pedro");
        arbolBusqueda1.insertar(23, "Pedro");
        arbolBusqueda1.insertar(21, "Pedro");
        arbolBusqueda1.insertar(33, "Pedro");
        System.out.println(arbolBusqueda1);
        //7. Implemente un método iterativo con el recorrido en inorden que 
        //retorne la cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario
        System.out.println("Cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario: "+arbolBusqueda1.ambosHijosInOrden());
        //8. Implemente un método recursivo que retorne la cantidad de nodos 
        //que tienen un solo hijo no vació
        System.out.println("Cantidad de nodos que tienen un solo hijo no vació: "+arbolBusqueda1.cantidadNodoUnSoloHijo());
        //9. Implemente un método iterativo con la lógica de un recorrido en 
        //inOrden que retorne el número de hijos vacios que tiene un árbol binario.
        System.out.println("Número de hijos vacios que tiene un árbol binario: "+arbolBusqueda1.numeroHijosVacioInOrden());
        /*
        10. Implemente un método que reciba en listas de parámetros las llaves 
        y valores de los recorridos en postorden e inorden respectivamente y que reconstruya el árbol binario original. Su método no debe usar el método insertar.
        */
        arbolBusqueda2 = new ArbolBinarioBusqueda<>(clavesInOrden, valoresInOrden, clavesPostOrden, valoresPostOrden, false);
        System.out.println("Arbol Binario de Busqueda con Listas");
        System.out.println(arbolBusqueda2);
        //11. Implemente un método privado que reciba un nodo binario de un árbol binario 
        //y que retorne cuál sería su predecesor inorden de la clave de dicho nodo.
        System.out.println("Predecesor inorden de la clave de dicho nodo: "+arbolBusqueda1.predecesor());
        //12. Implemente un método que retorne verdadero si solo hay nodos 
        //completos en el nivel n de un árbol m vias. Falso en caso contrario.
        System.out.println("Verdadero si solo hay nodos completos en el nivel n de un árbol m vias. Falso en caso contrario: "+arbolMvias.nodosCompletosEnNivelN(4));
        
        arbolEnteroCadena1.insertar(12, "Carlos");
        arbolEnteroCadena1.insertar(10, "Carlos");
        arbolEnteroCadena1.insertar(15, "Carlos");
        System.out.println("ArbolBinarioBusquedaEnteroCadena");
        System.out.println(arbolEnteroCadena1);
        
        
        arbolMvias2.insertar(5, "David");
        arbolMvias2.insertar(12, "David");
        arbolMvias2.insertar(3, "David");
        arbolMvias2.insertar(9, "David");
        arbolMvias2.insertar(17, "David");
        System.out.println("Arbol M Vias2");
        System.out.println(arbolMvias2);
        //14. Para un árbol m vías implementar un método que reciba otro árbol 
        //de parámetro y que retorne verdadero si los arboles son similares. Falso en caso contrario.
        System.out.println("Verdadero si los arboles son similares. Falso en caso contrario(árbol m vías): "+arbolMvias.esSimilar(arbolMvias2));
        
        System.out.println("Arbol Binario de Busqueda Comparable");
        arbolBusqueda3.insertar(15, "Pedro");
        arbolBusqueda3.insertar(11, "Pedro");
        arbolBusqueda3.insertar(9,  "Pedro");
        arbolBusqueda3.insertar(13, "Pedro");
        arbolBusqueda3.insertar(8,  "Pedro");
        arbolBusqueda3.insertar(10, "Pedro");
        arbolBusqueda3.insertar(14, "Pedro");
        arbolBusqueda3.insertar(12, "Pedro");
        arbolBusqueda3.insertar(19, "Pedro");
        arbolBusqueda3.insertar(17, "Pedro");
        arbolBusqueda3.insertar(22, "Pedro");
        arbolBusqueda3.insertar(18, "Pedro");
        arbolBusqueda3.insertar(16, "Pedro");
        arbolBusqueda3.insertar(23, "Pedro");
        arbolBusqueda3.insertar(21, "Pedro");
        arbolBusqueda3.insertar(33, "Pedro");
        System.out.println(arbolBusqueda3);
        /*
        15. Para un árbol binario de búsqueda implemente un método que reciba 
        como parámetro otro árbol y que retorne verdadero si los arboles 
        son similares, falso en caso contrario.
        */
        System.out.println("Verdadero si los arboles son similares, falso en caso contrario(árbol binario de búsqueda): "+arbolBusqueda1.sonSimilares(arbolBusqueda3));
          
    }
}
