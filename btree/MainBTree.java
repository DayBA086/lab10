package btree;
public class MainBTree {
    public static void main(String[] args) {
        BTree<Integer> arbol = new BTree<>(5);// el ordennnn
        arbol.insert(100);
        arbol.insert(50);
        arbol.insert(20);
        arbol.insert(70);
        arbol.insert(10);
        arbol.insert(30);
        arbol.insert(80);
        arbol.insert(90);
        arbol.insert(200);
        arbol.insert(25);
        arbol.insert(15);
        arbol.insert(5);
        arbol.insert(65);
        arbol.insert(35);
        arbol.insert(60);
        arbol.insert(18);
        arbol.insert(93);
        arbol.insert(94);


        System.out.println("Árbol después de inserciones:");
        System.out.println(arbol);

        // Buscar claves 
        System.out.println("\nhay clave 40?");
        boolean existe40 = arbol.search(40);
        System.out.println("resultado: " + existe40);
        System.out.println("\n hay clave 100?");
        boolean existe100 = arbol.search(100);
        System.out.println("resultado: " + existe100);
        

          

    


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

    

