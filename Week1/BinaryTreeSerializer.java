
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class BinaryTreeSerializer {
    
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("#,"); 
        } else {
            sb.append(node.val).append(",");  
            serializeHelper(node.left, sb);  
            serializeHelper(node.right, sb); 
        }
    }
    
    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        int[] index = {0};
        return deserializeHelper(nodes, index);
    }
    
    private TreeNode deserializeHelper(String[] nodes, int[] index) {
        if (index[0] >= nodes.length || nodes[index[0]].equals("#")) {
            index[0]++;  
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(nodes[index[0]++]));  
        node.left = deserializeHelper(nodes, index);  
        node.right = deserializeHelper(nodes, index);  
        return node;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        BinaryTreeSerializer serializer = new BinaryTreeSerializer();

        String serializedData = serializer.serialize(root);
        System.out.println("Serialized Tree: " + serializedData);

        TreeNode deserializedRoot = serializer.deserialize(serializedData);
        System.out.println("Deserialized Tree (Pre-order): " + preOrderTraversal(deserializedRoot));
    }

    public static String preOrderTraversal(TreeNode node) {
        if (node == null) {
            return "#";
        }
        return node.val + "," + preOrderTraversal(node.left) + "," + preOrderTraversal(node.right);
    }
}