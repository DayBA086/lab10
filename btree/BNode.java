package btree;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> {
    protected static int idCounter = 0; // Contador global de nodos
    protected int idNode;               // ID único del nodo

    public ArrayList<E> keys;                // Lista de claves
    protected ArrayList<BNode<E>> childs;       // Hijos del nodo
    protected int count;                        // Número actual de claves

    public BNode(int n) {
        this.idNode = ++idCounter; // Asigna un ID único al nodo
        this.keys = new ArrayList<>(n);
        this.childs = new ArrayList<>(n + 1);
        this.count = 0;

        // Inicializa claves y punteros a hijos con null
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
            this.childs.add(null);
        }
        this.childs.add(null); // Hijo extra en la posición n
    }

    // Verifica si el nodo está lleno (usa el máximo de claves permitido)
    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }

    // Verifica si el nodo está vacío
    public boolean nodeEmpty() {
        return count == 0;
    }

    // Busca si una clave existe en este nodo
    public boolean searchNode(E key) {
        for (int i = 0; i < count; i++) {
            if (key.equals(keys.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Nodo " + idNode + ": [");
        for (int i = 0; i < count; i++) {
            sb.append(keys.get(i));
            if (i < count - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
