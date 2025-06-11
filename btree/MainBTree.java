package btree;

public class MainBTree {
    public static void main(String[] args) {
        // Crear árbol B de orden 4
        BTree<Integer> arbol = new BTree<>(4);

        // Insertar claves
        arbol.insert(30);
        arbol.insert(10);
        arbol.insert(20);
        arbol.insert(50);
        arbol.insert(60);
        arbol.insert(70);
        arbol.insert(40);
        arbol.insert(80);
        arbol.insert(90);

        System.out.println("Árbol después de inserciones:");
        System.out.println(arbol);

        // Buscar claves existentes (con mensaje personalizado desde el método)
        System.out.println("\nhay clave 40?");
        boolean existe40 = arbol.search(40);
        System.out.println("resultado: " + existe40);

        System.out.println("\n hay clave 100?");
        boolean existe100 = arbol.search(100);
        System.out.println("resultado: " + existe100);

        // Eliminar una clave
        System.out.println("\neliminando la clave 30:");
        arbol.remove(30);
        System.out.println(arbol);
        // eliminar una clave inexistente
        System.out.println("\n eliminando clave 999 (no existe):");
        arbol.remove(999);
        //construir arbol desde archivo arbolB.txt
        try {
            System.out.println("\nconstruyendo arbol desde archivo arbolB.txt...");
            BTree<Integer> arbolArchivo = BTree.building_Btree("arbolB.txt");
            System.out.println("arbol cargado:");
            System.out.println(arbolArchivo);
        } catch (ItemNoFound e) {
            System.out.println("Error404: " + e.getMessage());
        }
    }
}
