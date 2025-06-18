package btree;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BTreeOrden4<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTreeOrden4(int orden) {
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

    public void remove(E key) {
        if (isEmpty()) {
            System.out.println("Árbol vacío");
            return;
        }
        removeKey(root, key);
        if (root.count == 0 && root.childs.get(0) != null) {
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

    private void fixUnderflow(BNode<E> parent, int pos) {
        BNode<E> node = parent.childs.get(pos);
        if (pos < parent.count) {
            BNode<E> right = parent.childs.get(pos + 1);
            if (right != null && right.count > (orden - 1) / 2) {
                node.keys.set(node.count, parent.keys.get(pos));
                node.childs.set(node.count + 1, right.childs.get(0));
                node.count++;
                parent.keys.set(pos, right.keys.get(0));
                for (int i = 0; i < right.count - 1; i++) {
                    right.keys.set(i, right.keys.get(i + 1));
                    right.childs.set(i, right.childs.get(i + 1));
                }
                right.childs.set(right.count - 1, right.childs.get(right.count));
                right.keys.set(right.count - 1, null);
                right.childs.set(right.count, null);
                right.count--;
                return;
            }
        }

        if (pos > 0) {
            BNode<E> left = parent.childs.get(pos - 1);
            if (left != null && left.count > (orden - 1) / 2) {
                for (int i = node.count - 1; i >= 0; i--) {
                    node.keys.set(i + 1, node.keys.get(i));
                    node.childs.set(i + 2, node.childs.get(i + 1));
                }
                node.childs.set(1, node.childs.get(0));
                node.keys.set(0, parent.keys.get(pos - 1));
                node.childs.set(0, left.childs.get(left.count));
                node.count++;
                parent.keys.set(pos - 1, left.keys.get(left.count - 1));
                left.keys.set(left.count - 1, null);
                left.childs.set(left.count, null);
                left.count--;
                return;
            }
        }

        if (pos < parent.count) {
            BNode<E> right = parent.childs.get(pos + 1);
            node.keys.set(node.count, parent.keys.get(pos));
            node.count++;
            for (int i = 0; i < right.count; i++) {
                node.keys.set(node.count, right.keys.get(i));
                node.childs.set(node.count, right.childs.get(i));
                node.count++;
            }
            node.childs.set(node.count, right.childs.get(right.count));
            for (int i = pos; i < parent.count - 1; i++) {
                parent.keys.set(i, parent.keys.get(i + 1));
                parent.childs.set(i + 1, parent.childs.get(i + 2));
            }
            parent.keys.set(parent.count - 1, null);
            parent.childs.set(parent.count, null);
            parent.count--;
        } else {
            BNode<E> left = parent.childs.get(pos - 1);
            left.keys.set(left.count, parent.keys.get(pos - 1));
            left.count++;
            for (int i = 0; i < node.count; i++) {
                left.keys.set(left.count, node.keys.get(i));
                left.childs.set(left.count, node.childs.get(i));
                left.count++;
            }
            left.childs.set(left.count, node.childs.get(node.count));
            for (int i = pos - 1; i < parent.count - 1; i++) {
                parent.keys.set(i, parent.keys.get(i + 1));
                parent.childs.set(i + 1, parent.childs.get(i + 2));
            }
            parent.keys.set(parent.count - 1, null);
            parent.childs.set(parent.count, null);
            parent.count--;
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) return "BTree vacío";
        return writeTree(this.root, 0);
    }

    private String writeTree(BNode<E> current, int nivel) {
        if (current == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(nivel)).append("Nivel ").append(nivel).append(" - Nodo ").append(current.idNode).append(": [");
        for (int i = 0; i < current.count; i++) {
            sb.append(current.keys.get(i));
            if (i < current.count - 1) sb.append(", ");
        }
        sb.append("]\\n");
        for (int i = 0; i <= current.count; i++) {
            BNode<E> child = current.childs.get(i);
            if (child != null) {
                sb.append(writeTree(child, nivel + 1));
            }
        }
        return sb.toString();
    }
}

