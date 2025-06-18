package btree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(E cl) {
        up = false;
        E mediana;
        BNode<E> pnew;
        mediana = push(this.root, cl);
        if (up) {
            pnew = new BNode<E>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) {
        int[] pos = new int[1];
        E mediana;

        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        } else {
            boolean found = current.searchNode(cl, pos);
            if (found) {
                System.out.println("Item duplicado: " + cl);
                up = false;
                return null;
            }

            mediana = push(current.childs.get(pos[0]), cl);

            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }
            return mediana;
        }
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int i, posMdna;

        if (k <= this.orden / 2) {
            posMdna = this.orden / 2;
        } else {
            posMdna = this.orden / 2 + 1;
        }
        nDes = new BNode<E>(this.orden);
        for (i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }
        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;
        if (k <= this.orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }
        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "BTree vacío";
        } else {
            return writeTree(this.root, 0);
        }
    }

    private String writeTree(BNode<E> current, int nivel) {
        if (current == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(nivel));
        sb.append("Nivel ").append(nivel).append(" - Nodo ").append(current.idNode).append(": [");

        for (int i = 0; i < current.count; i++) {
            sb.append(current.keys.get(i));
            if (i < current.count - 1) sb.append(", ");
        }
        sb.append("]\n");

        for (int i = 0; i <= current.count; i++) {
            BNode<E> child = current.childs.get(i);
            if (child != null) {
                sb.append(writeTree(child, nivel + 1));
            }
        }
        return sb.toString();
    }

    public boolean search(E key) {
        return searchNode(this.root, key);
    }

    private boolean searchNode(BNode<E> current, E key) {
        if (current == null) {
            return false;
        }

        for (int i = 0; i < current.count; i++) {
            E currentKey = current.keys.get(i);
            int cmp = key.compareTo(currentKey);

            if (cmp == 0) {
                System.out.println(key + " se encuentra en el nodo " + current.idNode + " en la posición " + i);
                return true;
            } else if (cmp < 0) {
                return searchNode(current.childs.get(i), key);
            }
        }
        return searchNode(current.childs.get(current.count), key);
    }

    public void remove(E key) {
        if (isEmpty()) {
            System.out.println("Árbol vacío");
            return;
        }
        removeKey(root, key);

        if (root.count == 0 && !root.childs.get(0).nodeEmpty()) {
            root = root.childs.get(0);
        } else if (root.count == 0) {
            root = null;
        }
    }

    private void removeKey(BNode<E> node, E key) {
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }

        if (i < node.count && key.equals(node.keys.get(i))) {
            if (node.childs.get(i) == null) {
                for (int j = i; j < node.count - 1; j++) {
                    node.keys.set(j, node.keys.get(j + 1));
                }
                node.keys.set(node.count - 1, null);
                node.count--;
            } else {
                BNode<E> predNode = node.childs.get(i);
                while (predNode.childs.get(predNode.count) != null) {
                    predNode = predNode.childs.get(predNode.count);
                }
                E pred = predNode.keys.get(predNode.count - 1);
                node.keys.set(i, pred);
                removeKey(node.childs.get(i), pred);
            }
        } else {
            if (node.childs.get(i) == null) {
                System.out.println("Clave no encontrada.");
                return;
            }
            removeKey(node.childs.get(i), key);

            if (node.childs.get(i).count < (orden - 1) / 2) {
                fixUnderflow(node, i);
            }
        }
    }

    private void fixUnderflow(BNode<E> node, int i) {
        // Implementar en función a redistribución o fusión
    }

    public static BTree<Integer> building_Btree(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null) throw new ItemNoFound("Archivo vacío");

            int orden = Integer.parseInt(line.trim());
            BTree<Integer> tree = new BTree<>(orden);

            Map<Integer, BNode<Integer>> nodos = new HashMap<>();
            Map<Integer, Integer> niveles = new HashMap<>();
            List<Integer> ordenLectura = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(",");
                int nivel = Integer.parseInt(parts[0].trim());
                int id = Integer.parseInt(parts[1].trim());

                BNode<Integer> node = new BNode<>(orden);
                node.idNode = id;
                node.count = parts.length - 2;

                for (int i = 2; i < parts.length; i++) {
                    int clave = Integer.parseInt(parts[i].trim());
                    node.keys.set(i - 2, clave);
                }

                nodos.put(id, node);
                niveles.put(id, nivel);
                ordenLectura.add(id);

                if (node.count > orden - 1) {
                    throw new ItemNoFound("Nodo " + id + " excede el número máximo de claves.");
                }
            }

            for (int i = 0; i < ordenLectura.size(); i++) {
                int id = ordenLectura.get(i);
                BNode<Integer> node = nodos.get(id);
                int nivel = niveles.get(id);

                if (nivel == 0) {
                    tree.root = node;
                } else {
                    for (int j = i - 1; j >= 0; j--) {
                        int padreID = ordenLectura.get(j);
                        BNode<Integer> padre = nodos.get(padreID);
                        int nivelPadre = niveles.get(padreID);

                        if (nivelPadre == nivel - 1) {
                            for (int k = 0; k <= padre.count; k++) {
                                if (padre.childs.get(k) == null) {
                                    padre.childs.set(k, node);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            return tree;
        } catch (IOException | NumberFormatException e) {
            throw new ItemNoFound("Error en archivo: " + e.getMessage());
        }
    }

    public String buscarNombre(int codigo) {
        if (!(root != null && root.keys.get(0) instanceof RegistroEstudiante)) {
            throw new UnsupportedOperationException("buscarNombre solo funciona con BTree<RegistroEstudiante>");
        }
        return buscarNombreRecursivo((BNode<RegistroEstudiante>) root, codigo);
    }

    @SuppressWarnings("unchecked")
    private String buscarNombreRecursivo(BNode<RegistroEstudiante> node, int codigo) {
        if (node == null) return "No encontrado";

        for (int i = 0; i < node.count; i++) {
            RegistroEstudiante actual = node.keys.get(i);
            if (codigo == actual.getCodigo()) {
                return actual.getNombre();
            } else if (codigo < actual.getCodigo()) {
                return buscarNombreRecursivo((BNode<RegistroEstudiante>) node.childs.get(i), codigo);
            }
        }
        return buscarNombreRecursivo((BNode<RegistroEstudiante>) node.childs.get(node.count), codigo);
    }
}
