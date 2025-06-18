package btree;
public class MainBTreeOrden4 {
    public static void main(String[] args) {
        BTree<Integer> arbol = new BTree<>(4);// el ordennnn
//Eliminar una clave del orden 4 
        int[] claves = {
    // Subárbol izquierdo
    12, 25, 80, 142,
    20, 150,
    176, 206,

    // Promover 300 en este punto
    300,

    // Insertar el resto (para que 297 no sea la mediana)
    297,
    380, 395, 412,
    430, 480, 520,
    451, 493, 506, 521, 600
};


        for (int c : claves) {
            arbol.insert(c);
        }

        System.out.println("arbol despues de inserciones:");
        System.out.println(arbol);
        System.out.println("\n=== Eliminando claves ===");

int[] clavesEliminar = {451, 20, 12, 150, 142, 506, 300, 297, 521};
for (int clave : clavesEliminar) {
    System.out.println("Eliminando clave: " + clave);
    arbol.remove(clave);
    System.out.println(arbol); //pa que muestre despues de cada eliminacion 
}
}
}


    
