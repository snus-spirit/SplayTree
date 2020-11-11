import java.util.Objects;

public class Node {
    Integer key;
    Node parent;
    Node left;
    Node right;

    Node(int keyValue) {
        this.key = keyValue;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Node node = (Node) obj;
        return node.key.equals(this.key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" key = ").append(this.key);
        if (this.parent != null) sb.append(" parent ").append(this.parent.key);
        if (this.left != null) sb.append(" left ").append(this.left.key);
        if (this.right != null) sb.append(" right ").append(this.right.key);
        return sb.toString();
    }

    @Override
    public int hashCode(){
        return
                Objects.hash(this.key *
                ((this.parent != null) ? this.parent.key : 1)  *
                ((this.left != null) ? this.left.key : 1) *
                ((this.right != null) ? this.right.key : 1));
    }
}