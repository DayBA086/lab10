package btree;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> {
    protected static int idCounter = 0; // Contador global de nodos
    protected int idNode;               // ID único del nodo

    public ArrayList<E> keys;                // Lista de claves
    protected ArrayList<BNode<E>> childs;       // Hijos del nodo
    protected int count;                        // Número actual de claves

 public BNode(int n) {
    this.idNode = ++idCounter;
    this.keys = new ArrayList<>(n);
    this.childs = new ArrayList<>(n + 1);
    this.count = 0;

    for (int i = 0; i < n; i++) {
        this.keys.add(null);
        this.childs.add(null);
    }
    this.childs.add(null);
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
   public boolean searchNode(E key, int[] pos) {
    pos[0] = 0;
    while (pos[0] < count && key.compareTo(keys.get(pos[0])) > 0) {
        pos[0]++;
    }
    return (pos[0] < count && key.equals(keys.get(pos[0])));
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
