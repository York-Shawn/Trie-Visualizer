import java.util.*;

class TrieNode {
    public char val;
    public boolean isWord;
    public int childNum = 0;
    public TrieNode[] children = new TrieNode[26];
    public TrieNode() {}
    TrieNode(char c) {
        TrieNode node = new TrieNode();
        node.val = c;
    }
}

public class Trie {
    private static TrieNode root;
    public Trie() {
        root = new TrieNode();
        root.val = ' ';
    }

    public static void insert(String word) {
        TrieNode ws = root; 
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (ws.children[c - 'a'] == null) { // 没有字节点就创建出来
                ws.children[c - 'a'] = new TrieNode(c);
                ws.childNum++;
            }
            ws = ws.children[c - 'a'];
        }
        ws.isWord = true;
    }

    public static int search(String word) {
        TrieNode ws = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (ws.children[c - 'a'] == null) {
                System.out.println("Word not existed");
                return i;
            }
            ws = ws.children[c - 'a'];
        }
        if (ws.isWord) {
            System.out.println("Word existed");
            return -1;
        }
        else {
            System.out.println("Word not existed");
            return word.length();
        }

    }

    public static int startsWith(String prefix) {
        TrieNode ws = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (ws.children[c - 'a'] == null) {
                System.out.println("Prefix not existed");
                return i;
            }
            ws = ws.children[c - 'a'];
        }
        System.out.println("Prefix existed");
        return -1;
    }

    public static int delete(String word) {
        TrieNode ws = root;
        TrieNode deletedNode = root;
        int j = 0;
        char lastDeletedChar = word.charAt(0);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (ws.childNum != 1 || ws.isWord) { // 记录下子节点数不为1或者是单词结尾的节点
                j = i;
                deletedNode = ws;
                lastDeletedChar = c;
            }
            ws = ws.children[c - 'a'];
        }
        if (ws.isWord&&ws.childNum!=0) {
            ws.isWord = false;
            return -1;
        }
        else { // 删除记录节点后的子节点
            deletedNode.children[lastDeletedChar - 'a'] = null;
            return j;
        }
    }

    private static TrieNode get(TrieNode x, String key, int d) { // 递归查找
        if (x ==  null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d);
        return get(x.children[c-'a'],key,d+1);
    }

    public static List<String> keysWithPrefix(String pre) {
        List<String> q = new ArrayList<>();

        collect(get(root,pre,0),pre,q);
        return q;
    }

    private static void collect(TrieNode x, String pre, List<String> q) {
        if (x == null)
            return;
        if (x.isWord == true) // 如果节点是单词结尾，添加单词
            q.add(pre);
        for (char c = 'a'; c <= 'z'; c++) { // 递归遍历子节点
            collect(x.children[c-'a'],pre + c, q);
        }
    }

    public void recurDelete(String key) {
        root = delete(root, key, 0);
    }

    private TrieNode delete(TrieNode x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length())
            x.isWord = false;
        else {
            char c = key.charAt(d);
            x.children[c - 'a'] = delete(x.children[c - 'a'], key, d + 1);
        }

        if (x.isWord != false)
            return x;
        for (char c = 'a'; c <= 'z'; c++) {
            if (x.children[c - 'a'] != null)
                return x;
        }
        return null;
    }

    public static String longestPrefixof(String s) {
        int length = longSearch(root, s, 0, 0);
        return s.substring(0,length);
    }

    private static int longSearch(TrieNode x, String s, int d, int length) {
        if (x == null)
            return length;
        length = d;
        if (d == s.length())
            return length;
        char c = s.charAt(d);
        return longSearch(x.children[c-'a'], s, d+1, length);
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        insert("app");
        insert("apple");
        insert("add");
        insert("op");
        System.out.println(trie.longestPrefixof("apply"));
    }
}

