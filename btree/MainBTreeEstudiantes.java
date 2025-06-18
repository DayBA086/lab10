package btree;

import btree.BTree;
public class MainBTreeEstudiantes {
    public static void main(String[] args) {
        BTree<RegistroEstudiante> arbol = new BTree<>(4);
        // insertar estudiantes
        arbol.insert(new RegistroEstudiante(103, "Ana"));
        arbol.insert(new RegistroEstudiante(110, "Luis"));
        arbol.insert(new RegistroEstudiante(101, "Carlos"));
        arbol.insert(new RegistroEstudiante(120, "Lucía"));
        arbol.insert(new RegistroEstudiante(115, "David"));
        arbol.insert(new RegistroEstudiante(125, "Jorge"));
        arbol.insert(new RegistroEstudiante(140, "Camila"));
        arbol.insert(new RegistroEstudiante(108, "Rosa"));
        arbol.insert(new RegistroEstudiante(132, "Ernesto"));
        arbol.insert(new RegistroEstudiante(128, "Denis"));
        arbol.insert(new RegistroEstudiante(145, "Enrique"));
        arbol.insert(new RegistroEstudiante(122, "Karina"));
        arbol.insert(new RegistroEstudiante(108, "Juan")); // clave duplicada

        // mostrar arbol
        System.out.println("Árbol B de estudiantes:");
        System.out.println(arbol);
        // Búsquedas
        System.out.println("\nBuscar estudiante con código 115: " + arbol.buscarNombre(115)); // David
        System.out.println("Buscar estudiante con código 132: " + arbol.buscarNombre(132));   // Ernesto
        System.out.println("Buscar estudiante con código 999: " + arbol.buscarNombre(999));   // No encontrado
        // Eliminar un estudiante (si tienes remove implementado)
        // Insertar nuevo estudiante
        arbol.insert(new RegistroEstudiante(106, "Sara"));

        // Buscar el nuevo
        System.out.println("Buscar estudiante con código 106: " + arbol.buscarNombre(106));   // Sara
    }
}
